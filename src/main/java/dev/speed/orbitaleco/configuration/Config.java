package dev.speed.orbitaleco.configuration;

import dev.speed.fastlib.configuration.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Config implements Configuration {

    EARN_MINIMUM("earn.minimum", 1D),
    EARN_MAXIMUM("earn.maximum", 5D),
    EARN_COOLDOWN("earn.cooldown", 60);

    private final String path;
    @Setter
    private Object value;

}
