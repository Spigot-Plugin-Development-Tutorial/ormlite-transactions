package me.kodysimpson.ormliterelationships.commands;

import me.kodysimpson.ormliterelationships.entities.Guild;
import me.kodysimpson.ormliterelationships.services.GuildService;
import me.kodysimpson.ormliterelationships.services.PlayerService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateGuildOld implements CommandExecutor {

    private final PlayerService playerService;
    private final GuildService guildService;

    public CreateGuildOld(PlayerService playerService, GuildService guildService) {
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

            if (guildService.findByName(name) != null){
                player.sendMessage("A guild with that name already exists!");
                return true;
            }

            //create the new guild
            var guild = new Guild(name);
            guild = guildService.create(guild);

            //here I am purposely throwing an exception to show that when
            // we do not use transactions, we can end up with a guild with no owner.
            // this violates our business rules and is not good.
            if (true){
                throw new RuntimeException("The database exploded.");
            }
            guildPlayer.setGuild(guild);
            playerService.update(guildPlayer);

            player.sendMessage("Guild created! You are now the owner of " + guild.getName() + "!");
        }else{
            sender.sendMessage("Only players can use this command!");
        }

        return true;
    }
}