package AntShares.Core;

import java.io.IOException;

import AntShares.IO.*;

public class TransactionAttribute implements Serializable
{
	public byte usage;
	public byte[] data;
	
	@Override
	public void serialize(BinaryWriter writer) throws IOException
	{
        writer.writeByte(usage);
        if (usage == TransactionAttributeUsage.Script)
            writer.writeVarInt(data.length);
        else if (usage == TransactionAttributeUsage.CertUrl || usage == TransactionAttributeUsage.DescriptionUrl)
            writer.writeByte((byte)data.length);
        else if (usage == TransactionAttributeUsage.Description || Byte.toUnsignedInt(usage) >= Byte.toUnsignedInt(TransactionAttributeUsage.Remark))
            writer.writeByte((byte)data.length);
        if (usage == TransactionAttributeUsage.ECDH02 || usage == TransactionAttributeUsage.ECDH03)
            writer.write(data, 1, 32);
        else
            writer.write(data);
	}

	@Override
	public void deserialize(BinaryReader reader) throws IOException
	{
		usage = reader.readByte();
        if (usage == TransactionAttributeUsage.ContractHash || (Byte.toUnsignedInt(usage) >= Byte.toUnsignedInt(TransactionAttributeUsage.Hash1) && Byte.toUnsignedInt(usage) <= Byte.toUnsignedInt(TransactionAttributeUsage.Hash15)))
        {
            data = reader.readBytes(32);
        }
        else if (usage == TransactionAttributeUsage.ECDH02 || usage == TransactionAttributeUsage.ECDH03)
        {
            data = new byte[33];
            data[0] = usage;
            reader.read(data, 1, 32);
        }
        else if (usage == TransactionAttributeUsage.Script)
        {
            data = reader.readVarBytes(65535);
        }
        else if (usage == TransactionAttributeUsage.CertUrl || usage == TransactionAttributeUsage.DescriptionUrl)
        {
            data = reader.readVarBytes(255);
        }
        else if (usage == TransactionAttributeUsage.Description || usage >= TransactionAttributeUsage.Remark)
        {
            data = reader.readVarBytes(255);
        }
        else
        {
            throw new IOException();
        }
	}
}
