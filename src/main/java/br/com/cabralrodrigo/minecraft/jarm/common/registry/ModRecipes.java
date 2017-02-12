package br.com.cabralrodrigo.minecraft.jarm.common.registry;

import br.com.cabralrodrigo.minecraft.jarm.common.item.misc.ItemResource;
import br.com.cabralrodrigo.minecraft.jarm.api.explosion.ExplosionCraftingManager;
import br.com.cabralrodrigo.minecraft.jarm.api.explosion.ExplosionRecipe;
import br.com.cabralrodrigo.minecraft.jarm.api.stamper.StamperCraftingManager;
import br.com.cabralrodrigo.minecraft.jarm.api.stamper.StamperRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
    public static void init() {

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.item_resource.createItemStack(ItemResource.ResourceType.SUPER_GLUE), new Object[]{
                "slimeball",
                "cropWheat",
                Items.MILK_BUCKET
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.super_fluffy_boots, new Object[]{
                "I I",
                "I I",
                "WGW",
                'I', "ingotIron",
                'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
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
                'E', Items.ENDER_PEARL,
                'R', "blockRedstone",
                'P', ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE)
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.super_fluffy_chestplate, new Object[]{
                "FFF",
                "DCD",
                "FFF",
                'F', Items.FEATHER,
                'D', "gemDiamond",
                'C', Items.IRON_CHESTPLATE
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
                'L', Items.LEATHER
        });

        GameRegistry.addRecipe(new ItemStack(ModItems.amulet_potion), new Object[]{
                "PPP",
                "PSP",
                "PPP",
                'P', new ItemStack(Items.POTIONITEM, 1, OreDictionary.WILDCARD_VALUE),
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
                Items.ROTTEN_FLESH,
                Items.BONE,
                Items.SPIDER_EYE,
                Items.GUNPOWDER
        }));

        FurnaceRecipes.instance().addSmeltingRecipeForBlock(Blocks.STONE, ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE), 0F);

        ExplosionCraftingManager.instance().addRecipe(new ExplosionRecipe()
                .addInputBlocks(Blocks.IRON_BLOCK)
                .addOutputItemStack(new ItemStack(ModItems.item_resource, 36, ItemResource.ResourceType.IRON_SPIKES.getDamage()))
        );

        ExplosionCraftingManager.instance().addRecipe(new ExplosionRecipe()
                .addInputBlocks(Blocks.IRON_ORE)
                .addOutputItemStack(new ItemStack(ModItems.item_resource, 4, ItemResource.ResourceType.IRON_SPIKES.getDamage()))
        );

        ExplosionCraftingManager.instance().addRecipe(new ExplosionRecipe()
                .addInputItems(ItemBlock.getItemFromBlock(Blocks.IRON_ORE))
                .addOutputItemStack(new ItemStack(ModItems.item_resource, 4, ItemResource.ResourceType.IRON_SPIKES.getDamage()))
        );

        ExplosionCraftingManager.instance().addRecipe(new ExplosionRecipe()
                .addInputItems(ItemBlock.getItemFromBlock(Blocks.IRON_BLOCK))
                .addOutputItemStack(new ItemStack(ModItems.item_resource, 36, ItemResource.ResourceType.IRON_SPIKES.getDamage()))
        );

        ExplosionCraftingManager.instance().addRecipe(new ExplosionRecipe()
                .addInputItems(Items.IRON_INGOT)
                .addOutputItemStack(new ItemStack(ModItems.item_resource, 4, ItemResource.ResourceType.IRON_SPIKES.getDamage()))
        );

        ExplosionCraftingManager.instance().addRecipe(new ExplosionRecipe()
                .addInputItems(ModItems.amulet_tepeport)
                .addOutputItems(ModItems.amulet_tepeport)
        );

        StamperCraftingManager.instance().addRecipe(new StamperRecipe(new ItemStack(ModItems.amulet_storage), 30,
                ModItems.item_resource.createItemStack(ItemResource.ResourceType.STONE_PLATE), new Object[]{
                Blocks.DIAMOND_BLOCK,
                Blocks.CHEST,
                Blocks.GOLD_BLOCK
        }));
    }
}
