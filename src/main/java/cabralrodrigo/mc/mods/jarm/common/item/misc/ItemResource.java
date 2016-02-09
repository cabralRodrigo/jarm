package cabralrodrigo.mc.mods.jarm.common.item.misc;

import cabralrodrigo.mc.mods.jarm.common.item.ItemJarmVariantBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemResource extends ItemJarmVariantBase {

    public ItemResource() {
        super(LibItems.ITEM_RESOURCE, ResourceType.getAllInVariantForm());
    }

    public static ResourceType getTypeFromItemStack(ItemStack stack) {
        if (stack != null && stack.getItem() == ModItems.item_resource)
            for (ResourceType type : ResourceType.getAllTypes())
                if (stack.getItemDamage() == type.getDamage())
                    return type;

        return ResourceType.UNKNOWN;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + ItemResource.getTypeFromItemStack(stack).getName();
    }

    public ItemStack createItemStack(ResourceType type) {
        return new ItemStack(this, 1, type.getDamage());
    }

    public enum ResourceType {
        UNKNOWN(-1, "unknown"),
        IRON_SPIKES(0, "iron_spikes"),
        SUPER_GLUE(1, "super_glue"),
        STONE_PLATE(2, "stone_plate");

        private final int damage;
        private final String name;

        private ResourceType(int damage, String name) {
            this.damage = damage;
            this.name = name;
        }

        public static ArrayList<ResourceType> getAllTypes() {
            ArrayList<ResourceType> types = new ArrayList<ResourceType>(Arrays.asList(ResourceType.values()));
            types.remove(UNKNOWN);

            return types;
        }

        public static Map<String, Integer> getAllInVariantForm() {
            Map<String, Integer> variants = new HashMap<String, Integer>();
            for (ResourceType type : getAllTypes())
                variants.put(type.getName(), type.getDamage());

            return variants;
        }

        public int getDamage() {
            return this.damage;
        }

        public String getName() {
            return this.name;
        }
    }
}
