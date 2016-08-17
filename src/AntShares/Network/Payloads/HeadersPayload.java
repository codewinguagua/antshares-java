package AntShares.Network.Payloads;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.Core.Block;
import AntShares.IO.Serializable;

public class HeadersPayload implements Serializable
{
    public Block[] Headers;
    
    private HeadersPayload(Block[] hds)
    {
        Headers = hds;
    }

    public static HeadersPayload Create(Block[] headers)
    {
        return new HeadersPayload(headers);
    }

    @Override
    public void Deserialize(InputStream reader)
    {
        // TODO
//        Headers = reader.ReadSerializableArray<Block>();
//        if (Headers.Any(p => !p.IsHeader))
//            throw new FormatException();
    }

    @Override
    public void Serialize(OutputStream writer)
    {
        //writer.Write(Headers);
    }
}
