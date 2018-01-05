package me.alexfinch;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GUIMenu extends JPanel implements MouseListener{ //Renders main GUI elements, such as main menu and options menu, also handles options with Window Class

	
	public static int x; //This X is used to align all of our elements
	

	public static int Logox; //This is used to align the logo
	//Declare the rest of our static variables 
	static Rectangle2D Play;

	static Rectangle2D Options;
	
	static Rectangle2D Exit;
	
	static Rectangle2D Back;
	
	static Rectangle2D Fullscreentoggle;
	
	static Image BackgroundImage;
	
	public static Image BackImage;
	
	static Image logo;
	
	//Declare our non static global variables
	
	boolean OptionsOpen = false;
	
	Image PlayImage;
	
	Image OptionsImage;
	
	Image ExitImage;
	
	Image FullscreenLabel;
	
	Image Tick;
	
	Image NoTick;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7576059487385380925L; //We need one of these its not really important but stops an error

	
	public GUIMenu() { //Class constructor for the GUIMENU Class
		
		this.addMouseListener(this);
		
		x = (int) (WindowSize.Width/2)-300/2; //Calculates the x depending on window size
		
		//Aligns buttons depending on x
		Play = new Rectangle2D.Double(x,400,300,50); 
		
		Options = new Rectangle2D.Double(x,500,300,50);
		
		Exit = new Rectangle2D.Double(x,600,300,50);
		
		Back = new Rectangle2D.Double(x,600,300,50);
		
		Fullscreentoggle = new Rectangle2D.Double(x+240, 500,64,50);
		
		
		//Gets images using the classloader and getting Resources as a stream.
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		InputStream input = classLoader.getResourceAsStream("Logo.png");
		
		InputStream PlayButton = classLoader.getResourceAsStream("Play.png");
		
		InputStream OptionsButton = classLoader.getResourceAsStream("Options.png");
		
		InputStream Background = classLoader.getResourceAsStream("Background.png");
		
		InputStream ExitButton = classLoader.getResourceAsStream("Exit.png");
		
		InputStream BackButton = classLoader.getResourceAsStream("Back.png");
		
		InputStream FullScreenLabel = classLoader.getResourceAsStream("FullScreenLabel.png");
		
		InputStream BoxWithNoTick = classLoader.getResourceAsStream("BoxNoTick.png");
		
		InputStream BoxWithTick = classLoader.getResourceAsStream("BoxWithTick.png");
		
		
		
		try { //We need a try becuase we could get a error if the image is not found
			
			//Declare Images then asign them to their global variables
			
			Image BackgroundImg = ImageIO.read(Background);
			
			BackgroundImg = BackgroundImg.getScaledInstance(WindowSize.Width, WindowSize.Height, Image.SCALE_SMOOTH);
			
			Image back = ImageIO.read(BackButton);
			
			Image play = ImageIO.read(PlayButton);
			
			Image options = ImageIO.read(OptionsButton);
			
			Image exit = ImageIO.read(ExitButton);
			
			Image tick = ImageIO.read(BoxWithTick);
			
			Image notick = ImageIO.read(BoxWithNoTick);
			
			Image FullScreenlabel  = ImageIO.read(FullScreenLabel);
			
			Image logo = ImageIO.read(input);
			
			BackgroundImage = BackgroundImg;
			
			OptionsImage = options;
			
			ExitImage = exit;
			
			PlayImage = play;
			
			BackImage = back;
			
			FullscreenLabel = FullScreenlabel;
			
			Tick = tick;
			
			NoTick = notick;
			
			
			//Aligns the logo to the center resizes unless window is bigger than 1000 pixels wide
			if(WindowSize.Width > 1000) {
				logo = logo.getScaledInstance(1000, 266, Image.SCALE_SMOOTH);
				Logox = (int) (WindowSize.Width/2)-1000/2;
				GUIMenu.logo = logo;
			} else {
				double change = ((double)WindowSize.Width/(double)2500);
				double Height = change*(double)667;
				Logox = (int) (WindowSize.Width/2)-WindowSize.Width/2;
				logo = logo.getScaledInstance(WindowSize.Width, (int) Height, Image.SCALE_SMOOTH);
				GUIMenu.logo = logo;
			}


			

		} catch (IOException e) { //This wont be triggered as the files are within the jar but we need to catch it anyway
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Timer timer = new Timer(); //Timer to repaint window
		
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				repaint(); //Repaints window refreshs graphical elements
				
			}
		}, 0, 10);
	}
	
	public void paint(Graphics graphics) { //Paints GUI elements
		
		super.paint(graphics); //Paints previous graphics such as background color
		
		Graphics2D G2D = (Graphics2D) graphics; //Convert our graphics into Graphics2D with the variable G2D
		
		G2D.drawImage(BackgroundImage, 0, 0,null); //Draw Background image
		
		G2D.setColor(Color.BLUE); //Set Color to Blue?
		
		//As explained earlier about logo size
		if(WindowSize.Width > 1000) {
			G2D.drawImage(logo, Logox, 0, null);
		} else {
			G2D.drawImage(logo, 0, 0, null);
		}
		

		if(!OptionsOpen) { //If options is not open render main menu
			G2D.setColor(Color.red);
			G2D.drawImage(PlayImage,(int) x,400, null);
			G2D.drawImage(OptionsImage,(int) x,500, null);
			G2D.drawImage(ExitImage,(int) x,600, null);
		} else { //Options is open so render options menu
			//Render Options Menu
			if(WindowSize.FullScreen) {
				G2D.drawImage(Tick, x+240, 500, null);
			} else {
				G2D.drawImage(NoTick, x+240, 500, null);
			}
			
			
			
			G2D.drawImage(FullscreenLabel, x, 500,null);
			
			G2D.drawImage(BackImage, x, 600, null);
			
			
			
			
			
			
		}
		

		
	}

	@Override
	public void mouseClicked(MouseEvent e) { //Makes GUI interactive 
		if(e.getButton() == 1) { //Make sure user right clicks
			
			if(!OptionsOpen) { //If options is not open use main menu buttons
				if(Play.contains(e.getPoint())) {
					Window.StartGame();
					Window.DisableCollision = false;
				} else if(Exit.contains(e.getPoint())) {
					System.exit(1);
				} else if(Options.contains(e.getPoint())) {
					OptionsOpen = true;
				}
			} else { //If options is open
				//Options Click Events
				if(Back.contains(e.getPoint())) {
					
					Window.ChangeSettings();
					
					OptionsOpen = false;
					
				} else if(Fullscreentoggle.contains(e.getPoint())) {
					if(WindowSize.FullScreen) {
						WindowSize.FullScreen = false;
					} else {
						WindowSize.FullScreen = true;
					}
				}
			}
		}


		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	
}
