package AntShares.IO.Caching;

public class TrackableCollection<TKey, TItem> 
    // TODO extends KeyedCollection<TKey, ? extends ITrackable<TKey>>
{
    public TrackableCollection() { }

    public TrackableCollection(Iterable<TItem> items)
    {
// TODO
//        for (TItem item : items)
//        {
//            base.InsertItem(Count, item);
//            item.TrackState = TrackState.None;
//        }
    }

    //@Override
    protected void ClearItems()
    {
        // TODO
//        for (int i = Count - 1; i >= 0; i--)
//            RemoveItem(i);
    }

    public void Commit()
    {
//        for (int i = Count - 1; i >= 0; i--)
//            if (Items[i].TrackState == TrackState.Deleted)
//                base.RemoveItem(i);
//            else
//                Items[i].TrackState = TrackState.None;
    }

//    public TItem[] GetChangeSet()
//    {
//        return Items.Where(p => p.TrackState != TrackState.None).ToArray();
//    }
//
//    protected override TKey GetKeyForItem(TItem item)
//    {
//        return item.Key;
//    }
//
//    protected override void InsertItem(int index, TItem item)
//    {
//        base.InsertItem(index, item);
//        item.TrackState = TrackState.Added;
//    }
//
//    protected override void RemoveItem(int index)
//    {
//        if (Items[index].TrackState == TrackState.Added)
//            base.RemoveItem(index);
//        else
//            Items[index].TrackState = TrackState.Deleted;
//    }
}