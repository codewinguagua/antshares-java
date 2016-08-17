package AntShares.Implementations.Wallets.EntityFramework;

public class Account
{
    private byte[] privateKeyEncrypted;
    private byte[] publicKeyHash;
    
    public byte[] getPrivateKeyEncrypted() {
        return privateKeyEncrypted;
    }
    public void setPrivateKeyEncrypted(byte[] privateKeyEncrypted) {
        this.privateKeyEncrypted = privateKeyEncrypted;
    }
    public byte[] getPublicKeyHash() {
        return publicKeyHash;
    }
    public void setPublicKeyHash(byte[] publicKeyHash) {
        this.publicKeyHash = publicKeyHash;
    }
}