package me.kodysimpson.ormliterelationships.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "guilds")
public class Guild {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private String tag;

    @DatabaseField(canBeNull = true)
    private String description;

    @ForeignCollectionField(foreignFieldName = "guild")
    private ForeignCollection<GuildPlayer> members;

    public Guild() {
    }

    public Guild(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ForeignCollection<GuildPlayer> getMembers() {
        return members;
    }
}
