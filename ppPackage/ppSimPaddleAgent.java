package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import acm.graphics.GRect; 
import acm.program.GraphicsProgram; 
import acm.util.RandomGenerator;


//A lot of this code has been taken/inspired by my ECSE 202 professor Frank Ferrie for the fall 2020 semester. Prof Ferrie provided his students with a pdf file explaining the code he provided and how we're suppose to implement each class of the assignment.


/**
*	This classes will be responsible to have the "random" part of the class, since it uses the RadomGenerator class-----> assignment 2
	This class will also listen for the buttons and the mouse Y position.	
*@author KarlRouhana, Inspired a lot by Prof Ferrie.
*	 */


public class ppSimPaddleAgent extends GraphicsProgram {
 
/**
*	This is the main class which nominally uses the default constructor. To get around
*	problems with the acm package, the following code is explicitly used to make
*	the entry point unambiguous.  This is beyond the requirements for this assignment
*	and is provided for convenience.
 */ 
	
	//To make the program work
    public static void main(String[] args) { 	 	 	 	 	 // Standalone Applet  	 	
    	new ppSimPaddleAgent().start(args);
 	 	}
        
 /*
*	Instance Variables
  */     	
    ppBall myBall;     													// create ball instance using ppBall class.
    ppPaddle myPaddle;    												// create paddle instance using ppPaddle class.
    ppPaddleAgent paddleAgent;											// create paddle instance using ppPaddleAgent class.
    ppTable myTable;													// create table instance using ppTable class.
    RandomGenerator rgen;												// create instance of RandomGenerator.
    
    boolean traceOn=true;												//create boolean for the trace state
	
    //create instances for the buttons
    private JButton clear;
	private JButton newServe;
	private JToggleButton trace;
	private JButton quit;
	private JTextField playerName;
	private JTextField agentName;
	private JLabel scores;
		
	int playerScore=0;
	int agentScore=0;
    
    
    
  
 	public void init() {
 	 	this.resize(scrWIDTH+OFFSET,scrHEIGHT+OFFSET);           // optional, initialize window size
 	 	addButtons();								
		addTextandScores();
 	 	addMouseListeners(); 	 	 	 	 	 	 	 	 	 // mouse operates paddle
		addActionListeners();									 // Button action Listeners

		// Set up display.  We pass the ppTable constructor a reference to the main class so that GraphicsProgram utilities are available to methods within.
 	 	myTable = new ppTable(this); 	 	 	 // set up the ping pong table display
 	 
 	 	// Set up random number generator
 	 	rgen = RandomGenerator.getInstance();
 	 	rgen.setSeed(RSEED);
 
 	 	// Create paddles
 	 	myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,ColorPaddle,myTable); 						 // create paddle object using ppPaddle constructor.
 	 	paddleAgent = new ppPaddleAgent(ppAgentXinit,ppAgentYinit,ColorAgent,myTable); 					 // create paddle object using ppPaddleAgent constructor.

 	 	
 	 	

 	 	 
 	 	pause(1000); 	 	 	 	 	 	 	    	//Pause before start
 	 	myPaddle.start();								//Starting the players' paddle
		paddleAgent.start();							//Starting the agents' paddle
		
		myBall=newBall(); 								//create a new ball 								
 	 	myBall.start();  	 	 	 	 	 	 	 	// Start this instance  	 	

 	}
 

 	
 	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
	
		if(trace.isSelected()) {								    //checks if the user wants the tracer points drawn or not
			traceOn=true;
			}
		else {
			traceOn=false;
			}
		
		if(command.equals("Clear")) {								//reset the score
			if(!myBall.ballInPlay()) {								//this if checks if the ball is in play
				resetScores();
				}
			
			}
	
		else if(command.equals("New Serve")) {						//clears everything from the screen , add everything again and start a new game
				if(!myBall.ballInPlay()) {							//this if checks if the ball is in play
					myTable.newScreen();
					addPaddles();
					
					pause(500);										//wait before starting a game
					myBall=newBall();
					myBall.start();
				}
			}
	
		else if(command.equals("Quit")) System.exit(0);					//quit the program
			
}


 	
 	
 	public ppBall newBall() {
		Color iColor = ColorBall;
		double iYinit =rgen.nextDouble(YinitMIN,YinitMAX);  									//random Yinit in the interval
		double iLoss =rgen.nextDouble(EMIN,EMAX);												//random loss in the interval
		double iVel =rgen.nextDouble(VoMIN,VoMAX);												//random initial Velocity in the interval
		double iTheta =rgen.nextDouble(ThetaMIN,ThetaMAX);										//random initial angle in the interval
		
		// Generate instance of ball with variable parameters
		ppBall iBall = new ppBall(XINIT,iYinit,iVel,iTheta,iColor,iLoss,myTable,traceOn); 
		
		myBall=iBall;									//sets the iBall created using the ppBall constructor to myBall, so it can be used in the init of this class

		add(iBall.getBall());							//adding ball to the screen	
									
		paddleAgent.attachBall(iBall);					//Sets the value of the myBall instance variable in ppPaddleAgent.
		iBall.setPaddle(myPaddle);						//let the ppBall class access the paddle
		iBall.setAgent(paddleAgent);					//let the ppBall class access the paddle
	
	return iBall;
}
 	
 	
/**
*	Mouse Handler - a moved event moves the paddle up and down in Y
 */  
	 public void mouseMoved(MouseEvent e) {
	 	 	this.myPaddle.setY(myTable.ScrtoY((double)e.getY()));
	 }

 	
 	
 	
 	

	
	public void addButtons() {							//Add all the buttons to the screen
		clear= new JButton("Clear");
		add(clear,SOUTH);
		
		newServe = new JButton ("New Serve");
		add(newServe,SOUTH);

		trace = new JToggleButton("Trace",true);  
		add(trace,SOUTH);

		quit = new JButton ("Quit");
		add(quit,SOUTH);

		
	}
		
	
	public void addTextandScores() {					//Add all text fields and scoreboard
		agentName = new JTextField("Agent");
		add(agentName,NORTH);

		scores =  new JLabel(agentScore +" " + playerScore);
		add(scores,NORTH);

		playerName = new JTextField("Human");
		add(playerName,NORTH);
	}

	
	public void addPaddles(){         					//add both paddle to the screen
		myPaddle.createPaddle();
		paddleAgent.createPaddle();
		
	}
	

 	public void addPlayerScore() {						//add 1 point to the player
		playerScore++;
		scores.setText(agentScore +" " + playerScore);

	}
	
	public void addAgentScore() {						//add 1 point to the agent
		agentScore++;
		scores.setText(agentScore +" " + playerScore);

	}
	
	public void resetScores(){							//reset the scoreboard to 0
		agentScore=0;
		playerScore=0;
		scores.setText(agentScore +" " + playerScore);

	}
	







}
