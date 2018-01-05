package me.alexfinch;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Movement implements KeyListener{ //This class handles the movement of the bird
	

	double Momentum = 0; //Declare Global Variables
	
	double UpMaxMomentum = 40;
	
	double MomentumIncrease = 45;
	
	double TerminalMomentum = 15;
	
	double Gravity = 5;
	
	boolean Released = true;
	
	public static Timer timer;; //Creates static timer this is so we can stop it using our main class (Window Class)
	
	public Movement() {
		
		timer = new Timer(); //Create a new timer whenever our movement class is called
		
		
		TimerTask CollisionDetection = new TimerTask() { //TimerTask that handles collision
			
			@Override
			public void run() { //Runable that TimerTask triggers
				for(int i = 0; i < Renderer.World.size(); i++) { //For i value 0 to current World.Size();
					ArrayList<Rectangle2D> RectList = Renderer.World.get(i); //Gets RectList (Top and Bottom of Pipe)
					if(RectList != null) { //Check List is not null
						for(int j = 0; j < RectList.size(); j++) {  //For the RectList Size (We know there is only two elements in it but good practice to use for loop)
							if(RectList.get(j) != null) { //Check if the RectList.get(j) is not null
								Rectangle2D rect = RectList.get(j); //Get the Rectangle2D at int j int the ArrayList
								if(Renderer.BirdCollision != null) { //Make sure the BirdCollision Detection exists/is not null
									if((Renderer.BirdCollision.intersects(rect)) | Renderer.BirdCollision.intersects(Renderer.BottomRect) && !Window.DisableCollision) {  //Check if BirdCollision intersects with either a pipe or the bottom collision detection
										if(!Window.GameOver) { //If game is not over (This stops it running multiple times)
											System.out.println("Game Over"); //Print Game over to console more for personal use/debugging
											Window.GameOver(); //Trigger Window.GameOver();
										}
	
									}
								}
								
							}
						}
					}

					
				}
				
			}
		};
		
		timer.schedule(CollisionDetection, 0, 5); //Schedule Collision detection check every 5 milliseconds
		
		TimerTask Gravity = new TimerTask() { //Create new TimerTask that applies gravity.
			
			@Override
			public void run() { //Runable for Gravity
				Renderer.BirdLocation.y = Renderer.BirdLocation.y + Movement.this.Momentum; //Adds momentum to the Birdloation variable.
				if(Momentum < TerminalMomentum) { //Check if TerminalMoment has been reached if not ...
					Momentum = Momentum + Movement.this.Gravity; // accelerates the bird
				}


			}
		};
		
		timer.schedule(Gravity, 0, 25); //Schedules gravity every 25 milliseconds
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) { //Check if Space bar is pressed
				if(Released) { //To make sure they can't hold down we use the Released variable
					if(Renderer.BirdLocation.y > 0) { //Stops bird from going higher than top of window
						if(Momentum-MomentumIncrease > -UpMaxMomentum) { //Make sure it wont reach higher than max speed
							Momentum = Momentum - MomentumIncrease;	
						} else {
							Momentum = -UpMaxMomentum; 
						}
					}
				}
				Released = false; //After run realeased = false
			} 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) { //Check if Key released is space
			Released = true; //released is true so space can be pressed again
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
