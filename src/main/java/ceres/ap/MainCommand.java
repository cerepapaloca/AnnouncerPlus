package ceres.ap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "reload" -> {
                    AnnouncerPlusCore.getInstance().reloadConfig();
                    AnnouncerPlusCore.sendMessage(sender, "Reloaded config");
                }
                case "start" -> {
                    AnnouncerPlusCore.getInstance().start();
                    AnnouncerPlusCore.sendMessage(sender, "Started");
                }
                case "stop" -> {
                    AnnouncerPlusCore.getInstance().getTask().cancel();
                    AnnouncerPlusCore.sendMessage(sender, "Stopped");
                }
            }
        }else {
            AnnouncerPlusCore.sendMessage(sender, "Missing arguments");
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("reload", "start", "stop").stream().filter(name -> name.toLowerCase().contains(args[0].toLowerCase())).toList();
        }else {
            return List.of();
        }
    }
}
