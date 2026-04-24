package edu.hitsz.application;

import edu.hitsz.GUI.GammingMode;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    private static GammingMode gammingMode;

    public static void main(String[] args) {
        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建GammingMode实例
        gammingMode = new GammingMode(frame);

        frame.setVisible(true);
    }

    /**
     * 获取GammingMode实例（供Game类调用）
     */
    public static GammingMode getGammingMode() {
        return gammingMode;
    }
}
