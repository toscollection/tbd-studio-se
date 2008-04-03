package org.talend.designer.components.persistent;

public interface ILookupBean {

    public byte[] toKeysData();

    public void loadKeysData(byte[] keysData);

    public byte[] toValuesData();

    public void loadValuesData(byte[] valuesData);

}
