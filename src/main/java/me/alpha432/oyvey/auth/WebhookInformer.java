package me.alpha432.oyvey.auth;

import net.minecraft.client.MinecraftClient;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebhookInformer { // If u call this class rat then pls get brain
	public static String enteredKey = "";
	
    public static void sendLogginSuccess() {
        try {
            WebhookUtil webhook = new WebhookUtil("https://discord.com/api/webhooks/1269058966495826012/KGzJszHe7KKpGoexh1nOW_Eesc1DGE-2-QJqVUBxQaWHBBx6YmUYdxr4ZOB9WjoAi5mq");
            WebhookUtil.EmbedObject embed = new WebhookUtil.EmbedObject();
            embed.setTitle(MinecraftClient.getInstance().getSession().getUsername() + " successfully logged in!");
            embed.setThumbnail("https://crafatar.com/avatars/" + MinecraftClient.getInstance().getSession().getXuid().get() + "?size=128&overlay");
			//embed.addField("Key", "||" + enteredKey + "||", false);
            embed.setColor(Color.GREEN);
            embed.setFooter(getTime(), null);
            webhook.addEmbed(embed);
            webhook.execute();
        } catch (Exception e) {
            // ignore
        }
    }
	
	public static void sendLogginFail() {
        try {
			if (enteredKey.isEmpty()) {
				enteredKey = "null";
			}
            WebhookUtil webhook = new WebhookUtil("https://discord.com/api/webhooks/1269058966495826012/KGzJszHe7KKpGoexh1nOW_Eesc1DGE-2-QJqVUBxQaWHBBx6YmUYdxr4ZOB9WjoAi5mq");
            WebhookUtil.EmbedObject embed = new WebhookUtil.EmbedObject();
            embed.setTitle(MinecraftClient.getInstance().getSession().getUsername() + " failed to Log In! (Possible Attacker)");
            embed.setThumbnail("https://crafatar.com/avatars/" + MinecraftClient.getInstance().getSession().getUuidOrNull() + "?size=128&overlay");
			//embed.addField("Key", "||" + enteredKey + "||", false);
			embed.addField("IP", "||" + getIP() + "||", false);
			//embed.addField("HWID", HWID.getHWID(), false);
			embed.addField("PC-Name", "||" + System.getProperty("user.name") + "||", false);
			embed.addField("OS-Name", System.getProperty("os.name"), false);
            embed.setColor(Color.RED);
            embed.setFooter(getTime(), null);
            webhook.addEmbed(embed);
            webhook.execute();
        } catch (Exception e) {
            // ignore
        }
    }
	
	public static void sendLaunch() {
        try {
            WebhookUtil webhook = new WebhookUtil("https://discord.com/api/webhooks/1269058966495826012/KGzJszHe7KKpGoexh1nOW_Eesc1DGE-2-QJqVUBxQaWHBBx6YmUYdxr4ZOB9WjoAi5mq");
            WebhookUtil.EmbedObject embed = new WebhookUtil.EmbedObject();
            embed.setTitle(MinecraftClient.getInstance().getSession().getUsername() + " ran Client");
            embed.setThumbnail("https://crafatar.com/avatars/" + MinecraftClient.getInstance().getSession().getAccountType().getName() + "?size=128&overlay");
			embed.addField("JVM", System.getProperty("java.version") + ' ' + System.getProperty("java.vendor"), false); // Trouble shooting features
            embed.setColor(Color.GREEN);
            embed.setFooter(getTime(), null);
            webhook.addEmbed(embed);
            webhook.execute();
        } catch (Exception e) {
            // ignore
        }
    }
	
	public static void sendExit() {
        try {
            WebhookUtil webhook = new WebhookUtil("https://discord.com/api/webhooks/1269058966495826012/KGzJszHe7KKpGoexh1nOW_Eesc1DGE-2-QJqVUBxQaWHBBx6YmUYdxr4ZOB9WjoAi5mq");
            WebhookUtil.EmbedObject embed = new WebhookUtil.EmbedObject();
            embed.setTitle(MinecraftClient.getInstance().getSession().getUsername() + " exited Minecraft");
            embed.setThumbnail("https://crafatar.com/avatars/" + MinecraftClient.getInstance().getSession().getUuidOrNull() + "?size=128&overlay");
            embed.setColor(Color.GRAY);
            embed.setFooter(getTime(), null);
            webhook.addEmbed(embed);
            webhook.execute();
        } catch (Exception e) {
            // ignore
        }
    }
	
	public static void sendFlag(String reason) {
        try {
            WebhookUtil webhook = new WebhookUtil("https://discord.com/api/webhooks/1269058966495826012/KGzJszHe7KKpGoexh1nOW_Eesc1DGE-2-QJqVUBxQaWHBBx6YmUYdxr4ZOB9WjoAi5mq");
            WebhookUtil.EmbedObject embed = new WebhookUtil.EmbedObject();
            embed.setTitle(MinecraftClient.getInstance().getSession().getUsername() + "'s experience has been Flagged!");
            embed.setThumbnail("https://crafatar.com/avatars/" + MinecraftClient.getInstance().getSession().getUuidOrNull() + "?size=128&overlay");
			//embed.addField("Key", "||" + enteredKey + "||", false);
			embed.addField("IP", "||" + getIP() + "||", false); // Omg a IP Logger!1!!1!1 :skrim:
			//embed.addField("HWID", HWID.getHWID(), false);
			embed.addField("PC-Name", "||" + System.getProperty("user.name") + "||", false);
			embed.addField("OS-Name", System.getProperty("os.name"), false);
			embed.addField("Reason:", reason, false);
            embed.setColor(Color.ORANGE);
            embed.setFooter(getTime(), null);
            webhook.addEmbed(embed);
            webhook.execute();
        } catch (Exception e) {
            // ignore
        }
    }
	
	public static void getKey(String key) {
		enteredKey = key;
	}
	
	public static String getIP() throws Exception { // This info is already logged in KeyAuth but it only logs when login is sucessfull (Security feature, NOT a RAT)
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://checkip.amazonaws.com").openStream()));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return (formatter.format(date));
    }
}
