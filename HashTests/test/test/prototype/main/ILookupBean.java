package test.prototype.main;

public interface ILookupBean {

    public byte[] toKeysData();

    public void loadKeysData(byte[] keysData);

    public byte[] toValuesData();

    public void loadValuesData(byte[] valuesData);

}
