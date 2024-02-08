# CustomDecentHolograms
CustomDecentHolograms allows server owners the ability to reward players with self-managed holograms. Players can customize and relocate owned holograms within predefined boundaries set by the config.yml. Please rate the plugin 5 stars on [SpigotMC](https://www.spigotmc.org/resources/customdecentholograms.110861/) if you enjoy using it!

**Features**
- Lightweight plugin using DecentHolograms API
- Reward players with customizable holograms
- Permissions to limit hologram amounts
- Easy to use commands and fast setup
- Fully customizable config and message files
- Supports [GriefPrevention](https://github.com/TechFortress/GriefPrevention/), [GriefDefender](https://www.spigotmc.org/resources/1-12-2-1-20-4-griefdefender-claim-plugin-grief-prevention-protection.68900/), [PlotSquared](https://www.spigotmc.org/resources/plotsquared-v6.77506/), [WorldGuard](https://dev.bukkit.org/projects/worldguard), [Towny](https://github.com/TownyAdvanced/Towny), [Lands](https://www.spigotmc.org/resources/lands-%E2%AD%95-land-claim-plugin-%E2%9C%85-grief-prevention-protection-gui-management-nations-wars-1-20-support.53313/), [BentoBox](https://github.com/BentoBoxWorld/BentoBox/), [SuperiorSkyblock2](https://github.com/BG-Software-LLC/SuperiorSkyblock2), [Iridium](https://github.com/Iridium-Development/IridiumSkyblock)

## Installation
Download the JAR file from [latest release](https://github.com/alexanderdidio/CustomDecentHolograms/releases/latest) and copy it to the "plugins" directory for your Minecraft server.

**Requirements**
- CraftBukkit, Spigot or PaperMC 1.13 or higher
- Latest version of [DecentHolograms](https://github.com/DecentSoftware-eu/DecentHolograms)
- One of the supported plugins installed

## How does it work?
Simply use the command `/hg create <player>` to create a new hologram for a player, or let them create holograms themselves with `/hg create`! Once the hologram is created, it will be located at the location configured in the `config.yml` until the player uses `/hg move <hologram>` to move the hologram to their claim/plot/island. Players can use the commands below to customize their holograms.

**Commands and Permissions**

For players to create holograms themselves, you must assign them the permission node `cdh.amount.<amount>` which will let them create up a certain amount of holograms. Read the PlaceholderAPI section below to learn how to automate this!
- /hg create - `cdh.create`
- /hg reload - `cdh.reload`
- /hg hide - `cdh.hide`
- /hg move [hologram] - `cdh.move`
- /hg edit [hologram] [line] [text] - `cdh.edit`
- /hg add [hologram] [text] - `cdh.add`
- /hg remove [hologram] [line] - `cdh.remove`
- /hg list - `cdh.list`
- /hg formats - `cdh.formats`

**PlaceholderAPI Support**

I recommend server owners take advantage of the [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) features available within [DeluxeMenus](https://www.spigotmc.org/resources/deluxemenus.11734/) to create automated purchasing and upgrading of hologram amounts, which is only possible using the placeholders below! An example menu of a hologram shop can be found [here](https://paste.helpch.at/hicajemiha.vbnet)!

- Display number of owned holograms - `%cdh_owned%`
- Display maximum allowed number of holograms - `%cdh_permission%`
- Display `true` or `false` if maximum number is reached - `%cdh_limit%`

It is highly recommended to use a plugin such as [ChatSentry](https://www.spigotmc.org/resources/%E3%80%90chatsentry%E3%80%91-smart-message-filtration-and-control-for-minecraft-servers-1-8-1-19-x.79616/) to block players from using inappropriate words and advertisements in commands which will prevent any sort of exploitation within hologram displays.

## Preview
Default configurable display upon player hologram creation

![Preview](https://i.imgur.com/tJjQKfD.gif)
