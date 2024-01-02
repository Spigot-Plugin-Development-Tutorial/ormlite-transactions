package me.kodysimpson.ormliterelationships.services;

import com.j256.ormlite.dao.Dao;
import me.kodysimpson.ormliterelationships.entities.Achievement;
import me.kodysimpson.ormliterelationships.entities.GuildPlayer;
import me.kodysimpson.ormliterelationships.entities.PlayerAchievement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AchievementService {

    private final Dao<Achievement, Integer> achievementDao;
    private final Dao<PlayerAchievement, Integer> playerAchievementDao;

    public AchievementService(Dao<Achievement, Integer> achievementDao, Dao<PlayerAchievement, Integer> playerAchievementDao) {
        this.achievementDao = achievementDao;
        this.playerAchievementDao = playerAchievementDao;
    }

    public Achievement create(Achievement achievement){
        try {
            achievementDao.create(achievement);
            return achievement;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addAchievementToPlayer(GuildPlayer guildPlayer, Achievement achievement){
        try{
            PlayerAchievement playerAchievement = new PlayerAchievement();
            playerAchievement.setGuildPlayer(guildPlayer);
            playerAchievement.setAchievement(achievement);
            playerAchievement.setDateAchieved(new Date());

            playerAchievementDao.create(playerAchievement);

            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public List<Achievement> getAchievementsForPlayer(GuildPlayer player){

        try{

            List<PlayerAchievement> playerAchievements = playerAchievementDao.queryBuilder()
                    .where().eq("player_id", player.getUuid()).query();

            if (playerAchievements == null){
                return null;
            }

            List<Achievement> achievements = new ArrayList<>();
            for (var playerAchievement : playerAchievements){
                achievements.add(playerAchievement.getAchievement());
            }

            if (achievements.isEmpty()){
                return null;
            }

            return achievements;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    public List<GuildPlayer> getPlayersWithAchievement(Achievement achievement){

        try{

            List<PlayerAchievement> playerAchievements = playerAchievementDao.queryBuilder()
                    .where().eq("achievement_id", achievement.getId()).query();

            if (playerAchievements == null){
                return null;
            }

            //Go through each player achievement and get the player object
            List<GuildPlayer> players = new ArrayList<>();
            for (PlayerAchievement playerAchievement : playerAchievements){
                players.add(playerAchievement.getGuildPlayer());
            }

            if (players.isEmpty()){
                return null;
            }

            return players;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
