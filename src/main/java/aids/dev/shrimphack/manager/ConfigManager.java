package aids.dev.shrimphack.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.Feature;
import aids.dev.shrimphack.features.settings.Bind;
import aids.dev.shrimphack.features.settings.EnumConverter;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.util.traits.Jsonable;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {
    private static final Path Shrimphack_PATH = FabricLoader.getInstance().getGameDir().resolve("Shrimphack");
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create();
    private final List<Jsonable> jsonables = List.of((Jsonable) Shrimphack.friendManager, Shrimphack.moduleManager, Shrimphack.commandManager);

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setValueFromJson(Feature feature, Setting setting, JsonElement element) {
        String str;
        switch (setting.getType()) {
            case "Boolean" -> {
                setting.setValue(element.getAsBoolean());
            }
            case "Double" -> {
                setting.setValue(element.getAsDouble());
            }
            case "Float" -> {
                setting.setValue(element.getAsFloat());
            }
            case "Integer" -> {
                setting.setValue(element.getAsInt());
            }
            case "String" -> {
                str = element.getAsString();
                setting.setValue(str.replace("_", " "));
            }
            case "Bind" -> {
                setting.setValue(new Bind(element.getAsInt()));
            }
            case "Enum" -> {
                try {
                    EnumConverter converter = new EnumConverter(((Enum) setting.getValue()).getClass());
                    Enum value = converter.doBackward(element);
                    setting.setValue((value == null) ? setting.getDefaultValue() : value);
                } catch (Exception exception) {
                }
            }
            default -> {
                Shrimphack.LOGGER.error("Unknown Setting type for: " + feature.getName() + " : " + setting.getName());
            }
        }
    }

    public void load() {
        Shrimphack.LOGGER.info("loading config");
        if (!Shrimphack_PATH.toFile().exists()) Shrimphack_PATH.toFile().mkdirs();
        for (Jsonable jsonable : jsonables) {
            try {
                Shrimphack.LOGGER.info("loading: " + jsonable.getFileName());
                String read = Files.readString(Shrimphack_PATH.resolve(jsonable.getFileName()));
                jsonable.fromJson(JsonParser.parseString(read));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        Shrimphack.LOGGER.info("saving config");
        if (!Shrimphack_PATH.toFile().exists()) Shrimphack_PATH.toFile().mkdirs();
        for (Jsonable jsonable : jsonables) {
            try {
                Shrimphack.LOGGER.info("saving: " + jsonable.getFileName());
                JsonElement json = jsonable.toJson();
                Files.writeString(Shrimphack_PATH.resolve(jsonable.getFileName()), gson.toJson(json));
            } catch (Throwable e) {
                Shrimphack.LOGGER.error(e.getMessage());
            }
        }
    }
}