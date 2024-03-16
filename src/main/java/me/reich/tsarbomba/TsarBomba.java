package me.reich.tsarbomba;

import me.reich.tsarbomba.modules.crash.CompletionCrash;
import com.mojang.logging.LogUtils;
import me.reich.tsarbomba.modules.crash.ErrorCrash;
import me.reich.tsarbomba.modules.util.Plugins;
import me.reich.tsarbomba.utils.Hwid;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import org.slf4j.Logger;

import java.io.IOException;

public class TsarBomba extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Tsar Bomba");
    public static final String NAME = "Tsar Bomba";
    public static final String MOD_ID = "tsar-bomba";
    public static final Version VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion();


    @Override
    public void onInitialize() {
        LOG.info("[{}] Initializing Tsar Bomba for Meteor", NAME);

        // Modules
        // Crash
        Modules.get().add(new CompletionCrash());
        Modules.get().add(new ErrorCrash());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "me.reich.tsarbomba";
    }
}
