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

        // 初始化各个面板
        startMenu = new StartMenu(this);
        rankingList = new RankingList(this);

        // 创建卡片面板并设置布局
        cardPanel = new JPanel(new CardLayout(0, 0));

        // 添加卡片
        cardPanel.add(startMenu.getStartPanel(), "START_MENU");
        cardPanel.add(rankingList.getRankPanel(), "RANKING_LIST");

        // 默认显示开始菜单 - 直接获取布局管理器来调用 show
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "START_MENU");

        // 将卡片面板添加到窗口
        frame.add(cardPanel);
    }

    /**
     * 切换到游戏界面
     */
    public void switchToGame() {
        System.out.println("Switching to game...");

        // 重置英雄机单例（恢复HP等状态）
        HeroAircraft.resetHeroAircraft();

        // 创建新的游戏实例
        game = new Game();

        if (mainFrame != null) {
            // 在事件调度线程中执行UI更新
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // 移除旧的内容
                    mainFrame.getContentPane().removeAll();

                    // 设置游戏面板
                    game.setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
                    mainFrame.getContentPane().add(game);

                    // 保持窗口大小不变
                    mainFrame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.revalidate();
                    mainFrame.repaint();

                    System.out.println("Game panel added to frame");

                    // 启动游戏
                    game.action();
                }
            });
        }
    }

    /**
     * 切换到排行榜界面
     */
    public void switchToRankingList() {
        if (mainFrame != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // 移除游戏面板
                    mainFrame.getContentPane().removeAll();

                    // 重新添加卡片面板
                    mainFrame.getContentPane().add(cardPanel);

                    // 刷新排行榜数据
                    if (rankingList != null) {
                        rankingList.refreshData();
                    }

                    // 显示排行榜
                    CardLayout cl = (CardLayout) cardPanel.getLayout();
                    cl.show(cardPanel, "RANKING_LIST");

                    // 恢复窗口大小
                    mainFrame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            });
        }
    }

    /**
     * 切换回开始菜单
     */
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
