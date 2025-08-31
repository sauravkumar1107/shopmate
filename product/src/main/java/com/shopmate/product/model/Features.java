package com.shopmate.product.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,        // use logical name, not class name
        include = JsonTypeInfo.As.PROPERTY,
        property = "category"                  // JSON will have a "type" field
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhoneFeatures.class, name = "PhoneFeatures"),
})
public interface Features {
}
