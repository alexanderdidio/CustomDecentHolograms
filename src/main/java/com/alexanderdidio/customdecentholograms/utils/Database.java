package com.alexanderdidio.customdecentholograms.utils;

import com.alexanderdidio.customdecentholograms.CustomDecentHolograms;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Database {
    private final Map<UUID, List<Hologram>> database = new HashMap<>();
    private final File dataFile = new File("plugins/CustomDecentHolograms/data.yml");
    private YamlConfiguration dataConfig;
    private final CustomDecentHolograms plugin;

    public Database(CustomDecentHolograms plugin) {
        this.plugin = plugin;
    }

    public void createHologram(UUID uuid, Hologram hologram) throws IOException {
        if (database.containsKey(uuid)) {
            List<String> hologramList = dataConfig.getStringList(uuid + ".holograms");
            hologramList.add(hologram.getName());
            dataConfig.set(uuid + ".holograms", hologramList);
            dataConfig.save(dataFile);
            database.get(uuid).add(hologram);
        } else {
            List<Hologram> hologramList = new ArrayList<>();
            List<String> hologramString = new ArrayList<>();
            hologramList.add(hologram);
            hologramString.add(hologram.getName());
            database.put(uuid, hologramList);
            dataConfig.set(uuid + ".holograms", hologramString);
            dataConfig.save(dataFile);
        }
    }

    public int countHolograms(UUID uuid) {
        if (database.containsKey(uuid)) {
            return database.get(uuid).size();
        } else {
            return 0;
        }
    }

    public List<Hologram> listHolograms(UUID uuid) {
        if (database.containsKey(uuid)) {
            return database.get(uuid);
        } else {
            return new ArrayList<>();
        }
    }

    public boolean validateHologram(UUID uuid, int index) {
        if (database.containsKey(uuid)) {
            return database.get(uuid).size() >= index + 1;
        } else {
            return false;
        }
    }

    public Hologram getHologram(UUID uuid, int index) {
        if (database.containsKey(uuid)) {
            return database.get(uuid).get(index);
        } else {
            return null;
        }
    }

    public void loadDatabase() {
        if (!dataFile.exists()) {
            plugin.saveResource("data.yml", false);
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        Set<String> uuids = dataConfig.getKeys(false);
        for (String uuid : uuids) {
            UUID playerUUID = UUID.fromString(uuid);
            List<String> hologramSection = dataConfig.getStringList(uuid + ".holograms");
            List<Hologram> hologramList = new ArrayList<>();
            for (String hologram : hologramSection) {
                Hologram hologramEntry = DHAPI.getHologram(hologram);
                hologramList.add(hologramEntry);
            }
            database.put(playerUUID, hologramList);
        }
    }
}
