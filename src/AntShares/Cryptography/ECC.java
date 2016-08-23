package AntShares.Cryptography;

import java.security.*;

import javax.security.auth.DestroyFailedException;

import org.bouncycastle.asn1.x9.*;

public class ECC
{
	public static final X9ECParameters secp256r1 = ECNamedCurveTable.getByName("secp256r1");
	
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
}
