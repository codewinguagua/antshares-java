package AntShares;

import java.io.IOException;
import java.util.Arrays;

import AntShares.IO.*;

public abstract class UIntBase implements Serializable
{
    protected byte[] data_bytes;

    protected UIntBase(int bytes, byte[] value)
    {
        if (value == null)
        {
            this.data_bytes = new byte[bytes];
            return;
        }
        if (value.length != bytes)
            throw new IllegalArgumentException();
        this.data_bytes = value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (!(obj instanceof UIntBase))
        {
            return false;
        }
        UIntBase other = (UIntBase) obj;
        return Arrays.equals(this.data_bytes, other.data_bytes);
    }

    @Override
    public int hashCode()
    {
        //BitConverter.ToInt32(data_bytes, 0)
        int v = 0;
        v |= data_bytes[3];
        v <<= 8;
        v |= data_bytes[2];
        v <<= 8;
        v |= data_bytes[1];
        v <<= 8;
        v |= data_bytes[0];
        return v;
    }

    public byte[] toArray()
    {
        return data_bytes;
    }

// TODO
//    /**
//     * 转为16进制字符串
//     * @return 返回16进制字符串
//     */
//    @Override
//    public String toString()
//    {
//        return data_bytes.Reverse().ToHexString();
//    }
    
    @Override
    public void serialize(BinaryWriter writer) throws IOException
    {
    	writer.write(data_bytes);
    }
    
    @Override
    public void deserialize(BinaryReader reader) throws IOException
    {
    	reader.read(data_bytes);
    }
}
