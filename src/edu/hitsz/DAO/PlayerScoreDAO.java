package edu.hitsz.DAO;

import java.util.List;

public interface PlayerScoreDAO {
    List<PlayerScore> getAll();
    void update(PlayerScore ps);
    void delete(PlayerScore ps);
}
