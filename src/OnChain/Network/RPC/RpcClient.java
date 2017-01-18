package OnChain.Network.RPC;

import java.io.*;
import java.net.*;

import OnChain.IO.Json.*;

public class RpcClient
{
	private final URL url;
	
	public RpcClient(String url) throws MalformedURLException
	{
		this.url = new URL(url);
	}
	
	public JObject call(String method, JObject ...params) throws RpcException, IOException
	{
		JObject response = send(makeRequest(method, params));
		if (response.containsProperty("result"))
			return response.get("result");
		else if (response.containsProperty("error"))
			throw new RpcException((int)response.get("error").get("code").asNumber(), response.get("error").get("message").asString());
		else
			throw new IOException();
	}
	
	private static JObject makeRequest(String method, JObject[] params)
	{
		JObject request = new JObject();
		request.set("jsonrpc", new JString("2.0"));
		request.set("method", new JString(method));
		request.set("params", new JArray(params));
		/**
		 * ChangLog:
		 * [desc] avoid to occured id=2.38977665544E-4, because when deserialized, "E-4" will lead to such exp:
		 * 		{"id":null,"jsonrpc":"2.0","error":{"code":-32700.0,"message":"Parse error"}}
		 * [auth]tsh
		 * [date]2017-01-17
		 */
//		request.set("id", new JNumber(Math.random()));
		request.set("id", new JNumber(getNextId()));
		return request;
	}
	private static double getNextId() {
		double d = 0.0;
		do{
			d = Math.random();
		} while((""+d).indexOf("E") != -1);
		return d;
	}
	
	private JObject send(JObject request) throws IOException
	{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		try (OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream()))
		{
			w.write(request.toString());
		}
		try (InputStreamReader r = new InputStreamReader(connection.getInputStream()))
		{
			return JObject.parse(r);
		}
	}
}
