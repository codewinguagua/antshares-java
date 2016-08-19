package AntShares.Core;

import java.io.IOException;

import org.bouncycastle.math.ec.ECPoint;

import AntShares.*;
import AntShares.IO.*;

public class RegisterTransaction extends Transaction
{
	public AssetType assetType;
	public String name;
	public Fixed8 amount;
	public ECPoint issuer;
	public UInt160 admin;
	
	public RegisterTransaction()
	{
		super(TransactionType.RegisterTransaction);
	}
	
	@Override
	protected void deserializeExclusiveData(BinaryReader reader) throws IOException
	{
		try
		{
			assetType = AssetType.valueOf(reader.readByte());
	        if (assetType == AssetType.CreditFlag || assetType == AssetType.DutyFlag)
	            throw new IOException();
	        name = reader.readVarString();
	        amount = reader.readSerializable(Fixed8.class);
	        if (amount.equals(Fixed8.ZERO) || amount.compareTo(Fixed8.SATOSHI.minus()) < 0)
	        	throw new IOException();
	        if (assetType == AssetType.Share && amount.compareTo(Fixed8.ZERO) <= 0)
	            throw new IOException();
	        if (assetType == AssetType.Invoice && !amount.equals(Fixed8.SATOSHI.minus()))
	            throw new IOException();
	        issuer = reader.readECPoint();
	        admin = reader.readSerializable(UInt160.class);
		}
		catch (IllegalArgumentException | InstantiationException | IllegalAccessException ex)
		{
			throw new IOException(ex);
		}
	}
	
	@Override
	public UInt160[] getScriptHashesForVerifying()
	{
		//TODO
		return super.getScriptHashesForVerifying();
	}
	
	@Override
	protected void serializeExclusiveData(BinaryWriter writer) throws IOException
	{
        writer.writeByte(assetType.value());
        writer.writeVarString(name);
        writer.writeSerializable(amount);
        writer.writeECPoint(issuer);
        writer.writeSerializable(admin);
	}
	
	@Override
	public Fixed8 systemFee()
	{
        if (assetType == AssetType.AntShare || assetType == AssetType.AntCoin)
            return Fixed8.ZERO;
        //TODO: mainnet
        return Fixed8.fromLong(100);
	}
	
	@Override
	public String toString()
	{
		//TODO
		return name;
	}
}
