package com.hm.kettle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinuxShell {
	private static Logger logger = LoggerFactory.getLogger(LinuxShell.class);
	public static String execute(String path) throws IOException, InterruptedException{
		logger.error(path);
		Process process = Runtime.getRuntime().exec("clamscan /root/atozvirus.rar");
		int result = process.waitFor();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		String line = "";
		StringBuffer returnBuffer = new StringBuffer();
		while((line = reader.readLine())!=null){
			returnBuffer.append(line);
		}
		logger.error(returnBuffer.toString());
		return returnBuffer.toString();
	}
}
