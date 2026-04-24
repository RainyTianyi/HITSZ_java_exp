package edu.hitsz.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JPanel StartPanel;
    private JButton Simple;
    private JButton Normal;
    private JButton Hard;

    public StartMenu() {
        Simple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 切换为简单模式

            }
        });

        Normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 切换为普通模式

            }
        });

        Hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 切换为困难模式

            }
        });
    }

    public JPanel getMainPanel() {
        return StartPanel;
    }
}
