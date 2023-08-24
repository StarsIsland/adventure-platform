package net.kyori.adventure.text.serializer.starkkit;

import cn.nukkit.lang.TextContainer;
import cn.nukkit.utils.TextFormat;
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
