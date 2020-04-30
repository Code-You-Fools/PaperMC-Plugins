package tm.info.anna.spigotmc.peacefulai;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandPeaceful implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        switch(args.length) {
            case 1: if(!args[0].toLowerCase().equals("true") && !args[0].toLowerCase().equals("false")) return false; 
                    PeacefulListener.setPeaceful((Player)sender, Boolean.parseBoolean(args[0]));
                    return true;
            //case 2: 
            //TODO: handle 2 arguments - check if player has rights to change others mode
            default: return false;
        }
    }

}