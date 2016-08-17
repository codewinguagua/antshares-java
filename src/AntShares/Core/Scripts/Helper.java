package AntShares.Core.Scripts;

import AntShares.UInt160;

public class Helper
{
    /**
     *  计算脚本的散列值，先使用sha256，然后再计算一次ripemd160
     *  @param script 要计算散列值的脚本
     *  @return 返回脚本的散列值
     */
    public static UInt160 ToScriptHash(byte[] script)
    {
        // TODO
        //return new UInt160(script.Sha256().RIPEMD160());
        return new UInt160();
    }
}
