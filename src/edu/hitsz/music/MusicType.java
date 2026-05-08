package edu.hitsz.music;

public enum MusicType {
    BGM("bgm.wav"),
    BGM_BOSS("bgm_boss.wav"),
    BOMB_EXPLOSION("bomb_explosion.wav"),
    BULLET_HIT("bullet_hit.wav"),
    GAME_OVER("game_over.wav"),
    GET_SUPPLY("get_supply.wav");

    private final String filename;

    MusicType(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
