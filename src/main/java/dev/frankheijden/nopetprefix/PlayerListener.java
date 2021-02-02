package dev.frankheijden.nopetprefix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements Listener {

    private final NoPetPrefix plugin;

    public PlayerListener(NoPetPrefix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Entity entity = event.getRightClicked();
        if (!(entity instanceof Tameable)) return;

        Tameable tameable = (Tameable) entity;
        if (!event.getPlayer().getUniqueId().equals(tameable.getOwnerUniqueId())) return;

        if (!(entity instanceof Sittable)) return;
        Sittable sittable = (Sittable) entity;

        boolean sitting = sittable.isSitting();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (sittable.isSitting() == sitting) {
                sittable.setSitting(!sittable.isSitting());
            }
        }, 1);
    }
}
