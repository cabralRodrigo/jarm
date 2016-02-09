package cabralrodrigo.mc.mods.jarm.common.item;

import cabralrodrigo.mc.mods.jarm.common.registry.util.IVariantRegistrable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public abstract class ItemJarmVariantBase extends ItemJarmBase implements IVariantRegistrable {
    protected Map<String, Integer> variants;

    protected ItemJarmVariantBase(String name, Map<String, Integer> variants) {
        super(name);
        this.variants = variants;
        this.setHasSubtypes(true);
    }

    @Override
    public Map<String, Integer> getVariants() {
        return variants;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        if (this.hasSubtypes)
            for (Map.Entry<String, Integer> variant : this.getVariants().entrySet()) {
                subItems.add(new ItemStack(this, 1, variant.getValue()));
            }
        else
            super.getSubItems(itemIn, tab, subItems);
    }
}
