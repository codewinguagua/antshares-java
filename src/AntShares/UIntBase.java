package AntShares;

import java.io.Serializable;
import java.util.Arrays;

public abstract class UIntBase implements Serializable
{
	private static final long serialVersionUID = 5471513971344249980L;
	
	byte[] data_bytes;

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

    public byte[] ToArray()
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

    public static boolean equals(UIntBase left, UIntBase right)
    {
        if (left == null || right == null)
    	{
    		return false;
    	}
    	if (left == right)
    	{
    		return true;
    	}
        return left.equals(right);
    }

}
