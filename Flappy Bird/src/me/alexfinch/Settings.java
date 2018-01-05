package me.alexfinch;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Settings { //Best coded class, has no static variables so each new instance gets new values;
	//Declare public variables
	String url = "C://FlappyBird//Settings.txt"; //Default File url for saving settings files
	
	boolean Fullscreen;
	
	Dimension dim;
	
	boolean FirstRun;
	
	public Settings() {
		
		File Dir = new File("C://FlappyBird//");
		
		Dir.mkdir();

		
		
		File file = new File(url);
		
		
		
		if(file.exists()) {
			FirstRun = false;
			
			try {
				Scanner filereader = new Scanner(file);
				
				while(filereader.hasNextLine()) {
					String line = filereader.nextLine();
					
					String[] Setting = line.replaceAll(" ", "").split(":");
					if(!(Setting.length > 2)) {
						
						String SettingName = Setting[0];
						
						String StringValue = Setting[1];
						
						if(SettingName.equalsIgnoreCase("Fullscreen")) {
							
							if(StringValue.equalsIgnoreCase("true")) {
								Fullscreen = true;
							} else if(StringValue.equalsIgnoreCase("false")) {
								Fullscreen = false;
							} else {
								System.out.println("Settings error");
								System.exit(1);
							}
							
						} else if(SettingName.equalsIgnoreCase("Resolution")) {
							
							if(StringValue.contains(",")) {
								
								
								String[] res = StringValue.split(",");
								
								if(!(res.length > 2)) {
									
									
									double DimInt[] = {Double.parseDouble(res[0]),Double.parseDouble(res[1]) };
									
									dim = new Dimension((int) DimInt[0], (int) DimInt[1]);
									
									
									
								} else {
									System.out.println("Settings error");
									System.exit(1);
								}
								
								
								
							} else {
								System.out.println("Settings error");
								System.exit(1);
							}
							
						}
						
						
						
						
						
						
					} else {
						System.out.println("Settings error");
						System.exit(1);
					}
					
					
				}
				
				filereader.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		} else {
			FirstRun = true;
			Fullscreen = false;
			
			try {
				
				
				
				file.createNewFile();
				
				FileWriter fw = new FileWriter(file);
				
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write("Fullscreen: false");
				bw.newLine();
				bw.write("Resolution: 1000,1000");
				dim = new Dimension(1000, 1000);
				
				bw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		}
		

		
		
		
	}
	
	
	
	public void UpdateSettings(Boolean Fullscreen, Dimension dim) {
		
		
		
		
		this.Fullscreen = Fullscreen;
		

		
		
		
		File file = new File(url);
		
		
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			bw.write("Fullscreen: " + Fullscreen.toString());
			bw.newLine();
			if(dim != null) {
				this.dim = dim;
				bw.write("Resolution: " + String.valueOf(dim.getWidth()) + "," + String.valueOf(dim.getHeight()));
			} else {
				bw.write("Resolution: " + String.valueOf(this.dim.getWidth()) + "," + String.valueOf(this.dim.getHeight()));
			}
			
			bw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		

		
	}
	

}
