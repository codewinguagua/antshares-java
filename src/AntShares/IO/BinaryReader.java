package AntShares.IO;

import java.io.*;
import java.nio.*;

public class BinaryReader
{
	private DataInputStream reader;
	private byte[] array = new byte[8];
	private ByteBuffer buffer = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN);
	
	public BinaryReader(InputStream stream)
	{
		this.reader = new DataInputStream(stream);
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
	
	public double readDouble() throws IOException
	{
		reader.readFully(array, 0, 8);
		return buffer.getDouble(0);
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
	
	public short readShort() throws IOException
	{
		reader.readFully(array, 0, 2);
		return buffer.getShort(0);
	}
}
