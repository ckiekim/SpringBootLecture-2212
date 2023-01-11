package com.mulcam.demo.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TanslateExercise {

	public static void main(String[] args) throws Exception {
		String accessId = "p98xdqtqe3";
		String secretKey = "7kH5hCVA3mQuqqkbhppenhtbN9VcHnja071XxbWJ";

		String text = URLEncoder.encode("만나서 반갑습니다.", "UTF-8");
        String apiURL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        // post request
        String postParams = "source=ko&target=en&text=" + text;
        conn.setDoOutput(true);
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.writeBytes(postParams);
        dos.flush();
        dos.close();
        
        int responseCode = conn.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 오류 발생
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
        System.out.println(sb.toString());
        
        JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject result = (JSONObject) ((JSONObject) object.get("message")).get("result");
		String transText = (String) result.get("translatedText");
		System.out.println(transText);
	}

}
