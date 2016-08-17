package AntShares.Network.Payloads;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.IO.Serializable;

public class NetworkAddressWithTime implements Serializable
{
    public static final long NODE_NETWORK = 1;

    public int Timestamp;
    public long Services;
    // TODO public IPEndPoint EndPoint;
    public Object EndPoint;

    private NetworkAddressWithTime(Object /*IPEndPoint*/ endpoint, long services, int timestamp)
    {
        EndPoint = endpoint;
        Services = services;
        Timestamp = timestamp;
    }

    public static NetworkAddressWithTime Create(Object /*IPEndPoint*/ endpoint, long services, int timestamp)
    {
        return new NetworkAddressWithTime(endpoint, services, timestamp);
    }

    @Override
    public void Deserialize(InputStream reader)
    {
        // TODO
//        Timestamp = reader.ReadUInt32();
//        Services = reader.ReadUInt64();
//        IPAddress address = new IPAddress(reader.ReadBytes(16));
//        ushort port = BitConverter.ToUInt16(reader.ReadBytes(2).Reverse().ToArray(), 0);
//        EndPoint = new IPEndPoint(address, port);
    }

    @Override
    public void Serialize(OutputStream writer)
    {
        // TODO
//        writer.Write(Timestamp);
//        writer.Write(Services);
//        writer.Write(EndPoint.Address.GetAddressBytes());
//        writer.Write(BitConverter.GetBytes((ushort)EndPoint.Port).Reverse().ToArray());
    }
}
