package AntShares.Wallets;

import java.lang.Thread.State;
import java.nio.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.bouncycastle.math.ec.ECPoint;

import AntShares.*;
import AntShares.Core.*;
import AntShares.Core.Scripts.Script;
import AntShares.Cryptography.*;
import AntShares.IO.Caching.TrackableCollection;

public abstract class Wallet implements AutoCloseable
{
    // TODO
    //public event EventHandler BalanceChanged;

    public static final byte COIN_VERSION = 0x17;

    private final String path;
    private byte[] iv;
    private byte[] masterKey;
    private Map<UInt160, Account> accounts;
    private Map<UInt160, Contract> contracts;
    private TrackableCollection<TransactionInput, Coin> coins;
    private int current_height;

    private Thread thread;
    private boolean isrunning = true;

    protected String dbPath() { return path; }
    protected final Object syncroot = new Object();

    protected int walletHeight() {
        return current_height;
    }

    private Wallet(String path, byte[] passwordKey, boolean create) throws BadPaddingException, IllegalBlockSizeException
    {
        this.path = path;
        if (create)
        {
            this.iv = AES.generateIV();
            this.masterKey = AES.generateKey();
            this.accounts = new HashMap<UInt160, Account>();
            this.contracts = new HashMap<UInt160, Contract>();
            this.coins = new TrackableCollection<TransactionInput, Coin>();
            this.current_height = Blockchain.current() != null ? Blockchain.current().headerHeight() + 1 : 0;
            buildDatabase();
            SaveStoredData("PasswordHash", Digest.sha256(passwordKey));
            SaveStoredData("IV", iv);
            SaveStoredData("MasterKey", AES.encrypt(masterKey, passwordKey, iv));
            SaveStoredData("Version", new byte[] { 0, 7, 0, 0 });
            SaveStoredData("Height", ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(current_height).array());
            //ProtectedMemory.Protect(masterKey, MemoryProtectionScope.SameProcess);
        }
        else
        {
            byte[] passwordHash = LoadStoredData("PasswordHash");
            if (passwordHash != null && !Arrays.equals(passwordHash, Digest.sha256(passwordKey)))
                throw new BadPaddingException();
            this.iv = LoadStoredData("IV");
			this.masterKey = AES.decrypt(LoadStoredData("MasterKey"), passwordKey, iv);
	        //ProtectedMemory.Protect(masterKey, MemoryProtectionScope.SameProcess);
            this.accounts = Arrays.stream(LoadAccounts()).collect(Collectors.toMap(p -> p.PublicKeyHash, p -> p));
            this.contracts = Arrays.stream(LoadContracts()).collect(Collectors.toMap(p -> p.PublicKeyHash, p -> p));
            this.coins = new TrackableCollection<TransactionInput, Coin>(LoadCoins());
            this.current_height = ByteBuffer.wrap(LoadStoredData("Height")).order(ByteOrder.LITTLE_ENDIAN).getInt();
        }
        Arrays.fill(passwordKey, (byte) 0);
        this.thread = new Thread(this::ProcessBlocks);
        this.thread.setDaemon(true);
        this.thread.setName("Wallet.ProcessBlocks");
        this.thread.start();
    }

    protected Wallet(String path, String password, boolean create) throws BadPaddingException, IllegalBlockSizeException
    {
        this(path, AES.generateKey(password), create);
    }

    // TODO
    //protected Wallet(String path, SecureString password, boolean create)
    //{
        //this(path, password.ToAesKey(), create)
    //}

    public void addContract(Contract contract)
    {
        synchronized (accounts)
        {
            if (!accounts.containsKey(contract.PublicKeyHash))
                throw new RuntimeException();
            synchronized(contracts)
            {
                contracts.put(contract.getScriptHash(), contract);
            }
        }
    }

    protected void buildDatabase()
    {
    }

    public static Fixed8 CalculateClaimAmount(Iterable<TransactionInput> inputs)
    {
//        if (!Blockchain.Default.Ability.HasFlag(BlockchainAbility.UnspentIndexes))
//            throw new NotSupportedException();
//        List<Claimable> unclaimed = new List<Claimable>();
        // TODO
//        for (var group : inputs.GroupBy(p => p.PrevHash))
//        {
//            Dictionary<ushort, Claimable> claimable = Blockchain.Default.GetUnclaimed(group.Key);
//            if (claimable == null || claimable.Count == 0)
//                throw new ArgumentException();
//            for (TransactionInput claim : group)
//            {
//                if (!claimable.ContainsKey(claim.PrevIndex))
//                    throw new ArgumentException();
//                unclaimed.Add(claimable[claim.PrevIndex]);
//            }
//        }
        Fixed8 amount_claimed = Fixed8.ZERO;
//        for (var group : unclaimed.GroupBy(p => new { p.StartHeight, p.EndHeight }))
//        {
//            uint amount = 0;
//            uint ustart = group.Key.StartHeight / Blockchain.DecrementInterval;
//            if (ustart < Blockchain.MintingAmount.length)
//            {
//                uint istart = group.Key.StartHeight % Blockchain.DecrementInterval;
//                uint uend = group.Key.EndHeight / Blockchain.DecrementInterval;
//                uint iend = group.Key.EndHeight % Blockchain.DecrementInterval;
//                if (uend >= Blockchain.MintingAmount.length)
//                {
//                    uend = (uint)Blockchain.MintingAmount.length;
//                    iend = 0;
//                }
//                if (iend == 0)
//                {
//                    uend--;
//                    iend = Blockchain.DecrementInterval;
//                }
//                while (ustart < uend)
//                {
//                    amount += (Blockchain.DecrementInterval - istart) * Blockchain.MintingAmount[ustart];
//                    ustart++;
//                    istart = 0;
//                }
//                amount += (iend - istart) * Blockchain.MintingAmount[ustart];
//            }
//            amount += (uint)(Blockchain.Default.GetSysFeeAmount(group.Key.EndHeight - 1) - (group.Key.StartHeight == 0 ? 0 : Blockchain.Default.GetSysFeeAmount(group.Key.StartHeight - 1)));
//            amount_claimed += group.Sum(p => p.Value) / 100000000 * amount;
//        }
        return amount_claimed;
    }

    public boolean changePassword(String password_old, String password_new)
    {
        byte[] passwordHash = LoadStoredData("PasswordHash");
        if (!Arrays.equals(passwordHash, Digest.sha256(AES.generateKey(password_old))))
            return false;
        byte[] passwordKey = AES.generateKey(password_new);
        //using (new ProtectedMemoryContext(masterKey, MemoryProtectionScope.SameProcess))
        {
            try
            {
                SaveStoredData("MasterKey", AES.encrypt(masterKey, passwordKey, iv));
                return true;
            }
            finally
            {
                Arrays.fill(passwordKey, (byte)0);
            }
        }
    }

    public void close()
    {
        isrunning = false;
        if (thread.getState() != State.NEW)
        {
			try
			{
				thread.join();
			}
			catch (InterruptedException ex)
			{
			}
        }
    }
    
    public boolean containsAccount(ECPoint publicKey)
    {
        return containsAccount(Script.toScriptHash(publicKey.getEncoded(true)));
    }

    public boolean containsAccount(UInt160 publicKeyHash)
    {
        synchronized (accounts)
        {
            return accounts.containsKey(publicKeyHash);
        }
    }

    public boolean containsAddress(UInt160 scriptHash)
    {
        synchronized (contracts)
        {
            return contracts.containsKey(scriptHash);
        }
    }

    public Account createAccount()
    {
        byte[] privateKey = ECC.generateKey();
        Account account = createAccount(privateKey);
        Arrays.fill(privateKey, (byte) 0);
        return account;
    }

    public Account createAccount(byte[] privateKey)
    {
        Account account = new Account(privateKey);
        synchronized (accounts)
        {
            accounts.put(account.PublicKeyHash, account);
        }
        return account;
    }

    protected byte[] decryptPrivateKey(byte[] encryptedPrivateKey) throws IllegalBlockSizeException, BadPaddingException
    {
        if (encryptedPrivateKey == null) throw new NullPointerException("encryptedPrivateKey");
        if (encryptedPrivateKey.length != 112) throw new IllegalArgumentException();
        //using (new ProtectedMemoryContext(masterKey, MemoryProtectionScope.SameProcess))
        {
            return AES.decrypt(encryptedPrivateKey, masterKey, iv);
        }
    }

    public boolean deleteAccount(UInt160 publicKeyHash)
    {
        synchronized (accounts)
        {
            synchronized (contracts)
            {
                for (Contract contract : (Contract[])contracts.values().stream().filter(p -> p.PublicKeyHash == publicKeyHash).toArray())
                {
                    deleteContract(contract.getScriptHash());
                }
            }
            return accounts.remove(publicKeyHash) != null;
        }
    }

    public boolean deleteContract(UInt160 scriptHash)
    {
        synchronized (contracts)
        {
            synchronized (coins)
            {
                // TODO
//                for (TransactionInput key : coins.Where(p => p.ScriptHash == scriptHash).Select(p => p.Input).ToArray())
//                {
//                    coins.Remove(key);
//                }
                coins.Commit();
                return contracts.remove(scriptHash) != null;
            }
        }
    }

    protected byte[] EncryptPrivateKey(byte[] decryptedPrivateKey)
    {
//        using (new ProtectedMemoryContext(masterKey, MemoryProtectionScope.SameProcess))
//        {
//            return decryptedPrivateKey.AesEncrypt(masterKey, iv);
//        }
        // TODO
        return null;
    }

    public Iterable<Coin> FindCoins()
    {
        synchronized (coins)
        {
            //return coins.Where(p => p.State == CoinState.Unconfirmed || p.State == CoinState.Unspent).ToArray();
            return null;
        }
    }

    public Iterable<Coin> FindUnspentCoins()
    {
        synchronized (coins)
        {
            //return coins.Where(p => p.State == CoinState.Unspent).ToArray();
            return null;
        }
    }

    public Coin[] FindUnspentCoins(UInt256 asset_id, Fixed8 amount)
    {
        synchronized (coins)
        {
            //return FindUnspentCoins(coins.Where(p => p.State == CoinState.Unspent), asset_id, amount);
            return null;
        }
    }

    protected static Coin[] FindUnspentCoins(Iterable<Coin> unspents, UInt256 asset_id, Fixed8 amount)
    {
//        Coin[] unspents_asset = unspents.Where(p => p.AssetId == asset_id).ToArray();
//        Fixed8 sum = unspents_asset.Sum(p => p.Value);
//        if (sum < amount) return null;
//        if (sum == amount) return unspents_asset;
//        Coin[] unspents_ordered = unspents_asset.OrderByDescending(p => p.Value).ToArray();
//        int i = 0;
//        while (unspents_ordered[i].Value <= amount)
//            amount -= unspents_ordered[i++].Value;
//        if (amount == Fixed8.Zero)
//            return unspents_ordered.Take(i).ToArray();
//        else
//            return unspents_ordered.Take(i).Concat(new[] { unspents_ordered.Last(p => p.Value >= amount) }).ToArray();
        return null;
    }

    public Account GetAccount(ECPoint publicKey)
    {
        //return GetAccount(publicKey.EncodePoint(true).ToScriptHash());
        return null;
    }

    public Account GetAccount(UInt160 publicKeyHash)
    {
        synchronized (accounts)
        {
//            if (!accounts.ContainsKey(publicKeyHash)) return null;
//            return accounts[publicKeyHash];
            return null;
        }
    }

    public Account GetAccountByScriptHash(UInt160 scriptHash)
    {
        synchronized (accounts)
        {
            synchronized (contracts)
            {
//                if (!contracts.ContainsKey(scriptHash)) return null;
//                return accounts[contracts[scriptHash].PublicKeyHash];
                return null;
            }
        }
    }

    public Iterable<Account> GetAccounts()
    {
//        synchronized (accounts)
//        {
//            for (var pair : accounts)
//            {
//                yield return pair.Value;
//            }
//        }
        return null;
    }

    public Iterable<UInt160> GetAddresses()
    {
//        synchronized (contracts)
//        {
//            for (var pair : contracts)
//            {
//                yield return pair.Key;
//            }
//        }
        return null;
    }

    public Fixed8 GetAvailable(UInt256 asset_id)
    {
//        synchronized (coins)
//        {
//            return coins.Where(p => p.State == CoinState.Unspent && p.AssetId == asset_id).Sum(p => p.Value);
//        }
        return null;
    }

    public Fixed8 GetBalance(UInt256 asset_id)
    {
//        synchronized (coins)
//        {
//            return coins.Where(p => (p.State == CoinState.Unconfirmed || p.State == CoinState.Unspent) && p.AssetId == asset_id).Sum(p => p.Value);
//        }
        return null;
    }

    public UInt160 GetChangeAddress()
    {
//        synchronized (contracts)
//        {
//            return contracts.Values.FirstOrDefault(p => p is SignatureContract)?.ScriptHash ?? contracts.Keys.FirstOrDefault();
//        }
        return null;
    }

    public Contract GetContract(UInt160 scriptHash)
    {
        synchronized (contracts)
        {
            if (!contracts.containsKey(scriptHash)) return null;
            return contracts.get(scriptHash);
        }
    }

    public Iterable<Contract> GetContracts()
    {
        synchronized (contracts)
        {
//            for (var pair : contracts)
//            {
//                yield return pair.Value;
//            }
            return null;
        }
    }

    public Iterable<Contract> GetContracts(UInt160 publicKeyHash)
    {
        synchronized (contracts)
        {
//            for (Contract contract : contracts.Values.Where(p => p.PublicKeyHash.Equals(publicKeyHash)))
//            {
//                yield return contract;
//            }
            return null;
        }
    }

    public static byte[] GetPrivateKeyFromWIF(String wif)
    {
        if (wif == null) throw new NullPointerException();
        byte[] data = Base58.Decode(wif);
        if (data.length != 38 || data[0] != 0x80 || data[33] != 0x01)
            throw new IllegalArgumentException();
        // TODO
        //byte[] checksum = data.Sha256(0, data.length - 4).Sha256();
//        if (!data.Skip(data.length - 4).SequenceEqual(checksum.Take(4)))
//            throw new FormatException();
        byte[] privateKey = new byte[32];
        System.arraycopy(data, 1, privateKey, 0, privateKey.length);
        Arrays.fill(data, (byte) 0);
        return privateKey;
    }

    public Iterable<Coin> GetUnclaimedCoins()
    {
        synchronized (coins)
        {
//            for (var coin : coins.Where(p => p.State == CoinState.Spent && p.AssetId == Blockchain.AntShare.Hash))
//            {
//                yield return coin;
//            }
            return null;
        }
    }

    public Account Import(/* X509Certificate2 */ Object cert)
    {
        byte[] privateKey = null;
//        using (ECDsaCng ecdsa = (ECDsaCng)cert.GetECDsaPrivateKey())
//        {
//            privateKey = ecdsa.Key.Export(CngKeyBlobFormat.EccPrivateBlob);
//        }
        Account account = createAccount(privateKey);
        Arrays.fill(privateKey, (byte) 0);
        return account;
    }

    public Account Import(String wif)
    {
        byte[] privateKey = GetPrivateKeyFromWIF(wif);
        Account account = createAccount(privateKey);
        Arrays.fill(privateKey, (byte) 0);
        return account;
    }

    protected abstract Account[] LoadAccounts();

    protected abstract Coin[] LoadCoins();

    protected abstract Contract[] LoadContracts();

    protected abstract byte[] LoadStoredData(String name);

    // TODO
    public <T> T MakeTransaction(T tx, Fixed8 fee) // TODO where T : Transaction
    {
        return tx;
//        if (tx.Outputs == null) throw new ArgumentException();
//        if (tx.Attributes == null) tx.Attributes = new TransactionAttribute[0];
//        fee += tx.SystemFee;
//        var pay_total = (typeof(T) == typeof(IssueTransaction) ? new TransactionOutput[0] : tx.Outputs).GroupBy(p => p.AssetId, (k, g) => new
//        {
//            AssetId = k,
//            Value = g.Sum(p => p.Value)
//        }).ToDictionary(p => p.AssetId);
//        if (fee > Fixed8.Zero)
//        {
//            if (pay_total.ContainsKey(Blockchain.AntCoin.Hash))
//            {
//                pay_total[Blockchain.AntCoin.Hash] = new
//                {
//                    AssetId = Blockchain.AntCoin.Hash,
//                    Value = pay_total[Blockchain.AntCoin.Hash].Value + fee
//                };
//            }
//            else
//            {
//                pay_total.Add(Blockchain.AntCoin.Hash, new
//                {
//                    AssetId = Blockchain.AntCoin.Hash,
//                    Value = fee
//                });
//            }
//        }
//        var pay_coins = pay_total.Select(p => new
//        {
//            AssetId = p.Key,
//            Unspents = FindUnspentCoins(p.Key, p.Value.Value)
//        }).ToDictionary(p => p.AssetId);
//        if (pay_coins.Any(p => p.Value.Unspents == null)) return null;
//        var input_sum = pay_coins.Values.ToDictionary(p => p.AssetId, p => new
//        {
//            AssetId = p.AssetId,
//            Value = p.Unspents.Sum(q => q.Value)
//        });
//        UInt160 change_address = GetChangeAddress();
//        List<TransactionOutput> outputs_new = new List<TransactionOutput>(tx.Outputs);
//        for (UInt256 asset_id : input_sum.Keys)
//        {
//            if (input_sum[asset_id].Value > pay_total[asset_id].Value)
//            {
//                outputs_new.Add(new TransactionOutput
//                {
//                    AssetId = asset_id,
//                    Value = input_sum[asset_id].Value - pay_total[asset_id].Value,
//                    ScriptHash = change_address
//                });
//            }
//        }
//        tx.Inputs = pay_coins.Values.SelectMany(p => p.Unspents).Select(p => p.Input).ToArray();
//        tx.Outputs = outputs_new.ToArray();
//        return tx;
    }

    protected abstract void OnProcessNewBlock(Block block, Iterable<Transaction> transactions, Iterable<Coin> added, Iterable<Coin> changed, Iterable<Coin> deleted);
    protected abstract void OnSendTransaction(Transaction tx, Iterable<Coin> added, Iterable<Coin> changed);

    private void ProcessBlocks()
    {
        while (isrunning)
        {
//            while (current_height <= Blockchain.Default?.Height && isrunning)
//            {
//                synchronized (SyncRoot)
//                {
//                    Block block = Blockchain.Default.GetBlock(current_height);
//                    if (block != null) ProcessNewBlock(block);
//                }
//            }
//            for (int i = 0; i < 20 && isrunning; i++)
//            {
//                Thread.Sleep(100);
//            }
        }
    }

    private void ProcessNewBlock(Block block)
    {
        Coin[] changeset;
        synchronized (contracts)
        {
            synchronized (coins)
            {
                HashSet<Transaction> transactions = new HashSet<Transaction>();
                // TODO
//                for (Transaction tx : block.Transactions)
//                {
//                    for (ushort index = 0; index < tx.Outputs.length; index++)
//                    {
//                        TransactionOutput output = tx.Outputs[index];
//                        if (contracts.ContainsKey(output.ScriptHash))
//                        {
//                            TransactionInput key = new TransactionInput
//                            {
//                                PrevHash = tx.Hash,
//                                PrevIndex = index
//                            };
//                            if (coins.Contains(key))
//                                coins[key].State = CoinState.Unspent;
//                            else
//                                coins.Add(new Coin
//                                {
//                                    Input = key,
//                                    AssetId = output.AssetId,
//                                    Value = output.Value,
//                                    ScriptHash = output.ScriptHash,
//                                    State = CoinState.Unspent
//                                });
//                            transactions.Add(tx);
//                        }
//                    }
//                }
//                for (Transaction tx : block.Transactions)
//                {
//                    for (TransactionInput input : tx.GetAllInputs())
//                    {
//                        if (coins.Contains(input))
//                        {
//                            if (coins[input].AssetId == Blockchain.AntShare.Hash)
//                                coins[input].State = CoinState.Spent;
//                            else
//                                coins.Remove(input);
//                            transactions.Add(tx);
//                        }
//                    }
//                }
//                for (ClaimTransaction tx : block.Transactions.OfType<ClaimTransaction>())
//                {
//                    for (TransactionInput claim : tx.Claims)
//                    {
//                        if (coins.Contains(claim))
//                        {
//                            coins.Remove(claim);
//                            transactions.Add(tx);
//                        }
//                    }
//                }
                current_height++;
                // TODO
//                changeset = coins.GetChangeSet();
//                if (block.Height == Blockchain.Default.Height || changeset.length > 0)
//                {
//                    OnProcessNewBlock(block, transactions, changeset.Where(p => ((ITrackable<TransactionInput>)p).TrackState == TrackState.Added), changeset.Where(p => ((ITrackable<TransactionInput>)p).TrackState == TrackState.Changed), changeset.Where(p => ((ITrackable<TransactionInput>)p).TrackState == TrackState.Deleted));
//                    coins.Commit();
//                }
            }
        }
        // TODO
//        if (changeset.length > 0)
//            BalanceChanged?.Invoke(this, EventArgs.Empty);
    }

    public void Rebuild()
    {
        synchronized (syncroot)
        {
            synchronized (coins)
            {
                // TODO
                //coins.Clear();
                coins.Commit();
                current_height = 0;
            }
        }
    }

    protected abstract void SaveStoredData(String name, byte[] value);

    public boolean SendTransaction(Transaction tx)
    {
        //Coin[] changeset;
        synchronized (contracts)
        {
            synchronized (coins)
            {
//                if (tx.GetAllInputs().Any(p => !coins.Contains(p) || coins[p].State != CoinState.Unspent))
//                    return false;
//                for (TransactionInput input : tx.GetAllInputs())
//                    coins[input].State = CoinState.Spending;
//                for (ushort i = 0; i < tx.Outputs.length; i++)
//                {
//                    if (contracts.ContainsKey(tx.Outputs[i].ScriptHash))
//                        coins.Add(new Coin
//                        {
//                            Input = new TransactionInput
//                            {
//                                PrevHash = tx.Hash,
//                                PrevIndex = i
//                            },
//                            AssetId = tx.Outputs[i].AssetId,
//                            Value = tx.Outputs[i].Value,
//                            ScriptHash = tx.Outputs[i].ScriptHash,
//                            State = CoinState.Unconfirmed
//                        });
//                }
//                changeset = coins.GetChangeSet();
//                if (changeset.length > 0)
//                {
//                    OnSendTransaction(tx, changeset.Where(p => ((ITrackable<TransactionInput>)p).TrackState == TrackState.Added), changeset.Where(p => ((ITrackable<TransactionInput>)p).TrackState == TrackState.Changed));
//                    coins.Commit();
//                }
            }
        }
//        if (changeset.length > 0)
//            BalanceChanged?.Invoke(this, EventArgs.Empty);
        return true;
    }

    public boolean Sign(SignatureContext context)
    {
        boolean fSuccess = false;
//        for (UInt160 scriptHash : context.ScriptHashes)
//        {
//            Contract contract = GetContract(scriptHash);
//            if (contract == null) continue;
//            Account account = GetAccountByScriptHash(scriptHash);
//            if (account == null) continue;
//            byte[] signature = context.Signable.Sign(account);
//            fSuccess |= context.Add(contract, account.PublicKey, signature);
//        }
        return fSuccess;
    }

    public static String ToAddress(UInt160 scriptHash)
    {
//        byte[] data = new byte[] { CoinVersion }.Concat(scriptHash.ToArray()).ToArray();
//        return Base58.Encode(data.Concat(data.Sha256().Sha256().Take(4)).ToArray());
        return "";
    }

    public static UInt160 ToScriptHash(String address)
    {
        byte[] data = Base58.Decode(address);
        if (data.length != 25)
            throw new IllegalArgumentException();
        if (data[0] != COIN_VERSION)
            throw new IllegalArgumentException();
//        if (!data.Take(21).Sha256().Sha256().Take(4).SequenceEqual(data.Skip(21)))
//            throw new FormatException();
//        return new UInt160(data.Skip(1).Take(20).ToArray());
        // TODO
        return new UInt160();
    }
}