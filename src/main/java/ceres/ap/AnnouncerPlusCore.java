package ceres.ap;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class AnnouncerPlusCore extends JavaPlugin {

    @Getter
    private static AnnouncerPlusCore instance;
    private static boolean hasPlaceholder;
    @Getter
    private ScheduledTask task;
    private static Config config;
    @Getter
    private static MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        start();
        PluginCommand pluginCommand = getCommand("announcerplus");
        assert pluginCommand != null;
        TabExecutor tabExecutor = new MainCommand();
        pluginCommand.setExecutor(tabExecutor);
        pluginCommand.setTabCompleter(tabExecutor);
        hasPlaceholder = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        FileConfiguration defConfig = getConfig();
        boolean isRando = defConfig.getBoolean("isRando");
        String prefix = defConfig.getString("prefix");
        int frequency = defConfig.getInt("frequency");
        boolean sendToConsole = defConfig.getBoolean("sendToConsole");
        List<?> raw = defConfig.getStringList("announcements");
        List<String> announcements = raw.stream().filter(object -> object instanceof String).map(object -> (String) object).toList();
        config = new Config(isRando, sendToConsole, announcements, prefix, frequency);
        start();
    }

    private int counter = 0;

    public void start(){
        if (task != null && !task.isCancelled()) task.cancel();
        counter = 0;
        task = Bukkit.getRegionScheduler().runAtFixedRate(this, Bukkit.getWorlds().get(0), 0, 0, (task) -> {
            List<CommandSender> senders = new ArrayList<>(Bukkit.getOnlinePlayers());
            if (config.isSendToConsole()) senders.add(Bukkit.getConsoleSender());
            String message;
            if (config.getAnnouncements() == null || config.getAnnouncements().isEmpty()) throw new NullPointerException("La lista de mensaje esta vaciá o no existe");
            if (config.isRando()) {
                Random rand = new Random();
                message = config.getAnnouncements().get(rand.nextInt(config.getAnnouncements().size()));
            }else {
                message = config.getAnnouncements().get(counter % config.getAnnouncements().size());
                counter++;
            }
            String prefix = config.getPrefix();
            if (prefix == null) prefix = "";
            String finalPrefix = prefix;
            senders.forEach(sender -> {
                if (hasPlaceholder) {
                    if (sender instanceof Player player) {
                        sender.sendMessage(Utils.chatColorLegacyToComponent(finalPrefix + PlaceholderAPI.setPlaceholders(player, message)));
                    }else {
                        sender.sendMessage(Utils.chatColorLegacyToComponent(finalPrefix + message));
                    }
                }else {
                    sender.sendMessage(Utils.chatColorLegacyToComponent(finalPrefix + message));
                }
            });
        },  config.getFrequency(), config.getFrequency());
    }


    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(Utils.chatColorLegacyToComponent("<dark_aqua>" +  message + "</dark_aqua>"));
    }
}
