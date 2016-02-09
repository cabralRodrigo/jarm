package cabralrodrigo.mc.mods.jarm.common.handler;

import cabralrodrigo.mc.mods.jarm.common.util.TextHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerItemFood {

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (GuiScreen.isShiftKeyDown()) {
            ItemStack stack = event.itemStack;
            if (stack != null && stack.getItem() instanceof ItemFood) {
                ItemFood food = (ItemFood) stack.getItem();
                int heal = food.getHealAmount(stack);
                float saturation = food.getSaturationModifier(stack);

                event.toolTip.add(TextHelper.translate("misc", "info.food.heal", heal));
                event.toolTip.add(TextHelper.translate("misc", "info.food.saturation", saturation));
            }
        }
    }
}
