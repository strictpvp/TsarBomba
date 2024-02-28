package me.reich.tsarbomba.utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.minecraft.client.MinecraftClient;
import org.apache.commons.codec.digest.DigestUtils;

import static me.reich.tsarbomba.TsarBomba.VERSION;

public class Hwid {
    public static boolean checkHWID() {
        String hwid = getHWID();
        try {
            URL url = new URL("https://pastebin.com/raw/gvCFjrBf");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(hwid)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getHWID() {
        return DigestUtils.sha3_256Hex(DigestUtils.sha512Hex(String.valueOf(
            System.getenv("os")) +
            System.getenv("PROCESSOR_IDENTIFIER") +
            System.getenv("PROCESSOR_ARCHITECTURE") +
            System.getenv("PROCESSOR_ARCHITEW6432") +
            System.getenv("NUMBER_OF_PROCESSORS")
        ));
    }

    public static void sendWebhook() throws IOException {
        Webhook webhook = new Webhook("https://discord.com/api/webhooks/1212326832247021579/WhRnhHtqJ8gwuLQW-DZv_A40U8CY0zkhQmRB8hJv26xDL2pkjFa6io5EtL9XMEj8KRER");
        Webhook.EmbedObject embed = new Webhook.EmbedObject();
        embed.setTitle("Name: " + MinecraftClient.getInstance().getSession().getUsername() + " - Version: " + VERSION);
        embed.setThumbnail("https://crafatar.com/avatars/" + MinecraftClient.getInstance().getSession().getUuidOrNull() + "?size=128&overlay");
        embed.setDescription("HWID: " + getHWID());

        if(checkHWID()){
            embed.setColor(Color.GREEN);
        } else {
            embed.setColor(Color.RED);
        }

        embed.setFooter(getTime(), null);
        webhook.addEmbed(embed);

        webhook.execute();
    }

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return (formatter.format(date));
    }
}


