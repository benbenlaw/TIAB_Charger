package com.benbenlaw.tiabcharger;

import com.benbenlaw.tiabcharger.block.TIABCapabilities;
import com.benbenlaw.tiabcharger.block.TIABChargerBlocks;
import com.benbenlaw.tiabcharger.block.TIABChargerBlockEntities;
import com.benbenlaw.tiabcharger.config.StartupConfig;
import com.benbenlaw.tiabcharger.item.TIABChargerItems;
import com.benbenlaw.tiabcharger.screen.TIABChargerScreen;
import com.benbenlaw.tiabcharger.screen.TIABMenus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.apache.logging.log4j.LogManager;
import org.mangorage.tiab.common.api.ICommonTimeInABottleAPI;
import org.mangorage.tiab.common.api.ITiabRegistration;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TIABCharger.MOD_ID)
public class TIABCharger {
    public static final String MOD_ID = "tiabcharger";
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    public TIABCharger(final IEventBus eventBus, final ModContainer modContainer) {

        TIABChargerItems.ITEMS.register(eventBus);
        TIABChargerBlocks.BLOCKS.register(eventBus);
        TIABChargerBlockEntities.BLOCK_ENTITIES.register(eventBus);
        TIABMenus.MENUS.register(eventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, StartupConfig.SPEC, "bbl/tiabcharger/charger.toml");

        eventBus.addListener(this::registerCapabilities);


        eventBus.addListener(this::addItemToCreativeTab);
    }

    private void addItemToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        ICommonTimeInABottleAPI api = ICommonTimeInABottleAPI.COMMON_API.get();
        ITiabRegistration registration = api.getRegistration();

        Identifier tabKey = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(registration.getCreativeTab());
        assert tabKey != null;
        ResourceKey<CreativeModeTab> tabResourceKey = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), tabKey);

        if (event.getTabKey() == tabResourceKey) {
            event.accept(TIABChargerBlocks.TIAB_CHARGER.get());
        }
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        TIABCapabilities.registerCapabilities(event);
    }

    @EventBusSubscriber(modid = TIABCharger.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(TIABMenus.TIAB_CHARGER_MENU.get(), TIABChargerScreen::new);
        }
    }


    public static Identifier identifier(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

}
