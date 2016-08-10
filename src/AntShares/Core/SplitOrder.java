package AntShares.Core;

import java.io.Serializable;

import AntShares.Fixed8;
import AntShares.UInt160;

/**
 *  部分成交的订单
 */
public class SplitOrder implements Serializable
{
	private static final long serialVersionUID = 8781924160988999643L;
	/**
     *  买入或卖出的数量
     */
    public Fixed8 Amount;
    /**
     *  价格
     */
    public Fixed8 Price;
    /**
     *  委托人的合约散列
     */
    public UInt160 Client;

}
