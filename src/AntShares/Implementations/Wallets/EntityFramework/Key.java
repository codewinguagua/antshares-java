package AntShares.Implementations.Wallets.EntityFramework;

public class Key {
    private String Name;// { get; set; }
    private byte[] Value;// { get; set; }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public byte[] getValue() {
        return Value;
    }
    public void setValue(byte[] value) {
        Value = value;
    }

}
