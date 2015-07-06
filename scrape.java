package Http_request;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Html_parse {
	static String themeStoreId;
	static long id;
	public String getHTML(String urlToRead) {
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
	
	public static void main(String args[]){
		Html_parse c = new Html_parse();
		String text = c.getHTML("http://new-shop-16.myshopify.com/");
		System.out.println(text);
		
	
		//String patternString = "(?i)(Shopify.theme)(.+?)(;)";
		String patternString = "Shopify.theme";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		
		while (matcher.find()) {
		    themeStoreId = matcher.group(0);
		    System.out.println(themeStoreId);
		}
		
		String content = themeStoreId;
//		content = content.replace("Shopify.theme = ", "");
//		content = content.replace(";", "");
		System.out.println(content);
		
//		int i = 0;
//		JSONParser parser = new JSONParser();
//		if (i == 0) {
//			try {
//				Object obj2 = parser.parse(content.toString());
//				JSONObject jsonObject = (JSONObject) obj2;
//				id = (long) jsonObject.get("theme_store_id");
//				System.out.println("theme id: "+id);
//				} catch (ParseException e) {
//				e.printStackTrace();
//				}
//			}
		}
	}

		
