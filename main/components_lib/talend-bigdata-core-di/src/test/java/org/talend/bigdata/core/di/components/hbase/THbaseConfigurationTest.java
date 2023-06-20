package org.talend.bigdata.core.di.components.hbase;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class THbaseConfigurationTest {

    @Test
    public void checkConfiguration() {
        Map<String, Object> globalMap = new HashMap<>();

        ImmutableTHbaseConnection immutableTHbaseConfiguration = ImmutableTHbaseConnection.builder()
                .globalMap(globalMap)
                .putHBaseConfigurationOptions("hbase.zookeeper.quorum","localhost")
                .putHBaseConfigurationOptions("hbase.zookeeper.property.clientPort","2108")
                .existingConnectionName("")
                .isUseExistingConnection(false)
                .build();

        immutableTHbaseConfiguration.createConfiguration();
        immutableTHbaseConfiguration.getConfiguration();

        Assertions.assertThat(immutableTHbaseConfiguration.getConfiguration()).isNotEmpty();

    }


}