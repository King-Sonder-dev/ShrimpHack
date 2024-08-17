package me.alpha432.oyvey.manager;

import com.google.gson.*;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.Feature;
import me.alpha432.oyvey.features.settings.Bind;
import me.alpha432.oyvey.features.settings.EnumConverter;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.traits.Jsonable;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class ConfigManager {
    private static final Path OYVEY_PATH = FabricLoader.getInstance().getGameDir().resolve("oyvey");
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create();
    private final List<Jsonable> jsonables = List.of(OyVey.friendManager, OyVey.moduleManager, OyVey.commandManager);

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setValueFromJson(Feature feature, Setting setting, JsonElement element) {
        switch (setting.getType()) {
            case "Boolean" -> setting.setValue(element.getAsBoolean());
            case "Double" -> setting.setValue(element.getAsDouble());
            case "Float" -> setting.setValue(element.getAsFloat());
            case "Integer" -> setting.setValue(element.getAsInt());
            case "String" -> setting.setValue(element.getAsString().replace("_", " "));
            case "Bind" -> setting.setValue(new Bind(element.getAsInt()));
            case "Enum" -> {
                try {
                    EnumConverter converter = new EnumConverter(((Enum) setting.getValue()).getClass());
                    Enum value = converter.doBackward(element);
                    setting.setValue((value == null) ? setting.getDefaultValue() : value);
                } catch (Exception exception) {
                    OyVey.LOGGER.error("Failed to convert enum for: " + feature.getName() + " : " + setting.getName(), exception);
                }
            }
            default -> OyVey.LOGGER.error("Unknown Setting type for: " + feature.getName() + " : " + setting.getName());
        }
    }

    public void load() {
        if (!Files.exists(OYVEY_PATH)) {
            try {
                Files.createDirectories(OYVEY_PATH);
            } catch (IOException e) {
                OyVey.LOGGER.error("Failed to create config directory", e);
                return;
            }
        }
        for (Jsonable jsonable : jsonables) {
            try {
                Path filePath = OYVEY_PATH.resolve(jsonable.getFileName());
                if (!Files.exists(filePath)) {
                    OyVey.LOGGER.warn("Config file not found: " + jsonable.getFileName());
                    continue;
                }
                String read = Files.readString(filePath);
                jsonable.fromJson(JsonParser.parseString(read));
            } catch (Throwable e) {
                OyVey.LOGGER.error("Failed to load configuration for: " + jsonable.getFileName(), e);
            }
        }
    }

    public void save() {
        if (!Files.exists(OYVEY_PATH)) {
            try {
                Files.createDirectories(OYVEY_PATH);
            } catch (IOException e) {
                OyVey.LOGGER.error("Failed to create config directory", e);
                return;
            }
        }
        for (Jsonable jsonable : jsonables) {
            try {
                JsonElement json = jsonable.toJson();
                Path filePath = OYVEY_PATH.resolve(jsonable.getFileName());
                Files.writeString(filePath, gson.toJson(json));
            } catch (Throwable e) {
                OyVey.LOGGER.error("Failed to save configuration for: " + jsonable.getFileName(), e);
            }
        }
    }
}