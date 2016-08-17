package AntShares.Network.Payloads;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.IO.Serializable;

public class AddrPayload implements Serializable
{
    public Object /* TODO NetworkAddressWithTime*/[] AddressList;

    private AddrPayload(Object[] addressList) {
        super();
        AddressList = addressList;
    }

    public static AddrPayload Create(/*NetworkAddressWithTime*/ Object[] addresses)
    {
        return new AddrPayload(addresses);
    }

    @Override
    public void Deserialize(InputStream reader)
    {
        // TODO
        //this.AddressList = reader.ReadSerializableArray<NetworkAddressWithTime>();
    }

    @Override
    public void Serialize(OutputStream writer)
    {
        // TODO
        //writer.Write(AddressList);
    }
}