package br.com.cabralrodrigo.minecraft.jarm.common.item;

import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IVariantRegistrable;

import java.util.Map;

public abstract class ItemAmuletVariantBase extends ItemAmuletBase implements IVariantRegistrable {

    private Map<String, Integer> variants;

    protected ItemAmuletVariantBase(String name, Map<String, Integer> variants) {
        super(name);
        this.variants = variants;
    }

    @Override
    public Map<String, Integer> getVariants() {
        return this.variants;
    }
}
