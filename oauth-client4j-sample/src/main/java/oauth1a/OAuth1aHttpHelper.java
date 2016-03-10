package oauth1a;

import java.util.HashMap;
import java.util.Map;

import http.HttpHelperAsync;
import http.HttpHelperAsync.Headers;
import http.HttpHelperAsync.Response;
import json.JSONObject;

public class OAuth1aHttpHelper {

	public static Response get(String consumerKey, String consumerSecret, 
			String signatureMethod, long timestamp, 
			String nonce, float version, 
			String oauthToken, String oauthTokenSecret, 
			String verifier, String url, Headers headers, Map<String, Object> parameters, long timeoutMillis) throws Exception {
		headers = headers(consumerKey, consumerSecret, signatureMethod,
				timestamp, nonce, version, oauthToken, oauthTokenSecret,
				verifier, url, headers, parameters, timeoutMillis, "GET");
		return HttpHelperAsync.get(url, headers, parameters, timeoutMillis);
	}
	
	public static Response post(String consumerKey, String consumerSecret, 
			String signatureMethod, long timestamp, 
			String nonce, float version, 
			String oauthToken, String oauthTokenSecret, 
			String verifier, String url, Headers headers, Map<String, Object> parameters, long timeoutMillis) throws Exception {
		headers = headers(consumerKey, consumerSecret, signatureMethod,
				timestamp, nonce, version, oauthToken, oauthTokenSecret,
				verifier, url, headers, parameters, timeoutMillis, "POST");
		return HttpHelperAsync.post(url, headers, parameters, timeoutMillis);
	}
	
	public static Response postJSON(String consumerKey, String consumerSecret, 
			String signatureMethod, long timestamp, 
			String nonce, float version, 
			String oauthToken, String oauthTokenSecret, 
			String verifier, String url, Headers headers, JSONObject parameters, long timeoutMillis) throws Exception {
		
		if (null == headers) {
			headers = new Headers();
			headers.put("Content-Type", HttpHelperAsync.APPLICATION_JSON);
		}
		
		headers.put("Authorization", AuthorizationUtil.generateAuthorizationHeader(consumerKey, consumerSecret, 
				signatureMethod, timestamp, nonce, version, 
				oauthToken, oauthTokenSecret, verifier, url, null, "POST"));
		return HttpHelperAsync.postJSON(url, headers, parameters, timeoutMillis);
	}
	
	private static Headers headers(String consumerKey, String consumerSecret, 
			String signatureMethod, long timestamp, 
			String nonce, float version, 
			String oauthToken, String oauthTokenSecret, 
			String verifier, String url, Headers headers, Map<String, Object> parameters, long timeoutMillis, String reqType) throws Exception {
		if (null != headers) {
			Object contentType = headers.get("Content-Type");
			if (null != contentType && HttpHelperAsync.APPLICATION_JSON.equals(contentType.toString())) {
				headers.put("Authorization", AuthorizationUtil.generateAuthorizationHeader(consumerKey, consumerSecret, 
						signatureMethod, timestamp, nonce, version, 
						oauthToken, oauthTokenSecret, verifier, url, null, reqType));
				return headers;
			}
		} else {
			headers = new Headers();
		}
		headers.put("Authorization", AuthorizationUtil.generateAuthorizationHeader(consumerKey, consumerSecret, 
				signatureMethod, timestamp, nonce, version, 
				oauthToken, oauthTokenSecret, verifier, url, parameters, reqType));
		return headers;
	}
	
	public static void main(String[] args) throws Exception {
		String oauth_consumer_key = "12udaAEghEJGEHkw";
		String oauth_consumer_secret = "0QNOWUHsWcI9i8UyqBFKUarayqBDUsVnxJrumYHEUl";
		String oauth_signature_method = null;
		long oauth_timestamp = System.currentTimeMillis()/1000;
		String oauth_nonce = String.valueOf(oauth_timestamp + AuthorizationUtil.RAND.nextInt());
		float oauth_version = 1.0f;
		String oauth_token = "3f3f94d26637acc3f265c016ca471183";
		String oauth_token_secret = "9a392124d84d6bddd44f86c88bbeca4";
		String oauth_verifier = null;
		
		String url = "http://192.168.22.144/openapi/v1/ticket/acquire.json";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("openToken", "a5487e1fae239bc947ac3951338d8ec");
		parameters.put("appid", "10103");
		parameters.put("mid", "10109");
		post(oauth_consumer_key, oauth_consumer_secret, 
				oauth_signature_method, oauth_timestamp, 
				oauth_nonce, oauth_version, oauth_token, oauth_token_secret, oauth_verifier, url, null, parameters, 0);
		
		String url2 = "http://192.168.22.144/openapi/third/v1/pubacc/pubsend";
		JSONObject parameters2 = JSONObject.parseObject("{\"from\":{\"no\":\"10109\",\"nonce\":\"0.6917891008699647\",\"pub\":\"XT-10000\",\"pubtoken\":\"ea3c34f11ada8219f0eeeb913d679b362c3f0810\",\"time\":\"1430471411\"},\"msg\":{\"todo\":\"1\",\"appid\":\"10103\",\"text\":\"各位金蝶同事，为了给用户提供更好的云之家服务，近一周内，我们会在非高峰使用时段对云之家服务做必要的性能测试及优化，测试和优化范围仅限金蝶内部用户，外部客户不受任何影响。\n在此期间，如遇任何异常使用问题，请及时与我们联系：林毓铮 0755-86072654。谢谢大家！\"},\"to\":[{\"no\":\"10109\",\"user\":[\"7b1096ec-90be-42a8-bde2-e1c6a7ed4371\"]}],\"type\":2}");
		postJSON("2016", "2016", 
				oauth_signature_method, oauth_timestamp, 
				oauth_nonce, oauth_version, null, null, null, url2, null, parameters2, 0);
	}
	
}
