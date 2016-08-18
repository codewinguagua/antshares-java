package AntShares.Core;

import java.io.IOException;

import AntShares.*;
import AntShares.IO.*;

/**
 *  交易输出
 */
public class TransactionOutput implements Serializable
{
    /**
     *  资产编号
     */
    public UInt256 assetId;
    /**
     *  金额
     */
    public Fixed8 value;
    /**
     *  收款地址
     */
    public UInt160 scriptHash;
    
	@Override
	public void serialize(BinaryWriter writer) throws IOException
	{
		writer.writeSerializable(assetId);
		writer.writeSerializable(value);
		writer.writeSerializable(scriptHash);
	}
	
	@Override
	public void deserialize(BinaryReader reader) throws IOException
	{
		try
		{
			assetId = reader.readSerializable(UInt256.class);
			value = reader.readSerializable(Fixed8.class);
			if (value.compareTo(Fixed8.ZERO) <= 0)
				throw new IOException();
			scriptHash = reader.readSerializable(UInt160.class);
		}
		catch (InstantiationException | IllegalAccessException e)
		{
		}
	}

// TODO
//    /**
//     *  将交易输出转变为json对象
//     *  <param name="index">该交易输出在交易中的索引</param>
//     *  <returns>返回json对象</returns>
//     */
//    public JObject ToJson(ushort index)
//    {
//        JObject json = new JObject();
//        json["n"] = index;
//        json["asset"] = AssetId.ToString();
//        json["value"] = Value.ToString();
//        json["high"] = Value.GetData() >> 32;
//        json["low"] = Value.GetData() & 0xffffffff;
//        json["address"] = Wallet.ToAddress(ScriptHash);
//        return json;
//    }
}
