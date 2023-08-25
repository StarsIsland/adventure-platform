/*
 * This file is part of adventure-platform, licensed under the MIT License.
 *
 * Copyright (c) 2018-2020 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.adventure.platform.starkkit;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;

import cn.nukkit.command.utils.RawText;
import cn.nukkit.lang.TextContainer;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.platform.facet.Facet;
import net.kyori.adventure.platform.facet.FacetBase;
import net.kyori.adventure.platform.facet.FacetComponentFlattener;
import net.kyori.adventure.platform.facet.FacetPointers;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.starkkit.StarkkitComponentSerializer;
import net.kyori.adventure.util.TriState;
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
            if(message.getText().contains("rawtext")) {
                viewer.sendRawTextMessage(RawText.fromRawText(message.getText()));
            } else {
                viewer.sendMessage(message);
            }
        }
    }

    static class ActionBar extends Message implements Facet.ActionBar<Player, TextContainer> {

        @Override
        public void sendMessage(@NotNull Player viewer, @NotNull TextContainer message) {
            viewer.sendActionBar(message.getText());
        }
    }

    static final class CommandSenderPointers extends StarkkitFacet<CommandSender> implements Facet.Pointers<CommandSender> {
        CommandSenderPointers() {
            super(CommandSender.class);
        }

        @Override
        public void contributePointers(final CommandSender viewer, final net.kyori.adventure.pointer.Pointers.Builder builder) {
            builder.withDynamic(Identity.NAME, viewer::getName);

            builder.withStatic(PermissionChecker.POINTER, perm -> viewer.hasPermission(perm) ? TriState.TRUE : TriState.FALSE);
            if (!(viewer instanceof Player)) {
                builder.withStatic(FacetPointers.TYPE, viewer == Server.getInstance().getConsoleSender() ? FacetPointers.Type.CONSOLE : FacetPointers.Type.OTHER);
            }
        }
    }

    static final class PlayerPointers extends StarkkitFacet<Player> implements Facet.Pointers<Player> {
        PlayerPointers() {
            super(Player.class);
        }

        @Override
        public void contributePointers(final Player viewer, final net.kyori.adventure.pointer.Pointers.Builder builder) {
            builder.withDynamic(Identity.UUID, viewer::getUniqueId);
            builder.withDynamic(Identity.LOCALE, viewer::getLocale);
            builder.withDynamic(FacetPointers.SERVER, () -> viewer.getServer().getName());
            builder.withStatic(FacetPointers.TYPE, FacetPointers.Type.PLAYER);
        }
    }

}
