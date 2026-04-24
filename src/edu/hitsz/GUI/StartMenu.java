package edu.hitsz.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JPanel StartPanel;
    private JButton Simple;
    private JButton Normal;
    private JButton Hard;

    private GammingMode gammingMode;

    public StartMenu(GammingMode gammingMode) {
        this.gammingMode = gammingMode;

        Simple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到游戏界面（所有难度暂时相同）
                gammingMode.switchToGame();
            }
        });

        Normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到游戏界面（所有难度暂时相同）
                gammingMode.switchToGame();
            }
        });

        Hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到游戏界面（所有难度暂时相同）
                gammingMode.switchToGame();
            }
        });
    }

    public JPanel getStartPanel() {
        return StartPanel;
    }

}
