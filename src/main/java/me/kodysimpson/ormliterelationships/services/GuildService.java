package me.kodysimpson.ormliterelationships.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.misc.TransactionManager;
import me.kodysimpson.ormliterelationships.entities.Guild;
import me.kodysimpson.ormliterelationships.entities.GuildPlayer;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

public class GuildService {

    private final Dao<Guild, Integer> guildDao;
    private final Dao<GuildPlayer, String> playerDao;

    public GuildService(Dao<Guild, Integer> guildDao, Dao<GuildPlayer, String> playerDao) {
        this.guildDao = guildDao;
        this.playerDao = playerDao;
    }

    public Guild create(Guild guild){
        try {
            guildDao.create(guild);
            return guild;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Guild createGuildFromPlayer(GuildPlayer player, String name) throws SQLException {
        return TransactionManager.callInTransaction(guildDao.getConnectionSource(), new Callable<Guild>() {
            @Override
            public Guild call() throws Exception {

                if (findByName(name) != null){
                    return null;
                }

                Guild guild = new Guild(name);
                guild = create(guild);

                //add the player
                player.setGuild(guild);
                playerDao.update(player);

                return guild;
            }
        });
    }

    public Guild findByName(String name){
        try{
            return guildDao.queryBuilder().where()
                    .eq("name", name).queryForFirst();
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public Guild findById(int id){
        try {
            return guildDao.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Guild> findAll(){
        try {
            return guildDao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Guild update(Guild guild){
        try {
            guildDao.update(guild);
            return guild;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteById(int id){
        try {
            guildDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ForeignCollection<GuildPlayer> getGuildMembers(int guildId){
        try{
            var guild = guildDao.queryForId(guildId);
            if (guild == null) return null;
            return guild.getMembers();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
