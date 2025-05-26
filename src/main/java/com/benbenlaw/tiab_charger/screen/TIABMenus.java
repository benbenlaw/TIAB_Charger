package com.benbenlaw.tiab_charger.screen;

import com.benbenlaw.tiab_charger.TIABCharger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TIABMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, TIABCharger.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<TIABChargerMenu>> TIAB_CHARGER_MENU;


    static {

        TIAB_CHARGER_MENU = MENUS.register("tiab_charger_menu", () ->
                IMenuTypeExtension.create(TIABChargerMenu::new));
    }
}
