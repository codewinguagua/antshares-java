package AntShares.Implementations.Blockchains.RPC;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import AntShares.*;
import AntShares.Core.*;
import AntShares.IO.Serializable;
import AntShares.IO.Json.*;
import AntShares.Network.RPC.*;
import AntShares.Wallets.Wallet;

public class RpcBlockchain extends Blockchain
{
	private final RpcClient rpc;
	
	public RpcBlockchain(RpcClient rpc)
	{
		this.rpc = rpc;
	}
	
	@Override
	public EnumSet<BlockchainAbility> ability()
	{
		return BlockchainAbility.None;
	}

	@Override
	public UInt256 currentBlockHash() throws RpcException, IOException
	{
		JObject result = rpc.call("getbestblockhash");
		return UInt256.parse(result.asString());
	}
	
	@Override
	public Block getBlock(int height) throws RpcException, IOException
	{
		JObject result = rpc.call("getblock", new JNumber(height));
		try
		{
			return Serializable.from(Helper.hexToBytes(result.asString()), Block.class);
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public Block getBlock(UInt256 hash) throws RpcException, IOException
	{
		JObject result = rpc.call("getblock", new JString(hash.toString()));
		try
		{
			return Serializable.from(Helper.hexToBytes(result.asString()), Block.class);
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public UInt256 getBlockHash(int height) throws RpcException, IOException
	{
		JObject result = rpc.call("getblockhash", new JNumber(height));
		return UInt256.parse(result.toString());
	}
	
	@Override
	public Transaction getTransaction(UInt256 hash) throws RpcException, IOException
	{
		JObject result = rpc.call("getrawtransaction", new JString(hash.toString()));
		return Transaction.deserializeFrom(Helper.hexToBytes(result.asString()));
	}

	@Override
	public int height() throws RpcException, IOException
	{
		JObject result = rpc.call("getblockcount");
		return (int)result.asNumber() - 1;
	}

	@Override
	public boolean isReadOnly()
	{
		return true;
	}

	@Override
	protected boolean addBlock(Block block)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void addHeaders(Iterable<Block> headers)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void close()
	{
	}

	@Override
	public boolean containsUnspent(UInt256 hash, int index) throws RpcException, IOException
	{
		JObject result = rpc.call("gettxout", new JString(hash.toString()), new JNumber(index));
		return result != null;
	}

	@Override
	public Stream<RegisterTransaction> getAssets()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<EnrollmentTransaction> getEnrollments(Stream<Transaction> others)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public UInt256[] getLeafHeaderHashes()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Block getNextBlock(UInt256 hash)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public UInt256 getNextBlockHash(UInt256 hash)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Fixed8 getQuantityIssued(UInt256 asset_id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public long getSysFeeAmount(UInt256 hash)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<Short, Claimable> getUnclaimed(UInt256 hash)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public TransactionOutput getUnspent(UInt256 hash, int index) throws RpcException, IOException
	{
		JObject result = rpc.call("gettxout", new JString(hash.toString()), new JNumber(index));
		TransactionOutput output = new TransactionOutput();
		output.assetId = UInt256.parse(result.get("asset").asString());
		output.value = Fixed8.parse(result.get("value").asString());
		output.scriptHash = Wallet.toScriptHash(result.get("address").asString());
		return output;
	}

	@Override
	public Stream<Vote> getVotes(Stream<Transaction> others)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDoubleSpend(Transaction tx)
	{
		throw new UnsupportedOperationException();
	}
}
