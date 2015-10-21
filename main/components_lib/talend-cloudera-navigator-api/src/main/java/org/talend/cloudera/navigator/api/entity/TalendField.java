package org.talend.cloudera.navigator.api.entity;

import org.talend.cloudera.navigator.api.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.entities.DatasetField;

@MClass(model = "talend_field")
public class TalendField extends DatasetField {

    public TalendField() {
        super();
        setSourceType(SourceType.PLUGIN);
        setNamespace(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE);
    }

    public TalendField(String name, String type) {
        this();
        setName(name);
        setDataType(type);
    }

    @Override
    public String toString() {
        return "{\"name\":\"" + getName() + "\", \"dataType\":\"" + getDataType() + "\"}";
    }
}
