package com.shopmate.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class PhoneFeatures  extends Features{
    private Map<String, String> connectivityFeatures;
    private Map<String, String> multimediaFeatures;
    private Map<String, String> callFeatures;
    private Map<String, String> displayFeatures;
    private Map<String, String> osProcessorFeatures;
    private Map<String, String> memoryStorageFeatures;
    private Map<String, String> batteryPowerFeatures;

}
