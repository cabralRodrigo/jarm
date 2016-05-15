package cabralrodrigo.mc.mods.jarm.common.crafting.stamper;


import java.util.ArrayList;
import java.util.List;

public class StamperCraftingManager {
    private static StamperCraftingManager instance = new StamperCraftingManager();
    private List<StamperRecipe> recipes = new ArrayList<StamperRecipe>();

    private StamperCraftingManager() {
    }

    public static StamperCraftingManager instance() {
        return instance;
    }

    public void addRecipe(StamperRecipe recipe) {
        this.recipes.add(recipe);
    }
}