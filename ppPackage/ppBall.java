package ppPackage;
import static ppPackage.ppSimParams.*; 
import java.awt.Color;
import acm.graphics.GObject; 
import acm.graphics.GOval;
import acm.util.RandomGenerator;

//This class is the heart of the program. It computes and changes the balls' location, checks for collision with the paddles and exports some methods used by other classes. Essentially it uses all the different classes created.
//A lot of this code has been taken/inspired by my ECSE 202 professor Frank Ferrie for the fall 2020 semester. Prof Ferrie provided his students with a pdf file explaining the code he provided and how we're suppose to implement each class of the assignment.

/**
*	The ppBall class simulates an instance of a ping pong ball moving through space
*	with potential collisions with obstacles in the scene.  Most of the code is * taken from the Bounce class of Assignment 1.
*	@author KarlRouhana, Inspired a lot by Prof Ferrie.
*	 */

public class ppBall extends Thread {
 
 	private double Xinit;  	 			 // Initial position of ball - X
 	private double Yinit;  	 			 // Initial position of ball - Y
 	private double Vo;  	 	 		 // Initial velocity (Magnitude)
 	private double theta;  	 			 // Initial direction 
 	private double loss; 	 	 	 	// Energy loss on collision

 	double Xo, X, Vx;  	 	 	 	 	 	 // X position and velocity variables     
 	double Yo, Y, Vy;  	 	 	 	 	 	 // Y position and velocity variables
 	double ScrX, ScrY;
 	boolean running;
 	private boolean traceOn;			//boolean checking for the traceState

 	
 	// Instance variables
 	private Color color; 	 	 				 // Color of ball
 	private ppTable table;  	 		 		// Instance of ping-pong table
 	private ppPaddle myPaddle;  				 // Instance of ping-pong paddle
 	private ppPaddleAgent paddleAgent;			// Graphics object representing paddle
 	private GOval myBall;  	 	 	 	 		// Graphics object representing ball



 
// Entry point is the run method
// The simulator code is copied and pasted here with minor modifications (see comments)
 
 	public void run() {
 	 	// Initialize simulation parameters
     
     	double time = 0; 	 	 	 	 	 	 	 // time (reset at each interval)
     	double Vt = bMass*g / (4*Pi*bSize*bSize*k);  // Terminal velocity
     	double KEx=ETHR,KEy=ETHR;  	 	 	 		 // Kinetic energy in X and Y directions      
     	double PE=ETHR;  	 	 	 	 	 	 	 // Potential energy

     
     	double Vox=Vo*Math.cos(theta*Pi/180);  	 // Initial velocity components in X and Y      
     	double Voy=Vo*Math.sin(theta*Pi/180);
     
     	Xo=Xinit;  	 	 	 	 	 	 	 	 // Initial X offset
     	Yo=Yinit;  	 	 	 	 	 	 	 	 // Initial Y offset
     
/*	
 * Main Simulation Loop, which is essentially the same as in Assignment 1.

 */     running = true;
     	while (running) {
     	 
     		
     		X	= Vox*Vt/g*(1-Math.exp(-g*time/Vt));  	 	 // Update relative position
     		Y	= Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
     	 	Vx = Vox*Math.exp(-g*time/Vt);
     	 	Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;

     	
     	 	// Collision with ground
     	 if (Vy<0 && (Yo+Y)<=bSize) {
     	 	 	KEx = 0.5*bMass*Vx*Vx*(1-loss);  	 // Kinetic energy in X direction after collision
     	 	 	KEy = 0.5*bMass*Vy*Vy*(1-loss);  	 // Kinetic energy in Y direction after collision
     	 	 	PE = 0;  	 	 	 	 	 	 	 // Potential energy (at ground)
     	 	 	Vox = Math.sqrt(2*KEx/bMass);  		 // Resulting horizontal velocity      	 	 	
     	 	 	Voy = Math.sqrt(2*KEy/bMass);  	 	 // Resulting vertical velocity      	 	 
     	 	 	if (Vx<0) Vox=-Vox;  	 	 	 	 // Preserve sign of Vox
     
     	 	 	time=0;  	 	 	 	 	 	 	 // Reset current interval time
     	 	 	Xo+=X;  	 	 	 	 	 	 	 // Update X and Y offsets
     	 	 	Yo=bSize;
     	 	 	X=0; 	 	 	 	 	 	 	 	 // Reset X and Y for next iteration
     	 	 	Y=0;
     	 	 if ((KEx+KEy+PE)<ETHR) running=false;	 // Terminate if insufficient energy
     	 }

     	// Collision with Player paddle.
     	 if (Vx>0 && (Xo+X)>=myPaddle.getX()-bSize-ppPaddleW/2) {
     	 	 if (myPaddle.contact(Xo+X,Yo+Y)) {
     	     	// Ball contacts paddle

     	 	 	  KEx = 0.5*bMass*Vx*Vx*(1-loss);  		 // Kinetic energy in X direction after collision
     	 	 	  KEy = 0.5*bMass*Vy*Vy*(1-loss);  		 // Kinetic energy in Y direction after collision
     	 	 	  PE = bMass*g*Y;  	 	 	 	 		 // Potential energy
     	 	 	  Vox = -Math.sqrt(2*KEx/bMass); 	 	 // Resulting horizontal velocity      	 	 	
     	 	 	  Voy = Math.sqrt(2*KEy/bMass);  	 	 // Resulting vertical velocity
     	 	 	  
     	
     	 	 // Boost Vx and Vy of ball on paddle hit
					Vox=Math.min(Vox*ppPaddleXgain,7); 								//Gain of velocity in x.
					Voy=Math.min(Voy*ppPaddleYgain*myPaddle.getVy(),5);				//Gain of velocity in y.
     	 	 	  
					
			// Reset parameters for next parabolic trajectory
     	 	 	  time=0;  	 	 	 	 	 	 	 		// Reset current interval time
     	 	 	  Xo=myPaddle.getX()-bSize-ppPaddleW/2;  	// Update X and Y offsets
     	 	 	  Yo+=Y;
     	 	 	  X=0;  	 	 	 	 	 	 	 		// Reset X to zero for start of next interval
     	 	 	  Y=0;
     	 	 	}
     	 	 
     	// Ball misses paddle
     	 	 else {											//If the ball does not touch the paddle, stop the simulation (stop the game) and increase the agents' score by 1
					table.getDisplay().addAgentScore();
     	 	 	 	running=false;  	 	
     	 	 	}
     	 	}
 
		//Collision with the paddleAgent
     	 if (Vx<0 && (Xo+X)<=(paddleAgent.getX()+ppPaddleW/2)) {
     		 if (paddleAgent.contact(Xo+X,Yo+Y)) {
     		 	// Ball contacts paddle
     			 
     			KEx = 0.5*bMass*Vx*Vx*(1-loss);  	 // Kinetic energy in X direction after collision
     	 	 	KEy = 0.5*bMass*Vy*Vy*(1-loss);  	 // Kinetic energy in Y direction after collision
     	 	 	PE = bMass*g*Y;  	 	 	 	 	 // Potential energy
     	 	 	Vox = Math.sqrt(2*KEx/bMass);  	 // Resulting horizontal velocity      	 	 	
     	 	 	Voy = Math.sqrt(2*KEy/bMass);  	 // Resulting vertical velocity      	 	 
     	 	 	if (Vy<0) Voy=-Voy;  	 	 	 	 // Preserve sign of Voy
     	 	 
        	 // Boost Vx and Vy of ball on paddle hit
     	 	 	Vox=Math.min(Vox*ppAgentXgain,7); 											//Gain of velocity in x.
				
     	 	 // Did not bother to add Vy of the agent paddle since I personally found that the game runs better without it.
     	 	 	Voy=Math.min(Voy*ppAgentYgain,5);											//Gain of velocity in y.
				
			// Reset parameters for next parabolic trajectory
     	 	 	time=0;  	 	 	 	 	 	 					  // Reset current interval time
     	 	 	Xo=paddleAgent.getX()+ppPaddleW/2+bSize; 	 	 	  // Update X and Y offsets
     	 	 	Yo+=Y;
     	 	 	X=0; 	 	 	 	 	 	 	 					  // Reset X to zero for start of next interval
     	 	 	Y=0;
     		 }
     		else {											//If the ball does not touch the paddle, stop the simulation (stop the game) and increase the players' score by 1
				table.getDisplay().addPlayerScore();
 	 	 	 	running=false;  	 	
 	 	 	}
     	 }
     	 
     	 
     	// Collision with roof.
			if((Y+Yo)>YMAX-bSize){
				if(Vx<0) {
					table.getDisplay().addAgentScore(); 	  //If the player is responsible for the roof collision, give a point to the agent.
					}
				else {
					table.getDisplay().addPlayerScore();	  //If the agent is responsible for the roof collision, give a point to the player.
				}

				running=false;					//Either way, stop the game.
			}
     	 
     	 
		 // Update ball position on screen     	 
     	    ScrX = table.toScrX(Xo+X-bSize); 	 	 // Convert to screen units      	 	
     	    ScrY = table.toScrY(Yo+Y+bSize);      	   
     	    
     	    myBall.setLocation(ScrX,ScrY);  	 	 // Change position of ball in display      	 
     	  
     	    if(traceOn) table.trace(Xo+X,Yo+Y,ColorTrace);  	 	 // Place a marker on the current position if the user selected the toggle button.
     	 	     	 
     	 // Delay and update clocks
 	 	 
 	 	 	table.getDisplay().pause(TICK*TIMESCALE); 		// Convert time units to mS
 	 	 	time+=TICK;  	 	 	 	 	 	 	 		// Update time
    	     	 
     	} 
    
 	}	


 // Initialize instance variables and create an instance of the ppBall
 	/**
 	* The constructor for the ppBall class copies parameters to instance variables, creates an
	* instance of a GOval to represent the ping-pong ball, and adds it to the display. * 	
 * @param Xinit  - starting position of the ball X (meters)
 * @param Yinit  - starting position of the ball Y (meters)
 * @param Vo - initial velocity (meters/second)
 * @param theta - initial angle to the horizontal (degrees)
 * @param color - ball color (Color)
 * @param loss - loss on collision ([0,1])
 * @param table - a reference to the ppTable class used to manage the display
 * @param traceOn - a boolean parameter to check if the trace is selected or not
 */
 	public ppBall(double Xinit, double Yinit, double Vo, double theta, Color color, double loss, ppTable table, boolean traceOn) {
  	 	
  		 // Copy constructor parameters to instance variables  
  		
  		this.Xinit=Xinit;  	 		 	
  	 	this.Yinit=Yinit;  	 	
  	 	this.Vo=Vo;  	 	
  	 	this.theta=theta;  	 
  	 	this.loss=loss;  	 	
  	 	this.table=table;
		this.color = color;
		this.traceOn=traceOn;
		
  	 
      	double ScrX = table.toScrX(Xinit-bSize); 	 	 	 // Cartesian to screen      
      	double ScrY = table.toScrY(Yinit-bSize);
      	myBall = new GOval(ScrX,ScrY,2*bSize*SCALE,2*bSize*SCALE);      	
      	myBall.setColor(color);      
      	myBall.setFilled(true);      	
      	table.getDisplay().add(myBall);
  	}



  	public double getBallY() {										//returns the Y position of the ball
		return Y+Yo;
	}
	
	
	public void setPaddle (ppPaddle myPaddle) {						//allows ppBall access to paddle objects
		this.myPaddle=myPaddle;

	}
	public void setAgent (ppPaddleAgent paddleAgent) {				//allows ppBall access to paddle objects
		this.paddleAgent=paddleAgent;

	}

	
	public boolean ballInPlay() {									//returns the state of the ball (in play or not)
		return running;
	}
	
	
 	public GObject getBall() {										//Used to return graphics objects, such as GOval, created within a ppBall instance.
		return myBall;						
	}
	





}