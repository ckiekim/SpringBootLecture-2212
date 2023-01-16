package com.mulcam.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpeechNaver {

	public static void main(String[] args) throws Exception {
		String accessId = "spyvtsexbi";             // Application Client ID";
        String secretKey = "i0fuRdbOBhXv2B5TbJCwwvwo1pK0xnGLSZOAGe0c";
        
        String imgFile = "/Temp/spring/rawAudio.wav";
        File voiceFile = new File(imgFile);
        String language = "Kor";        // 언어 코드 ( Kor, Jpn, Eng, Chn )
        String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
        URL url = new URL(apiURL);
        
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        
        OutputStream os = conn.getOutputStream();
        FileInputStream is = new FileInputStream(voiceFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        is.close();
        
        BufferedReader br = null;
        int responseCode = conn.getResponseCode();
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 오류 발생
            System.out.println("error!!!!!!! responseCode= " + responseCode);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
        System.out.println(sb.toString());
        
        
	}

}
