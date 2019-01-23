package com.emreditor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicReference;

public class URIUtil {
	/**
	 * 向指定URL发送GET方法的请求
	 * 为什么cookie需要从浏览器那边复制过来
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		AtomicReference<StringBuffer> result = new AtomicReference<>(new StringBuffer());
		AtomicReference<BufferedReader> in = new AtomicReference<>();
		try {
			String urlNameString = url + (param==null||param.length()==0?"":"?" + param);
			//System.err.println(urlNameString);
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			setConnect(connection);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in.set(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			String line;
			while ((line = in.get().readLine()) != null) result.get().append(line);
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {// 使用finally块来关闭输入流
			try {
				if (in.get() != null) {
					in.get().close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.get().toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		AtomicReference<PrintWriter> out = new AtomicReference<>();
		AtomicReference<BufferedReader> in = new AtomicReference<>();
		AtomicReference<StringBuilder> result = new AtomicReference<>(new StringBuilder());

		System.out.println(url + "?" + param);
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			setConnect(conn);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out.set(new PrintWriter(conn.getOutputStream()));
			// 发送请求参数
			out.get().print(param);
			// flush输出流的缓冲
			out.get().flush();
			
			// 定义BufferedReader输入流来读取URL的响应
			in.set(new BufferedReader(new InputStreamReader(conn.getInputStream())));
			String line;
			while ((line = in.get().readLine()) != null) {
				result.get().append(line);
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally {// 使用finally块来关闭输出流、输入流
			try {
				if (out.get() != null) {
					out.get().close();
				}
				if (in.get() != null) {
					in.get().close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.get().toString();
	}
	/**
	 * 设置通用的请求属性
	 */
	private static void setConnect(URLConnection conn) {
//		conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		conn.setRequestProperty("Cache-Control", "max-age=0");
//		conn.setRequestProperty("connection", "Keep-Alive");
//		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//		conn.setRequestProperty("Cookie", sessionid);
//		conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
	}
	public static void main(String[] args) throws Exception {
		String path="http://192.168.10.68:8080/dpage/getPageData";
		String param=null;
		String wardData=URIUtil.sendGet(path, param);
		System.err.println(wardData);
	}
}