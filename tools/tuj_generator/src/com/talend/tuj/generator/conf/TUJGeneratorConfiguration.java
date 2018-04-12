package com.talend.tuj.generator.conf;

import com.talend.tuj.generator.exception.UnknownDistributionException;
import com.talend.tuj.generator.utils.DistributionType;

import java.util.HashMap;

public class TUJGeneratorConfiguration extends HashMap<String, String> {
    public TUJGeneratorConfiguration() {
    }

    public DistributionType getDistributionName() throws UnknownDistributionException {
        try {
            return DistributionType.valueOf(get("distributionName"));
        } catch (IllegalArgumentException e) {
            throw new UnknownDistributionException();
        }
    }
}
