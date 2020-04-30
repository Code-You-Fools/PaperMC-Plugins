package tm.info.anna.spigotmc.peacefulai;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class App extends JavaPlugin {

    private static App me;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadPeacefulPlayers();
        getCommand("peaceful").setExecutor(new CommandPeaceful());
        getServer().getPluginManager().registerEvents(new PeacefulListener(), this);
        me = this;
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @SuppressWarnings("unchecked")
    public static void loadPeacefulPlayers() {
        try {
            Object config = me.getConfig().get("peaceful-players");
            if(!(config instanceof ArrayList<?>));
            if(!(((ArrayList<String>)config).get(0) instanceof String)) {
                me.getConfig().set("peaceful-players", null);
                me.getLogger().severe("peaceful-players config value is invalid - reinitializing.");
            }
            ArrayList<String> playerlist = (ArrayList<String>)config;
            for(String p : playerlist) {
                PeacefulListener.loadPeaceful(Bukkit.getPlayerExact(p));
            }
        }
        catch(NullPointerException e) {
        }
    }

    public static void updateConfig(ArrayList<Player> list) {
        if(list == null) me.getConfig().set("peaceful-players", new ArrayList<String>());
        else {
            ArrayList<String> tosave = new ArrayList<String>();
            for(Player p : list) {
                if(tosave.contains(p.getName())) continue;
                tosave.add(p.getName());
            }
            me.getConfig().set("peaceful-players", tosave);
        }
        me.saveConfig();
    }

    public static Logger getLog() {
        return me.getLogger();
    }

    @SuppressWarnings("unchecked")
    public static boolean checkConfig(Player p) {
        try {
            Object config = me.getConfig().get("peaceful-players");
            if(!(config instanceof ArrayList<?>));
            if(!(((ArrayList<String>)config).get(0) instanceof String)) {
                me.getConfig().set("peaceful-players", null);
                me.getLogger().severe("peaceful-players config value is invalid - reinitializing.");
            }
            return ((ArrayList<String>)config).contains(p.getName());
        }
        catch(NullPointerException e) {
            return false;
        }
    }
}