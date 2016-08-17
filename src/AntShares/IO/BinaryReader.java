package AntShares.IO;

import java.io.*;
import java.nio.*;
import java.util.Vector;

public class BinaryReader
{
	private DataInputStream reader;
	private byte[] array = new byte[8];
	private ByteBuffer buffer = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN);
	
	public BinaryReader(InputStream stream)
	{
		this.reader = new DataInputStream(stream);
	}

	public void read(byte[] buffer) throws IOException
	{
		reader.readFully(buffer);
	}
	
	public void read(byte[] buffer, int index, int length) throws IOException
	{
		reader.readFully(buffer, index, length);
	}
	
	public boolean readBoolean() throws IOException
	{
		return reader.readBoolean();
	}
	
	public byte readByte() throws IOException
	{
		return reader.readByte();
	}
	
	public byte[] readBytes(int count) throws IOException
	{
		byte[] buffer = new byte[count];
		reader.readFully(buffer);
		return buffer;
	}
	
	public double readDouble() throws IOException
	{
		reader.readFully(array, 0, 8);
		return buffer.getDouble(0);
	}
	
	public String readFixedString(int length) throws IOException
	{
		byte[] data = readBytes(length);
		int count = -1;
		while (data[++count] != 0);
		return new String(data, 0, count, "UTF-8");
	}
	
	public float readFloat() throws IOException
	{
		reader.readFully(array, 0, 4);
		return buffer.getFloat(0);
	}
	
	public int readInt() throws IOException
	{
		reader.readFully(array, 0, 4);
		return buffer.getInt(0);
	}
	
	public long readLong() throws IOException
	{
		reader.readFully(array, 0, 8);
		return buffer.getLong(0);
	}
	
	public <T extends Serializable> T readSerializable(Class<T> t) throws InstantiationException, IllegalAccessException, IOException
	{
		T obj = t.newInstance();
		obj.deserialize(this);
		return obj;
	}
	
	public <T extends Serializable> T[] readSerializableArray(Class<T> t) throws InstantiationException, IllegalAccessException, IOException
	{
		Vector<T> vector = new Vector<T>((int)readVarInt(0x10000000));
		for (int i = 0; i < vector.capacity(); i++)
		{
			T obj = t.newInstance();
			obj.deserialize(this);
			vector.addElement(obj);
		}
		return vector.toArray(null);
	}
	
	public short readShort() throws IOException
	{
		reader.readFully(array, 0, 2);
		return buffer.getShort(0);
	}
	
	public byte[] readVarBytes() throws IOException
	{
		return readVarBytes(0X7fffffc7);
	}
	
	public byte[] readVarBytes(int max) throws IOException
	{
		return readBytes((int)readVarInt(max));
	}
	
	public long readVarInt() throws IOException
	{
		return readVarInt(Long.MAX_VALUE);
	}
	
	public long readVarInt(long max) throws IOException
	{
        long fb = Byte.toUnsignedLong(readByte());
        long value;
        if (fb == 0xFD)
            value = Short.toUnsignedLong(reader.readShort());
        else if (fb == 0xFE)
            value = Integer.toUnsignedLong(reader.readInt());
        else if (fb == 0xFF)
            value = readLong();
        else
            value = fb;
        if (Long.compareUnsigned(value, max) > 0) throw new IOException();
        return value;
	}
	
	public String readVarString() throws IOException
	{
		return new String(readVarBytes(), "UTF-8");
	}
}
