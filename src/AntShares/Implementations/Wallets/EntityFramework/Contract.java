package AntShares.Implementations.Wallets.EntityFramework;

public class Contract
{
    private String Type;// { get; set; }
    private byte[] RawData;// { get; set; }
    private byte[] ScriptHash;// { get; set; }
    private byte[] PublicKeyHash;// { get; set; }
    private Account Account;// { get; set; }
    
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public byte[] getRawData() {
        return RawData;
    }
    public void setRawData(byte[] rawData) {
        RawData = rawData;
    }
    public byte[] getScriptHash() {
        return ScriptHash;
    }
    public void setScriptHash(byte[] scriptHash) {
        ScriptHash = scriptHash;
    }
    public byte[] getPublicKeyHash() {
        return PublicKeyHash;
    }
    public void setPublicKeyHash(byte[] publicKeyHash) {
        PublicKeyHash = publicKeyHash;
    }
    public Account getAccount() {
        return Account;
    }
    public void setAccount(Account account) {
        Account = account;
    }
}
