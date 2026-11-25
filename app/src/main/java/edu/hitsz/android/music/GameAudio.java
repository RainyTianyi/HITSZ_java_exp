package edu.hitsz.android.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseBooleanArray;

import edu.hitsz.android.R;
import edu.hitsz.android.game.GameEngine.SoundCue;

/** 一局内的音频资源和播放状态，由 GameActivity 在主线程管理。 */
public final class GameAudio {
    private final MediaPlayer normalMusic;
    private final MediaPlayer bossMusic;
    private final SoundPool effects;
    private final SparseBooleanArray loaded = new SparseBooleanArray();
    private final int bulletHit;
    private final int bombExplosion;
    private final int getSupply;
    private final int gameOver;

    private boolean resumed;
    private boolean bossActive;
    private boolean roundOver;

    public GameAudio(Context context, boolean enabled) {
        if (!enabled) {
            normalMusic = null;
            bossMusic = null;
            effects = null;
            bulletHit = bombExplosion = getSupply = gameOver = 0;
            return;
        }

        AudioAttributes musicAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        normalMusic = createMusic(context, R.raw.bgm, musicAttributes);
        bossMusic = createMusic(context, R.raw.bgm_boss, musicAttributes);

        // 参考教师 SoundPool 示例，在开局预加载短音效，播放时直接使用音效 ID。
        AudioAttributes effectAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        effects = new SoundPool.Builder()
                .setAudioAttributes(effectAttributes)
                .setMaxStreams(4)
                .build();
        effects.setOnLoadCompleteListener((pool, id, status) -> loaded.put(id, status == 0));
        bulletHit = effects.load(context, R.raw.bullet_hit, 1);
        bombExplosion = effects.load(context, R.raw.bomb_explosion, 1);
        getSupply = effects.load(context, R.raw.get_supply, 1);
        gameOver = effects.load(context, R.raw.game_over, 1);
    }

    private static MediaPlayer createMusic(Context context, int resource, AudioAttributes attributes) {
        MediaPlayer player = MediaPlayer.create(context, resource, attributes,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        if (player != null) {
            player.setLooping(true);
            player.setVolume(0.5f, 0.5f);
        }
        return player;
    }

    public void onCue(SoundCue cue) {
        switch (cue) {
            case BOSS_ENTER:
                bossActive = true;
                updateMusic();
                break;
            case BOSS_EXIT:
                bossActive = false;
                updateMusic();
                if (bossMusic != null) {
                    bossMusic.seekTo(0);
                }
                break;
            case BULLET_HIT:
                play(bulletHit, 1);
                break;
            case BOMB_EXPLOSION:
                play(bombExplosion, 2);
                break;
            case GET_SUPPLY:
                play(getSupply, 1);
                break;
        }
    }

    public void resume() {
        resumed = true;
        updateMusic();
        if (effects != null && !roundOver) {
            effects.autoResume();
        }
    }

    public void pause() {
        resumed = false;
        updateMusic();
        if (effects != null) {
            effects.autoPause();
        }
    }

    public void finishRound() {
        roundOver = true;
        updateMusic();
        if (effects != null) {
            effects.autoPause();
            play(gameOver, 3);
        }
    }

    private void updateMusic() {
        MediaPlayer next = resumed && !roundOver ? (bossActive ? bossMusic : normalMusic) : null;
        // Boss 登场时先暂停普通 BGM，击毁后从原进度继续正常 BGM。
        if (normalMusic != next) {
            pauseMusic(normalMusic);
        }
        if (bossMusic != next) {
            pauseMusic(bossMusic);
        }
        if (next != null && !next.isPlaying()) {
            next.start();
        }
    }

    private static void pauseMusic(MediaPlayer player) {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    private void play(int soundId, int priority) {
        if (resumed && effects != null && loaded.get(soundId)) {
            effects.play(soundId, 1f, 1f, priority, 0, 1f);
        }
    }

    public void release() {
        // 离开游戏页面后释放两条 BGM 和短音效池。
        if (effects != null) {
            effects.setOnLoadCompleteListener(null);
            effects.release();
        }
        if (normalMusic != null) {
            normalMusic.release();
        }
        if (bossMusic != null) {
            bossMusic.release();
        }
    }
}
