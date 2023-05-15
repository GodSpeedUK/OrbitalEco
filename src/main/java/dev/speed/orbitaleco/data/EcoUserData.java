package dev.speed.orbitaleco.data;

import dev.speed.fastlib.user.data.UserData;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EcoUserData extends UserData {
    public EcoUserData(UUID uuid) {
        super(uuid);
    }

    private double balance = 0;

}
