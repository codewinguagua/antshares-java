package AntShares.IO.Caching;

interface ITrackable<TKey>
{
    TKey getKey();
    TrackState getTrackState();
    void setTrackState(TrackState state);
}
