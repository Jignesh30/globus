package com.globus.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

public class Configuration {
	public static String path;
	public static String selenium_host;
	public static String selenium_port;
	public static String selenium_browser;
	public static String configPath = "/config.properties";
	public static String test_url;
	public static String username;
	public static String password;
	
	public static void initializeSettings() throws IOException {
		String line = null;
		String[] keyValue = null;
		path = new File(".").getCanonicalPath().replace("\\", "/");
		FileReader fr = new FileReader(new File(path+configPath).getAbsoluteFile());;
		BufferedReader br = new BufferedReader(fr);
		while ((line = br.readLine()) != null) {
			try{
				keyValue = line.split("=");
				if(keyValue.length != 1) {
					keyValue[1] = keyValue[1].trim();
				} else {
					throw new Exception("The value for key='"+keyValue[0]+"' should not be blank.Please check config.properties file");
				}
				
				switch(keyValue[0].toString()){
				case "selenium_host": 
					selenium_host = keyValue[1];
					//System.out.println("selenium_host:"+selenium_host);
					break;
				case "selenium_port": 
					selenium_port = keyValue[1];
					//System.out.println("selenium_port:"+selenium_port);
					break;
				case "selenium_browser": 
					selenium_browser = keyValue[1];
					//System.out.println("selenium_browser:"+selenium_browser);
					break;
				case "test_url":
					test_url = keyValue[1];
					//System.out.println("selenium_url:"+test_url);
					break;
				case "username":
					username=keyValue[1];
					break;
				case "password":
					password=keyValue[1];
					break;
				}
			}catch(FileNotFoundException e){
				System.out.println(e.getMessage());
			}catch(NullPointerException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void killbrowserInstances() throws ExecuteException, IOException, InterruptedException {
		CommandLine command = new CommandLine("cmd"); 
		command.addArgument("/c"); 
		command.addArgument("taskkill");
		command.addArgument("/F"); 
		command.addArgument("/IM");
		command.addArgument("chromedriver.exe");
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler(); 
		DefaultExecutor executor = new DefaultExecutor(); 
		executor.setExitValue(1); 
		executor.execute(command, resultHandler); 
		Thread.sleep(15000);
	}
}
