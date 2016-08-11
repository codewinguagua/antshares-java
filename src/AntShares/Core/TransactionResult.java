package AntShares.Core;

import AntShares.Fixed8;
import AntShares.UInt256;

/**
 *  交易结果，表示交易中资产的变化量
 */
public class TransactionResult
{
    /**
     *  资产编号
     */
    public UInt256 AssetId;
    /**
     *  该资产的变化量
     */
    public Fixed8 Amount;
}
