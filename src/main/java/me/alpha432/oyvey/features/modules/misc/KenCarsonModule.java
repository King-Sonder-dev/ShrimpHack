package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.fabricmc.loader.api.FabricLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

public class KenCarsonModule extends Module {

    private static final Path OYVEY_PATH = FabricLoader.getInstance().getGameDir().resolve("oyvey");
    private final String folderName = OYVEY_PATH.resolve("KenCarsonImages").toString();
    private final String query = "Ken Carson";
    public Setting<Integer> download = this.register(new Setting<>("Download", 10, 0, 100));

    public KenCarsonModule() {
        super("KenCarson", "", Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        createFolder();
        downloadImages();
        this.disable();
    }

    private void createFolder() {
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs();
            Command.sendMessage("Folder '" + folderName + "' created.");
        } else {
            Command.sendMessage("Folder '" + folderName + "' already exists.");
        }
    }

    private void downloadImages() {
        try {
            String searchUrl = "https://www.google.com/search?q=" + query.replace(" ", "+") + "&tbm=isch";
            Document doc = Jsoup.connect(searchUrl).userAgent("Mozilla/5.0").get();
            Elements images = doc.select("img[src]");

            int count = 0;
            for (Element img : images) {
                if (count >= download.getValue()) break;
                String imgUrl = img.attr("src");
                if (imgUrl.startsWith("http")) {
                    downloadImage(imgUrl, folderName + "/kencarson_" + (count + 1) + ".jpg");
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadImage(String imgUrl, String fileName) {
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            InputStream in = connection.getInputStream();
            FileOutputStream out = new FileOutputStream(new File(fileName));

            byte[] buffer = new byte[4096];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }

            out.close();
            in.close();
            Command.sendMessage("Downloaded image " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}