package me.alexfinch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")



public class Renderer extends JPanel implements MouseListener { //Used to render graphical elements to screen also contains GameOverGUI
	
	
	

	
	public BufferedImage Bird;
	
	public Image BackgroundImage;
	
	public Image PlayAgainButton;
	
	public Image MainMenuButton;
	
	public Image GameOverSquare;
	
	public static Rectangle2D BottomRect;
	
	public static Location BirdLocation = new Location(0, 0);
	
	int y = (WindowSize.Height/2)-500/2;
	
	public static Ellipse2D BirdCollision;
	
	

	public static ArrayList<ArrayList<Rectangle2D>> World = new ArrayList<>();
	
	Rectangle2D PlayAgain =  new Rectangle2D.Double(GUIMenu.x,y+200,450,80);
	
	Rectangle2D MainMenu =  new Rectangle2D.Double(GUIMenu.x,y+300,450,80);
	
	public Renderer() {
		
		
		this.addMouseListener(this);
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		
		InputStream BirdStream = classLoader.getResourceAsStream("Bird.png");
		
		InputStream Background = classLoader.getResourceAsStream("Background.png");
		
		InputStream PlayAgain = classLoader.getResourceAsStream("Playagain.png");
		
		InputStream Mainmenu = classLoader.getResourceAsStream("MainMenu.png");
		
		InputStream Gameover = classLoader.getResourceAsStream("Gameover.png");
		
		BirdLocation.x = 10;
		
		try {
			Image Playagain = ImageIO.read(PlayAgain);
			
			Image MainMenu = ImageIO.read(Mainmenu);
			
			Image GameOver = ImageIO.read(Gameover);
			
			GameOverSquare = GameOver;
			
			MainMenuButton = MainMenu;
			
			PlayAgainButton = Playagain;
			
			BufferedImage bird = ImageIO.read(BirdStream);
			
			Image BackgroundImg = ImageIO.read(Background);
			
			BackgroundImg = BackgroundImg.getScaledInstance(WindowSize.Width, WindowSize.Height, Image.SCALE_SMOOTH);
			
			BackgroundImage = BackgroundImg;
			
			
			
			Bird = bird;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				repaint();
				
			}
		}, 0, 1);
		
	}
	
	
	
	public void paint(Graphics g) {
		
		

		
		super.paint(g);
		
		Graphics2D G2D = (Graphics2D) g;
		
		G2D.drawImage(BackgroundImage, 0, 0,null);
		
		Color color = new Color(0, 0, 0, 0);
		
		
		
		Ellipse2D circle = new Ellipse2D.Double(BirdLocation.x+45, BirdLocation.y+43, 55, 40);
		
		BottomRect = new Rectangle2D.Double(BirdLocation.x, (double) WindowSize.Height-(0.15*WindowSize.Height), 100,1);
		
		
		
		
		BirdCollision = circle;
		
		G2D.setColor(color);
		G2D.fill(circle);
		G2D.fill(BottomRect);
		G2D.drawImage(Bird, (int) BirdLocation.x, (int) BirdLocation.y, null);
		//Code to Render Stuff
		
		
		
		for(int i = 0; i < World.size(); i++) {
			G2D.setColor(Color.green);

			ArrayList<Rectangle2D> rectlist = World.get(i);
			if(rectlist != null) {
				for(int j = 0; j < rectlist.size(); j++) {
					G2D.fill(rectlist.get(j));
				}	
			}

		}
		
		G2D.setColor(Color.red);
		int FontSize = (WindowSize.Width/30);
		G2D.setFont(new Font("Haettenschweiler", Font.PLAIN, FontSize));
		
		
		
		G2D.drawString("Score: " + (int) Window.Score, WindowSize.Width/100, WindowSize.Height/100+FontSize);
		
		
		if(Window.GameOver) {
			/*G2D.setFont(new Font("Helvetica", Font.PLAIN, 50));
			G2D.drawString("Game Over", (int) ((double) WindowSize.Width/(double)2.8), (int) ((double)WindowSize.Height/(double)2));
			G2D.drawString("You got a score of " + (int) Window.Score, (int) ((double)WindowSize.Width/(double)3.84), (int) ((double)WindowSize.Height/(double)1.81));
			G2D.setColor(Color.BLUE);*/
			
			G2D.drawImage(GameOverSquare, (WindowSize.Width/2)-250, y, null);
			G2D.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 50));
			G2D.drawString("Score: " + (int) Window.Score, (WindowSize.Width/2)-76, y+150);
			G2D.drawImage(PlayAgainButton, GUIMenu.x, y+200, null);
			G2D.drawImage(MainMenuButton,GUIMenu.x, y+300, null);
		}
		
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == 1) {
			if(Window.GameOver) {
				if(PlayAgain.contains(e.getPoint())) {
					Window.ResetGame();
				} else if(MainMenu.contains(e.getPoint())) {
					Window.MainMenu();
				}
			}
		}
		


		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
