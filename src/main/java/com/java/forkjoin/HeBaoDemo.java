package com.java.forkjoin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import sun.misc.BASE64Encoder;

public class HeBaoDemo {

	public static void main(String[] args) {
		String mblNo = getMblNo("1587797,1466651047,1466651647,1466651047,127.0.0.1,client.cmpay.com",
				"4e925199db805bfdfc89f9b4cbeb322b",
				"tdSFntjTrwOVry39RT3AuFdPhW6sXXVA",
				"http://218.75.224.28:28700/ccaweb/CCLIMCA4/2208000.dor",
				"WPH00001");
		System.out.println("获取手机号码:" + mblNo);

	}

	private static String getMblNo(String credential, String signData, String secret, String url, String devId) {
		String xml = buildConfirmXmlMessage(credential,signData,secret,devId);
		String respXml = sendRequest(xml,url);
		String mblNo = "";
		
		try {
			Document doc = DocumentHelper.parseText(respXml);
			Element root = doc.getRootElement();
			mblNo = root.element("BODY").element("MBL_NO").getText();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mblNo;
	}

	private static String sendRequest(String reqData, String url) {
		StringWriter writer = new StringWriter();
		OutputStreamWriter osw = null;
		
		try {
			URL reqUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)reqUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("User-Agent", "stargate");
			conn.setRequestProperty("Content-Type", "application/json");
			osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
			osw.write(reqData);
			osw.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			char[] chars = new char[256];
			int count = 0;
			while((count = br.read(chars)) > 0) {
				writer.write(chars,0,count);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return writer.toString();
		
	}

	private static String buildConfirmXmlMessage(String credential, String signData, String secret, String devId) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("ROOT");
		Element head = root.addElement("HEAD");
		Element body = root.addElement("BODY");
		
		//HEAD部分
		head.addElement("TXNCD").setText("2208000");
		head.addElement("MBLNO").setText("");
		head.addElement("SESSIONID").setText("");
		head.addElement("PLAT").setText("99");
		head.addElement("UA").setText("default");
		head.addElement("VERSION").setText("default");
		head.addElement("PLUGINVER").setText("");
		head.addElement("NETTYPE").setText("");
		head.addElement("MCID").setText("default");
		head.addElement("MCA").setText("default");
		head.addElement("IMEI").setText("default");
		head.addElement("IMSI").setText("default");
		head.addElement("SOURCE").setText("default");
		head.addElement("DEVID").setText(devId);
		
		Date currentTime = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HHmmss");
		String serialNo = df.format(currentTime);
		head.addElement("SERLNO").setText(serialNo);
		
		//BODY部分
		body.addElement("CREDTENTIAL").setText(credential);
		body.addElement("SIGN_DATA").setText(signData);
		body.addElement("SIGN_TYPE").setText("MD5");
		
		String reqDate = root.asXML();
		
		reqDate = reqDate.substring(0, reqDate.length() - 7);
		reqDate = reqDate.substring(6, reqDate.length());
		
		String signature = signature(reqDate,secret);
		
		//把签名结果组装到xml中
		root.addElement("SIGNATURE").setText(signature);
		String xml = null;
		XMLWriter writer = null;
		ByteArrayOutputStream baos = null;
		
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setIndent(false);
		format.setNewlines(false);
		format.setLineSeparator("");
		baos = new ByteArrayOutputStream();
		try {
			writer = new XMLWriter(baos,format);
			writer.write(doc);
			return xml;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return xml;
	}

	private static String signature(String reqDate, String secret) {
		String hashAlgo = "HmacSHA1";
		String appSecret = secret;
		String reqData = reqDate;
		SecretKeySpec spec = new SecretKeySpec(appSecret.getBytes(),hashAlgo);
		
		Mac mac;
		String sign = "";
		
		try {
			mac = Mac.getInstance(hashAlgo);
			mac.init(spec);
			byte[] bytes = mac.doFinal(reqData.getBytes());
			sign = new String((new BASE64Encoder()).encodeBuffer(bytes));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sign;
	}

}
