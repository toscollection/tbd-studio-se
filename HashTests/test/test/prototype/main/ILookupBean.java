package test.prototype.main;





public interface ILookupBean {

    public byte[] toKeysData(int valuesDataSize);
    
    public int loadKeysData(byte[] keysData);
    
    public byte[] toValuesData();

    public void loadValuesData(byte[] valuesData);

    
}
