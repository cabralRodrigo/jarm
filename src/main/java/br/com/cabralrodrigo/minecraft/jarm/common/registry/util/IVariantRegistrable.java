package br.com.cabralrodrigo.minecraft.jarm.common.registry.util;

import java.util.Map;

public interface IVariantRegistrable extends IRegistrable {
    Map<String, Integer> getVariants();
}
