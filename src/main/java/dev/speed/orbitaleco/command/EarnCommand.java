package dev.speed.orbitaleco.command;

import dev.speed.fastlib.command.LibCommand;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.util.Placeholder;
import dev.speed.fastlib.util.number.NumberUtil;
import dev.speed.orbitaleco.OrbitalEco;
import dev.speed.orbitaleco.configuration.Config;
import dev.speed.orbitaleco.configuration.Messages;
import dev.speed.orbitaleco.data.EcoUserData;
import dev.speed.orbitaleco.util.Util;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EarnCommand extends LibCommand {

    private static final Map<UUID, Long> COOLDOWN = new HashMap<>();
    public EarnCommand() {
        super("earn");
        setUsage("/earn");
        setRequiresPlayer(true);
        setPermission("orbitaleco.earn");

    }

    @Override
    public boolean perform(LibCommandSender libCommandSender, String[] args) {

        if(isOnCooldown(libCommandSender.getPlayer().getUniqueId())){
            Messages.EARN_COOLDOWN.send(libCommandSender, new Placeholder("{time}", String.valueOf(getCooldown(libCommandSender.getPlayer().getUniqueId()))));
            return true;
        }

        double min = Config.EARN_MINIMUM.getDouble();
        double max = Config.EARN_MAXIMUM.getDouble();

        double amount = Math.floor(ThreadLocalRandom.current().nextDouble(min, max));

        EcoUserData ecoUserData = Util.getUserData(libCommandSender.getPlayer().getUniqueId());

        ecoUserData.setBalance(ecoUserData.getBalance() + amount);

        Messages.EARN.send(libCommandSender, new Placeholder("{amount}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(amount))));
        startCooldown(libCommandSender.getPlayer().getUniqueId());
        return true;
    }

    private void startCooldown(UUID uuid){
        long time = System.currentTimeMillis();
        int cooldownSeconds = Config.EARN_COOLDOWN.getInt();
        int cooldownMillis = cooldownSeconds * 1000;

        long cooldownExpires = time + cooldownMillis;

        COOLDOWN.put(uuid, cooldownExpires);

        Bukkit.getScheduler().runTaskLater(OrbitalEco.getInstance(), () -> COOLDOWN.remove(uuid), (long) 20*cooldownSeconds);
    }

    private int getCooldown(UUID uuid){
        if(!COOLDOWN.containsKey(uuid)){
            return 0;
        }

        long time = System.currentTimeMillis();
        long cooldownExpires = COOLDOWN.get(uuid);

        if(time >= cooldownExpires){
            return 0;
        }

        return (int) ((cooldownExpires - time) / 1000);
    }

    private boolean isOnCooldown(UUID uuid){
        return COOLDOWN.containsKey(uuid);
    }

}
