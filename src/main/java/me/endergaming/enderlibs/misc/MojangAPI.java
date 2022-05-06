package me.endergaming.enderlibs.misc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MojangAPI {
    @Nullable
    public static PlayerProfile getPlayerProfile(String username) {
        PlayerProfile profile = null;
        try {
            UUID playerUUID = null;
            String name = "";
            // Create URL
            URL enjinurl = getUrl(username);
            HttpURLConnection con = (HttpURLConnection) enjinurl.openConnection();
            con.setReadTimeout(5000);
            con.setConnectTimeout(5000);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", "Mozilla/4.0");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            InputStream in = con.getInputStream();
            String json = parseInput(in);
            // Parse api return
            if (!json.equals("")) {
                Gson gson = new Gson();
                JsonObject result = gson.fromJson(json, JsonObject.class);
                String id = result.get("id").toString();
                name = result.get("name").toString();
                if (id.length() == 32) {
                    id = id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32);
                }
                if (id.length() == 36) {
                    playerUUID = UUID.fromString(id);
                }

                if (!name.isBlank() && playerUUID != null) {
                    profile = new PlayerProfile(name, playerUUID);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return profile;
    }

    private static URL getUrl(String name) throws Throwable {
        return new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
    }

    public static String parseInput(InputStream in) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = in.read(buffer);
        StringBuilder builder = new StringBuilder();
        while (bytesRead > 0) {
            builder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            bytesRead = in.read(buffer);
        }
        return builder.toString();
    }
}
