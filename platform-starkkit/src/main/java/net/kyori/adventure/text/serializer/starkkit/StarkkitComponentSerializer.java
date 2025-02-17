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
package net.kyori.adventure.text.serializer.starkkit;

import cn.nukkit.lang.TextContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class StarkkitComponentSerializer implements ComponentSerializer<Component, Component, TextContainer> {

    private static final StarkkitComponentSerializer INSTANCE = new StarkkitComponentSerializer();

    private static final GsonComponentSerializer SERIALIZER = GsonComponentSerializer.builder().build();

    public static @NotNull StarkkitComponentSerializer get() {
        return INSTANCE;
    }

    private StarkkitComponentSerializer() {
    }

    @Override
    public @NotNull Component deserialize(@NotNull TextContainer input) {
        return SERIALIZER.deserialize(input.getText());
    }

    @Override
    public @NotNull TextContainer serialize(@NotNull Component component) {
        return new TextContainer(SERIALIZER.serialize(component));
    }
}
