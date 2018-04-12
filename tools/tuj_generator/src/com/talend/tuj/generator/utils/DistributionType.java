package com.talend.tuj.generator.utils;

public enum DistributionType {
    CDH("CLOUDERA"),
    HDP("HORTONWOKRS"),
    MAPR("MAPR"),
    DATAPROC("GOOGLE_CLOUD_DATAPROC"),
    HDI("MICROSOFT_HD_INSIGHT"),
    SPARK_LOCAL("SPARK_LOCAL");

    private String xmlDistributionName;

    DistributionType(String xmlDistributionName) {
        this.xmlDistributionName = xmlDistributionName;
    }

    public String getXmlDistributionName() {
        return xmlDistributionName;
    }
}
