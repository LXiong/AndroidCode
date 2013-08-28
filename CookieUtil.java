package com.example.httpdemo.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Cookie HTTP ���ʹ�����
 * 
 * @author cally
 * 
 */
public class CookieUtil {
	/**
	 * ��½�ӷ������л�ȡ��Cookie��ȫ��keyֵ
	 */
	public static String[] key = new String[] { "fWfx_2132_sid",
			"fWfx_2132_auth", "fWfx_2132_lastact", "fWfx_2132_creditrule",
			"fWfx_2132_creditbase", "fWfx_2132_creditnotice",
			"fWfx_2132_clearUserdata", "fWfx_2132_ulastactivity",
			"fWfx_2132_lastvisit" };

	/**
	 * ��½ ��cookieֵ����xml������������
	 * 
	 * @param context
	 *            Context����
	 * @param Url
	 *            ���ӵ�ַ
	 * @return
	 */
	public static String loginRequest(Context context, String url) {
		try {
			HttpClient httpClient = new HttpClient();
			DefaultHttpParams.getDefaultParams().setParameter(
					"http.protocol.cookie-policy",
					CookiePolicy.BROWSER_COMPATIBILITY);
			GetMethod getMethod = new GetMethod(url);
			httpClient.executeMethod(getMethod);
			Header header = getMethod.getResponseHeader("Set-cookie");
			String headerCookie = header.getValue();
			String[] value = new String[key.length];
			SharedPreferences.Editor editor = context.getSharedPreferences(
					"cookies", Context.MODE_PRIVATE).edit();
			for (int i = 0; i < key.length; i++) {
				String index = key[i] + "=";
				value[i] = headerCookie.substring(headerCookie.indexOf(index)
						+ index.length(),
						headerCookie.indexOf(";", headerCookie.indexOf(index)));
				editor.putString(key[i], value[i]);
			}
			editor.commit();
			return getMethod.getStatusCode() == 200 ? getMethod
					.getResponseBodyAsString() : "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ���������� �ӱ��ػ�ȡCookie��ֵ����������������������
	 * 
	 * @param context
	 * @param url
	 * @return
	 */
	public static String otherRequest(Context context, String url) {
		try {
			HttpClient httpClient = new HttpClient();
			StringBuffer sb = new StringBuffer();
			SharedPreferences share = context.getSharedPreferences("cookies",
					Context.MODE_PRIVATE);
			for (int i = 0; i < key.length; i++) {
				sb.append(key[i] + "=").append(share.getString(key[i], ""))
						.append(";");
			}
			PostMethod method = new PostMethod(url);
			method.setRequestHeader("Cookie", sb.toString());
			httpClient.executeMethod(method);
			return method.getStatusCode() == 200 ? method
					.getResponseBodyAsString() : "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
