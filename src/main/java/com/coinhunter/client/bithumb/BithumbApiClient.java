package com.coinhunter.client.bithumb;


import com.coinhunter.utils.bithumb.BithumbClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Slf4j
public class BithumbApiClient {

	private String apiUrl;

	private String apikey;

	private String apiSecret;

	public BithumbApiClient(String apiUrl,String apiKey, String apiSecret) {
		this.apiUrl = apiUrl;
		this.apikey = apiKey;
		this.apiSecret = apiSecret;
	}

	private String usecTime() {
		return String.valueOf(System.currentTimeMillis());
	}

	private String request(String strHost, String strMemod, HashMap<String, String> rgParams, HashMap<String, String> httpHeaders) {
		String response = "";

		if (strHost.startsWith("https://")) {
			HttpRequest request = HttpRequest.get(strHost);
			// Accept all certificates
			request.trustAllCerts();
			// Accept all hostnames
			request.trustAllHosts();
		}

		if (strMemod.toUpperCase().equals("HEAD")) {
		} else {
			HttpRequest request = null;

			// POST/GET ????
			if (strMemod.toUpperCase().equals("POST")) {

				request = new HttpRequest(strHost, "POST");
				request.readTimeout(10000);

				//System.out.println("POST ==> " + request.url());

				if (httpHeaders != null && !httpHeaders.isEmpty()) {
					httpHeaders.put("api-client-type", "2");
					request.headers(httpHeaders);
					//System.out.println(httpHeaders.toString());
				}
				if (rgParams != null && !rgParams.isEmpty()) {
					request.form(rgParams);
				//	System.out.println(rgParams.toString());
				}
			} else {
				request = HttpRequest.get(strHost
					+ BithumbClientUtils.mapToQueryString(rgParams));
				request.readTimeout(10000);

				//System.out.println("Response was: " + response);
			}

			if (request.ok()) {
				response = request.body();
			} else {
				response = "error : " + request.code() + ", message : "
					+ request.body();
			}
			request.disconnect();
		}

		return response;
	}

	public static String encodeURIComponent(String s) {
		String result = null;

		try {
			result = URLEncoder.encode(s, "UTF-8")
				.replaceAll("\\+", "%20")
				.replaceAll("\\%21", "!")
				.replaceAll("\\%27", "'")
				.replaceAll("\\%28", "(")
				.replaceAll("\\%29", ")")
				.replaceAll("\\%26", "&")
				.replaceAll("\\%3D", "=")
				.replaceAll("\\%7E", "~");
		}

		// This exception should never occur.
		catch (UnsupportedEncodingException e) {
			result = s;
		}

		return result;
	}

	private HashMap<String, String> getHttpHeaders(String endpoint, HashMap<String, String> rgData, String apiKey, String apiSecret) {

		String strData = BithumbClientUtils.mapToQueryString(rgData).replace("?", "");
		String nNonce = usecTime();

		strData = strData.substring(0, strData.length() - 1);
		strData = encodeURIComponent(strData);

		HashMap<String, String> array = new HashMap<String, String>();
		String str = endpoint + ";" + strData + ";" + nNonce;
		String encoded = asHex(hmacSha512(str, apiSecret));

		array.put("Api-Key", apiKey);
		array.put("Api-Sign", encoded);
		array.put("Api-Nonce", String.valueOf(nNonce));

		return array;
	}

	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String HMAC_SHA512 = "HmacSHA512";

	public static byte[] hmacSha512(String value, String key) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(
				key.getBytes(DEFAULT_ENCODING),
				HMAC_SHA512);

			Mac mac = Mac.getInstance(HMAC_SHA512);
			mac.init(keySpec);

			final byte[] macData = mac.doFinal(value.getBytes());
			return new Hex().encode(macData);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String asHex(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

	@SuppressWarnings("unchecked")
	public String callApi(String endpoint, HashMap<String, String> params) {
		String rgResultDecode = "";
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("endpoint", endpoint);

		if (params != null) {
			rgParams.putAll(params);
		}

		String api_host = apiUrl + endpoint;
		HashMap<String, String> httpHeaders = getHttpHeaders(endpoint, rgParams, apikey, apiSecret);

		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);

//		if (!rgResultDecode.startsWith("error")) {
//			HashMap<String, String> result;
//			try {
//				result = new ObjectMapper().readValue(rgResultDecode,
//					HashMap.class);
//				//System.out.println("==== ??? ??? ====");
//				System.out.println(result.get("status"));
//			} catch (IOException e) {
//				log.error(e.getMessage(), e);
//			}
//		}
		return rgResultDecode;
	}
}
