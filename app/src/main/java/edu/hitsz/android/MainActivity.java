package edu.hitsz.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import edu.hitsz.android.game.Difficulty;

public class MainActivity extends Activity {
    private static final String SOUND_ENABLED = "sound_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        Switch sound = findViewById(R.id.sound_enabled);
        sound.setChecked(settings.getBoolean(SOUND_ENABLED, true));
        sound.setOnCheckedChangeListener((button, enabled) ->
                settings.edit().putBoolean(SOUND_ENABLED, enabled).apply());

        // 按钮通过 Intent 传入本局难度和声音设置，游戏页面据此创建新对局。
        findViewById(R.id.easy).setOnClickListener(v -> openGame(Difficulty.SIMPLE, sound));
        findViewById(R.id.normal).setOnClickListener(v -> openGame(Difficulty.NORMAL, sound));
        findViewById(R.id.hard).setOnClickListener(v -> openGame(Difficulty.HARD, sound));
    }

    private void openGame(Difficulty difficulty, Switch sound) {
        startActivity(GameActivity.createIntent(this, difficulty, sound.isChecked()));
    }
}
