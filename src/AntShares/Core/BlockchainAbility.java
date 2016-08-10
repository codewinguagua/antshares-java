package AntShares.Core;

/**
 * 表示特定区块链实现所提供的功能
 */
public enum BlockchainAbility
{
    /**
     *  无
     */
    None(0),

    /**
     *  必须实现的虚函数：GetBlockAndHeight, GetBlockHeight, GetNextBlock, GetNextBlockHash, GetSysFeeAmount
     */
    BlockIndexes(0x01),

    /**
     *  必须实现的虚函数：ContainsAsset, GetAssets, GetEnrollments
     */
    TransactionIndexes(0x02),

    /**
     *  必须实现的虚函数：ContainsUnspent, GetUnclaimed, GetUnspent, GetVotes, IsDoubleSpend
     */
    UnspentIndexes(0x04),

    /**
     *  必须实现的虚函数：GetQuantityIssued
     */
    Statistics(0x08),

    /**
     *  所有的功能
     */
    All(0xff);

    private byte value;

	BlockchainAbility(int v)
	{
		value = (byte)v;
	}

	public byte getByte()
	{
		return value;
	}
}
