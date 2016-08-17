package AntShares.Core;

/**
 * 资产类别
 */
public enum AssetType
{
    CreditFlag(0x40),
    DutyFlag(0x80),

    /**
     * 小蚁股
     */
    AntShare(0x00),

    /**
     * 小蚁币
     */
    AntCoin(0x01),

    /**
     * 法币
     */
    Currency(0x08),

    /**
     * 股权
     */
    Share(DutyFlag.getByte() | 0x10),

    Invoice(DutyFlag.getByte() | 0x18),

    Token(CreditFlag.getByte() | 0x20);

    private byte value;

    AssetType(int v)
    {
        value = (byte)v;
    }

    public byte getByte()
    {
        return value;
    }
}
