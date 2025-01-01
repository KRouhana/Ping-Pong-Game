package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GRect;

//A lot of this code has been taken/inspired by my ECSE 202 professor Frank Ferrie for the fall 2020 semester. Prof Ferrie provided his students with a pdf file explaining the code he provided and how we're suppose to implement each java class of the assignments.
/**
This class extends thread. It is responsible for everything related to the paddle and its method exported.
 * 
*@author KarlRouhana, Inspired a lot by Prof Ferrie.
*	 */


public class ppPaddle extends Thread {
 
 	double X;  	 		 // Paddle X location
 	double Y;  	 		 // Paddle Y location
 	double Vx;  	 	 // Paddle velocity in X
 	double Vy;  	 	 // Paddle velocity in Y
 	double lastX;  	 	 // X position on previous cycle
 	double lastY;  	 	 // Y position on previous cycle
 	double ScrX;
 	double ScrY;
 	ppTable myTable; 	 	// Instance of pp table.
 	GRect myPaddle;  	 	// GRect implements paddle
 	Color color;
 	
 
	/**
 	* The constructor for the ppPaddle class copies parameters to instance variables, creates an
	* instance of a GRect to represent the paddle, and adds it to the display. * 
	 * @param X - X position of the paddle
	 * @param Y - Y position of the paddle
	 * @param color - color of the paddle
	 * @param myTable - reference to ppTable object
	 */
	
 	public ppPaddle (double X, double Y, Color color, ppTable myTable) {
 	 	this.X=X;  	 	
 	 	this.Y=Y;
 	 	this.myTable=myTable;  
 	 	this.color=color;
 	 	
 	 	lastX=X;  	 	
 	 	lastY=Y;
 	 	ScrX = myTable.toScrX(X-ppPaddleW/2);  	 	
 	 	ScrY = myTable.toScrY(Y+ppPaddleH/2);
 	 	createPaddle();

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
 	 	 	}
 	}
 
 	
 	
 	
 	
 	
 	public void createPaddle(){						//Method to create any paddle with X,Y defined with the constructor

 	 	// Create screen display for paddle
 	 	myPaddle = new GRect(ScrX,ScrY,ppPaddleW*SCALE,ppPaddleH*SCALE);  	 
 	 	myPaddle.setFilled(true);
 	 	myPaddle.setColor(color);
 	 	myTable.getDisplay().add(myPaddle);  
	}
		
/**
*	Contact - a method to determine if the surface of the ball is in contact
*	with the surface of the paddle.
*	@param Sx - X coordinate of contacting surface
*	@param Sy - Y coordinate of contacting surface 
*   @return  - true of in contact, false otherwise.
 */
 	
 	public boolean contact(double Sx, double Sy) {
 	 
// A surface at position (Sx,Sy) is deemed to be in contact with 
// the paddle if it lies in a box from [X-ppPaddleW/2,X+ppPaddleW/2] 
// and [Y-ppPaddleH/2,Y+ppPaddleH/2].
//
// This method is called when the ball reaches an X position that just
// touches the surface of the paddle.  So, the ball is deemed to be in
// contact (to a first order approximation) if Sy >= Y-ppPaddleH/2 &&
// Sy <= Y+ppPaddleH/2;
 	 	
 		return (Sy>=(Y-ppPaddleH/2) && Sy<=(Y+ppPaddleH/2));
 	}
 
/**
*	getVx
*	@return double - X velocity component of paddle
*/  
 	public double getVx() {
 	  return Vx;
 	}

/**
*	getVy
*	@return double - Y velocity component of paddle
*/  
 	public double getVy() {
 	  return Vy;
 	}
 
/**
*	setX - update position, move on screen
*	@return void
*/  
 	public void setX(double X) {
 	 	this.X=X;
 	 	double ScrX = myTable.toScrX(this.X-ppPaddleW/2);  	 	
 	 	double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
 	 	myPaddle.setLocation(ScrX,ScrY); 	 	 	 	// Update display  
 	}
 	
/**
*	setY  - update position, move on screen
*	@return void
*/ 	 
 	public void setY(double Y) {
 	  this.Y=Y;
 	  double ScrX = myTable.toScrX(this.X-ppPaddleW/2);  	 
 	  double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
 	  myPaddle.setLocation(ScrX,ScrY);  	 	 	    	// Update display  
 	}
 
/**
*	getX - get X position of the paddle
*	@return double X
*/   	
 	public double getX() {
 	 	return X;
 	}
 
/**
*	getY - get Y position of the paddle
*	@return double Y
*/
 	public double getY() {
 	 	return Y;
 	}

/**
*	getSgnVy() -  get sign of Vy of the paddle
*	@return double
 */  
 	public double getSgnVy() {
 		if (Vy<0)  	return -1.0;  
 		else  	 	return 1.0;
 	}

 	
	



}
