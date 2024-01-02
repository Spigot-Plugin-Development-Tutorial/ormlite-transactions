package me.kodysimpson.ormliterelationships.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player_stats")
public class PlayerStats {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private long blocksBroken;

    @DatabaseField(foreign = true, unique = true)
    private GuildPlayer guildPlayer;

    public PlayerStats() {
    }

    public int getId() {
        return id;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(long blocksBroken) {
        this.blocksBroken = blocksBroken;
    }

    public GuildPlayer getGuildPlayer() {
        return guildPlayer;
    }

    public void setGuildPlayer(GuildPlayer guildPlayer) {
        this.guildPlayer = guildPlayer;
    }
}
