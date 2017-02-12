package br.com.cabralrodrigo.minecraft.jarm.common.handler;

import br.com.cabralrodrigo.minecraft.jarm.common.util.Translator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerItemFood {

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (GuiScreen.isShiftKeyDown()) {
            ItemStack stack = event.getItemStack();
            if (stack.getItem() instanceof ItemFood) {
                ItemFood food = (ItemFood) stack.getItem();
                int heal = food.getHealAmount(stack);
                float saturation = food.getSaturationModifier(stack);

                event.getToolTip().add(Translator.translate("misc", "info.food.heal", heal));
                event.getToolTip().add(Translator.translate("misc", "info.food.saturation", saturation));
            }
        }
    }
}
