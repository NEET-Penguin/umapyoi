package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class FadedUmaSoulItem extends Item {

    public FadedUmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
            TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.umapyoi.umadata.name",
                UmaSoulUtils.getTranslatedUmaName(this.getUmaName(pStack))).withStyle(ChatFormatting.GRAY));
    }

    public ResourceLocation getUmaName(ItemStack pStack) {
        if (pStack.getOrCreateTag().getString("name").isBlank())
            return UmaDataRegistry.COMMON_UMA.getId();
        return Optional.ofNullable(ResourceLocation.tryParse(pStack.getOrCreateTag().getString("name")))
                .orElse(UmaDataRegistry.COMMON_UMA.getId());
    }

}
