package AntShares.Wallets;

import AntShares.Fixed8;
import AntShares.UInt160;
import AntShares.UInt256;
import AntShares.Core.TransactionInput;
import AntShares.IO.Caching.ITrackable;
import AntShares.IO.Caching.TrackState;

public class Coin implements ITrackable<TransactionInput>
{
    public TransactionInput input;
    public UInt256 assetId;
    public Fixed8 value;
    public UInt160 scriptHash;

    // TODO [NonSerialized]
    private String _address = null;

    public String address()
    {
        if (_address == null)
        {
            // TODO _address = Wallet.ToAddress(ScriptHash);
        }
        return _address;
    }

    // TODO [NonSerialized]
    private CoinState state;
    public CoinState getState()
    {
        return state;
    }
    
    public void setState(CoinState value)
    {
        if (state != value)
        {
            state = value;
            ITrackable<TransactionInput> _this = this;
            if (_this.getTrackState() == TrackState.None)
                _this.setTrackState(TrackState.Changed);
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Coin)) return false;
        return input.equals(((Coin) obj).input);
    }

    @Override
    public int hashCode()
    {
        return input.hashCode();
    }

    // TODO
    //TransactionInput ITrackable<TransactionInput>.Key => Input;

    @Override
    public TransactionInput getKey() {
        // TODO Auto-generated method stub
        return null;
    }

    // TODO
    //TrackState ITrackable<TransactionInput>.TrackState { get; set; }

    @Override
    public TrackState getTrackState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTrackState(TrackState state) {
        // TODO Auto-generated method stub
        
    }
}