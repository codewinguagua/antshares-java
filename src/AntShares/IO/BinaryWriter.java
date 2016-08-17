package AntShares.IO;

import java.io.*;
import java.nio.*;

public class BinaryWriter
{
	private DataOutputStream writer;
	private byte[] array = new byte[8];
	private ByteBuffer buffer = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN);
	
	public BinaryWriter(OutputStream stream)
	{
		this.writer = new DataOutputStream(stream);
	}
	
	public void write(byte[] buffer) throws IOException
	{
		writer.write(buffer);
	}

	public void writeBoolean(boolean v) throws IOException
	{
		writer.writeBoolean(v);
	}
	
	public void writeByte(byte v) throws IOException
	{
		writer.writeByte(v);
	}
	
	public void writeDouble(double v) throws IOException
	{
		buffer.putDouble(0, v);
		writer.write(array, 0, 8);
	}
	
	public void writeFloat(float v) throws IOException
	{
		buffer.putFloat(0, v);
		writer.write(array, 0, 4);
	}
	
	public void writeInt(int v) throws IOException
	{
		buffer.putInt(0, v);
		writer.write(array, 0, 4);
	}
	
	public void writeLong(long v) throws IOException
	{
		buffer.putLong(0, v);
		writer.write(array, 0, 8);
	}
	
	public void writeShort(short v) throws IOException
	{
		buffer.putShort(0, v);
		writer.write(array, 0, 2);
	}
	
	public void writeVarInt(long v) throws IOException
	{
        if (v < 0)
            throw new IllegalArgumentException();
        if (v < 0xFD)
        {
            writeByte((byte)v);
        }
        else if (v <= 0xFFFF)
        {
            writeByte((byte)0xFD);
            writeShort((short)v);
        }
        else if (v <= 0xFFFFFFFF)
        {
        	writeByte((byte)0xFE);
            writeInt((int)v);
        }
        else
        {
            writeByte((byte)0xFF);
            writeLong(v);
        }
	}
}
