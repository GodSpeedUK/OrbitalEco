package dev.speed.orbitaleco;

import dev.speed.fastlib.configuration.Configuration;
import dev.speed.fastlib.file.YamlFile;
import dev.speed.fastlib.plugin.FastPlugin;
import dev.speed.fastlib.user.user.User;
import dev.speed.orbitaleco.command.BalanceCommand;
import dev.speed.orbitaleco.command.EarnCommand;
import dev.speed.orbitaleco.command.GiveCommand;
import dev.speed.orbitaleco.command.SetBalanceCommand;
import dev.speed.orbitaleco.configuration.Config;
import dev.speed.orbitaleco.configuration.Messages;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class OrbitalEco extends FastPlugin {

    @Getter
    private static OrbitalEco instance;
    @Override
    public void onEnable() {
        instance = this;
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), Messages.values());
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        registerCommands(new BalanceCommand(), new EarnCommand(), new GiveCommand(), new SetBalanceCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
        // Plugin shutdown logic
    }
}
