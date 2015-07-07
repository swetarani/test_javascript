package com.wizrocket.shopify.service;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
//import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by ankit on 16/06/15.
 */

/* Modified for Oauth */

public class Servlet extends AbstractHandler {

    private static int port = 8980;
    static String code;
    static String Oauth_token;
    static String Shop_name;
    static String theme_id;
    static String dynamic_script;
    
    
    public void handle(String CodeValue, Request baseRequest, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        
        if ( uri.equals("/s")){
            Runtime r = Runtime.getRuntime();
            long total = r.totalMemory();
            
            Servlet.code = request.getParameter("code").toString();
            Servlet.Shop_name = request.getParameter("shop").toString();
            String out = "{\"memory_total\":"+total+"}"+"code : "+ code;
            if( code != null )
				try {
					sendPost();
				} catch (Exception e) {
					e.printStackTrace();
				}
            respond(out,baseRequest,response);
            } 
        else {
        	respond(null,baseRequest,response);
        	}  
        
        if (Shop_name != null){
        	System.out.println("Got shop name");
        	String text = readScript("https://raw.githubusercontent.com/swetarani/test_javascript/master/global.js");
        	System.out.println("Read github");
        	modify(text);
        	System.out.println("Modified script");
        	System.out.println(dynamic_script);	
        }
        
        // script posting
        if ( uri.equals("/j")){
        	String out = "hello j ";
        	if(dynamic_script != null){
        		out = dynamic_script + "hello done";
        	}
            respond(out,baseRequest,response);
            add_scripts();
            } 
        else {
        	respond(null,baseRequest,response);
        	}  
    }
    
    private static void respond(String out,Request baseRequest, HttpServletResponse response) throws IOException{
        if ( out != null )
            response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        if ( out != null )
            response.setCharacterEncoding("utf-8");
        baseRequest.setHandled(true);
        if ( out != null)
            response.getWriter().write(out);
    }
    
    private static String USER_AGENT = "Mozilla/5.0";
    
	// HTTP POST request for getting Oauth Token
	public static void sendPost() throws Exception {
        String client_id = "a35a72235a8a299152491ba950da3c28";
        String client_secret = "24a1e7c88be093c7d2768e8a79d50c87";
        String c = code;
        System.out.println("c:  "+ c);
        
        String shop_url = String.format("https://%s/admin/oauth/access_token", Shop_name);
		//String url = "https://test-store-1017.myshopify.com/admin/oauth/access_token";
		URL obj = new URL(null, shop_url, new sun.net.www.protocol.https.Handler());
		//URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		
		//String r = String.format("A String %s %s", client_id, client_secret);
		String urlParameters = String.format("{\r\n    \"client_id\": \"%s\",\r\n    \"client_secret\": \"%s\",\r\n    \"code\": \"%s\"\r\n}", client_id, client_secret, c);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + shop_url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		
		int i = 0;
		
		JSONParser parser = new JSONParser();
		if (i == 0) {
			try {
				Object obj2 = parser.parse(response.toString());
				JSONObject jsonObject = (JSONObject) obj2;
				Servlet.Oauth_token = (String) jsonObject.get("access_token");
				System.out.println("Oauth Token: "+Oauth_token);
				if( Oauth_token != null )
					try {
						getThemeId(Oauth_token);
						copyTheme();
						create_json_generator();
						update_theme();
						i = 1;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (ParseException e) {
				e.printStackTrace();
				}
		}
 
	}
	
	public static String readScript(String urlToRead){
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		StringBuilder result = new StringBuilder();
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result.append(line);
				}
			rd.close();
			} catch (IOException e) {
				e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					}
		return result.toString();
	}
	
	public static String modify(String text){
		String patternString = "6Z6-57Z-6K4Z";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
		    dynamic_script = matcher.replaceAll(Shop_name);
		    //System.out.println(present_id);
		}
		System.out.println("Dynamic_Script : "+dynamic_script);
		return dynamic_script;
	}
	
	
	private static void getThemeId(String token) throws Exception {
        int shoplen = Shop_name.length()-14;
        String shop = Shop_name.substring(0, shoplen);
            String url = "https://"+shop+".myshopify.com/admin/themes.json?fields=id,role";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Shopify-Access-Token", token);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            String substr = "main";
            int index= response.toString().indexOf(substr);
            System.out.println(index);
            theme_id = response.toString().substring(index-17, index-9);
            System.out.println(theme_id);
        }


	// HTTP PUT request to copy theme.liquid into snippet as theme_copy.liquid
	public static void copyTheme() throws IOException {
		//String theme_id = "33274243";
		String asset_url = String.format("https://%s/admin/themes/%s/assets.json", Shop_name, theme_id);
		URL obj = new URL(null, asset_url, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("PUT");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("X-Shopify-Access-Token", Oauth_token);
		String urlParameters = String.format("{\r\n    \"asset\": {\r\n        \"key\": \"snippets\\/theme_copy.liquid\",\r\n        \"source_key\": \"layout\\/theme.liquid\"\r\n    }\r\n}");
 
		// Send put request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + asset_url);
		System.out.println("Put parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
	}
	


	// HTTP PUT request to create json_generator.liquid in snippet
	public static void create_json_generator() throws IOException {
		//String theme_id = "33274243";
		String url = String.format("https://%s/admin/themes/%s/assets.json", Shop_name, theme_id);
		URL obj = new URL(null, url, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("PUT");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("X-Shopify-Access-Token", Oauth_token);
		String urlParameters = "{\r\n  \"asset\": {\r\n    \"key\": \"snippets\\/json_generator.liquid\",\r\n    \"value\": \"<script>\\n\\tvar variables = \\\"shop_url,shop_domain,shop_email,shop_money_format,product_json,product_title,product_price,cart_json,cart_item_count,cart_total_price\\\";\\n\\tvar shop_url = \\\"{{ shop.url }}\\\";\\n\\tvar shop_domain = \\\"{{ shop.domain }}\\\";\\n\\tvar shop_email = \\\"{{ shop.email }}\\\";\\n\\tvar shop_money_format = \\\"{{ shop.money_format }}\\\";\\n\\tvar shop_name =\\\"{{shop.name}}\\\";\\n\\tvar charged_currency = \\\"{{shop.currency}}\\\";\\n\\tvar customer_name = \\\"{{ customer.name }}\\\";\\n\\tvar customer_identity = \\\"{{ customer.id }}\\\";\\n\\tvar customer_email = \\\"{{ customer.email }}\\\";\\n\\tvar customer_phone = \\\"{{ customer.phone }}\\\";\\n{% if template contains 'product' %}\\n\\tvar product_json = {{ product | json }};\\n\\tvar product_title = \\\"{{ product.title }}\\\";\\n\\tvar product_price = \\\"{{ product.price | money }}\\\"\\n\\tvar product_category_name = \\\"{{ collection.title }}\\\";\\n\\tvar currency = \\\"{{ shop.currency }}\\\";\\n\\tvar quantity =\\\"{{ line_item.quantity }}\\\";\\n\\tvar seller_name = \\\"{{product.vendor}}\\\"\\n{% endif %}\\n{% if template contains 'collection' %}\\n\\tvar collection_name = \\\"{{collection.title}}\\\"\\n{% endif %}\\n{% if search.performed %}\\n\\tvar searchterm = \\\"{{search.terms}}\\\"\\n{% endif %}\\n{% if template contains 'cart' %}\\n\\tvar cart_json = {{ cart | json }};\\n\\tvar cart_item_count= \\\"{{ cart.item_count }}\\\";\\n\\tvar cart_total_price=\\\"{{ cart.total_price | money }}\\\";\\n{% endif %}\\n</script>\"\r\n  }\r\n}";
		// Send put request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + url);
		System.out.println("Put parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
	}
	

	// HTTP PUT request to update original theme.liquid in layout
	public static void update_theme() throws IOException {
		//String theme_id = "33274243";
		String url = String.format("https://%s/admin/themes/%s/assets.json", Shop_name, theme_id);
		URL obj = new URL(null, url, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("PUT");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("X-Shopify-Access-Token", Oauth_token);
		String urlParameters = "{\r\n  \"asset\": {\r\n    \"key\": \"layout\\/theme.liquid\",\r\n    \"value\": \"{{content_for_header}}\\n{{content_for_layout}}\\n{% include 'theme_copy' %}\\n{% include 'json_generator' %}\"\r\n  }\r\n}";
		// Send put request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + url);
		System.out.println("Put parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
	}
	
	public static void add_scripts() throws IOException {
		//String theme_id = "33274243";
		String script_url = String.format("https://%s/admin/script_tags.json", Shop_name);
		URL obj = new URL(null, script_url, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("X-Shopify-Access-Token", Oauth_token);
		//String urlParameters = "{\r\n    \"script_tag\": {\r\n        \"event\": \"onload\",\r\n        \"src\": \"https://rawgit.com/swetarani/test_javascript/master/global.js\"\r\n        \r\n    }\r\n}";
		String urlParameters = "{\r\n    \"script_tag\": {\r\n        \"event\": \"onload\",\r\n        \"src\": \"http://localhost:8989/s\"\r\n        \r\n    }\r\n}";
		// Send put request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + script_url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
	}
	


    public static void main(String s[]) throws Exception{
        Server server = new Server();
        server.setHandler(new Servlet());
        HttpConfiguration http_config = new HttpConfiguration();
        ServerConnector http = new ServerConnector(server,new HttpConnectionFactory(http_config));
        http.setPort(port);
        

        /* 15 minutes */
        //http.setIdleTimeout(900000);
        server.setConnectors(new Connector[]{http});
        server.start();
        server.join(); 
    }
}
