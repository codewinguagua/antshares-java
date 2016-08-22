package AntShares.Core;

import java.io.IOException;

import org.bouncycastle.math.ec.ECPoint;

import AntShares.*;
import AntShares.IO.*;
import AntShares.Wallets.SignatureContract;

public class EnrollmentTransaction extends Transaction
{
	public ECPoint publicKey;
	
	public EnrollmentTransaction()
	{
		super(TransactionType.EnrollmentTransaction);
	}
	
	@Override
	protected void deserializeExclusiveData(BinaryReader reader) throws IOException
	{
		publicKey = reader.readECPoint();
	}
	
	@Override
	public UInt160[] getScriptHashesForVerifying()
	{
		//TODO
		return super.getScriptHashesForVerifying();
	}
	
	private UInt160 _miner = null;
	public UInt160 miner()
	{
        if (_miner == null)
        {
            _miner = SignatureContract.Create(publicKey).getScriptHash();
        }
        return _miner;
	}
	
	@Override
	protected void onDeserialized() throws IOException
	{
        super.onDeserialized();
        if (outputs.length == 0 || outputs[0].assetId != Blockchain.ANTCOIN.hash() || outputs[0].scriptHash != miner())
            throw new IOException();
	}
	
	@Override
	protected void serializeExclusiveData(BinaryWriter writer) throws IOException
	{
		writer.writeECPoint(publicKey);
	}
	
	@Override
	public Fixed8 systemFee()
	{
		return Fixed8.fromLong(1000);
	}
}
