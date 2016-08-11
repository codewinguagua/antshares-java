package AntShares.Core;

import AntShares.Fixed8;
import AntShares.UInt256;

/**
 *  投票信息
 */
public class Vote
{
    /**
     *  报名表的散列值列表
     */
    public UInt256[] Enrollments;
    /**
     *  选票的数目
     */
    public Fixed8 Count;
}
