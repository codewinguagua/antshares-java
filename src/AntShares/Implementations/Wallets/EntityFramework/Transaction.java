package AntShares.Implementations.Wallets.EntityFramework;

import java.util.Date;

import AntShares.Core.TransactionType;

public class Transaction {
    private byte[] Hash;// { get; set; }
    private TransactionType Type;// { get; set; }
    private byte[] RawData;// { get; set; }
    private Integer Height;// { get; set; }
    private Date Time;// { get; set; }

    public byte[] getHash() {
        return Hash;
    }
    public void setHash(byte[] hash) {
        Hash = hash;
    }
    public TransactionType getType() {
        return Type;
    }
    public void setType(TransactionType type) {
        Type = type;
    }
    public byte[] getRawData() {
        return RawData;
    }
    public void setRawData(byte[] rawData) {
        RawData = rawData;
    }
    public Integer getHeight() {
        return Height;
    }
    public void setHeight(Integer height) {
        Height = height;
    }
    public Date getTime() {
        return Time;
    }
    public void setTime(Date time) {
        Time = time;
    }

}
