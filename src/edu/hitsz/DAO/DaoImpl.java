package edu.hitsz.DAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl {
    private List<PlayerScore> PSList;
    private String dataFile;

    public DaoImpl(String difficulty) {
        this.PSList = new ArrayList<PlayerScore>();

        switch (difficulty.toLowerCase()) {
            case "simple":
                this.dataFile = "out/production/AircraftWar-base/data/player_score_simple.txt";
                break;
            case "normal":
                this.dataFile = "out/production/AircraftWar-base/data/player_score_normal.txt";
                break;
            case "hard":
                this.dataFile = "out/production/AircraftWar-base/data/player_score_hard.txt";
                break;
            default:
                this.dataFile = "out/production/AircraftWar-base/data/player_score_simple.txt";
                break;
        }

        read();
    }

    public List<PlayerScore> getPSList()
    {
        // 从文件中读取数据库数据
        read();
        // 对数据库数据按照分数大小进行排序（从高到低）
        this.PSList.sort((PlayerScore a, PlayerScore b) -> b.getScore() - a.getScore());
        // 将数据库数据写入文件
        write();
        return this.PSList;
    }

    public void update(PlayerScore ps)
    {
        this.PSList.add(ps);
        // 对数据库数据按照分数大小进行排序（从高到低）
        this.PSList.sort((PlayerScore a, PlayerScore b) -> b.getScore() - a.getScore());
        // 将数据库数据写入文件
        write();
    }

    public void delete(PlayerScore ps)
    {
        this.PSList.remove(ps);
        // 将数据库数据写入文件
        write();
    }

    // 从文件中读取数据
    private void read()
    {
        File parentDir = new File(dataFile).getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        File file = new File(dataFile);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            this.PSList.clear();
            String line;
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
                    this.PSList.add(new PlayerScore(playerName, score, time));
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件失败: " + e.getMessage());
        }
    }

    // 将数据写入文件 out/production/AircraftWar-base/data/player_score.txt
    // 如果文件已经存在，则覆盖
    private void write()
    {
        try {
            File file = new File(dataFile);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (PlayerScore ps : this.PSList) {
                    writer.println(ps.getPlayerName() + "," + ps.getScore() + "," + ps.getTime());
                }
            }
        } catch (IOException e) {
            System.err.println("写入文件失败: " + e.getMessage());
        }
    }
}