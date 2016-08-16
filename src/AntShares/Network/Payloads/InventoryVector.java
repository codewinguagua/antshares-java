package AntShares.Network.Payloads;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.UInt256;
import AntShares.IO.Serializable;
import AntShares.Network.InventoryType;

public class InventoryVector implements Serializable
{
    public InventoryType Type;
    public UInt256 Hash;

    @Override
    public void Deserialize(InputStream reader)
    {
    	// TODO
//        Type = (InventoryType)reader.ReadUInt32();
//        if (!Enum.IsDefined(typeof(InventoryType), Type))
//            throw new FormatException();
//        Hash = reader.ReadSerializable<UInt256>();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof InventoryVector)) return false;
        if (this == obj) return true;
        return Hash == ((InventoryVector) obj).Hash;
    }

    @Override
    public int hashCode()
    {
        return Hash.hashCode();
    }

    @Override
    public void Serialize(OutputStream writer)
    {
//        writer.Write((uint)Type);
//        writer.Write(Hash);
    }
}