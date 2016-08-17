package AntShares.Implementations.Wallets.EntityFramework;

import AntShares.Wallets.CoinState;
import AntShares.Wallets.Contract;

public class Coin
{
    private byte[] TxId;// { get; set; }
    private short Index;// { get; set; }
    private byte[] AssetId;// { get; set; }
    private long Value;// { get; set; }
    private byte[] ScriptHash;// { get; set; }
    private CoinState State;// { get; set; }
    private Contract Contract;// { get; set; }

    public byte[] getTxId() {
        return TxId;
    }
    public void setTxId(byte[] txId) {
        TxId = txId;
    }
    public short getIndex() {
        return Index;
    }
    public void setIndex(short index) {
        Index = index;
    }
    public byte[] getAssetId() {
        return AssetId;
    }
    public void setAssetId(byte[] assetId) {
        AssetId = assetId;
    }
    public long getValue() {
        return Value;
    }
    public void setValue(long value) {
        Value = value;
    }
    public byte[] getScriptHash() {
        return ScriptHash;
    }
    public void setScriptHash(byte[] scriptHash) {
        ScriptHash = scriptHash;
    }
    public CoinState getState() {
        return State;
    }
    public void setState(CoinState state) {
        State = state;
    }
    public Contract getContract() {
        return Contract;
    }
    public void setContract(Contract contract) {
        Contract = contract;
    }
}
