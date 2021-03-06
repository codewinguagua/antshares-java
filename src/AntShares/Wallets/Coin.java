package AntShares.Wallets;

import AntShares.*;
import AntShares.Core.TransactionInput;
import AntShares.IO.Caching.*;

public class Coin implements ITrackable<TransactionInput>
{
    public TransactionInput input;
    public UInt256 assetId;
    public Fixed8 value;
    public UInt160 scriptHash;

    //[NonSerialized]
    private String _address = null;
    public String address()
    {
        if (_address == null)
        {
            _address = Wallet.toAddress(scriptHash);
        }
        return _address;
    }

    //[NonSerialized]
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

    @Override
    public TransactionInput key()
    {
        return input;
    }

    private TrackState trackState;
    @Override
    public TrackState getTrackState()
    {
        return trackState;
    }

    @Override
    public void setTrackState(TrackState state)
    {
    	trackState = state;
    }
}
