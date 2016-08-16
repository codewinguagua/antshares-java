package AntShares.Network.Payloads;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.UInt256;
import AntShares.IO.Serializable;

public class GetBlocksPayload implements Serializable
{
	public UInt256[] HashStart;
    public UInt256 HashStop;

    private GetBlocksPayload(UInt256[] hashStart, UInt256 hashStop) {
        HashStart = hashStart;
        HashStop = hashStop != null ? hashStop : UInt256.Zero;
	}

    public static GetBlocksPayload Create(UInt256[] hash_start, UInt256 hash_stop)
    {
        return new GetBlocksPayload(hash_start, hash_stop);
    }

    @Override
    public void Deserialize(InputStream reader)
    {
//        HashStart = reader.ReadSerializableArray<UInt256>();
//        HashStop = reader.ReadSerializable<UInt256>();
    }

    @Override
    public void Serialize(OutputStream writer)
    {
//        writer.Write(HashStart);
//        writer.Write(HashStop);
    }
}