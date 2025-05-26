package com.benbenlaw.tiab_charger.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class StartupConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Integer> rfPerCharge;
    public static final ModConfigSpec.ConfigValue<Integer> secondsPerCharge;

    static {

        // Ender Scrambler Configs
        BUILDER.comment("TIAB Charger Config")
                .push("TIAB Charger");

        rfPerCharge = BUILDER.comment("The amount of RF needed to add a defined charge of time, default = 20000")
                .define("RF per charge", 20000);

        secondsPerCharge = BUILDER.comment("The amount of seconds added per charge, default = 10")
                .define("Seconds per charge", 1);

        BUILDER.pop();


        //LAST

        SPEC = BUILDER.build();

    }

}
