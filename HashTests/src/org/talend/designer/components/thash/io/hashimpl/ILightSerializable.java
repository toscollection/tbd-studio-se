package org.talend.designer.components.thash.io.hashimpl;

public interface ILightSerializable extends Comparable {

    public ILightSerializable createInstance(byte[] byteArray);

    public byte[] toByteArray();

}
