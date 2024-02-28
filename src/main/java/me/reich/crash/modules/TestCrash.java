package me.reich.crash.modules;

import me.reich.crash.Crash;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class TestCrash extends Module {
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

    public TestCrash() {
        super(Crash.CATEGORY, "TestModule", "idk Im finding fucking crash module 😎");
    }

    @Override
    public void onActivate(){
        mc.player.networkHandler.sendPacket(PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, HitResult.Type.BLOCK(Blocks.STONE), 1));
    }

    @EventHandler
    public void onTick(TickEvent.Pre tickEvent) {

    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }
}
