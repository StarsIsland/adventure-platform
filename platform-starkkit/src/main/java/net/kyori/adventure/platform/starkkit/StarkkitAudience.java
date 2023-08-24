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
import cn.nukkit.command.CommandSender;
import java.util.Collection;
import net.kyori.adventure.platform.facet.Facet;
import net.kyori.adventure.platform.facet.FacetAudience;
import net.kyori.adventure.platform.facet.FacetAudienceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class StarkkitAudience extends FacetAudience<CommandSender> {

  private static final Collection<Facet.Chat<? extends CommandSender, ?>> CHAT = Facet.of(
          StarkkitFacet.ChatPlayer::new,
          StarkkitFacet.ChatConsole::new);
  private static final Collection<Facet.ActionBar<Player, ?>> ACTION_BAR = Facet.of(
          StarkkitFacet.ActionBar::new);
  private static final Collection<Facet.Title<Player, ?, ?, ?>> TITLE = Facet.of();
  private static final Collection<Facet.BossBar.Builder<Player, ? extends Facet.BossBar<Player>>> BOSS_BAR = Facet.of();
  private static final Collection<Facet.Pointers<? extends CommandSender>> POINTERS = Facet.of();

  StarkkitAudience(@NotNull FacetAudienceProvider provider, @NotNull Collection<? extends CommandSender> viewers, @Nullable Collection<? extends Facet.Chat> chat, @Nullable Collection<? extends Facet.ActionBar> actionBar, @Nullable Collection<? extends Facet.Title> title, @Nullable Collection<? extends Facet.Sound> sound, @Nullable Collection<? extends Facet.EntitySound> entitySound, @Nullable Collection<? extends Facet.Book> book, @Nullable Collection<? extends Facet.BossBar.Builder> bossBar, @Nullable Collection<? extends Facet.TabList> tabList, @Nullable Collection<? extends Facet.Pointers> pointerProviders) {
    super(provider, viewers, chat, actionBar, title, sound, entitySound, book, bossBar, tabList, pointerProviders);
  }
}
