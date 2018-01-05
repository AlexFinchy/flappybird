package me.alexfinch;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WorldGeneration { //This class handles WorldGeneration, Scoring and Collision Detection
	
	int MinGap = 200;
	
	int Gap = 600;
	
	int PipeThinkness = 100;
	
	int VerticalGap = 300;
	
	static double x = 400;
	
	static int CurrentDifficulty = 0;
	
	static double Speed = 5;
	
	long TimerSpeed = 10;
	
	int WorldGenerationSpeed = 200;
	
	int MaxGenerateLimit = 10;
	
	int WorldBuffer = 5;
	
	
	

	public static Timer timer = new Timer();
	
	public WorldGeneration() { 
		

		timer = new Timer();
		
		
		
		
		
		TimerTask GenerateWorld = new TimerTask() {
			
			@Override
			public void run() {
				
				//Remove Things that can't be Rendered.
				
				for(int i = 0; i < Renderer.World.size(); i++) {
					ArrayList<Rectangle2D> RectList = Renderer.World.get(i);
					if(RectList != null) {
						for(int j = 0; j < RectList.size(); j++) {
							
							Rectangle2D rect = RectList.get(j);
							
							if(rect.getX() < -PipeThinkness) {
								RectList.remove(rect);
								Renderer.World.remove(i);
							}
							
						}
					}

					
				}

				
				
				if(Renderer.World.size() < MaxGenerateLimit + Window.Score) {
					Random rnd = new Random();
					
					int RandomInt = rnd.nextInt((WindowSize.Height-400 - 0) + 1) + 0;
					int Secondint = WindowSize.Height-VerticalGap-RandomInt;
					ArrayList<Rectangle2D> CurrentLocation = new ArrayList<>();
					x = x + Gap;
					Rectangle2D rect1 = new Rectangle2D.Double(x,0,PipeThinkness,RandomInt); //Top Half
					CurrentLocation.add(rect1);
					
					Rectangle2D rect2 = new Rectangle2D.Double(x,WindowSize.Height-Secondint,PipeThinkness,Secondint); //Bottom Half
					CurrentLocation.add(rect2);
					Renderer.World.add(CurrentLocation);
				}

			}
		};

		
		timer.schedule(GenerateWorld, 0, WorldGenerationSpeed);
		
		TimerTask MoveWorld = new TimerTask() {
			
			@Override
			public void run() {
				System.out.println(WorldGeneration.Speed);
				for(int i = 0; i < Renderer.World.size(); i++) {
					ArrayList<Rectangle2D> RectList = Renderer.World.get(i);
					if(RectList != null) {
						for(int j = 0; j < RectList.size(); j++) {
							Rectangle2D rect = RectList.get(j);
							rect.setRect(rect.getX()-Speed,rect.getY(),rect.getWidth(),rect.getHeight()); 
							
 
							
							for(double k = 0; k < Speed; k=k+0.5) {
								if(Renderer.BirdCollision != null) {
									if(rect.getX() - k == Renderer.BirdCollision.getX()) {
										Window.Score = Window.Score + 0.5;
										if(Window.Score % 5 == 0 && VerticalGap > MinGap) {
											CurrentDifficulty = CurrentDifficulty + 10;
											VerticalGap = VerticalGap - CurrentDifficulty;
											Speed = Speed + 0.5;
											if(WorldGenerationSpeed - CurrentDifficulty > 15) {
												WorldGenerationSpeed = WorldGenerationSpeed - CurrentDifficulty;
											}

										}
										
									}
								}

							}
						}
					}

					
				}
				

				
				
			}
		};
		
		timer.schedule(MoveWorld, 0,TimerSpeed);
		
		


		
	}
	
	
}
