package AntShares.Core;

import java.io.IOException;
import java.util.Arrays;

import AntShares.*;
import AntShares.IO.*;

public class VotingTransaction extends Transaction
{
	public UInt256[] enrollments;
	
	public VotingTransaction()
	{
		super(TransactionType.VotingTransaction);
	}
	
	@Override
	protected void deserializeExclusiveData(BinaryReader reader) throws IOException
	{
        try
        {
			enrollments = reader.readSerializableArray(UInt256.class);
		}
        catch (InstantiationException | IllegalAccessException ex)
        {
			throw new IOException(ex);
		}
        if (enrollments.length == 0 || enrollments.length > 1024)
            throw new IOException();
        if (enrollments.length != Arrays.stream(enrollments).distinct().count())
            throw new IOException();
	}
	
	@Override
	protected void onDeserialized() throws IOException
	{
        super.onDeserialized();
        if (Arrays.stream(outputs).allMatch(p -> !p.assetId.equals(Blockchain.AntShare.hash())))
            throw new IOException();
	}
	
	@Override
	public Fixed8 systemFee()
	{
		return Fixed8.fromLong(10);
	}
}
