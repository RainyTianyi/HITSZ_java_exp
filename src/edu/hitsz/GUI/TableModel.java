package edu.hitsz.GUI;

import edu.hitsz.DAO.PlayerScore;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TableModel {
    // 从文件中读取数据，用于创建JTable
    private static final String DATA_FILE_SIMPLE = "out/production/AircraftWar-base/data/player_score_simple.txt";
    private static final String DATA_FILE_NORMAL = "out/production/AircraftWar-base/data/player_score_normal.txt";
    private static final String DATA_FILE_HARD = "out/production/AircraftWar-base/data/player_score_hard.txt";

    private List<Object[]> rowData;
    private String difficulty;

    public TableModel() {
        this("simple");
    }

    public TableModel(String difficulty) {
        this.difficulty = difficulty.toLowerCase();
        this.rowData = new ArrayList<>();
        loadDataFromFile();
    }

    /**
     * 从文件中加载数据
     */
    private void loadDataFromFile() {
        String filePath = getDataFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int rank = 1;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String playerName = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    String time = parts[2].trim();

                    Object[] row = {rank, playerName, score, time};
                    rowData.add(row);
                    rank++;
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件失败: " + e.getMessage());
        }
    }

    /**
     * 获取表格数据
     * @return 二维对象数组，适合JTable使用
     */
    public Object[][] getRowData() {
        return rowData.toArray(new Object[0][]);
    }

    /**
     * 重新加载数据（用于数据更新后刷新）
     */
    public void reload() {
        rowData.clear();
        loadDataFromFile();
    }

    private String getDataFilePath() {
        switch (difficulty) {
            case "simple":
                return DATA_FILE_SIMPLE;
            case "normal":
                return DATA_FILE_NORMAL;
            case "hard":
                return DATA_FILE_HARD;
            default:
                return DATA_FILE_SIMPLE;
        }
    }
}
