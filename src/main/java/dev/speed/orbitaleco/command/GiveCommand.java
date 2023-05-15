package dev.speed.orbitaleco.command;

import dev.speed.fastlib.command.LibCommand;
import dev.speed.fastlib.command.parameter.ParameterHolder;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.util.CommandUtil;
import dev.speed.fastlib.util.Placeholder;
import dev.speed.fastlib.util.number.NumberUtil;
import dev.speed.orbitaleco.configuration.Messages;
import dev.speed.orbitaleco.data.EcoUserData;
import dev.speed.orbitaleco.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;

import java.math.BigDecimal;
import java.util.Collections;

public class GiveCommand extends LibCommand {
    public GiveCommand() {
        super("give");
        setUsage("/give <player> <amount>");
        setAliases(Collections.singletonList("pay"));
        setPermission("orbitaleco.give");
        setRequiresPlayer(true);
        addParameters(new ParameterHolder(0, false, CommandUtil.getParameter(OfflinePlayer.class)), new ParameterHolder(1, false, CommandUtil.getParameter(Double.class)));
    }

    @Override
    public boolean perform(LibCommandSender libCommandSender, String[] strings) {

        OfflinePlayer offlinePlayer = (OfflinePlayer) getParameterHolders().get(0).parse(strings);
        double amount = (double) getParameterHolders().get(1).parse(strings);

        EcoUserData senderUserData = Util.getUserData(libCommandSender.getPlayer().getUniqueId());

        if(senderUserData.getBalance() < amount){
            Messages.PAY_NOT_ENOUGH.send(libCommandSender);
            return true;
        }

        EcoUserData receiverUserData = Util.getUserData(offlinePlayer.getUniqueId());

        senderUserData.setBalance(senderUserData.getBalance() - amount);

        receiverUserData.setBalance(receiverUserData.getBalance() + amount);

        Messages.PAY.send(libCommandSender, new Placeholder("{player}", offlinePlayer.getName()), new Placeholder("{amount}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(amount))));

        if(offlinePlayer.isOnline()){
            Messages.PAY_RECEIVED.send(LibCommandSender.of(offlinePlayer.getPlayer()), new Placeholder("{player}", libCommandSender.getPlayer().getName()), new Placeholder("{amount}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(amount))));
        }

        return true;
    }
}
