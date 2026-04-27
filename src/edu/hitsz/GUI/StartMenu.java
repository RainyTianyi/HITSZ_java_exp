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
                gammingMode.switchToGame("simple");
            }
        });

        Normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gammingMode.switchToGame("normal");
            }
        });

        Hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gammingMode.switchToGame("hard");
            }
        });
    }

    public JPanel getStartPanel() {
        return StartPanel;
    }

}
