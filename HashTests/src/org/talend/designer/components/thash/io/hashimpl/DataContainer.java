package org.talend.designer.components.thash.io.hashimpl;
class DataContainer {

    long cursorPosition;

    Object object;

    byte[] data;

    public void reset() {
        object = null;
        data = null;
    }
}
