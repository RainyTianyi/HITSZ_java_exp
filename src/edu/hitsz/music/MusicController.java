package edu.hitsz.music;

import java.util.HashMap;
import java.util.Map;

public class MusicController {

    private static final String MUSIC_PATH = "src/videos/";

    private final Map<MusicType, MusicThread> musicThreads;
    private MusicType currentBgm;

    public MusicController() {
        musicThreads = new HashMap<>();
        currentBgm = null;
    }

    public void playMusic(MusicType musicType) {
        String filePath = MUSIC_PATH + musicType.getFilename();
        MusicThread musicThread = new MusicThread(filePath);
        musicThread.start();
        musicThreads.put(musicType, musicThread);
    }

    public void playBgm() {
        stopMusic(currentBgm);
        playMusic(MusicType.BGM);
        currentBgm = MusicType.BGM;
    }

    public void playBossBgm() {
        stopMusic(currentBgm);
        playMusic(MusicType.BGM_BOSS);
        currentBgm = MusicType.BGM_BOSS;
    }

    public void playSoundEffect(MusicType musicType) {
        if (musicType == MusicType.BGM || musicType == MusicType.BGM_BOSS) {
            System.err.println("请使用 playBgm() 或 playBossBgm() 播放背景音乐");
            return;
        }
        playMusic(musicType);
    }

    public void stopMusic(MusicType musicType) {
        if (musicType != null && musicThreads.containsKey(musicType)) {
            MusicThread thread = musicThreads.get(musicType);
            if (thread != null && thread.isPlaying()) {
                thread.stopPlaying();
            }
            musicThreads.remove(musicType);
        }
    }

    public void stopAllMusic() {
        MusicType[] types = musicThreads.keySet().toArray(new MusicType[0]);
        for (MusicType type : types) {
            stopMusic(type);
        }
        musicThreads.clear();
        currentBgm = null;
    }

    public void setLoop(MusicType musicType, boolean loop) {
        if (musicThreads.containsKey(musicType)) {
            MusicThread thread = musicThreads.get(musicType);
            if (thread != null) {
                thread.setLoop(loop);
            }
        }
    }

    public void enableBgmLoop(boolean loop) {
        if (currentBgm != null) {
            setLoop(currentBgm, loop);
        }
    }

    public boolean isPlaying(MusicType musicType) {
        if (musicThreads.containsKey(musicType)) {
            MusicThread thread = musicThreads.get(musicType);
            return thread != null && thread.isPlaying();
        }
        return false;
    }

    public boolean isAnyBgmPlaying() {
        return isPlaying(MusicType.BGM) || isPlaying(MusicType.BGM_BOSS);
    }
}
