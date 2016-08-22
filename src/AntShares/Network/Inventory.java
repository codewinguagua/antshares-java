package AntShares.Network;

import java.io.*;

import AntShares.UInt256;
import AntShares.Core.Signable;
import AntShares.Cryptography.Digest;
import AntShares.IO.BinaryWriter;

public abstract class Inventory implements Signable
{
    //[NonSerialized]
    private UInt256 _hash = null;
    public UInt256 hash()
    {
        if (_hash == null)
        {
			_hash = new UInt256(Digest.hash256(getHashData()));
        }
        return _hash;
    }

    public abstract InventoryType getInventoryType();

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
