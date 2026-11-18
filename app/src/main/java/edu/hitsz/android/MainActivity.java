package edu.hitsz.android;

import android.app.Activity;
import android.os.Bundle;

import edu.hitsz.android.game.Difficulty;

public class MainActivity extends Activity {
    private BaseGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new BaseGame(this, Difficulty.SIMPLE);
        setContentView(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.resumeGame();
    }

    @Override
    protected void onPause() {
        game.pauseGame();
        super.onPause();
    }
}
