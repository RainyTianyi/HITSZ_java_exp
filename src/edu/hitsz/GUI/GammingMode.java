package edu.hitsz.GUI;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.HeroAircraft;

import javax.swing.*;
import java.awt.*;

public class GammingMode {
    private JPanel cardPanel;

    private StartMenu startMenu;
    private RankingList rankingList;
    private Game game;
    private JFrame mainFrame;

    public GammingMode(JFrame frame) {
        this.mainFrame = frame;

        startMenu = new StartMenu(this);
        rankingList = new RankingList(this);

        cardPanel = new JPanel(new CardLayout(0, 0));

        cardPanel.add(startMenu.getStartPanel(), "START_MENU");
        cardPanel.add(rankingList.getRankPanel(), "RANKING_LIST");

        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "START_MENU");

        frame.add(cardPanel);
    }

    public void switchToGame(String difficulty) {
        System.out.println("Switching to game with difficulty: " + difficulty);

        HeroAircraft.resetHeroAircraft();

        game = new Game(difficulty);

        if (mainFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainFrame.getContentPane().removeAll();

                    game.setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
                    mainFrame.getContentPane().add(game);

                    mainFrame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.revalidate();
                    mainFrame.repaint();

                    System.out.println("Game panel added to frame");

                    game.action();
                }
            });
        }
    }

    public void switchToRankingList() {
        if (mainFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainFrame.getContentPane().removeAll();

                    mainFrame.getContentPane().add(cardPanel);

                    if (rankingList != null) {
                        rankingList.refreshData();
                    }

                    CardLayout cl = (CardLayout) cardPanel.getLayout();
                    cl.show(cardPanel, "RANKING_LIST");

                    mainFrame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            });
        }
    }

    public void switchToStartMenu() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "START_MENU");
            }
        });
    }

    public StartMenu getStartMenu() {
        return startMenu;
    }

    public RankingList getRankingList() {
        return rankingList;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

}
