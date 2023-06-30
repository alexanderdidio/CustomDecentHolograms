# CustomDecentHolograms
CustomDecentHolograms allows server owners the ability to reward players with self-managed holograms. Players can customize and relocate owned holograms within predefined boundaries set by the config.yml. Please rate the plugin 5 stars on [SpigotMC](https://www.spigotmc.org/resources/customdecentholograms.110861/) if you enjoy using it!

**Features**
- Lightweight plugin using DecentHolograms API
- Reward players with customizable holograms
- Easy to use commands and fast setup
- Fully customizable config and message files
- Supports [GriefPrevention](https://github.com/TechFortress/GriefPrevention/), [PlotSquared](https://www.spigotmc.org/resources/plotsquared-v6.77506/), [Towny](https://github.com/TownyAdvanced/Towny), [BentoBox](https://github.com/BentoBoxWorld/BentoBox/), [SuperiorSkyblock2](https://github.com/BG-Software-LLC/SuperiorSkyblock2), [Iridium](https://github.com/Iridium-Development/IridiumSkyblock)

## Installation
Download the JAR file from [latest release](https://github.com/alexanderdidio/CustomDecentHolograms/releases/latest) and copy it to the "plugins" directory for your Minecraft server.

**Requirements**
- CraftBukkit, Spigot or PaperMC 1.13 or higher
- Latest version of [DecentHolograms](https://github.com/DecentSoftware-eu/DecentHolograms)
- One of the supported plugins installed

## How does it work?
Simply use the command `/hg create <player>` to create a new hologram for a player. Once the hologram is created, it will be located at the location configured in the config.yml until the player uses `/hg move <hologram>` to move the hologram to their claim/plot/island. Players can use the commands below to customize their holograms.

**Commands and Permissions**
- /hg create [player] - `cdh.create`
- /hg move [hologram] - `cdh.move`
- /hg edit [hologram] [line] [text] - `cdh.edit`
- /hg add [hologram] [text] - `cdh.add`
- /hg remove [hologram] [line] - `cdh.remove`
- /hg list - `cdh.list`
- /hg formats - `cdh.formats`

It is highly recommended to use a plugin such as [ChatSentry](https://www.spigotmc.org/resources/%E3%80%90chatsentry%E3%80%91-smart-message-filtration-and-control-for-minecraft-servers-1-8-1-19-x.79616/) to block players from using inappropriate words and advertisements in commands which will prevent any sort of exploitation within hologram displays.

## Preview
Default configurable display upon player hologram creation

![Preview](https://i.imgur.com/tJjQKfD.gif)
