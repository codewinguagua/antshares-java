package OnChain.Core;

import java.io.IOException;

import OnChain.Helper;
import OnChain.IO.*;
import OnChain.IO.Json.*;

public class TransactionAttribute implements Serializable
{
	public TransactionAttributeUsage usage;
	public byte[] data;
	
	@Override
	public void serialize(BinaryWriter writer) throws IOException
	{
        writer.writeByte(usage.value());
        if (usage == TransactionAttributeUsage.DescriptionUrl)
            writer.writeByte((byte)data.length);
        else if (usage == TransactionAttributeUsage.Description || Byte.toUnsignedInt(usage.value()) >= Byte.toUnsignedInt(TransactionAttributeUsage.Remark.value()))
            writer.writeVarInt(data.length);
        else if (usage == TransactionAttributeUsage.ECDH02 || usage == TransactionAttributeUsage.ECDH03)
            writer.write(data, 1, 32);
        else
            writer.write(data);
	}

	@Override
	public void deserialize(BinaryReader reader) throws IOException
	{
		usage = TransactionAttributeUsage.valueOf(reader.readByte());
        if (usage == TransactionAttributeUsage.ContractHash || (Byte.toUnsignedInt(usage.value()) >= Byte.toUnsignedInt(TransactionAttributeUsage.Hash1.value()) && Byte.toUnsignedInt(usage.value()) <= Byte.toUnsignedInt(TransactionAttributeUsage.Hash15.value())))
        {
            data = reader.readBytes(32);
        }
        else if (usage == TransactionAttributeUsage.ECDH02 || usage == TransactionAttributeUsage.ECDH03)
        {
            data = new byte[33];
            data[0] = usage.value();
            reader.read(data, 1, 32);
        }
        else if (usage == TransactionAttributeUsage.Script)
        {
            data = reader.readBytes(20);
        }
        else if (usage == TransactionAttributeUsage.DescriptionUrl)
        {
            data = reader.readBytes(reader.readByte());
        }
        else if (usage == TransactionAttributeUsage.Description || Byte.toUnsignedInt(usage.value()) >= Byte.toUnsignedInt(TransactionAttributeUsage.Remark.value()))
        {
            data = reader.readVarBytes(65535);
        }
        else
        {
            throw new IOException();
        }
	}
	
	public JObject json()
	{
        JObject json = new JObject();
        json.set("usage", new JString(usage.toString()));
        json.set("data", new JString(Helper.toHexString(data)));
        return json;
	}
}
