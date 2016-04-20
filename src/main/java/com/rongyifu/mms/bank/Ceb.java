package com.rongyifu.mms.bank;

	
import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.*;
import com.rongyifu.mms.utils.LogUtil;

public class Ceb {
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	@SuppressWarnings("deprecation")
	public static String query(Map<String, String> p) throws Exception {
		String TransName=p.get("TransName");
        String URL=p.get("Url");
		String Plain=p.get("Plain");
		String Signature=p.get("Signature");
		InputStream in = null;
		OutputStream out = null;
//		byte[] buffer = new byte[4096];
		String str_return = "";
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
		    URL console = new URL(URL+"?TransName="+TransName+"&Plain="+Plain+"&Signature="+Signature);
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.connect();
			InputStream is = conn.getInputStream();
			DataInputStream indata = new DataInputStream(is);
			String ret = "";

			while (ret != null) {
				ret = indata.readLine();
				if (ret != null && !ret.trim().equals("")) {
					str_return = str_return + new String(ret.getBytes("ISO-8859-1"), "UTF-8");
					
				}
			}
			conn.disconnect();
		} catch (ConnectException e) {
			LogUtil.printErrorLog("", e);
			throw e;

		} catch (IOException e) {
			LogUtil.printErrorLog("", e);
			throw e;

		} finally {
			try {
				if(in != null)
					in.close();
			} catch (Exception e) {
			}
			try {
				if(out != null)
					out.close();
			} catch (Exception e) {
			}
		}
		return str_return;
	}
	
}