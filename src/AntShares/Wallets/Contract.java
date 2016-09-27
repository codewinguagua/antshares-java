package AntShares.Wallets;

import AntShares.UInt160;
import AntShares.Core.Scripts.Script;
import AntShares.IO.Serializable;

/**
 *  所有合约的基类
 */
public abstract class Contract implements Serializable
{
    /**
     *  合约脚本代码
     */
    public byte[] redeemScript;
    /**
     *  公钥散列值，用于标识该合约在钱包中隶属于哪一个账户
     */
    public UInt160 publicKeyHash;

    private String _address;
    /**
     *  合约地址
     */
    public String address()
    {
        if (_address == null)
        {
            _address = Wallet.toAddress(scriptHash());
        }
        return _address;
    }

    /**
     *  合约的形式参数列表
     */
    public abstract ContractParameterType[] parameterList();

    private UInt160 _scriptHash;
    /**
     *  脚本散列值
     */
    public UInt160 scriptHash()
    {
        if (_scriptHash == null)
        {
            _scriptHash = Script.toScriptHash(redeemScript);
        }
        return _scriptHash;
    }

    /**
     *  比较与另一个对象是否相等
     *  <param name="obj">另一个对象</param>
     *  <returns>返回比较的结果</returns>
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Contract)) return false;
        return scriptHash().equals(((Contract) obj).scriptHash());
    }

    /**
     *  获得HashCode
     *  <returns>返回HashCode</returns>
     */
    @Override
    public int hashCode()
    {
        return scriptHash().hashCode();
    }
}
