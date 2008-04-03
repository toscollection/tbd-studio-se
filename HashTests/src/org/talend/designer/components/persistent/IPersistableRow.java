package org.talend.designer.components.persistent;



public interface IPersistableRow {

    public byte[] toData();

    public void loadData(byte[] data);

}
