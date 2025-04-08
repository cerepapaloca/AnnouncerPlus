package ceres.ap;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class Utils {

    @NotNull
    @Contract(pure = true)
    public String convertToMiniMessageFormat(String input) {
        input = input.replace('§', '&');
        input = input.replaceAll("&#([A-Fa-f0-9]{6})", "<#$1>");

        input = input.replace("&l", "<bold>");
        input = input.replace("&o", "<italic>");
        input = input.replace("&n", "<underlined>");
        input = input.replace("&m", "<strikethrough>");
        input = input.replace("&k", "<obfuscated>");
        input = input.replace("&r", "<reset>");
        input = input.replace("&0", "<black>");
        input = input.replace("&1", "<dark_blue>");
        input = input.replace("&2", "<dark_green>");
        input = input.replace("&3", "<dark_aqua>");
        input = input.replace("&4", "<dark_red>");
        input = input.replace("&5", "<dark_purple>");
        input = input.replace("&6", "<gold>");
        input = input.replace("&7", "<gray>");
        input = input.replace("&8", "<dark_gray>");
        input = input.replace("&9", "<blue>");
        input = input.replace("&a", "<green>");
        input = input.replace("&b", "<aqua>");
        input = input.replace("&c", "<red>");
        input = input.replace("&d", "<light_purple>");
        input = input.replace("&e", "<yellow>");
        input = input.replace("&f", "<white>");

        return input;
    }

    @NotNull
    @Contract(pure = true)
    public Component chatColorLegacyToComponent(String input) {
        return AnnouncerPlusCore.getMiniMessage().deserialize(Utils.convertToMiniMessageFormat(input.replace('§', '&')));
    }

}
