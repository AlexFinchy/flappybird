package me.alexfinch;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame implements MouseListener{
	
	
	public static double Score = 0;
	//Sets Score to 0
	public static GUIMenu menu;
	//Declares Public Static Variable GUIMENU menu and other static variables, static means not changing even I create new instance of class variables stay the same;
	public static Window w;
	public static Renderer r;
	public static boolean GameOver = false;
	public static WorldGeneration g;
	public static Movement m;
	public static String[] args;
	public static boolean DisableCollision = false;
	
	public static void main(String[] args) {
		
		
		//I use way to many static variables, I don't most of the time but because I had a very tight time limit I did
		
		w = new Window(); //Sets global variable w to new Window();

		
	}
	
	public Window() { //Constructor for Window Class
		
		Settings settings = new Settings(); //Create new instance of settings class
		
		if(settings.Fullscreen) { //Check if settings file had fullscreen true in it
			WindowSize.FullScreen = true; //Set SizeSize to Fullscreen
			setExtendedState(JFrame.MAXIMIZED_BOTH); //Sets Screen To MAXIMIZED
			Dimension D = Toolkit.getDefaultToolkit().getScreenSize(); //Get Screen Size
			WindowSize.Height = D.height; //Set WindowSize to Height and Width of Screen
			WindowSize.Width = D.width; //^^
		} else { //Else fullscreen
			WindowSize.Width = settings.dim.width; //Set Window and Width to Settings height and width
			WindowSize.Height = settings.dim.height; //Set Window and Width to Settings height and width
			setSize(WindowSize.Width, WindowSize.Height); //Set Window size to WindowSize.Width and WindowSize.Height
		}
	

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Make program Exit on close
		
		
		setUndecorated(true); //Set it to undecorated
		
		
		menu = new GUIMenu(); //Create a new GUI Menu
		
		add(menu); //Add menu to JFrame
		
		
		setLocationRelativeTo(null); //Center Window by setting it relative to null component
		setVisible(true); //Set JFrame visible
		
		
		
	}
	
	public static void GameOver() { //Runs when collision detection
		WorldGeneration.timer.cancel(); //Cancels Both Tiemrs
		Movement.timer.cancel();
		GameOver = true; //Sets Gameover to true
		
	}
	public static void StartGame() { //Method that runs Startup code for game
		
		DisableCollision = false; //Makes sure Collision is enabled
		
		//w is a global static instance of the Window Class
		
		r = new Renderer(); //Creates a new renderer and declares it as the global variable r
		w.remove(menu); //Removes GUI MENU
		w.addMouseListener(w); //Adds a mouse listener that requests focus if the window is clicked.
		m = new Movement(); //Creates new Movement class instance and declares as global variable m
		g = new WorldGeneration(); //Creates new WorldGeneration class instance and declares as global variable m
		w.addKeyListener(m); //Adds key listener to JFrame that allows the JFrame to log movement for the movement class
		w.add(r); //adds the Rendner to the JFrame
		w.repaint(); //Repaints the JFrame
		w.setTitle("Flappy Bird Game"); //Sets window title to Flappy Bird Game
		w.setVisible(true); //Sets Window Visable
		
	}
	
	public static void ResetGame() { //Method to Reset The Game
		DisableCollision = true; //Collision is disabled while game is reset
		GameOver = false; //GameOver is set to false
		w.remove(r); //Rendner is removed from Window/JFrame
		Score = 0; //Score is set to 0
		
	
		WorldGeneration.CurrentDifficulty = 0; //Difficulty reset
		WorldGeneration.Speed = 5; //WindowGeneration.Speed set to 0
		WorldGeneration.x = 400; //Resets The WorldGeneration Point	
		Renderer.BottomRect = new Rectangle2D.Double(); //Resets the collision detection BottomRect
		Renderer.World = new ArrayList<ArrayList<Rectangle2D>>(); //Resets World
		Renderer.BirdLocation = new Location(10, 0); //Resets BirdLocation
		StartGame(); //Starts Game
		
	}
	
	public static void MainMenu() { //Code For Going Back to MainMenu
		DisableCollision = true; 
		GameOver = false;
		w.remove(r);
		Score = 0;
		
	
		WorldGeneration.CurrentDifficulty = 0;
		WorldGeneration.Speed = 5;
		WorldGeneration.x = 400;
		
		
		Renderer.BottomRect = new Rectangle2D.Double();
		Renderer.World = new ArrayList<ArrayList<Rectangle2D>>();
		Renderer.BirdLocation = new Location(10, 0);
		
		//All Code is Same as Reset But instead adds Menu to JFrame/Window
		
		w.add(menu);
		
		
	}
	
	public static void ChangeSettings() { //Method for Changing Settings
		
		//TODO NEEDS WORK
		
		Settings settings = new Settings();

		if(!(String.valueOf(WindowSize.FullScreen) == String.valueOf(settings.Fullscreen))) {
			
			if(WindowSize.FullScreen) {
				w.setExtendedState(JFrame.MAXIMIZED_BOTH);
				Dimension D = Toolkit.getDefaultToolkit().getScreenSize();
				WindowSize.Height = D.height;
				WindowSize.Width = D.width;
				w.setAlwaysOnTop(true);
				settings.UpdateSettings(WindowSize.FullScreen, null);
			} else {
				
				if(settings.dim.width == WindowSize.Width && settings.dim.height == WindowSize.Height) {
					settings.UpdateSettings(WindowSize.FullScreen, new Dimension(settings.dim));
				} else {
					settings.UpdateSettings(WindowSize.FullScreen, new Dimension(settings.dim));
					w.setSize(settings.dim);
					WindowSize.Width = settings.dim.width;
					WindowSize.Height = settings.dim.height;
				}
				
			}
		}
		

		
		GUIMenu.x = (int) (WindowSize.Width/2)-300/2;
		
		
		GUIMenu.Play = new Rectangle2D.Double(GUIMenu.x,400,300,50);
		
		GUIMenu.Options = new Rectangle2D.Double(GUIMenu.x,500,300,50);
		
		GUIMenu.Exit = new Rectangle2D.Double(GUIMenu.x,600,300,50);
		
		GUIMenu.Back = new Rectangle2D.Double(GUIMenu.x,600,300,50);
		
		GUIMenu.Fullscreentoggle = new Rectangle2D.Double(GUIMenu.x+240, 500,64,50);
		
		GUIMenu.BackgroundImage = GUIMenu.BackgroundImage.getScaledInstance(WindowSize.Width, WindowSize.Height, Image.SCALE_SMOOTH);
		
		if(WindowSize.Width > 1000) {
			
			GUIMenu.logo = GUIMenu.logo.getScaledInstance(1000, 266, Image.SCALE_SMOOTH);
			GUIMenu.Logox = (int) (WindowSize.Width/2)-1000/2;
			
		} else {
			double change = ((double)WindowSize.Width/(double)2500);
			
			double Height = change*(double)667;
			GUIMenu.Logox = (int) (WindowSize.Width/2)-Window.WIDTH/2;
			GUIMenu.logo = GUIMenu.logo.getScaledInstance(WindowSize.Width, (int) Height, Image.SCALE_SMOOTH);
		}
		
		w.setLocationRelativeTo(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		requestFocus();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		//Not needed
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Not needed
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Not needed
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Not needed
	}
	
	

	
	//Our Window Class

}
