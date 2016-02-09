package cabralrodrigo.mc.mods.jarm.common.registry;

import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.item.amulet.*;
import cabralrodrigo.mc.mods.jarm.common.item.misc.*;

public class ModItems {

    public static ItemSuperFluffyBoots super_fluffy_boots;
    public static ItemSuperFluffyChestplate super_fluffy_chestplate;
    public static ItemWoolCard wool_card;
    public static ItemResource item_resource;
    public static ItemOrbOfSin orb_of_sin;
    public static ItemAmuletTeleport amulet_tepeport;
    public static ItemAmuletStorage amulet_storage;
    public static ItemAmuletEnderStorage amulet_ender_storage;
    public static ItemAmuletMagnetic amulet_magnetic;
    public static ItemSeedBag seed_bag;
    public static ItemAmuletExperience amulet_experience;
    public static ItemAmuletPotion amulet_potion;
    public static ItemAmuletTeleposer amulet_teleposer;

    public static void preInit() {
        Jarm.proxy.registerItem(super_fluffy_chestplate = new ItemSuperFluffyChestplate());
        Jarm.proxy.registerItem(super_fluffy_boots = new ItemSuperFluffyBoots());
        Jarm.proxy.registerItem(wool_card = new ItemWoolCard());
        Jarm.proxy.registerItem(orb_of_sin = new ItemOrbOfSin());
        Jarm.proxy.registerItem(amulet_tepeport = new ItemAmuletTeleport());
        Jarm.proxy.registerItem(amulet_storage = new ItemAmuletStorage());
        Jarm.proxy.registerItem(amulet_ender_storage = new ItemAmuletEnderStorage());
        Jarm.proxy.registerItem(amulet_magnetic = new ItemAmuletMagnetic());
        Jarm.proxy.registerItem(item_resource = new ItemResource());
        Jarm.proxy.registerItem(seed_bag = new ItemSeedBag());
        Jarm.proxy.registerItem(amulet_experience = new ItemAmuletExperience());
        Jarm.proxy.registerItem(amulet_potion = new ItemAmuletPotion());
        Jarm.proxy.registerItem(amulet_teleposer = new ItemAmuletTeleposer());
    }
}
