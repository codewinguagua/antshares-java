package AntShares.IO.Caching;

import AntShares.UInt256;
import AntShares.Network.Inventory;

abstract class RelayCache extends FIFOCache<UInt256, Inventory>
{
    public RelayCache(int max_capacity)
    {
        super(max_capacity);
    }

    @Override
    protected UInt256 GetKeyForItem(Inventory item)
    {
        // TODO
        //return item.Hash;
        return new UInt256();
    }
}