package com.shopmate.product.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,        // use logical name, not class name
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"                  // JSON will have a "type" field
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhoneFeatures.class, name = "PhoneFeatures"),
})
public abstract class Features {
}
