package dev.frankheijden.nopetprefix;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.entity.Tameable;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Optional;

public class PetPrefixAdapter extends PacketAdapter {

    public static final WrappedDataWatcher.WrappedDataWatcherObject OWNER = new WrappedDataWatcher
            .WrappedDataWatcherObject(17, WrappedDataWatcher.Registry.getUUIDSerializer(true));

    public PetPrefixAdapter(Plugin plugin, ListenerPriority listenerPriority) {
        super(plugin, listenerPriority, PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        try {
            WrapperPlayServerEntityMetadata wrap = new WrapperPlayServerEntityMetadata(packet);
            if (!(wrap.getEntity(event.getPlayer().getWorld()) instanceof Tameable)) return;
            List<WrappedWatchableObject> meta = wrap.getMetadata();

            for (int i = 0; i < meta.size(); i++) {
                if (meta.get(i).getIndex() == 17) {
                    meta.set(i, new WrappedWatchableObject(OWNER, Optional.empty()));
                    break;
                }
            }

            wrap.setMetadata(meta);
            event.setPacket(wrap.getHandle());
        } catch (FieldAccessException ex) {
            ex.printStackTrace();
        }
    }
}
