package AntShares.Cryptography;

import java.security.*;

import javax.security.auth.DestroyFailedException;

import org.bouncycastle.asn1.x9.*;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;

import AntShares.Helper;

public class ECC
{
	private static final X9ECParameters secp256r1nc = ECNamedCurveTable.getByName("secp256r1");
	public static final ECDomainParameters secp256r1 = new ECDomainParameters(secp256r1nc.getCurve(), secp256r1nc.getG(), secp256r1nc.getN(), secp256r1nc.getH(), secp256r1nc.getSeed());
			
	public static int compare(ECPoint a, ECPoint b)
	{
		if (a == b) return 0;
		int result = a.getXCoord().toBigInteger().compareTo(b.getXCoord().toBigInteger());
		if (result != 0) return result;
		return a.getYCoord().toBigInteger().compareTo(b.getYCoord().toBigInteger());
	}
	
	public static byte[] generateKey()
	{
		KeyPair keyPair = null;
		try
		{
			KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
			g.initialize(256);
			keyPair = g.generateKeyPair();
			return keyPair.getPrivate().getEncoded();
		}
		catch (NoSuchAlgorithmException | NoSuchProviderException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			if (keyPair != null)
			{
				try
				{
					keyPair.getPrivate().destroy();
				}
				catch (DestroyFailedException ex)
				{
				}
			}
		}
	}
	
	public static String toString(ECPoint p)
	{
		return Helper.toHexString(p.getEncoded(true));
	}
}
