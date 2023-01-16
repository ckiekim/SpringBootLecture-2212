package com.mulcam.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class PoseNaver {

	public static void main(String[] args) throws Exception {
		String accessId = "spyvtsexbi";             // Application Client ID";
        String secretKey = "i0fuRdbOBhXv2B5TbJCwwvwo1pK0xnGLSZOAGe0c";
        
        String imgFile = "/Temp/spring/런지.png";
        File uploadFile = new File(imgFile);
        String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-pose/v1/estimate"; // 사람 인식
        URL url = new URL(apiURL);
        
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // multipart request
        String boundary = "---" + System.currentTimeMillis() + "---";
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        
        OutputStream os = conn.getOutputStream();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
        String LINE_FEED = "\n";
        // file 추가
        String fileName = uploadFile.getName();
        out.append("--" + boundary).append(LINE_FEED);
        out.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        out.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        out.append(LINE_FEED);
        out.flush();
        
        FileInputStream is = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush(); is.close();
        out.append(LINE_FEED).flush();
        out.append("--" + boundary + "--").append(LINE_FEED);
        out.close();
        
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
