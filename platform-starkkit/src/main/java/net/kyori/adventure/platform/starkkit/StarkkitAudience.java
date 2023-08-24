package net.kyori.adventure.platform.starkkit;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import net.kyori.adventure.platform.facet.Facet;
import net.kyori.adventure.platform.facet.FacetAudience;
import net.kyori.adventure.platform.facet.FacetAudienceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class StarkkitAudience extends FacetAudience<CommandSender> {


    private static final Collection<Facet.Chat<? extends CommandSender, ?>> CHAT = Facet.of(
            StarkkitFacet.ChatPlayer::new,
            StarkkitFacet.ChatConsole::new);
    private static final Collection<Facet.ActionBar<Player, ?>> ACTION_BAR = Facet.of(
            StarkkitFacet.ActionBar::new);
    private static final Collection<Facet.Title<Player, ?, ?, ?>> TITLE = Facet.of();
    private static final Collection<Facet.BossBar.Builder<Player, ? extends Facet.BossBar<Player>>> BOSS_BAR = Facet.of();
    private static final Collection<Facet.Pointers<? extends CommandSender>> POINTERS = Facet.of();

    public StarkkitAudience(@NotNull FacetAudienceProvider provider, @NotNull Collection<? extends CommandSender> viewers, @Nullable Collection<? extends Facet.Chat> chat, @Nullable Collection<? extends Facet.ActionBar> actionBar, @Nullable Collection<? extends Facet.Title> title, @Nullable Collection<? extends Facet.Sound> sound, @Nullable Collection<? extends Facet.EntitySound> entitySound, @Nullable Collection<? extends Facet.Book> book, @Nullable Collection<? extends Facet.BossBar.Builder> bossBar, @Nullable Collection<? extends Facet.TabList> tabList, @Nullable Collection<? extends Facet.Pointers> pointerProviders) {
        super(provider, viewers, chat, actionBar, title, sound, entitySound, book, bossBar, tabList, pointerProviders);
    }
}