package org.talend.designer.components.persistent;
class DataContainer<V> {

    long cursorPosition;

    V object;

    byte[] data;

    public void reset() {
        object = null;
        data = null;
    }
}
