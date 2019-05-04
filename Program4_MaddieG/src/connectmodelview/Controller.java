package connectmodelview;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JOptionPane;

import java.awt.Point;

import connectmodel.PieceType;
import connectmodel.Player;
import connectmodel.GameEngine;
import connectmodel.GameBoard;
import connectmodel.ComputerPlayer;
import connectmodelview.View;

/**
 * Controller communicates between View and connectmodel classes.
 *
 * @author madisongipson
 */
public class Controller
{
    private View myView;
    private GameBoard myGameBoard;
    
    private GameEngine myGameEngine; //this is like the model
    
	private Player myPlayer;
	private ComputerPlayer myComputerPlayer;
	private PieceType[] myPieces;
    private int myNumRows = 6;
    private int myNumColumns = 7;
    private int myWinLength = 4;
    private String myName;
    private PieceType myPieceType;
    private PieceType computerPieceType;
    
    /**
	* Initializes and sets required components of game.
	* @author Madison Gipson */
	public Controller(){
		myView = new View(this);
		myPieces = new PieceType[2]; 
		setPieceTypes();
		setGameBoard();
		setMyPlayer();
		setMyComputerPlayer();
		setGameEngine();
	}	
	
	/**
	* When "Start Game" button is pressed in View, this method starts the game by 
	* making sure the starting player is the player that didn't start the last game.
	* If the starting player is the computer, it prompts the computer to make the first move.
	* Update score labels to reflect stored scores of each player.
	* @author Madison Gipson */
	public void startGame()
	{
		Player lastStartingPlayer = myGameEngine.getStartingPlayer(); //starting player of last game
		myView.resetBoard();
		myGameEngine.startGame(); //resets gameboard, assigns starting player & player up
		if(myGameEngine.getStartingPlayer().getName() == myComputerPlayer.getName()) 
		{
			placeComputerPiece();
			myGameEngine.switchPlayerUp();
		}
		myView.updateMessageField("Starting Player is: "+myGameEngine.getStartingPlayer().getName());
		myView.updateScoreLabels(); //makes the score carry over game to game
	}
		
	/**
	* When any column is pressed in View, this method places a piece by 
	* locating the row associated with where the piece would "fall."
	* It changes the image at the column and row to match the player's piece color.
	* If it's a win, it figures out who the winner is, then updates the message and score.
	* It highlights the win begin to end.
	* @author Madison Gipson */
    public void placePiece(Integer Col)
    {
    	int col = Col.intValue(); 
    	myGameEngine.placePiece(col);
        int row = myGameBoard.getLastRow();
        myView.switchImage(col, row);
        myView.updateMessageField(myName+" placed piece in column " +col+". It's now Opponent's turn.");
        if(myGameBoard.checkIfWin()==false) //computer only places piece if game hasn't been won yet
    	{
        	myGameEngine.switchPlayerUp();
            placeComputerPiece();
            myGameEngine.switchPlayerUp();
    	}
      
        Point secondWinPoint;
		Point thirdWinPoint; 
		
        if(myGameBoard.checkIfWin() == true)
    	{
    		PieceType myWinType = myGameBoard.getPieceOnBoard(myGameBoard.getWinBegin());
    		if(myWinType == myPlayer.getPieceType()) //if the player wins
    		{
    			myView.updateMessageField(myName+" won this game, shown by the highlighted pieces! "
    					+ "Press 'Start Game' button to play again");
    			myPlayer.incrementScore();
    			myPlayer.getScore();
    		}
    		else //if the computer wins
    		{
    			myView.updateMessageField("Opponent won this game, shown by the highlighted pieces. "
    					+ "Press 'Start Game' button to play again");
    			myComputerPlayer.incrementScore();
    			myComputerPlayer.getScore();
    		}
    		myView.updateScoreLabels();
    		if(myGameBoard.checkHorizontalWin() == true)
    		{
    			secondWinPoint = new Point(myGameBoard.getWinEnd().x-2, myGameBoard.getWinEnd().y);
    			thirdWinPoint = new Point(myGameBoard.getWinEnd().x-1, myGameBoard.getWinEnd().y);
    		}
    		else if(myGameBoard.checkVerticalWin() == true)
    		{
    			secondWinPoint = new Point(myGameBoard.getWinEnd().x, myGameBoard.getWinEnd().y-2);
    			thirdWinPoint = new Point(myGameBoard.getWinEnd().x, myGameBoard.getWinEnd().y-1);
    		}
    		else
    		{
    			secondWinPoint = new Point(myGameBoard.getWinEnd().x-2, myGameBoard.getWinEnd().y-2);
    			thirdWinPoint = new Point(myGameBoard.getWinEnd().x-1, myGameBoard.getWinEnd().y-1);
    			
    		}
    		myView.highlightWin(myGameBoard.getWinBegin(), myGameBoard.getWinEnd(), secondWinPoint, thirdWinPoint); 
    	}
    }
    
    /**
	* Right after player places a piece, computer places a piece. This
	* involves calling the next move method of the computer player, which 
	* finds the best move. It switches the image at the column and row 
	* to match the computer's piece color.
	* @author Madison Gipson */
    public void placeComputerPiece()
    {
    	int col = myComputerPlayer.nextMove();
    	myGameEngine.placePiece(col);
    	int row = myGameBoard.getLastRow();
        myView.switchImage(col, row);
        myView.updateMessageField("Opponent placed piece in column " +col+". It's now "+myName+"'s turn");
    }
    
    public int getMyNumColumns()
	{
		return myNumColumns;
	}
	
	public int getMyWinLength() 
	{
		return myWinLength;
	}

	public int getMyNumRows() 
	{
		return myNumRows;
	}
	
	public void setGameBoard() {
		myGameBoard = new GameBoard(myNumRows, myNumColumns, myWinLength, myPieces);
	}
	
	public void setGameEngine()
	{
		myGameEngine = new GameEngine(myPlayer, myGameBoard);
	}
	
	public void setPieceTypes()
	{
		myPieces[0] = myView.getMyPieceType();
		myPieces[1] = myView.getMyOtherType();
	}
	
	public void setMyPlayer()
	{
		myName = myView.getMyName();
		myPieceType = myPieces[0];
		myPlayer = new Player(myName, myPieceType);
		if(myPlayer.getName() != myName)
		{
			myName = myPlayer.getName();
			myView.setMyName(myPlayer.getName()); //may not need this
			myView.updateNameLabel();
		}
	}
	
	public void setMyComputerPlayer()
	{
		computerPieceType = myPieces[1];
		myComputerPlayer = new ComputerPlayer("Opponent", computerPieceType, myGameBoard);
	}
	
	public String getMyPlayerName()
	{
		return myPlayer.getName();
	}
	
	public int getMyPlayerScore()
	{
		return myPlayer.getScore();
	}
	
	public int getComputerPlayerScore()
	{
		return myComputerPlayer.getScore();
	}
	
	public PieceType getPieceType()
	{
		if(myGameEngine.getPlayerUp() == myPlayer)
		{
			return myView.getMyPieceType();
		}
		else 
		{
			return myView.getMyOtherType();
		}
	}
}