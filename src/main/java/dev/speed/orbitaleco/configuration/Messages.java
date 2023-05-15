package dev.speed.orbitaleco.configuration;

import dev.speed.fastlib.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Messages implements Message {

    PREFIX("prefix", "&7[&5OrbitalEco&7]&f"),
    BALANCE_OWN("balance.own", "{prefix} Your Balance: &a{balance}$"),
    BALANCE_OTHER("balance.other", "{prefix} {player}'s Balance: &a{balance}$"),
    EARN("earn.success", "{prefix} You earned &a{amount}$&f!"),
    EARN_COOLDOWN("earn.cooldown", "{prefix} You can earn again in &a{time} seconds&f!"),

    PAY("pay.sent", "{prefix} You paid &a{amount}$&f to &a{player}&f!"),
    PAY_RECEIVED("pay.received", "{prefix} &a{player}&f paid you &a{amount}$&f!"),
    PAY_NOT_ENOUGH("pay.not-enough", "{prefix} You don't have enough money!"),
    BALANCE_SET("balance.set", "{prefix} You set &a{player}'s&f balance to &a{amount}$&f!"),

    ;
    private final String path;

    @Setter
    private Object value;

    public String getPrefix(){
        return PREFIX.getString();
    }

}
