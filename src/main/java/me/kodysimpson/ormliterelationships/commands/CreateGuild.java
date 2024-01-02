package me.kodysimpson.ormliterelationships.commands;

import me.kodysimpson.ormliterelationships.entities.Guild;
import me.kodysimpson.ormliterelationships.services.GuildService;
import me.kodysimpson.ormliterelationships.services.PlayerService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CreateGuild implements CommandExecutor {

    private final PlayerService playerService;
    private final GuildService guildService;

    public CreateGuild(PlayerService playerService, GuildService guildService) {
        this.playerService = playerService;
        this.guildService = guildService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player player){

            if (args.length == 0){
                player.sendMessage("You must provide a name for your guild!");
                return true;
            }

            String name = args[0];

            //get the guild player
            var guildPlayer = this.playerService.findByUuid(player.getUniqueId().toString());

            if (guildPlayer == null){
                player.sendMessage("Error, you are not in the DB for some reason!");
                return true;
            }

            if (guildPlayer.getGuild() != null){
                player.sendMessage("You are already in a guild!");
                return true;
            }

            try {
                Guild guild = guildService.createGuild(guildPlayer, name);
                if (guild == null) {
                    player.sendMessage("A guild with that name already exists or an error occurred!");
                } else {
                    player.sendMessage("Guild created! You are now the owner of " + guild.getName() + "!");
                }
            } catch (SQLException e) {
                player.sendMessage("An error occurred while creating the guild. Try again later.");
                e.printStackTrace();
            }
        }else{
            sender.sendMessage("Only players can use this command!");
        }

        return true;
    }
}
