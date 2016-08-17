package AntShares.Network.Payloads;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.UInt256;
import AntShares.IO.Serializable;
import AntShares.Network.InventoryType;

public class InvPayload implements Serializable
{
    public InventoryVector[] Inventories;

    private InvPayload(InventoryVector[] vcts)
    {
        Inventories = vcts;
    }
    
    public static InvPayload Create(InventoryVector[] vectors)
    {
        return new InvPayload(vectors);
    }

    public static InvPayload Create(InventoryType type, UInt256[] hashes)
    {
        return new InvPayload(null);
        // TODO
//        {
//            Inventories = hashes.Select(p => new InventoryVector { Type = type, Hash = p }).ToArray()
//        };
    }

    @Override
    public void Deserialize(InputStream reader)
    {
        //TODO
        //Inventories = reader.ReadSerializableArray<InventoryVector>();
    }

    @Override
    public void Serialize(OutputStream writer)
    {
        // TODO
        //writer.Write(Inventories);
    }
}
