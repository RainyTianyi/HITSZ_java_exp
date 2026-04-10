package edu.hitsz.EnemyFactory;

import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class AceEnemyFactory implements EnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft() {
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ACE_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2);
        int speedX = (int) (Math.random() * 10 - 5);
        int speedY = 8;
        int hp = 50;
        return new AceEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
