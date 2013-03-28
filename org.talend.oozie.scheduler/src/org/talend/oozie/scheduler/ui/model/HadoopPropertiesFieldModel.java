package org.talend.oozie.scheduler.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;

public class HadoopPropertiesFieldModel extends ExtendedTableModel<HadoopPropertiesType> {

    public HadoopPropertiesFieldModel(String name) {
        super(name);
        setProperties(new ArrayList<HadoopPropertiesType>());
    }

    public HadoopPropertiesFieldModel(List<HadoopPropertiesType> conditionTypeList, String name) {
        super(name);
        setProperties(conditionTypeList);
    }

    public void setProperties(List<HadoopPropertiesType> properties) {
        registerDataList((List<HadoopPropertiesType>) properties);
    }

    public HadoopPropertiesType createHadoopPropertiesType() {
        return new HadoopPropertiesType();
    }
}
