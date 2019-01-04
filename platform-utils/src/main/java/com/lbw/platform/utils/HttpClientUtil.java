package com.lbw.platform.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	private static CookieStore cookieStore = null;
	private static HttpClientContext context = null;

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookieStore).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	/**
	 * 模拟请求
	 * 
	 * @param url
	 *            资源地址
	 * @param map
	 *            参数列表
	 * @param encoding
	 *            编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String send(String url, String jsonstr, String encoding)
			throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		String body = "";
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();

		CloseableHttpClient hp = createSSLClientDefault();
		// HttpGet hg = new HttpGet("https://news.cnblogs.com/");
		HttpPost hg = new HttpPost(url);
		hg.addHeader("Content-Type", "application/json");
		StringEntity se = new StringEntity(jsonstr);
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
		hg.setEntity(se);
		CloseableHttpResponse response = hp.execute(hg);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity, "utf-8");
		System.out.println(content);
		hp.close();
		return content;
	}

	public static String doPost(String url, String jsonstr, String charset) {
		SSLClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json");
			StringEntity se = new StringEntity(jsonstr);
			se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
			httpPost.setEntity(se);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
					CookieStore cookieStore = httpClient.getCookieStore();
					httpClient.setCookieStore(cookieStore);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private static void getResoucesByLoginCookies() throws Exception {

		String username = "13755181631";// 登录用户
		String password = "1234";// 登录密码

		// 需要提交登录的信息
		String urlLogin = "https://jwb.sc-edu.com/api/user/login/";

		// 登录成功后想要访问的页面 可以是下载资源 需要替换成自己的iteye Blog地址
		// String urlAfter = "http://314649444.iteye.com/";

		// DefaultHttpClient client = new DefaultHttpClient(new
		// PoolingClientConnectionManager());
		SSLClient client = new SSLClient();
		
		//CloseableHttpClient client =  createSSLClientDefault();

		/**
		 * 第一次请求登录页面 获得cookie 相当于在登录页面点击登录，此处在URL中 构造参数，
		 * 如果参数列表相当多的话可以使用HttpClient的方式构造参数 此处不赘述
		 */
		HttpPost post = new HttpPost(urlLogin);
		String param = "mobile=" + username + "&auth=" + password;
		StringEntity stringEntity = new StringEntity(param);
		stringEntity.setContentType("application/x-www-form-urlencoded");
		post.setEntity(stringEntity);
		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		byte[] content = new byte[300];
		response.getEntity().getContent().read(content);
		System.out.println(content.toString());

		
		//setCookieStore(response,"https://jwb.sc-edu.com");
	}

	public static void setCookieStore(HttpResponse httpResponse, String host) {
		cookieStore = new BasicCookieStore();
		// JSESSIONID
		if (null == httpResponse.getFirstHeader("Set-Cookie")) {
			cookieStore = null;
		} else {
			String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
			String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
			// 新建一个Cookie
			BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
			cookie.setVersion(0);
			cookie.setDomain(host);
			cookie.setPath("/");
			cookieStore.addCookie(cookie);
		}
	}

	/*public static void main(String[] args) throws Exception {
		// String url = "https://jwb.sc-edu.com/api/user/login";
		// String jsonStr = "{\"mobile\":\"13954174621\",\"auth\":\"1234\"}";
		// String httpOrgCreateTestRtn = HttpClientUtil.doPost(url, jsonStr,
		// "utf-8");
		// String username = "13755181631";// 登录用户
		// String password = "1234";// 登录密码
		// String url =
		// "https://jwb.sc-edu.com/api/user/login/?mobile=13755181631&auth=5556";
		// String jsonstr="{\"mobile\":\"12344556677\",\"auth\":\"1234\"}";
		// System.out.println(send(url,jsonstr,"utf-8"));

		getResoucesByLoginCookies();

		
		 * StaticTest st1 = new StaticTest(); StaticTest st2 = new StaticTest();
		 * 
		 * if (st1 == st2) System.out.println("st1 == st2"); if
		 * (st1.equals(st2)) System.out.println("st1 equesal st2");
		 
	}*/
}

class StaticTest {
	static int i = 47;
}
