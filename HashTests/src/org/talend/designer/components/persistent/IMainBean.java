package org.talend.designer.components.persistent;



public interface IMainBean {

    public byte[] toData();

    public void loadData(byte[] data);

}
