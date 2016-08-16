package AntShares.Wallets;

import java.util.Arrays;

import AntShares.UInt160;
import AntShares.Cryptography.Base58;
import AntShares.Cryptography.ECC.ECCurve;
import AntShares.Cryptography.ECC.ECPoint;

public class Account
{
    public final byte[] PrivateKey;
    public final ECPoint PublicKey;
    public final UInt160 PublicKeyHash;

    public Account(byte[] privateKey)
    {
        if (privateKey.length != 32 && privateKey.length != 96 && privateKey.length != 104)
            throw new IllegalArgumentException();
        this.PrivateKey = new byte[32];
        System.arraycopy(privateKey, privateKey.length - 32, PrivateKey, 0, 32);
        if (privateKey.length == 32)
        {
        	// TODO
            //this.PublicKey = ECCurve.Secp256r1.G * privateKey;
        	this.PublicKey = ECPoint.FromBytes(null, null);
        }
        else
        {
            this.PublicKey = ECPoint.FromBytes(privateKey, ECCurve.Secp256r1);
        }
        // TODO
        this.PublicKeyHash = new UInt160();
        //this.PublicKeyHash = PublicKey.EncodePoint(true).ToScriptHash();
        //ProtectedMemory.Protect(PrivateKey, MemoryProtectionScope.SameProcess);
    }

    // TODO
//    public IDisposable Decrypt()
//    {
//        return new ProtectedMemoryContext(PrivateKey, MemoryProtectionScope.SameProcess);
//    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Account)) return false;
        return PublicKeyHash.equals(((Account) obj).PublicKeyHash);
    }

    public String Export()
    {
    	// TODO
//        using (Decrypt())
//        {
            byte[] data = new byte[38];
            data[0] = (byte) 0x80;
            System.arraycopy(PrivateKey, 0, data, 1, 32);
            data[33] = (byte) 0x01;
            // TODO
            //byte[] checksum = data.Sha256(0, data.length - 4).Sha256();
            byte[] checksum = new byte[1];
            System.arraycopy(checksum, 0, data, data.length - 4, 4);
            String wif = Base58.Encode(data);
            Arrays.fill(data, (byte) 0);
            return wif;
//        }
    }

    @Override
    public int hashCode()
    {
        return PublicKeyHash.hashCode();
    }
}
