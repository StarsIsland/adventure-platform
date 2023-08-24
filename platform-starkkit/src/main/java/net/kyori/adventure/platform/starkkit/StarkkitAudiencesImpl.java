package net.kyori.adventure.platform.starkkit;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.LogLevel;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.platform.facet.FacetAudienceProvider;
import net.kyori.adventure.platform.facet.Knob;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import net.kyori.adventure.translation.GlobalTranslator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import static java.util.Objects.requireNonNull;

public class StarkkitAudiencesImpl extends FacetAudienceProvider<CommandSender, StarkkitAudience> implements StarkkitAudiences {

    static {
        Knob.OUT = message -> Server.getInstance().getLogger().log(LogLevel.INFO, message);
        Knob.ERR = (message, error) -> Server.getInstance().getLogger().log(LogLevel.WARNING, message, error);
    }

    private static final Map<String, StarkkitAudiences> INSTANCES = Collections.synchronizedMap(new HashMap<>(4));

    static @NotNull StarkkitAudiences instanceFor(final @NotNull Plugin plugin) {
        return builder(plugin).build();
    }

    static @NotNull StarkkitAudiences.Builder builder(final @NotNull Plugin plugin) {
        return new Builder(plugin);
    }


    private final Plugin plugin;

    private final Listener listener;

    StarkkitAudiencesImpl(final Plugin plugin, final @NotNull ComponentRenderer<Pointered> componentRenderer) {
        super(componentRenderer);
        this.plugin = requireNonNull(plugin, "plugin");
        this.listener = new Listener();
        this.plugin.getServer().getPluginManager().registerEvents(this.listener, this.plugin);

        final CommandSender console = this.plugin.getServer().getConsoleSender();
        this.addViewer(console);

        for (final Player player : this.plugin.getServer().getOnlinePlayers().values()) {
            this.addViewer(player);
        }
    }

    static final class Builder implements StarkkitAudiences.Builder {
        private final @NotNull Plugin plugin;
        private ComponentRenderer<Pointered> componentRenderer;

        Builder(final @NotNull Plugin plugin) {
            this.plugin = requireNonNull(plugin, "plugin");
            this.componentRenderer(ptr -> ptr.getOrDefault(Identity.LOCALE, DEFAULT_LOCALE), GlobalTranslator.renderer());
        }

        @Override
        public @NotNull Builder componentRenderer(final @NotNull ComponentRenderer<Pointered> componentRenderer) {
            this.componentRenderer = requireNonNull(componentRenderer, "component renderer");
            return this;
        }

        @Override
        public StarkkitAudiences.@NotNull Builder partition(final @NotNull Function<Pointered, ?> partitionFunction) {
            requireNonNull(partitionFunction, "partitionFunction"); // unused
            return this;
        }

        @Override
        public @NotNull StarkkitAudiences build() {
            return INSTANCES.computeIfAbsent(this.plugin.getDescription().getName(), name -> new StarkkitAudiencesImpl(this.plugin, this.componentRenderer));
        }
    }

    @Override
    public @NotNull ComponentFlattener flattener() {
        return StarkkitFacet.FLATTENER;
    }

    @Override
    protected @NotNull StarkkitAudience createAudience(@NotNull Collection<CommandSender> viewers) {
        return null;
    }

    @Override
    public @NotNull Audience sender(@NotNull CommandSender sender) {
        if(sender instanceof Player) {
            return this.player((Player) sender);
        } else if(sender instanceof ConsoleCommandSender) {
            return this.console();
        }
        return this.createAudience(Collections.singletonList(sender));
    }

    @Override
    public void close() {
        INSTANCES.remove(this.plugin, plugin.getDescription().getName());
        super.close();
    }

    @Override
    public @NotNull Audience player(@NotNull Player player) {
        return this.player(player.getUniqueId());
    }

    public final class Listener implements cn.nukkit.event.Listener {

        @EventHandler(priority = EventPriority.LOWEST)
        public void onLogin(final PlayerLoginEvent event) {
            StarkkitAudiencesImpl.this.addViewer(event.getPlayer());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onDisconnect(final PlayerQuitEvent event) {
            StarkkitAudiencesImpl.this.removeViewer(event.getPlayer());
        }
    }

}