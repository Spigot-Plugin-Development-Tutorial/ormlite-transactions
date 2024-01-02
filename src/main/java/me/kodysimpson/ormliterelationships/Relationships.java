package me.kodysimpson.ormliterelationships;

import me.kodysimpson.ormliterelationships.commands.CreateAchievements;
import me.kodysimpson.ormliterelationships.commands.CreateGuild;
import me.kodysimpson.ormliterelationships.database.Database;
import me.kodysimpson.ormliterelationships.listeners.JoinListener;
import me.kodysimpson.ormliterelationships.services.AchievementService;
import me.kodysimpson.ormliterelationships.services.GuildService;
import me.kodysimpson.ormliterelationships.services.PlayerService;
import org.bukkit.plugin.java.JavaPlugin;

public final class Relationships extends JavaPlugin {

    private Database database;
    private PlayerService playerService;
    private GuildService guildService;
    private AchievementService achievementService;

    @Override
    public void onEnable() {

        try{
            //Ensure the plugins data folder exists
            if (!getDataFolder().exists()){
                getDataFolder().mkdirs();
            }

            //Create the database connection
            database = new Database(getDataFolder().getAbsolutePath() + "/database.db");

            //create our services
            playerService = new PlayerService(database.getPlayerDao());
            guildService = new GuildService(database.getGuildDao(), database.getPlayerDao());
            achievementService = new AchievementService(database.getAchievementDao(), database.getPlayerAchievementDao());

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to create the DB! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("createguild").setExecutor(new CreateGuild(playerService, guildService));
        getCommand("createachievements").setExecutor(new CreateAchievements(playerService, achievementService));

        getServer().getPluginManager().registerEvents(new JoinListener(playerService), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
