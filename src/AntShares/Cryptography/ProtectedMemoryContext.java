package AntShares.Cryptography;

class ProtectedMemoryContext // TODO : IDisposable
{
//TODO
//    private static Dictionary<byte[], byte> counts = new Dictionary<byte[], byte>();
//    private byte[] memory;
//    private MemoryProtectionScope scope;
//
//    public ProtectedMemoryContext(byte[] memory, MemoryProtectionScope scope)
//    {
//        this.memory = memory;
//        this.scope = scope;
//        if (counts.ContainsKey(memory))
//        {
//            counts[memory]++;
//        }
//        else
//        {
//            counts.Add(memory, 1);
//            ProtectedMemory.Unprotect(memory, scope);
//        }
//    }
//
//    void IDisposable.Dispose()
//    {
//        if (--counts[memory] == 0)
//        {
//            counts.Remove(memory);
//            ProtectedMemory.Protect(memory, scope);
//        }
//    }
}
