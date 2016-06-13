package org.talend.cloudera.navigator.api.entity;

import org.talend.cloudera.navigator.api.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.entities.DatasetField;

/**
 * 
 * Represents input component's schemas as Cloudera Navigator entities
 *
 */
@MClass(model = "talend_field")
public class TalendField extends DatasetField {

    public TalendField() {
        super();
        setSourceType(SourceType.SDK);
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
