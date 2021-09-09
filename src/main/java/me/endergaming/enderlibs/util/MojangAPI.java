package me.endergaming.enderlibs.util;

import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MojangAPI {
    @Nullable
    public static UUID getMojangPlayerUUID(String name) {
        UUID playerUUID = null;
        try {
            URL enjinurl = getUrl(name);
            HttpURLConnection con = (HttpURLConnection) enjinurl.openConnection();
            con.setReadTimeout(5000);
            con.setConnectTimeout(5000);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", "Mozilla/4.0");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            InputStream in = con.getInputStream();
            String json = parseInput(in);
            if (!json.equals("")) {
                JSONParser parser = new JSONParser();
                JSONObject result = (JSONObject) parser.parse(json);
                String id = result.get("id").toString();
                if (id.length() == 32) {
                    id = id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32);
                }
                if (id.length() == 36) {
                    playerUUID = UUID.fromString(id);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return playerUUID;
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
