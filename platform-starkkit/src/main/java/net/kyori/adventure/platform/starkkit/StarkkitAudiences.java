package net.kyori.adventure.platform.starkkit;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface StarkkitAudiences extends AudienceProvider {
    /**
     * Creates an audience provider for a plugin.
     *
     * <p>There will only be one provider for each plugin.</p>
     *
     * @param plugin a plugin
     * @return an audience provider
     * @since 4.0.0
     */
    static @NotNull StarkkitAudiences create(final @NotNull Plugin plugin) {
        return StarkkitAudiencesImpl.instanceFor(plugin);
    }

    /**
     * Creates an audience provider builder for a plugin.
     *
     * <p>There will only be one provider for each plugin.</p>
     *
     * @param plugin a plugin
     * @return an audience provider
     * @since 4.0.0
     */
    static @NotNull Builder builder(final @NotNull Plugin plugin) {
        return StarkkitAudiencesImpl.builder(plugin);
    }

    /**
     * Gets an audience for a command sender.
     *
     * @param sender a command sender
     * @return an audience
     * @since 4.0.0
     */
    @NotNull Audience sender(final @NotNull CommandSender sender);

    /**
     * Gets an audience for a player.
     *
     * @param player a player
     * @return an audience
     * @since 4.0.0
     */
    @NotNull Audience player(final @NotNull Player player);

    /**
     * Creates an audience based on a filter.
     *
     * @param filter a filter
     * @return an audience
     * @since 4.0.0
     */
    @NotNull Audience filter(final @NotNull Predicate<CommandSender> filter);

    /**
     * A builder for {@link StarkkitAudiences}.
     *
     * @since 4.0.0
     */
    interface Builder extends AudienceProvider.Builder<StarkkitAudiences, Builder> {
    }
}
