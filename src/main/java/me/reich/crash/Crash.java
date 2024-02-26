package me.reich.crash;

import me.reich.crash.commands.CommandExample;
import me.reich.crash.hud.HudExample;
import me.reich.crash.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import me.reich.crash.utils.Hwid;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

import static javax.swing.JOptionPane.*;

public class Crash extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("ReichCrash");
    public static final HudGroup HUD_GROUP = new HudGroup("ReichCrash");
    public static final String NAME = "ReichCrash";


    @Override
    public void onInitialize() {
        LOG.info("[{}] Initializing Reich Crash for Meteor", NAME);
        LOG.info("[{}] Checking Hwid", NAME);

        if (!Hwid.CheckHWID()) {
            LOG.warn("[{}] Invalid HWID", NAME);
            LOG.warn("[{}] Your HWID is : " + Hwid.GetHWID(), NAME);
            LOG.warn("[{}] Dm to (Discord)c_arrot_ with your hwid", NAME);
            System.exit(0);
        }

        // Modules
        Modules.get().add(new ModuleExample());

        // Commands
        //Commands.add(new CommandExample());

        // HUD
        //Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "me.reich.crash";
    }
}
