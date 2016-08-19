package AntShares.Core;

import java.util.*;

import org.bouncycastle.math.ec.ECPoint;

import AntShares.*;
import AntShares.Network.TimeSpan;

/**
 *  实现区块链功能的基类
 */
public abstract class Blockchain // TODO : IDisposable
{
    /**
     *  当区块被写入到硬盘后触发
     */
    //public static event EventHandler<Block> PersistCompleted;

    /**
     *  产生每个区块的时间间隔，已秒为单位
     */
    public static final int SecondsPerBlock = 15;
    /**
     *  小蚁币产量递减的时间间隔，以区块数量为单位
     */
    public static final int DecrementInterval = 2000000;
    /**
     *  每个区块产生的小蚁币的数量
     */
    public static final int[] MintingAmount = { 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
    /**
     *  产生每个区块的时间间隔
     */
    public static final TimeSpan TimePerBlock = TimeSpan.FromSeconds(SecondsPerBlock);
    /**
     *  后备记账人列表
     */
    public static final ECPoint[] StandbyMiners =
    {
//        ECPoint.DecodePoint("0327da12b5c40200e9f65569476bbff2218da4f32548ff43b6387ec1416a231ee8".HexToBytes(), ECCurve.Secp256r1),
//        ECPoint.DecodePoint("026ce35b29147ad09e4afe4ec4a7319095f08198fa8babbe3c56e970b143528d22".HexToBytes(), ECCurve.Secp256r1),
//        ECPoint.DecodePoint("0209e7fd41dfb5c2f8dc72eb30358ac100ea8c72da18847befe06eade68cebfcb9".HexToBytes(), ECCurve.Secp256r1),
//        ECPoint.DecodePoint("039dafd8571a641058ccc832c5e2111ea39b09c0bde36050914384f7a48bce9bf9".HexToBytes(), ECCurve.Secp256r1),
//        ECPoint.DecodePoint("038dddc06ce687677a53d54f096d2591ba2302068cf123c1f2d75c2dddc5425579".HexToBytes(), ECCurve.Secp256r1),
//        ECPoint.DecodePoint("02d02b1873a0863cd042cc717da31cea0d7cf9db32b74d4c72c01b0011503e2e22".HexToBytes(), ECCurve.Secp256r1),
//        ECPoint.DecodePoint("034ff5ceeac41acf22cd5ed2da17a6df4dd8358fcb2bfb1a43208ad0feaab2746b".HexToBytes(), ECCurve.Secp256r1),
    };

    /**
     *  小蚁股
     */
    public static final RegisterTransaction AntShare = new RegisterTransaction()
    {
//        AssetType = AssetType.AntShare,
////#if TESTNET
//        Name = "[{'lang':'zh-CN','name':'小蚁股(测试)'},{'lang':'en','name':'AntShare(TestNet)'}]",
////#else
////        Name = "[{'lang':'zh-CN','name':'小蚁股'},{'lang':'en','name':'AntShare'}]",
////#endif
//        Amount = Fixed8.FromDecimal(100000000),
//        Issuer = ECPoint.DecodePoint((new[] { (byte)0x02 }).Concat(ECCurve.Secp256r1.G.EncodePoint(false).Skip(1).Sha256().Sha256()).ToArray(), ECCurve.Secp256r1),
//        Admin = (new[] { (byte)ScriptOp.OP_TRUE }).ToScriptHash(),
//        Attributes = new TransactionAttribute[0],
//        Inputs = new TransactionInput[0],
//        Outputs = new TransactionOutput[0],
//        Scripts = new Script[0]
    };

    /**
     *  小蚁币
     */
    public static final RegisterTransaction AntCoin = new RegisterTransaction()
    {
//        AssetType = AssetType.AntCoin,
//#if TESTNET
//        Name = "[{'lang':'zh-CN','name':'小蚁币(测试)'},{'lang':'en','name':'AntCoin(TestNet)'}]",
//#else
//        Name = "[{'lang':'zh-CN','name':'小蚁币'},{'lang':'en','name':'AntCoin'}]",
//#endif
//        Amount = Fixed8.FromDecimal(MintingAmount.Sum(p => p * DecrementInterval)),
//        Issuer = ECPoint.DecodePoint((new[] { (byte)0x02 }).Concat(ECCurve.Secp256r1.G.EncodePoint(false).Skip(1).Sha256().Sha256()).ToArray(), ECCurve.Secp256r1),
//        Admin = (new[] { (byte)ScriptOp.OP_FALSE }).ToScriptHash(),
//        Attributes = new TransactionAttribute[0],
//        Inputs = new TransactionInput[0],
//        Outputs = new TransactionOutput[0],
//        Scripts = new Script[0]
    };

    /**
     *  创世区块
     */
    public static final Block GenesisBlock = new Block()
    {
//        PrevBlock = UInt256.Zero,
//        Timestamp = (new DateTime(2016, 7, 15, 15, 8, 21, DateTimeKind.Utc)).ToTimestamp(),
//        Height = 0,
//        Nonce = 2083236893, //向比特币致敬
//        NextMiner = GetMinerAddress(StandbyMiners),
//        Script = new Script
//        {
//            StackScript = new byte[0],
//            RedeemScript = new[] { (byte)ScriptOp.OP_TRUE }
//        },
//        Transactions = new Transaction[]
//        {
//            new MinerTransaction
//            {
//                Nonce = 2083236893,
//                Attributes = new TransactionAttribute[0],
//                Inputs = new TransactionInput[0],
//                Outputs = new TransactionOutput[0],
//                Scripts = new Script[0]
//            },
//            AntShare,
//            AntCoin,
//            new IssueTransaction
//            {
//                Nonce = 2083236893,
//                Attributes = new TransactionAttribute[0],
//                Inputs = new TransactionInput[0],
//                Outputs = new[]
//                {
//                    new TransactionOutput
//                    {
//                        AssetId = AntShare.Hash,
//                        Value = AntShare.Amount,
//                        ScriptHash = MultiSigContract.CreateMultiSigRedeemScript(StandbyMiners.Length / 2 + 1, StandbyMiners).ToScriptHash()
//                    }
//                },
//                Scripts = new[]
//                {
//                    new Script
//                    {
//                        StackScript = new byte[0],
//                        RedeemScript = new[] { (byte)ScriptOp.OP_TRUE }
//                    }
//                }
//            }
//        }
    };

    /**
     *  区块链所提供的功能
     */
    public abstract BlockchainAbility getAbility();// { get; }
    /**
     *  当前最新区块散列值
     */
    public abstract UInt256 getCurrentBlockHash();// { get; }
    /**
     *  当前最新区块头的散列值
     */
    public UInt256 getCurrentHeaderHash(){ return getCurrentBlockHash(); }
    /**
     *  默认的区块链实例
     */
    private static Blockchain _default = null;
    public static Blockchain getDefault() { return _default; }
    /**
     *  区块头高度
     */
    public int getHeaderHeight() { return getHeight(); }
    /**
     *  区块高度
     */
    public abstract int getHeight();
    /**
     *  表示当前的区块链实现是否为只读的
     */
    public abstract boolean IsReadOnly();

    // TODO
//    static Blockchain()
//    {
//        GenesisBlock.RebuildMerkleRoot();
//    }

    /**
     *  将指定的区块添加到区块链中
     *  <param name="block">要添加的区块</param>
     *  <returns>返回是否添加成功</returns>
     */
    protected abstract boolean AddBlock(Block block);

    /**
     *  将指定的区块头添加到区块头链中
     *  <param name="headers">要添加的区块头列表</param>
     */
    protected abstract void AddHeaders(Iterable<Block> headers);

    /**
     *  判断区块链中是否包含指定的资产
     *  <param name="hash">资产编号</param>
     *  <returns>如果包含指定资产则返回true</returns>
     */
    public boolean ContainsAsset(UInt256 hash)
    {
        // TODO;
        //return hash == AntShare.Hash || hash == AntCoin.Hash;
        return false;
    }

    /**
     *  判断区块链中是否包含指定的区块
     *  <param name="hash">区块编号</param>
     *  <returns>如果包含指定区块则返回true</returns>
     */
    public boolean ContainsBlock(UInt256 hash)
    {
        // TODO
        //return hash == GenesisBlock.Hash;
        return false;
    }

    /**
     *  判断区块链中是否包含指定的交易
     *  <param name="hash">交易编号</param>
     *  <returns>如果包含指定交易则返回true</returns>
     */
    public boolean ContainsTransaction(UInt256 hash)
    {
        // TODO
        //return GenesisBlock.Transactions.Any(p => p.Hash == hash);
        return false;
    }

    public boolean ContainsUnspent(TransactionInput input)
    {
        return ContainsUnspent(input.prevHash, input.prevIndex);
    }

    public abstract boolean ContainsUnspent(UInt256 hash, short index);

    public abstract void Dispose();

    public abstract Iterable<RegisterTransaction> GetAssets();

    /**
     *  根据指定的高度，返回对应的区块信息
     *  <param name="height">区块高度</param>
     *  <returns>返回对应的区块信息</returns>
     */
    public Block GetBlock(int height)
    {
        return GetBlock(GetBlockHash(height));
    }

    /**
     *  根据指定的散列值，返回对应的区块信息
     *  <param name="hash">散列值</param>
     *  <returns>返回对应的区块信息</returns>
     */
    public Block GetBlock(UInt256 hash)
    {
//        if (hash == GenesisBlock.Hash)
//            return GenesisBlock;
        return null;
    }

    /**
     *  根据指定的高度，返回对应区块的散列值
     *  <param name="height">区块高度</param>
     *  <returns>返回对应区块的散列值</returns>
     */
    public UInt256 GetBlockHash(int height)
    {
//        if (height == 0) return GenesisBlock.Hash;
        return null;
    }

    public Iterable<EnrollmentTransaction> GetEnrollments()
    {
        return GetEnrollments(Collections.emptyList());
    }

    public abstract Iterable<EnrollmentTransaction> GetEnrollments(Iterable<Transaction> others);

    /**
     *  根据指定的高度，返回对应的区块头信息
     *  <param name="height">区块高度</param>
     *  <returns>返回对应的区块头信息</returns>
     */
    public Block GetHeader(int height)
    {
        return GetHeader(GetBlockHash(height));
    }

    /**
     *  根据指定的散列值，返回对应的区块头信息
     *  <param name="hash">散列值</param>
     *  <returns>返回对应的区块头信息</returns>
     */
    public Block GetHeader(UInt256 hash)
    {
        Block b = GetBlock(hash);
        return b == null ? null : b.getHeader();
    }

    public abstract UInt256[] GetLeafHeaderHashes();

    /**
     *  获取记账人的合约地址
     *  <param name="miners">记账人的公钥列表</param>
     *  <returns>返回记账人的合约地址</returns>
     */
    public static UInt160 GetMinerAddress(ECPoint[] miners)
    {
//        return MultiSigContract.CreateMultiSigRedeemScript(miners.Length - (miners.Length - 1) / 3, miners).ToScriptHash();
        return new UInt160();
    }

    private List<ECPoint> _miners = new ArrayList<ECPoint>();
    /**
     *  获取下一个区块的记账人列表
     *  <returns>返回一组公钥，表示下一个区块的记账人列表</returns>
     */
    public ECPoint[] GetMiners()
    {
        synchronized (_miners)
        {
            if (_miners.size() == 0)
            {
                // TODO
                //_miners.AddRange(GetMiners(Enumerable.Empty<Transaction>()));
            }
            return _miners.toArray(new ECPoint[_miners.size()]);
        }
    }

    public Iterable<ECPoint> GetMiners(Iterable<Transaction> others)
    {
//        if (!Ability.HasFlag(BlockchainAbility.TransactionIndexes) || !Ability.HasFlag(BlockchainAbility.UnspentIndexes))
//            throw new NotSupportedException();
//        //TODO: 此处排序可能将耗费大量内存，考虑是否采用其它机制
//        Vote[] votes = GetVotes(others).OrderBy(p => p.Enrollments.Length).ToArray();
//        int miner_count = (int)votes.WeightedFilter(0.25, 0.75, p => p.Count.GetData(), (p, w) => new
//        {
//            MinerCount = p.Enrollments.Length,
//            Weight = w
//        }).WeightedAverage(p => p.MinerCount, p => p.Weight);
//        miner_count = Math.Max(miner_count, StandbyMiners.Length);
//        Dictionary<ECPoint, Fixed8> miners = new Dictionary<ECPoint, Fixed8>();
//        Dictionary<UInt256, ECPoint> enrollments = GetEnrollments(others).ToDictionary(p => p.Hash, p => p.PublicKey);
//        foreach (var vote in votes)
//        {
//            foreach (UInt256 hash in vote.Enrollments)
//            {
//                if (!enrollments.ContainsKey(hash)) continue;
//                ECPoint pubkey = enrollments[hash];
//                if (!miners.ContainsKey(pubkey))
//                {
//                    miners.Add(pubkey, Fixed8.Zero);
//                }
//                miners[pubkey] += vote.Count;
//            }
//        }
//        return miners.OrderByDescending(p => p.Value).ThenBy(p => p.Key).Select(p => p.Key).Concat(StandbyMiners).Take(miner_count);
        return null;
    }

    /**
     *  根据指定的散列值，返回下一个区块的信息
     *  <param name="hash">散列值</param>
     *  <returns>返回下一个区块的信息>
     */
    public abstract Block GetNextBlock(UInt256 hash);

    /**
     *  根据指定的散列值，返回下一个区块的散列值
     *  <param name="hash">散列值</param>
     *  <returns>返回下一个区块的散列值</returns>
     */
    public abstract UInt256 GetNextBlockHash(UInt256 hash);

    /**
     *  根据指定的资产编号，返回对应资产的发行量
     *  <param name="asset_id">资产编号</param>
     *  <returns>返回对应资产的当前已经发行的数量</returns>
     */
    public abstract Fixed8 GetQuantityIssued(UInt256 asset_id);

    /**
     *  根据指定的区块高度，返回对应区块及之前所有区块中包含的系统费用的总量
     *  <param name="height">区块高度</param>
     *  <returns>返回对应的系统费用的总量</returns>
     */
    public long GetSysFeeAmount(int height)
    {
        return GetSysFeeAmount(GetBlockHash(height));
    }

    /**
     *  根据指定的区块散列值，返回对应区块及之前所有区块中包含的系统费用的总量
     *  <param name="hash">散列值</param>
     *  <returns>返回系统费用的总量</returns>
     */
    public abstract long GetSysFeeAmount(UInt256 hash);

    /**
     *  根据指定的散列值，返回对应的交易信息
     *  <param name="hash">散列值</param>
     *  <returns>返回对应的交易信息</returns>
     */
    public Transaction GetTransaction(UInt256 hash)
    {
        Out<Integer> height = new Out<Integer>();
        return GetTransaction(hash, height);
    }

    /**
     *  根据指定的散列值，返回对应的交易信息与该交易所在区块的高度
     *  <param name="hash">交易散列值</param>
     *  <param name="height">返回该交易所在区块的高度</param>
     *  <returns>返回对应的交易信息</returns>
     */
    public Transaction GetTransaction(UInt256 hash, Out<Integer> height)
    {
//        Transaction tx = GenesisBlock.Transactions.FirstOrDefault(p => p.Hash == hash);
//        if (tx != null)
//        {
//            height = 0;
//            return tx;
//        }
        height.set(-1);
        return null;
    }

    public abstract Map<Short, Claimable> GetUnclaimed(UInt256 hash);

    /**
     *  根据指定的散列值和索引，获取对应的未花费的资产
     *  <param name="hash">交易散列值</param>
     *  <param name="index">输出的索引</param>
     *  <returns>返回一个交易输出，表示一个未花费的资产</returns>
     */
    public abstract TransactionOutput GetUnspent(UInt256 hash, short index);

    /**
     *  获取选票信息
     *  <returns>返回一个选票列表，包含当前区块链中所有有效的选票</returns>
     */
    public Iterable<Vote> GetVotes()
    {
        return GetVotes(Collections.emptyList());
    }

    public abstract Iterable<Vote> GetVotes(Iterable<Transaction> others);

    /**
     *  判断交易是否双花
     *  <param name="tx">交易</param>
     *  <returns>返回交易是否双花</returns>
     */
    public abstract boolean IsDoubleSpend(Transaction tx);

    /**
     *  当区块被写入到硬盘后调用
     *  <param name="block">区块</param>
     */
    protected void OnPersistCompleted(Block block)
    {
        synchronized (_miners)
        {
            _miners.clear();
        }
        // TODO
        //if (PersistCompleted != null) PersistCompleted(this, block);
    }

    /**
     *  注册默认的区块链实例
     *  <param name="blockchain">区块链实例</param>
     *  <returns>返回注册后的区块链实例</returns>
     */
    public static Blockchain RegisterBlockchain(Blockchain blockchain)
    {
        if (blockchain == null) throw new NullPointerException();
        if (getDefault() != null) getDefault().Dispose();
        _default = blockchain;
        return blockchain;
    }
}