package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import com.google.common.base.Suppliers;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.SupportCardRegistry;
import net.tracen.umapyoi.registry.training.SupportContainer;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SupportCardItem extends Item implements SupportContainer {
    public SupportCardItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (this.allowdedIn(pCategory)) {
            if (Minecraft.getInstance().getConnection() != null) {
                for (Entry<ResourceKey<SupportCard>, SupportCard> card : ClientUtils.getClientSupportCardRegistry()
                        .entrySet()) {
                    if (card.getKey().location().equals(SupportCardRegistry.BLANK_CARD.getId()))
                        continue;
                    ItemStack result = this.getDefaultInstance();
                    result.getOrCreateTag().putString("support_card", card.getKey().location().toString());
                    result.getOrCreateTag().putString("ranking",
                            card.getValue().getGachaRanking().name().toLowerCase());
                    pItems.add(result);
                }
            }
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack result = super.getDefaultInstance();
        result.getOrCreateTag().putString("support_card", SupportCardRegistry.BLANK_CARD.getId().toString());
        result.getOrCreateTag().putString("ranking",
                SupportCardRegistry.BLANK_CARD.get().getGachaRanking().name().toLowerCase());
        return result;
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        GachaRanking ranking = GachaRanking.getGachaRanking(pStack);
        return ranking == GachaRanking.SSR ? Rarity.EPIC : ranking == GachaRanking.SR ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        return Util.makeDescriptionId("support_card", this.getSupportCardID(pStack)) + ".name";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.supports").withStyle(ChatFormatting.AQUA));
            this.getSupports(worldIn, stack)
                    .forEach(support -> tooltip.add(support.getDescription().copy().withStyle(ChatFormatting.GRAY)));
        } else {
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.support_card.press_shift_for_supports")
                    .withStyle(ChatFormatting.AQUA));
        }

        List<ResourceLocation> supporters = ClientUtils.getClientSupportCardRegistry().get(this.getSupportCardID(stack))
                .getSupporters();
        if (!supporters.isEmpty()) {
            if (Screen.hasControlDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
                tooltip.add(new TranslatableComponent("tooltip.umapyoi.supporters").withStyle(ChatFormatting.AQUA));
                supporters.forEach(name -> tooltip
                        .add(UmaSoulUtils.getTranslatedUmaName(name).copy().withStyle(ChatFormatting.GRAY)));
            } else {
                tooltip.add(new TranslatableComponent("tooltip.umapyoi.support_card.press_ctrl_for_supporters")
                        .withStyle(ChatFormatting.AQUA));
            }
        }
    }

    public ResourceLocation getSupportCardID(ItemStack stack) {
        if (stack.getOrCreateTag().contains("support_card"))
            return ResourceLocation.tryParse(stack.getOrCreateTag().getString("support_card"));
        return SupportCardRegistry.BLANK_CARD.getId();
    }

    public SupportCard getSupportCard(Level level, ItemStack stack) {
        ResourceLocation cardID = this.getSupportCardID(stack);
        if (level == null || !UmapyoiAPI.getSupportCardRegistry(level).containsKey(cardID))
            return SupportCardRegistry.BLANK_CARD.get();
        return UmapyoiAPI.getSupportCardRegistry(level).get(cardID);
    }

    @Override
    public boolean isConsumable(Level level, ItemStack stack) {
        return false;
    }

    @Override
    public GachaRanking getSupportLevel(Level level, ItemStack stack) {
        if (level == null)
            return GachaRanking.R;
        return this.getSupportCard(level, stack).getGachaRanking();
    }

    @Override
    public SupportType getSupportType(Level level, ItemStack stack) {
        return this.getSupportCard(level, stack).getSupportType();
    }

    @Override
    public List<SupportStack> getSupports(Level level, ItemStack stack) {
        return Suppliers.memoize(this.getSupportCard(level, stack)::getSupportStacks).get();
    }

    @Override
    public Predicate<ItemStack> canSupport(Level level, ItemStack stack) {
        return itemstack -> {
            if (level == null)
                return false;
            if (itemstack.getItem() instanceof UmaSoulItem) {
                UmaData data = UmapyoiAPI.getUmaDataRegistry(level).get(UmaSoulUtils.getName(itemstack));
                return !(this.getSupportCard(level, stack).getSupporters().contains(data.getIdentifier()));
            }
            if (itemstack.getItem()instanceof SupportCardItem other) {
                return this.checkSupports(level, stack, itemstack);
            }
            return true;
        };
    }

    public boolean checkSupports(Level level, ItemStack stack, ItemStack other) {
        if (stack.getItem()instanceof SupportCardItem otherItem) {
            for (ResourceLocation name : this.getSupportCard(level, stack).getSupporters()) {
                if (otherItem.getSupportCard(level, stack).getSupporters().contains(name))
                    return false;
            }
        }
        return true;
    }
}
