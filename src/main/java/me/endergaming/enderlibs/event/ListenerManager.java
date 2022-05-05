package me.endergaming.enderlibs.event;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Example Usage:
 * <p>
 * listenerManager.register(listener);
 */
public class ListenerManager {
    private final JavaPlugin plugin;
    private final List<Listener> listeners = new ArrayList<>();

    public ListenerManager(@NotNull final JavaPlugin instance) {
        this.plugin = instance;
    }

    /**
     * Registers all {@link Listener} added to the listener list.
     */
    public void registerAll() {
        if (!this.listeners.isEmpty()) {
            this.unregisterAll();
            for (Listener listener : this.listeners)
                this.register(listener);
        }
    }

    /**
     * Unregisters all {@link Listener} added to the listener list.
     */
    public void unregisterAll() {
        if (!this.listeners.isEmpty()) {
            for (Listener listener : this.listeners)
                HandlerList.unregisterAll(listener);
        }
    }

    /**
     * Registers the specified {@link Listener} if it's contained in the listener list.
     *
     * @param listener Specified listener to register.
     */
    public void register(Listener listener) {
        if (!this.listeners.contains(listener)) {
            this.listeners.add(listener);
            Bukkit.getServer().getPluginManager().registerEvents(listener, this.plugin);
        }
    }

    /**
     * Unregisters the specified {@link Listener} if it's contained in the listener list.
     *
     * @param listener Specified listener to unregister.
     */
    public void unregister(Listener listener) {
        if (this.listeners.contains(listener)) {
            this.listeners.remove(listener);
            HandlerList.unregisterAll(listener);
        }
    }

    /**
     * Adds the specified {@link Listener} to the listener list. (Without registering it)
     *
     * @param listener Specified listener to add.
     */
    public void add(Listener listener) {
        this.listeners.add(listener);
    }

    /**
     * Adds the specified {@link Listener} to the listener list.
     *
     * @param listener Specified listener to remove.
     */
    public void remove(Listener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Clears & Unregisters all listeners from the manager
     */
    public void clearAndRemove() {
        this.listeners.forEach(HandlerList::unregisterAll);
        this.listeners.clear();
    }
}
