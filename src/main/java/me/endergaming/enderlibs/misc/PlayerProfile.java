package me.endergaming.enderlibs.misc;

import java.util.UUID;

public class PlayerProfile {
    private final String name;
    private final UUID uniqueId;

    public PlayerProfile(String name, UUID uuid) {
        this.name = name;
        this.uniqueId = uuid;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public String getName() {
        return this.name;
    }
}
