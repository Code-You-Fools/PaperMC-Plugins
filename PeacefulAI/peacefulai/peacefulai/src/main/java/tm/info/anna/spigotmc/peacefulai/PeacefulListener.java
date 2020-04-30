package tm.info.anna.spigotmc.peacefulai;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PeacefulListener implements Listener {

    private static ArrayList<Player> peacefulPlayers = new ArrayList<Player>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamaged(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if(!(isPeaceful((Player)event.getEntity()))) return;
        switch(((EntityDamageEvent)event).getCause()) {
            case DRAGON_BREATH:
            case ENTITY_ATTACK:
            case ENTITY_EXPLOSION:
            case ENTITY_SWEEP_ATTACK:
            case MAGIC:
            case POISON:
            case PROJECTILE:
            case WITHER: ((Cancellable)event).setCancelled(true); return;
            default: return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTargeted(EntityTargetEvent event) {
        if(!(((EntityTargetEvent)event).getTarget() instanceof Player)) return;
        if(!isPeaceful((Player)(((EntityTargetEvent)event).getTarget()))) return;
        switch(((EntityTargetEvent)event).getReason()) {
            case CLOSEST_ENTITY:
            case CLOSEST_PLAYER:
            case DEFEND_VILLAGE:
            case FOLLOW_LEADER:
            case OWNER_ATTACKED_TARGET:
            case REINFORCEMENT_TARGET:
            case TARGET_ATTACKED_ENTITY:
            case TARGET_ATTACKED_NEARBY_ENTITY: ((Cancellable)event).setCancelled(true); break;
            default: break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        switch(event.getEntityType()) {
            case ENDERMITE:
            case PHANTOM:
            case SILVERFISH: ((Cancellable)event).setCancelled(true); break;
            default: return;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoined(PlayerJoinEvent event) {
        if(App.checkConfig(event.getPlayer())) peacefulPlayers.add(event.getPlayer());
    }

    public static boolean isPeaceful(Player player) {
        return peacefulPlayers.contains(player);
    }

    public static void setPeaceful(Player player, boolean peaceful) {
        if(peaceful) {
            if(peacefulPlayers.contains(player)) return;
            peacefulPlayers.add(player);
            App.updateConfig(peacefulPlayers);
        }
        else if(peacefulPlayers.contains(player)) {
            peacefulPlayers.remove(player);
            App.updateConfig(peacefulPlayers);
        }
    }

    public static void loadPeaceful(Player player) {
        peacefulPlayers.add(player);
    }

}