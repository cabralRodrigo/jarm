package cabralrodrigo.mc.mods.jarm.common.registry;

import cabralrodrigo.mc.mods.jarm.common.item.misc.ItemResource;
import cabralrodrigo.mc.mods.jarm.common.util.ItemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
    public static void init() {

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.item_resource.createItemStack(ItemResource.ResourceType.SUPER_GLUE), new Object[]{
                "slimeball",
                "cropWheat",
                Items.milk_bucket
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.super_fluffy_boots, new Object[]{
                "I I",
                "I I",
                "WGW",
                'I', "ingotIron",
                'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE),
                'G', ModItems.item_resource.createItemStack(ItemResource.ResourceType.SUPER_GLUE)
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.wool_card, new Object[]{
                "LGI",
                "S  ",
                'L', "plankWood",
                'G', ModItems.item_resource.createItemStack(ItemResource.ResourceType.SUPER_GLUE),
                'I', ModItems.item_resource.createItemStack(ItemResource.ResourceType.IRON_SPIKES),
                'S', "stickWood"
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.amulet_tepeport, new Object[]{
                "DLE",
                "RPR",
                "ELD",
                'D', "gemDiamond",
                'L', "blockLapis",
                'E', Items.ender_pearl,
                'R', "blockRedstone",
                'P', ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE)
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.super_fluffy_chestplate, new Object[]{
                "FFF",
                "DCD",
                "FFF",
                'F', Items.feather,
                'D', "gemDiamond",
                'C', Items.iron_chestplate
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.amulet_storage, new Object[]{
                "CCC",
                "CPC",
                "CCC",
                'C', "chestWood",
                'P', ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE)
        }));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.amulet_ender_storage, new Object[]{
                ModItems.amulet_storage,
                "chestEnder"
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.amulet_magnetic, new Object[]{
                "RIL",
                "RSL",
                "RIL",
                'R', "dustRedstone",
                'I', "ingotIron",
                'L', "gemLapis",
                'S', ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE)
        }));

        GameRegistry.addRecipe(new ItemStack(ModItems.seed_bag), new Object[]{
                "LLL",
                "L L",
                "LLL",
                'L', Items.leather
        });

        GameRegistry.addRecipe(new ItemStack(ModItems.amulet_potion), new Object[]{
                "PPP",
                "PSP",
                "PPP",
                'P', new ItemStack(Items.potionitem, 1, OreDictionary.WILDCARD_VALUE),
                'S', ModItems.amulet_storage
        });

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.amulet_teleposer), new Object[]{
                ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE),
                "gemDiamond"
        }));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.amulet_cage), new Object[]{
                ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE),
                "gemDiamond",
                "slimeball",
                Items.rotten_flesh,
                Items.bone,
                Items.spider_eye,
                Items.gunpowder
        }));

        FurnaceRecipes.instance().addSmeltingRecipeForBlock(Blocks.stone, ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE), 0F);
    }

    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Detonate event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            int stackSizeToSpawn = 0;

            for (Entity entity : event.getAffectedEntities())
                if (entity instanceof EntityItem) {
                    ItemStack stack = ((EntityItem) entity).getEntityItem();
                    if (stack != null) {
                        if (stack.getItem() == Items.iron_ingot) {
                            stackSizeToSpawn += stack.stackSize * 4;
                            entity.setDead();
                        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.iron_block)) {
                            stackSizeToSpawn += stack.stackSize * 9;
                            entity.setDead();
                        }
                    }
                }

            while (stackSizeToSpawn > 0) {
                ItemStack stack = ModItems.item_resource.createItemStack(ItemResource.ResourceType.IRON_SPIKES);
                stack.stackSize = Math.min(stack.getMaxStackSize(), stackSizeToSpawn);

                stackSizeToSpawn -= stack.stackSize;
                ItemHelper.spawnItemStack(event.world, new BlockPos(event.explosion.getPosition()), stack);
            }
        }

    }
}
