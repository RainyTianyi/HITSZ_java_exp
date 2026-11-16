package edu.hitsz.android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.hitsz.android.game.Difficulty;
import edu.hitsz.android.game.Sprite;

import java.util.EnumMap;
import java.util.Map;

final class ImageManager {
    final Bitmap background;
    private final Map<Sprite, Bitmap> sprites = new EnumMap<>(Sprite.class);

    ImageManager(Resources resources, Difficulty difficulty) {
        int backgroundId = R.drawable.bg;
        if (difficulty == Difficulty.NORMAL) {
            backgroundId = R.drawable.bg2;
        } else if (difficulty == Difficulty.HARD) {
            backgroundId = R.drawable.bg3;
        }
        background = BitmapFactory.decodeResource(resources, backgroundId);

        // 每局只解码一次，渲染循环直接取缓存。nodpi 保留素材的原始像素尺寸。
        load(resources, Sprite.HERO, R.drawable.hero);
        load(resources, Sprite.MOB, R.drawable.mob);
        load(resources, Sprite.ELITE, R.drawable.elite);
        load(resources, Sprite.CRACK, R.drawable.elite_plus);
        load(resources, Sprite.ACE, R.drawable.elite_pro);
        load(resources, Sprite.BOSS, R.drawable.boss);
        load(resources, Sprite.HERO_BULLET, R.drawable.bullet_hero);
        load(resources, Sprite.ENEMY_BULLET, R.drawable.bullet_enemy);
        load(resources, Sprite.BLOOD, R.drawable.prop_blood);
        load(resources, Sprite.FIRE, R.drawable.prop_bullet);
        load(resources, Sprite.FIRE_PLUS, R.drawable.prop_bullet_plus);
        load(resources, Sprite.BOMB, R.drawable.prop_bomb);
        load(resources, Sprite.ICE, R.drawable.prop_freeze);
    }

    private void load(Resources resources, Sprite sprite, int drawableId) {
        sprites.put(sprite, BitmapFactory.decodeResource(resources, drawableId));
    }

    Bitmap get(Sprite sprite) {
        return sprites.get(sprite);
    }
}
