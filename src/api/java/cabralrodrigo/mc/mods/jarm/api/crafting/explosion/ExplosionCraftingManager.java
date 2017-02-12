package cabralrodrigo.mc.mods.jarm.api.crafting.explosion;

import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

public class ExplosionCraftingManager {
    private static ExplosionCraftingManager instance = new ExplosionCraftingManager();
    private List<ExplosionRecipe> recipes = new ArrayList<ExplosionRecipe>();

    private ExplosionCraftingManager() {
    }

    public static ExplosionCraftingManager instance() {
        return instance;
    }

    public void addRecipe(ExplosionRecipe recipe) {
        this.recipes.add(recipe);
    }

    @SubscribeEvent
    public void onExplosionDetonate(ExplosionEvent.Detonate event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            for (ExplosionRecipe recipe : this.recipes) {
                boolean recipeCrafted;

                do recipeCrafted = recipe.tryCraft(event);
                while (recipeCrafted);
            }

    }
}
