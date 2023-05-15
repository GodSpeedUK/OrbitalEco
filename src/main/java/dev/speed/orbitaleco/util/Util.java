package dev.speed.orbitaleco.util;

import dev.speed.fastlib.user.user.User;
import dev.speed.orbitaleco.data.EcoUserData;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Util {

    public EcoUserData getUserData(UUID uuid){
        return User.get(uuid).getUserData(EcoUserData.class);
    }


}
