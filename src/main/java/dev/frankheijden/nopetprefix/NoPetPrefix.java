package dev.frankheijden.nopetprefix;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NoPetPrefix extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PetPrefixAdapter(this, ListenerPriority.HIGHEST));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }
}
