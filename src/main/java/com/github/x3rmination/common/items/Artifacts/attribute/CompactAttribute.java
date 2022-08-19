package com.github.x3rmination.common.items.Artifacts.attribute;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

public class CompactAttribute {

    private final Attribute attribute;
    private final String name;
    private final float modifier;
    private final AttributeModifier.Operation operation;


    public CompactAttribute(Attribute attribute, String name, float modifier, AttributeModifier.Operation operation) {
        this.attribute = attribute;
        this.name = name;
        this.modifier = modifier;
        this.operation = operation;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getName() {
        return name;
    }

    public float getModifier() {
        return modifier;
    }

    public AttributeModifier.Operation getOperation() {
        return operation;
    }
}
