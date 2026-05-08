package edu.hitsz.GUI;

import edu.hitsz.DAO.DaoImpl;
import edu.hitsz.DAO.PlayerScore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingList {
    private JTable RankingTable;
    private JButton deleteButton;
    private JButton backButton;
    private JPanel RankPanel;
    private JScrollPane tableScrollPanel;

    // 从接口中获取数据
    private TableModel tableModel;
    private DefaultTableModel model;

    private GammingMode gammingMode;
    private String currentDifficulty;

    public RankingList(GammingMode gammingMode) {
        this.gammingMode = gammingMode;
        this.currentDifficulty = "simple";
        this.tableModel = new TableModel(currentDifficulty);

        initializeTable();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 返回开始菜单
                gammingMode.switchToStartMenu();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = RankingTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(deleteButton, "请先选择要删除的记录", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int result = JOptionPane.showConfirmDialog(deleteButton, "是否删除该记录？");
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        // 先获取要删除的数据（在删除行之前）
                        String playerName = (String) model.getValueAt(row, 1);
                        int score = (int) model.getValueAt(row, 2);
                        String time = (String) model.getValueAt(row, 3);

                        // 调用DAO接口对数据库进行删除
                        DaoImpl dao = new DaoImpl(currentDifficulty);
                        PlayerScore ps = new PlayerScore(playerName, score, time);
                        dao.delete(ps);

                        // 重新加载数据
                        tableModel.reload();

                        // 清空现有模型的所有行
                        model.setRowCount(0);

                        // 添加新数据
                        Object[][] newData = tableModel.getRowData();
                        for (Object[] rowData : newData) {
                            model.addRow(rowData);
                        }

                        JOptionPane.showMessageDialog(deleteButton, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(deleteButton, "删除失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 初始化表格
     */
    private void initializeTable() {
        tableModel.reload();
        String[] columnNames = {"排名", "玩家名称", "分数", "时间"};
        Object[][] rowData = tableModel.getRowData();

        // 处理空数据情况
        if (rowData == null) {
            rowData = new Object[0][4];
        }

        model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        RankingTable.setModel(model);
        tableScrollPanel.setViewportView(RankingTable);
    }

    /**
     * 刷新数据（公开方法，供外部调用）
     */
    public void refreshData() {
        tableModel.reload();

        // 清空现有模型的所有行
        model.setRowCount(0);

        // 添加新数据
        Object[][] newData = tableModel.getRowData();
        for (Object[] rowData : newData) {
            model.addRow(rowData);
        }
    }

    public JPanel getRankPanel() {
        return RankPanel;
    }

    public void setDifficulty(String difficulty) {
        this.currentDifficulty = difficulty.toLowerCase();
        this.tableModel = new TableModel(currentDifficulty);
        refreshData();
    }
}
