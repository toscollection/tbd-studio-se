package org.talend.designer.bigdata.di.components.thbase;

import java.util.HashMap;
import java.util.Map;

public class THBaseConfiguration {

    public Map<String, String> getConnectionConfiguration(THbase tHbase) {

        Map<String, String> hbaseConf = new HashMap<>();
        if (tHbase.isUseExistingConnection()) return hbaseConf;

        hbaseConf.put("\"hbase.zookeeper.quorum\"", tHbase.getZookeeperUrl());
        hbaseConf.put("\"hbase.zookeeper.property.clientPort\"",tHbase.getZookeeperPort());
        hbaseConf.put("\"hbase.cluster.distributed\"","\"true\"");

        if (tHbase.isZNodeParentSet()){
            hbaseConf.put("\"zookeeper.znode.parent\"",tHbase.getZNodeParent());
        }
        if (tHbase.getHbaseDistrib() != null) {
            if (tHbase.getHbaseDistrib().doSupportKerberos() && tHbase.useKrb()) {
                hbaseConf.put("\"hbase.master.kerberos.principal\"", tHbase.getHbaseMasterPrincipal());
                hbaseConf.put("\"hbase.regionserver.kerberos.principal\"", tHbase.getHbaseRegionServerPrincipal());
                hbaseConf.put("\"hbase.security.authorization\"", "\"true\"");
                hbaseConf.put("\"hbase.security.authentication\"", "\"kerberos\"");

            }
        }
        hbaseConf.putAll(tHbase.getHbaseParameters());

        return hbaseConf;
    }

    public String getKeytab(THbase tHbase) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (tHbase.getHbaseDistrib() != null) {
            if(tHbase.getHbaseDistrib().doSupportKerberos() && tHbase.useKrb()){
                if(tHbase.useKeytab()){
                    stringBuilder.append("org.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(");
                    stringBuilder.append(tHbase.getUsePrincipal());
                    stringBuilder.append(",");
                    stringBuilder.append(tHbase.getKeytabPath());
                    stringBuilder.append(");");
                }
            }
        }
        return stringBuilder.toString();
    }
}
