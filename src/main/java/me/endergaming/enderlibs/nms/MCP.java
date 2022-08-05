package me.endergaming.enderlibs.nms;

public enum MCP {
    GAME_PROFILE("com.mojang.authlib.GameProfile"),
    I_CHAT_BASE_COMPONENT("net.minecraft.network.chat.IChatBaseComponent"),
    PACKET("net.minecraft.network.protocol.Packet"),
    PACKET_PLAY_OUT_PLAYER_INFO("net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo"),
    ENUM_PLAYER_INFO_ACTION("net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.EnumPlayerInfoAction"),
    PLAYER_INFO("net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.PlayerInfoData"),
    PLAYER_CONNECTION("net.minecraft.server.network.PlayerConnection"),
    ENTITY_HUMAN("net.minecraft.world.entity.player.EntityHuman"),
    ENUM_GAMEMODE("net.minecraft.world.level.EnumGamemode");

    private final String path;

    MCP(final String path) {
        this.path = path;
    }

    public String get() {
        return this.path;
    }
}
