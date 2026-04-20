package edu.hitsz.DAO;

public class PlayerScore {
    private String PlayerName;
    private int Score;
    private String Time;

    public PlayerScore(String PlayerName, int Score, String Time)
    {
        this.PlayerName = PlayerName;
        this.Score = Score;
        this.Time = Time;
    }
    public String getPlayerName()
    {
        return PlayerName;
    }
    public int getScore()
    {
        return Score;
    }
    public String getTime()
    {
        return Time;
    }
    public void setPlayerName(String PlayerName)
    {
        this.PlayerName = PlayerName;
    }
    public void setScore(int Score)
    {
        this.Score = Score;
    }
    public void setTime(String Time)
    {
        this.Time = Time;
    }
}
