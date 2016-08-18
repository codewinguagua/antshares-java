package AntShares.Network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import AntShares.UInt256;
import AntShares.Core.Signable;
import AntShares.IO.BinaryWriter;

public abstract class Inventory implements Signable
{
    //[NonSerialized]
    private UInt256 _hash = null;
    public UInt256 hash()
    {
        if (_hash == null)
        {
        	try
        	{
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				_hash = new UInt256(md.digest(md.digest(getHashData())));
			}
        	catch (NoSuchAlgorithmException e)
        	{
			}
        }
        return _hash;
    }

    public abstract InventoryType inventoryType();

    protected byte[] getHashData()
    {
    	try (ByteArrayOutputStream ms = new ByteArrayOutputStream())
    	{
	    	try (BinaryWriter writer = new BinaryWriter(ms))
	        {
	            serializeUnsigned(writer);
	            writer.flush();
	            return ms.toByteArray();
	        }
    	}
    	catch (IOException ex)
    	{
    		throw new UnsupportedOperationException(ex);
    	}
    }

    public abstract boolean verify();
}
