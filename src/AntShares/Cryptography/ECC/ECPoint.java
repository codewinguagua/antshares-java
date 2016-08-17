package AntShares.Cryptography.ECC;

import java.io.IOException;
import java.math.BigInteger;

import AntShares.FormatException;
import AntShares.IO.*;

public class ECPoint implements Comparable<ECPoint>, Serializable
{
    ECFieldElement X, Y;
    ECCurve Curve;
    
    /**
     *  判断是否为无穷远点
     */
    public boolean IsInfinity()
    {
        return X == null && Y == null;
    }

    ECPoint(ECFieldElement x, ECFieldElement y, ECCurve curve)
    {
        if ((x != null && y == null) || (x == null && y != null))
            throw new IllegalArgumentException("Exactly one of the field elements is null");
        this.X = x;
        this.Y = y;
        this.Curve = curve;
    }

    /**
     *  与另一对象进行比较
     *  <param name="other">另一对象</param>
     *  <returns>返回比较的结果</returns>
     */
    @Override
    public int compareTo(ECPoint other)
    {
        if (this == other) return 0;
        int result = X.compareTo(other.X);
        if (result != 0) return result;
        return Y.compareTo(other.Y);
    }

    /**
     *  从字节数组中解码
     *  <param name="encoded">要解码的字节数组</param>
     *  <param name="curve">曲线参数</param>
     *  <returns></returns>
     */
    public static ECPoint DecodePoint(byte[] encoded, ECCurve curve)
    {
        ECPoint p = null;
        //int expectedLength = (curve.Q.GetBitLength() + 7) / 8;
        // TODO
        int expectedLength = 1;
        switch (encoded[0])
        {
            case 0x00: // infinity
                {
                    if (encoded.length != 1)
                        throw new IllegalArgumentException("Incorrect length for infinity encoding, encoded");
                    p = curve.Infinity;
                    break;
                }
            case 0x02: // compressed
            case 0x03: // compressed
                {
                    if (encoded.length != (expectedLength + 1))
                        throw new IllegalArgumentException("Incorrect length for compressed encoding, encoded");
                    int yTilde = encoded[0] & 1;
                    // TODO
                    BigInteger X1 = BigInteger.ONE;
                    //BigInteger X1 = new BigInteger(encoded.Skip(1).Reverse().Concat(new byte[1]).ToArray());
                    p = DecompressPoint(yTilde, X1, curve);
                    break;
                }
            case 0x04: // uncompressed
            case 0x06: // hybrid
            case 0x07: // hybrid
                {
                    if (encoded.length != (2 * expectedLength + 1))
                        throw new IllegalArgumentException("Incorrect length for uncompressed/hybrid encoding, encoded");
                    // TODO
                    BigInteger X1 = BigInteger.ONE;
                    BigInteger Y1 = BigInteger.ONE;
//                    BigInteger X1 = new BigInteger(encoded.Skip(1).Take(expectedLength).Reverse().Concat(new byte[1]).ToArray());
//                    BigInteger Y1 = new BigInteger(encoded.Skip(1 + expectedLength).Reverse().Concat(new byte[1]).ToArray());
                    p = new ECPoint(new ECFieldElement(X1, curve), new ECFieldElement(Y1, curve), curve);
                    break;
                }
            default:
                throw new FormatException("Invalid point encoding " + encoded[0]);
        }
        return p;
    }

    private static ECPoint DecompressPoint(int yTilde, BigInteger X1, ECCurve curve)
    {
        ECFieldElement x = new ECFieldElement(X1, curve);
        //ECFieldElement alpha = x * (x.Square() + curve.A) + curve.B;
        // TODO
        ECFieldElement alpha = x;
        ECFieldElement beta = alpha.Sqrt();

        //
        // if we can't find a sqrt we haven't got a point on the
        // curve - run!
        //
        if (beta == null)
            throw new ArithmeticException("Invalid point compression");

        BigInteger betaValue = beta.Value;
        int bit0 = ((betaValue.intValue() & 1) == 0) ? 0 : 1;

        if (bit0 != yTilde)
        {
            // Use the other root
            // TODO
            beta = null;//new ECFieldElement(curve.Q - betaValue, curve);
        }

        return new ECPoint(x, beta, curve);
    }

    @Override
    public void deserialize(BinaryReader reader)
    {
        throw new UnsupportedOperationException();
    }

    /**
     *  反序列化
     *  <param name="reader">数据来源</param>
     *  <param name="curve">椭圆曲线参数</param>
     *  <returns></returns>
     * @throws IOException 
     */
    public static ECPoint deserializeFrom(BinaryReader reader, ECCurve curve) throws IOException
    {
        //int expectedLength = (curve.Q.GetBitLength() + 7) / 8;
        // TODO
        int expectedLength = 32;
        byte[] buffer = new byte[1 + expectedLength * 2];
        buffer[0] = (byte) reader.readByte();
        switch (buffer[0])
        {
            case 0x00:
                return curve.Infinity;
            case 0x02:
            case 0x03:
                reader.read(buffer, 1, expectedLength);
                // TODO
                //return DecodePoint(buffer.Take(1 + expectedLength).ToArray(), curve);
            case 0x04:
            case 0x06:
            case 0x07:
                reader.read(buffer, 1, expectedLength * 2);
                return DecodePoint(buffer, curve);
            default:
                throw new FormatException("Invalid point encoding " + buffer[0]);
        }
    }

    /**
     *  将对象编码到字节数组
     *  <param name="commpressed">是否为压缩格式的编码</param>
     *  <returns>返回编码后的字节数组</returns>
     */
    public byte[] EncodePoint(boolean commpressed)
    {
        if (IsInfinity()) return new byte[1];
        byte[] data = null;
//        if (commpressed)
//        {
//            data = new byte[33];
//        }
//        else
//        {
//            data = new byte[65];
//            byte[] yBytes = Y.Value.ToByteArray().Reverse().ToArray();
//            Buffer.BlockCopy(yBytes, 0, data, 65 - yBytes.length, yBytes.length);
//        }
//        byte[] xBytes = X.Value.ToByteArray().Reverse().ToArray();
//        Buffer.BlockCopy(xBytes, 0, data, 33 - xBytes.length, xBytes.length);
//        data[0] = commpressed ? Y.Value.IsEven ? (byte)0x02 : (byte)0x03 : (byte)0x04;
        // TODO
        return data;
    }

    /**
     *  比较与另一个对象是否相等
     *  <param name="obj">另一个对象</param>
     *  <returns>返回比较的结果</returns>
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof ECPoint)) return false;
        ECPoint other = (ECPoint) obj;
        if (IsInfinity() && other.IsInfinity()) return true;
        if (IsInfinity() || other.IsInfinity()) return false;
        return X.equals(other.X) && Y.equals(other.Y);
    }

    /**   
     *  从指定的字节数组中解析出公钥，这个字节数组可以是任意形式的公钥编码、或者包含私钥的内容
     *  <param name="pubkey">要解析的字节数组</param>
     *  <param name="curve">椭圆曲线参数</param>
     *  <returns>返回解析出的公钥</returns>
     */
    public static ECPoint FromBytes(byte[] pubkey, ECCurve curve)
    {
        switch (pubkey.length)
        {
            case 33:
            case 65:
                return DecodePoint(pubkey, curve);
            case 64:
            case 72:
                // TODO
                //return DecodePoint(new byte[] { 0x04 }.Concat(pubkey.Skip(pubkey.length - 64)).ToArray(), curve);
            case 96:
            case 104:
                // TODO
                //return DecodePoint(new byte[] { 0x04 }.Concat(pubkey.Skip(pubkey.length - 96).Take(64)).ToArray(), curve);
            default:
                throw new FormatException();
        }
    }

    /**
     *  获取HashCode
     *  <returns>返回HashCode</returns>
     */
    @Override
    public int hashCode()
    {
        return X.hashCode() + Y.hashCode();
    }

    static ECPoint Multiply(ECPoint p, BigInteger k)
    {
        // floor(log2(k))
        int m = 0; // TODO k.GetBitLength();

        // width of the Window NAF
        //sbyte width;
        // TODO
        int width = 0;

        // Required length of precomputation array
        int reqPreCompLen;

        // Determine optimal width and corresponding length of precomputation
        // array based on literature values
        if (m < 13)
        {
            width = 2;
            reqPreCompLen = 1;
        }
        else if (m < 41)
        {
            width = 3;
            reqPreCompLen = 2;
        }
        else if (m < 121)
        {
            width = 4;
            reqPreCompLen = 4;
        }
        else if (m < 337)
        {
            width = 5;
            reqPreCompLen = 8;
        }
        else if (m < 897)
        {
            width = 6;
            reqPreCompLen = 16;
        }
        else if (m < 2305)
        {
            width = 7;
            reqPreCompLen = 32;
        }
        else
        {
            width = 8;
            reqPreCompLen = 127;
        }

        // The length of the precomputation array
        int preCompLen = 1;

        ECPoint[] preComp = new ECPoint[] { p };
        //ECPoint twiceP = p.Twice();

        if (preCompLen < reqPreCompLen)
        {
            // Precomputation array must be made bigger, copy existing preComp
            // array into the larger new preComp array
            ECPoint[] oldPreComp = preComp;
            preComp = new ECPoint[reqPreCompLen];
            System.arraycopy(oldPreComp, 0, preComp, 0, preCompLen);

            for (int i = preCompLen; i < reqPreCompLen; i++)
            {
                // Compute the new ECPoints for the precomputation array.
                // The values 1, 3, 5, ..., 2^(width-1)-1 times p are
                // computed
                // TODO
                //preComp[i] = twiceP + preComp[i - 1];
            }
        }

        // Compute the Window NAF of the desired width
        // TODO
        //sbyte[] wnaf = WindowNaf(width, k);
        int[] wnaf = new int[] {width};
        int l = wnaf.length;

        // Apply the Window NAF to p using the precomputed ECPoint values.
        ECPoint q = p.Curve.Infinity;
        for (int i = l - 1; i >= 0; i--)
        {
            q = q.Twice();

            if (wnaf[i] != 0)
            {
                if (wnaf[i] > 0)
                {
                    // TODO
                    //q += preComp[(wnaf[i] - 1) / 2];
                }
                else
                {
                    // TODO
                    // wnaf[i] < 0
                    //q -= preComp[(-wnaf[i] - 1) / 2];
                }
            }
        }

        return q;
    }

    public static ECPoint Parse(String value, ECCurve curve)
    {
        // TODO
        //return DecodePoint(value.HexToBytes(), curve);
        return null;
    }

    @Override
    public void serialize(BinaryWriter writer)
    {
        // TODO
        //writer.write(EncodePoint(true));
    }

    @Override
    public String toString()
    {
        //return EncodePoint(true).ToHexString();
        // TODO
        return "";
    }

    ECPoint Twice()
    {
        if (this.IsInfinity())
            return this;
        if (this.Y.Value.signum() == 0)
            return Curve.Infinity;
//        ECFieldElement TWO = new ECFieldElement(BigInteger.valueOf(2), Curve);
//        ECFieldElement THREE = new ECFieldElement(BigInteger.valueOf(3), Curve);
//        ECFieldElement gamma = (this.X.Square() * THREE + Curve.A) / (Y * TWO);
//        ECFieldElement x3 = gamma.Square() - this.X * TWO;
//        ECFieldElement y3 = gamma * (this.X - x3) - this.Y;
//        return new ECPoint(x3, y3, Curve);
        // TODO
        return null;
    }

    // TODO
    //private static sbyte[] WindowNaf(sbyte width, BigInteger k)
    static byte[] WindowNaf(byte width, BigInteger k)
    {
        // TODO
//        byte[] wnaf = new byte[k.GetBitLength() + 1];
//        short pow2wB = (short)(1 << width);
//        int i = 0;
//        int length = 0;
//        while (k.Sign > 0)
//        {
//            if (!k.IsEven)
//            {
//                BigInteger remainder = k % pow2wB;
//                if (remainder.TestBit(width - 1))
//                {
//                    wnaf[i] = (sbyte)(remainder - pow2wB);
//                }
//                else
//                {
//                    wnaf[i] = (sbyte)remainder;
//                }
//                k -= wnaf[i];
//                length = i;
//            }
//            else
//            {
//                wnaf[i] = 0;
//            }
//            k >>= 1;
//            i++;
//        }
//        length++;
//        sbyte[] wnafShort = new sbyte[length];
//        Array.Copy(wnaf, 0, wnafShort, 0, length);
//        return wnafShort;
        return new byte[1];
    }

//    public static ECPoint operator -(ECPoint x)
//    {
//        return new ECPoint(x.X, -x.Y, x.Curve);
//    }
//
//    public static ECPoint operator *(ECPoint p, byte[] n)
//    {
//        if (p == null || n == null)
//            throw new ArgumentNullException();
//        if (n.length != 32)
//            throw new IllegalArgumentException();
//        if (p.IsInfinity)
//            return p;
//        //BigInteger的内存无法被保护，可能会有安全隐患。此处的k需要重写一个SecureBigInteger类来代替
//        BigInteger k = new BigInteger(n.Reverse().Concat(new byte[1]).ToArray());
//        if (k.Sign == 0)
//            return p.Curve.Infinity;
//        return Multiply(p, k);
//    }
//
//    public static ECPoint operator +(ECPoint x, ECPoint y)
//    {
//        if (x.IsInfinity)
//            return y;
//        if (y.IsInfinity)
//            return x;
//        if (x.X.Equals(y.X))
//        {
//            if (x.Y.Equals(y.Y))
//                return x.Twice();
//            Debug.Assert(x.Y.Equals(-y.Y));
//            return x.Curve.Infinity;
//        }
//        ECFieldElement gamma = (y.Y - x.Y) / (y.X - x.X);
//        ECFieldElement x3 = gamma.Square() - x.X - y.X;
//        ECFieldElement y3 = gamma * (x.X - x3) - x.Y;
//        return new ECPoint(x3, y3, x.Curve);
//    }
//
//    public static ECPoint operator -(ECPoint x, ECPoint y)
//    {
//        if (y.IsInfinity)
//            return x;
//        return x + (-y);
//    }
}

