package AntShares.Core;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

import AntShares.*;
import AntShares.Core.Scripts.Script;
import AntShares.IO.*;
import AntShares.Network.*;

public abstract class Transaction extends Inventory
{
	public final TransactionType type;
	public TransactionAttribute[] attributes;
	public TransactionInput[] inputs;
	public TransactionOutput[] outputs;
	public Script[] scripts;
	
	protected Transaction(TransactionType type)
	{
		this.type = type;
	}
	
	@Override
	public void deserialize(BinaryReader reader) throws IOException
	{
		deserializeUnsigned(reader);
		try
		{
			scripts = reader.readSerializableArray(Script.class);
		}
		catch (InstantiationException | IllegalAccessException e)
		{
		}
	}
	
	protected void deserializeExclusiveData(BinaryReader reader) throws IOException
	{
	}
	
	public static Transaction deserializeFrom(byte[] value) throws IOException
	{
		return deserializeFrom(value, 0);
	}
	
	public static Transaction deserializeFrom(byte[] value, int offset) throws IOException
	{
		try (ByteArrayInputStream ms = new ByteArrayInputStream(value, offset, value.length - offset))
		{
			try (BinaryReader reader = new BinaryReader(ms))
			{
				return deserializeFrom(reader);
			}
		}
	}

	static Transaction deserializeFrom(BinaryReader reader) throws IOException
	{
        try
        {
            TransactionType type = TransactionType.valueOf(reader.readByte());
            String typeName = "AntShares.Core." + type.toString();
            Transaction transaction = (Transaction)Class.forName(typeName).newInstance();
            transaction.deserializeUnsignedWithoutType(reader);
			transaction.scripts = reader.readSerializableArray(Script.class);
			return transaction;
		}
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
			throw new IOException(ex);
		}
	}
	
	@Override
	public void deserializeUnsigned(BinaryReader reader) throws IOException
	{
        if (type.value() != reader.readByte())
            throw new IOException();
        deserializeUnsignedWithoutType(reader);
	}

	private void deserializeUnsignedWithoutType(BinaryReader reader) throws IOException
	{
        try
        {
            deserializeExclusiveData(reader);
			attributes = reader.readSerializableArray(TransactionAttribute.class);
	        if (Arrays.stream(attributes).map(p -> p.usage).distinct().count() != attributes.length)
	            throw new IOException();
	        inputs = reader.readSerializableArray(TransactionInput.class);
	        TransactionInput[] inputs_all = (TransactionInput[])getAllInputs().toArray();
	        for (int i = 1; i < inputs_all.length; i++)
	            for (int j = 0; j < i; j++)
	                if (inputs_all[i].prevHash == inputs_all[j].prevHash && inputs_all[i].prevIndex == inputs_all[j].prevIndex)
	                    throw new IOException();
	        outputs = reader.readSerializableArray(TransactionOutput.class);
	        if (outputs.length > 65536) throw new IOException();
//	        if (Blockchain.AntShare != null)
//	            for (TransactionOutput output : Outputs.Where(p -> p.AssetId == Blockchain.AntShare.Hash))
//	                if (output.Value.GetData() % 100000000 != 0)
//	                    throw new FormatException();
		}
        catch (InstantiationException | IllegalAccessException ex)
        {
			throw new IOException(ex);
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Transaction)) return false;
		Transaction tx = (Transaction)obj;
		return hash().equals(tx.hash());
	}
	
	public Stream<TransactionInput> getAllInputs()
	{
		return Arrays.stream(inputs);
	}
	
	@Override
	public UInt160[] getScriptHashesForVerifying()
	{
//        if (References == null) throw new InvalidOperationException();
//        HashSet<UInt160> hashes = new HashSet<UInt160>(Inputs.Select(p => References[p].ScriptHash));
//        foreach (var group in Outputs.GroupBy(p => p.AssetId))
//        {
//            RegisterTransaction tx = Blockchain.Default.GetTransaction(group.Key) as RegisterTransaction;
//            if (tx == null) throw new InvalidOperationException();
//            if (tx.AssetType.HasFlag(AssetType.DutyFlag))
//            {
//                hashes.UnionWith(group.Select(p => p.ScriptHash));
//            }
//        }
//        return hashes.OrderBy(p => p).ToArray();
		return new UInt160[0];
	}
	
	@Override
	public int hashCode()
	{
		return hash().hashCode();
	}

	@Override
	public final InventoryType inventoryType()
	{
		return InventoryType.TX;
	}
	
	protected void onDeserialized()
	{
	}
	
	@Override
	public void serialize(BinaryWriter writer) throws IOException
	{
        serializeUnsigned(writer);
        writer.writeSerializableArray(scripts);
	}
	
	protected void serializeExclusiveData(BinaryWriter writer) throws IOException
	{
	}
	
	@Override
	public void serializeUnsigned(BinaryWriter writer) throws IOException
	{
        writer.writeByte(type.value());
        serializeExclusiveData(writer);
        writer.writeSerializableArray(attributes);
        writer.writeSerializableArray(inputs);
        writer.writeSerializableArray(outputs);
	}
	
	public Fixed8 systemFee()
	{
		return Fixed8.ZERO;
	}
	
	@Override
	public boolean verify()
	{
//        if (Blockchain.Default.ContainsTransaction(Hash)) return true;
//        if (!Blockchain.Default.Ability.HasFlag(BlockchainAbility.UnspentIndexes) || !Blockchain.Default.Ability.HasFlag(BlockchainAbility.TransactionIndexes))
//            return false;
//        if (Blockchain.Default.IsDoubleSpend(this))
//            return false;
//        foreach (UInt256 hash in Outputs.Select(p => p.AssetId).Distinct())
//            if (!Blockchain.Default.ContainsAsset(hash))
//                return false;
//        TransactionResult[] results = GetTransactionResults()?.ToArray();
//        if (results == null) return false;
//        TransactionResult[] results_destroy = results.Where(p => p.Amount > Fixed8.Zero).ToArray();
//        if (results_destroy.Length > 1) return false;
//        if (results_destroy.Length == 1 && results_destroy[0].AssetId != Blockchain.AntCoin.Hash)
//            return false;
//        if (SystemFee > Fixed8.Zero && (results_destroy.Length == 0 || results_destroy[0].Amount < SystemFee))
//            return false;
//        TransactionResult[] results_issue = results.Where(p => p.Amount < Fixed8.Zero).ToArray();
//        switch (Type)
//        {
//            case TransactionType.MinerTransaction:
//            case TransactionType.ClaimTransaction:
//                if (results_issue.Any(p => p.AssetId != Blockchain.AntCoin.Hash))
//                    return false;
//                break;
//            case TransactionType.IssueTransaction:
//                if (results_issue.Any(p => p.AssetId == Blockchain.AntCoin.Hash))
//                    return false;
//                break;
//            default:
//                if (results_issue.Length > 0)
//                    return false;
//                break;
//        }
//        TransactionAttribute script = Attributes.FirstOrDefault(p => p.Usage == TransactionAttributeUsage.Script);
//        if (script != null)
//        {
//            ScriptEngine engine = new ScriptEngine(new Script
//            {
//                StackScript = new byte[0],
//                RedeemScript = script.Data
//            }, this);
//            if (!engine.Execute()) return false;
//        }
//        return this.VerifySignature();
		return false;
	}
}
