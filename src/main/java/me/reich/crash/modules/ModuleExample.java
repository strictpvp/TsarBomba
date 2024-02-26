package me.reich.crash.modules;

import me.reich.crash.Crash;
import meteordevelopment.meteorclient.systems.modules.Module;

public class ModuleExample extends Module {
    public ModuleExample() {
        super(Crash.CATEGORY, "example", "An example module in a custom category.");
    }
}
