package project.reminder.common.util.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.reminder.common.exception.HttpCallException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Slf4j
@Service
public class HttpCallService {

	public String call(String method, String reqURL, String header, String param) {
		String result = "";
		int responseCode = 0;
		try {
			String response = "";
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", header);
            if(param != null) {
				log.debug("param : {}", param);
            	conn.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(param);
                bw.flush();

            }
            responseCode = conn.getResponseCode();
			log.debug("responseCode : {}", responseCode);

			log.debug("reqURL : {}", reqURL);
			log.debug("method : {}", method);
			log.debug("Authorization : {}", header);
			InputStream stream = conn.getErrorStream();
		    if (stream != null) {
			    try (Scanner scanner = new Scanner(stream)) {
			        scanner.useDelimiter("\\Z");
			        response = scanner.next();
			    }
				log.debug("error response : {}", response);
		    }
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			log.debug("response body : {}", result);
			
			br.close();
		} catch (IOException e) {
			throw new HttpCallException(e, HttpStatus.valueOf(responseCode));
		}
		return result;
	}	
	
	public String callWithToken(String method, String reqURL, String access_Token) {
		return callWithToken(method, reqURL, access_Token, null);
	}	
	
	public String callWithToken(String method, String reqURL, String access_Token, String param) {
		String header = "Bearer " + access_Token;
		return call(method, reqURL, header, param);
	}		
}
