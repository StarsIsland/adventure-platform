package net.kyori.adventure.platform.starkkit;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;

import cn.nukkit.lang.TextContainer;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.platform.facet.Facet;
import net.kyori.adventure.platform.facet.FacetBase;
import net.kyori.adventure.platform.facet.FacetComponentFlattener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.starkkit.StarkkitComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class StarkkitFacet <V extends CommandSender> extends FacetBase<V> {

    private static final Collection<? extends FacetComponentFlattener.Translator<Server>> TRANSLATORS = Facet.of(

    );
    static final ComponentFlattener FLATTENER = FacetComponentFlattener.get(Server.getInstance(), TRANSLATORS);

    protected StarkkitFacet(@Nullable Class<? extends V> viewerClass) {
        super(viewerClass);
    }

    static class ChatConsole extends StarkkitFacet<CommandSender> implements Facet.Chat<CommandSender, TextContainer> {
        protected ChatConsole() {
            super(CommandSender.class);
        }

        @Override
        public TextContainer createMessage(final @NotNull CommandSender viewer, final @NotNull Component message) {
            return StarkkitComponentSerializer.get().serialize(message);
        }

        @Override
        public void sendMessage(@NotNull CommandSender viewer, @NotNull Identity source, @NotNull TextContainer message, @NotNull Object type) {
            viewer.sendMessage(message);
        }
    }

    static class Message extends StarkkitFacet<Player> implements Facet.Message<Player, TextContainer> {
        protected Message() {
            super(Player.class);
        }


        @Override
        public @Nullable TextContainer createMessage(@NotNull Player viewer, @NotNull Component message) {
            return StarkkitComponentSerializer.get().serialize(message);
        }
    }

    static class ChatPlayer extends Message implements Facet.Chat<Player, TextContainer> {
        @Override
        public @Nullable TextContainer createMessage(@NotNull Player viewer, @NotNull Component message) {
            return StarkkitComponentSerializer.get().serialize(message);
        }

        @Override
        public void sendMessage(@NotNull Player viewer, @NotNull Identity source, @NotNull TextContainer message, @NotNull Object type) {
            viewer.sendMessage(message);
        }
    }

    static class ActionBar extends Message implements Facet.ActionBar<Player, TextContainer> {

        @Override
        public void sendMessage(@NotNull Player viewer, @NotNull TextContainer message) {
            viewer.sendActionBar(message.getText());
        }
    }



}
