package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GOval; 
import acm.graphics.GRect;

//This class is uses to export different methods and is the class which will be responsible to computing the terrain.
//A lot of this code has been taken/inspired by my ECSE 202 professor Frank Ferrie for the fall 2020 semester. Prof Ferrie provided his students with a pdf file explaining the code he provided and how we're suppose to implement each class of the assignment.

/**
*	The ppTable class is responsible for creating the initial display and * providing utility methods for converting from world to display coordinates.
*	@author KarlRouhana, Inspired a lot by Prof Ferrie.
*	 */

public class ppTable {
 
	// Instance Variables
 	ppSimPaddleAgent dispRef;
 
 	// The table is created in the constructor
 	public ppTable(ppSimPaddleAgent dispRef) {
 	
 	 	this.dispRef=dispRef;
 	
 	 	// Create the ground plane
     	GRect gPlane = new GRect(0,scrHEIGHT,scrWIDTH+OFFSET,3); // A thick line HEIGHT pixels down from the top      	
     	gPlane.setColor(Color.BLACK);      
     	gPlane.setFilled(true);      	
     	dispRef.add(gPlane);
}
 	
 	/*
 	 * The remainder of this class consists of utility methods for display  */
 	
 	
	public void newScreen() {					//Method to set a new screen
     	dispRef.removeAll();
		GRect gPlane = new GRect(0,scrHEIGHT,scrWIDTH+OFFSET,3); 		// A thick line HEIGHT pixels down from the top      	
     	gPlane.setColor(Color.BLACK);      
     	gPlane.setFilled(true);      	
     	dispRef.add(gPlane);
	}
		
	
	
	double toScrX(double X) {  	 			 // X to Screen X	 	
		return X*SCALE;
	}
	 	 	 	
	double toScrY(double Y) {  	 	 	 		// Y to Screen Y
	 	return scrHEIGHT - Y*SCALE;  	 		// Sy = SH - Y*SCALE
	}
 	
 	
 	
 	double ScrtoX(double ScrX) { 	 	 	 	 // ScrX to X  	 	
 		return ScrX/SCALE;
 	}
  	
 	double ScrtoY(double ScrY) {				// ScrY to Y
 	 	return (scrHEIGHT-ScrY)/SCALE;
 	}
 
 	
 	ppSimPaddleAgent getDisplay() {  	 	 	 	 	 	// Reference to display  	 	
 		return dispRef;
 	}
 

 	// A simple method to plot trace points on the screen
 	void trace(double x, double y, Color color) {
 	 	double ScrX = toScrX(x);  	 	
 	 	double ScrY = toScrY(y);
 	 	GOval pt = new GOval(ScrX,ScrY,PD,PD);
 	 	pt.setColor(color);  	 	
 	 	pt.setFilled(true);
 	 	dispRef.add(pt); 
 	 	}


}
