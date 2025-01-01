package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;


//A lot of this code has been taken/inspired by my ECSE 202 professor Frank Ferrie for the fall 2020 semester. Prof Ferrie provided his students with a pdf file explaining the code he provided and how we're suppose to implement each java class of the assignments.
/**
* This class extends ppPaddle. It is responsible for everything related to the agentPaddle and will use all the methods defined in ppPaddle.
* It will also use its constructor
*@author KarlRouhana, Inspired a lot by Prof Ferrie.
*	 */

public class ppPaddleAgent extends ppPaddle{
	
	private ppBall myBall; 								//create myBall object using the ppBall class.
	
	/**
 	* The constructor for the ppPaddleAgent class is the one of ppPaddle since ppPaddleAgent extends ppPaddle.
	 * @param X - X position of the paddle
	 * @param Y - Y position of the paddle
	 * @param color - color of the paddle
	 * @param myTable - reference to ppTable object
	 */
	
	public ppPaddleAgent (double X, double Y, Color color, ppTable myTable) {
		super(X,Y,color,myTable);
	}
	
	
	
	/*
	 * The run method simply wakes periodically and updates paddle velocities.  * Everything else is done via class methods.
	 */ 

 	public void run() {  	 	
 		while (true) {  	 	 
 			Vx=(X-lastX)/TICK;
 	 	 	Vy=(Y-lastY)/TICK;  	 	 	
 	 	 	lastX=X;  	 	 	
 	 	 	lastY=Y;
 	 	 	myTable.getDisplay().pause(TICK*TIMESCALE);  	// Convert time units to mS
 	
 	 	 	setY(myBall.getBallY());						//Updating the Y position of the agent based on the Y of the ball

 		}
 	}
	
	public void attachBall(ppBall myBall) {								//Sets the value of the myBall instance variable in ppPaddleAgent.
		this.myBall = myBall;
	}
	

}
