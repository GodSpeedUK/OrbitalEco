package dev.speed.orbitaleco.command;

import dev.speed.fastlib.command.LibCommand;
import dev.speed.fastlib.command.parameter.ParameterHolder;
import dev.speed.fastlib.command.parameter.implementation.DoubleParameter;
import dev.speed.fastlib.command.parameter.implementation.OfflinePlayerParameter;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.util.CommandUtil;
import dev.speed.fastlib.util.Placeholder;
import dev.speed.fastlib.util.number.NumberUtil;
import dev.speed.orbitaleco.configuration.Messages;
import dev.speed.orbitaleco.data.EcoUserData;
import dev.speed.orbitaleco.util.Util;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.Collections;

public class SetBalanceCommand extends LibCommand {
    public SetBalanceCommand() {
        super("setbalance");
        setAliases(Collections.singletonList("setbal"));
        setPermission("orbitaleco.setbalance");
        setUsage("/setbalance <player> <amount>");
        addParameters(new ParameterHolder(0, false, CommandUtil.getParameter(OfflinePlayer.class)), new ParameterHolder(1, false, CommandUtil.getParameter(Double.class)));
    }

    @Override
    public boolean perform(LibCommandSender libCommandSender, String[] strings) {
        OfflinePlayer offlinePlayer = (OfflinePlayer) getParameterHolders().get(0).parse(strings);
        double amount = (double) getParameterHolders().get(1).parse(strings);

        EcoUserData ecoUserData = Util.getUserData(offlinePlayer.getUniqueId());

        ecoUserData.setBalance(amount);

        Messages.BALANCE_SET.send(libCommandSender, new Placeholder("{player}", offlinePlayer.getName()), new Placeholder("{amount}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(amount))));
        return true;
    }
}
