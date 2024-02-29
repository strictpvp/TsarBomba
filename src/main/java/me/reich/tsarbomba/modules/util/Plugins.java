package me.reich.tsarbomba.modules.util;

import me.reich.tsarbomba.TsarBomba;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Plugins extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> tryTabComplete = sgGeneral.add(new BoolSetting.Builder()
        .name("Try Tab Complete")
        .description("")
        .defaultValue(true)
        .build());

    public Plugins() {
        super(TsarBomba.CATEGORY, "Plugins", "checkPlugin");
    }

    private static final String[] knownAntiCheats = {
        "nocheatplus",
        "grimac",
        "aac",
        "intave",
        "horizon",
        "vulcan",
        "Vulcan",
        "spartan",
        "kauri",
        "anticheatreloaded",
        "matrix",
        "themis",
        "negativity"
    };

    @EventHandler
    public void onTick(TickEvent.Pre tickEvent) {
        if(tryTabComplete.get())
            mc.getNetworkHandler().sendPacket(new RequestCommandCompletionsC2SPacket(0, "/"));
    }

    /*@EventHandler
    public void onPacketReceived(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof CommandSuggestionsS2CPacket) {
            Set<String> plugins = ((CommandSuggestionsS2CPacket) packet).getSuggestions().getList().stream()
                .map(cmd -> {
                    String[] command = cmd.getText().split(":");
                    return command.length > 1 ? command[0].replace("/", "") : null;
                })
                .filter(Objects::nonNull)  // Remove null values
                .distinct()
                .sorted()
                .collect(Collectors.toSet());

            takePluginInput(plugins);
        }
    }*/

    public static boolean contains(String[] arr, String value) {
        return Arrays.asList(arr).contains(value);
    }

    private void takePluginInput(Set<String> plugins) {
        if (!plugins.isEmpty()) {
            StringBuilder pluginsString = new StringBuilder();
            for (String plugin : plugins) {
                if (contains(knownAntiCheats, plugin)) {
                    pluginsString.append("§a").append(plugin);
                } else {
                    pluginsString.append("§c").append(plugin);
                }
            }
            info("§aPlugins §7(§8" + plugins.size() + "§7): " + pluginsString.toString());
        } else {
            info("noPluginsFound");
        }
        toggle();
    }
}
