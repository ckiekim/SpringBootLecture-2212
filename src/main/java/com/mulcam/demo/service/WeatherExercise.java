package com.mulcam.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WeatherExercise {

	public static void main(String[] args) throws Exception {
		Double lat = 37.311494, lon = 127.075369;
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather" 
					+ "?lat=" + lat
					+ "&lon=" + lon
					+ "&appid=9e9e4eefc93b5af91c70bd666c346dc8"
					+ "&units=metric&lang=kr";
		System.out.println(apiUrl);
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		
		System.out.println(sb.toString());
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
//		JSONArray weather_ = (JSONArray) object.get("weather");
//		JSONObject weather = (JSONObject) weather_.get(0);
		JSONObject weather = (JSONObject) ((JSONArray) object.get("weather")).get(0);
		String desc = (String) weather.get("description");
		String iconCode = (String) weather.get("icon");
		String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
		JSONObject main = (JSONObject) object.get("main");
		Double temp = (Double) main.get("temp");
		Double tempMin = (Double) main.get("temp_min");
		Double tempMax = (Double) main.get("temp_max");
		
		String html = "<img src=\"" + iconUrl + "\" height=\"32\"><strong>"
					+ desc + "</strong>, 온도: <strong>"
					+ temp + "&#8451</strong>";
		System.out.println(html);
	}

}
