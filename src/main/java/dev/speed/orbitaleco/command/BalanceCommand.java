package dev.speed.orbitaleco.command;

import dev.speed.fastlib.command.LibCommand;
import dev.speed.fastlib.command.parameter.ParameterHolder;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.message.CoreMessages;
import dev.speed.fastlib.util.CommandUtil;
import dev.speed.fastlib.util.Placeholder;
import dev.speed.fastlib.util.number.NumberUtil;
import dev.speed.orbitaleco.configuration.Messages;
import dev.speed.orbitaleco.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Collections;

public class BalanceCommand extends LibCommand {
    public BalanceCommand() {
        super("balance");
        setAliases(Collections.singletonList("bal"));
        addParameters(new ParameterHolder(0, true ,CommandUtil.getParameter(OfflinePlayer.class)));
        setUsage("/balance [player]");
    }

    @Override
    public boolean perform(LibCommandSender libCommandSender, String[] args) {

        if(args.length == 0){
            if(!libCommandSender.isPlayer()){
                CoreMessages.INVALID_SENDER.send(libCommandSender);
                return true;
            }

            Player player = libCommandSender.getPlayer();

            double balance = Util.getUserData(player.getUniqueId()).getBalance();

            Messages.BALANCE_OWN.send(libCommandSender, new Placeholder("{balance}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(balance))));
            return true;
        }

        if(!getParameterHolders().get(0).getParameter().isParsable(args[0])){
            CoreMessages.PLAYER_NOT_FOUND.send(libCommandSender, new Placeholder("{player}", args[0]));
            return true;
        }

        OfflinePlayer offlinePlayer = (OfflinePlayer) getParameterHolders().get(0).parse(args);

        double balance = Util.getUserData(offlinePlayer.getUniqueId()).getBalance();

        Messages.BALANCE_OTHER.send(libCommandSender, new Placeholder("{player}", offlinePlayer.getName()), new Placeholder("{balance}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(balance))));

        return true;
    }
}
