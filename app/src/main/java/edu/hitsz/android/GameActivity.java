package edu.hitsz.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import edu.hitsz.android.game.Difficulty;

public class GameActivity extends Activity {
    private static final String EXTRA_DIFFICULTY = "difficulty";
    static final String EXTRA_SOUND = "sound";
    private static final String STATE_SCORE = "result_score";
    private static final int NO_RESULT = -1;
    private static final int GAME_OVER = 1;

    private BaseGame game;
    private Difficulty difficulty;
    private int resultScore = NO_RESULT;
    private boolean resumed;
    private AlertDialog resultDialog;

    // 参考 HandlerDemo。绘制线程传递最终分数，弹窗只在主线程显示。
    private final Handler mainHandler = new Handler(Looper.getMainLooper(), message -> {
        if (message.what == GAME_OVER && !isFinishing()) {
            resultScore = message.arg1;
            showResult();
            return true;
        }
        return false;
    });

    static Intent createIntent(Context context, Difficulty difficulty, boolean soundEnabled) {
        return new Intent(context, GameActivity.class)
                .putExtra(EXTRA_DIFFICULTY, difficulty.name())
                .putExtra(EXTRA_SOUND, soundEnabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String selected = getIntent().getStringExtra(EXTRA_DIFFICULTY);
        difficulty = selected == null ? Difficulty.SIMPLE : Difficulty.valueOf(selected);
        if (savedInstanceState != null) {
            resultScore = savedInstanceState.getInt(STATE_SCORE, NO_RESULT);
        }

        game = new BaseGame(this, difficulty,
                score -> mainHandler.obtainMessage(GAME_OVER, score, 0).sendToTarget());
        FrameLayout root = new FrameLayout(this);
        root.addView(game, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        root.setOnApplyWindowInsetsListener((view, insets) -> {
            Insets safe = insets.getInsets(
                    WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
            view.setPadding(safe.left, safe.top, safe.right, safe.bottom);
            return WindowInsets.CONSUMED;
        });
        setContentView(root);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumed = true;
        if (resultScore == NO_RESULT) {
            game.resumeGame();
        } else {
            showResult();
        }
    }

    @Override
    protected void onPause() {
        resumed = false;
        game.pauseGame();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SCORE, resultScore);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mainHandler.removeCallbacksAndMessages(null);
        if (resultDialog != null) {
            resultDialog.dismiss();
        }
        super.onDestroy();
    }

    private void showResult() {
        if (!resumed || resultDialog != null) {
            return;
        }
        int difficultyLabel;
        switch (difficulty) {
            case HARD:
                difficultyLabel = R.string.hard;
                break;
            case NORMAL:
                difficultyLabel = R.string.normal;
                break;
            default:
                difficultyLabel = R.string.easy;
                break;
        }
        resultDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.game_over)
                .setMessage(getString(R.string.game_result, getString(difficultyLabel), resultScore))
                .setCancelable(false)
                .setNegativeButton(R.string.back_to_menu, (dialog, which) -> finish())
                .setPositiveButton(R.string.play_again, (dialog, which) -> {
                    startActivity(new Intent(getIntent()));
                    finish();
                })
                .show();
    }
}
