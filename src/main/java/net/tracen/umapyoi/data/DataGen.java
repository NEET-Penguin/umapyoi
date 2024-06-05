package net.tracen.umapyoi.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;
import net.tracen.umapyoi.data.advancements.UmapyoiAdvancementsDataProvider;
import net.tracen.umapyoi.data.loot.UmapyoiLootTableProvider;
import net.tracen.umapyoi.data.tag.UmaDataTagProvider;
import net.tracen.umapyoi.data.tag.UmapyoiBlockTagProvider;
import net.tracen.umapyoi.data.tag.UmapyoiItemTagsProvider;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if(event.includeClient()) {
            dataGenerator.addProvider(event.includeClient(), new UmapyoiBlockStateProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(event.includeClient(), new UmapyoiItemModelProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(event.includeClient(), new UmapyoiLangProvider(dataGenerator));
        }
        if(event.includeServer()) {
            
            dataGenerator.addProvider(event.includeServer(), new UmaDataProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(event.includeServer(), new SupportCardDataProvider(dataGenerator, existingFileHelper));
            UmapyoiBlockTagProvider blockTagProvider = new UmapyoiBlockTagProvider(dataGenerator, existingFileHelper);
            dataGenerator.addProvider(event.includeServer(), blockTagProvider);
            dataGenerator.addProvider(event.includeServer(), new UmapyoiItemTagsProvider(dataGenerator, blockTagProvider, existingFileHelper));
            dataGenerator.addProvider(event.includeServer(), new UmapyoiLootTableProvider(dataGenerator));
            dataGenerator.addProvider(event.includeServer(), new UmaDataTagProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(event.includeServer(), new UmapyoiRecipeProvider(dataGenerator));
            dataGenerator.addProvider(event.includeServer(), new UmapyoiAdvancementsDataProvider(dataGenerator, existingFileHelper));
        }
    }

}
