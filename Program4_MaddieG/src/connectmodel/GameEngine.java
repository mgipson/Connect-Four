package connectmodel;

import java.awt.Point;
import java.util.Vector;

/**
 * @purpose GameEngine controls game setup concerning players and the game board.
 * @author madisongipson
 *
 * @datedue March 27, 2019, 11:59pm
 * 
 * @input Player and game board.  
 * @output 
 */

public class GameEngine 
{
	private Vector<Player> myPlayers;
	private Player myPlayerUp;
	private Player myStartingPlayer;
	private GameBoard myGameBoard;
	private ComputerPlayer myComputerPlayer;
  
	public GameEngine(Player player, GameBoard gameboard) 
	{
		myGameBoard = gameboard;
		myStartingPlayer = player;
		myPlayerUp = player;
		myPlayers = new Vector<Player>(2, 1);
		myPlayers.add(0, player);
		myComputerPlayer = new ComputerPlayer("Opponent", PieceType.BLACK, myGameBoard);
		myPlayers.add(1, myComputerPlayer);
		
	}

	 /**
	    * Method to select the starting player
	    * @param player 
	    * @return status of starting player being player passed in
	    * @author madisongipson */
	public boolean selectStartingPlayer(Player player)
	{
		if (myStartingPlayer == player) //assigns starting player to be player passed in
		{
			return true;
		}
		return false;
	}

	 /**
	    * Method to start the game on the condition that the game board is not already empty
	    * & there is a player currently up
	    * @return success of game starting
	    * @author madisongipson */
	public boolean startGame() 
	{
		if(myGameBoard != null && myPlayers.firstElement() != null)
		{
			if(myStartingPlayer == myPlayers.firstElement())
			{
				myPlayerUp = myPlayers.lastElement();
				myStartingPlayer = myPlayers.lastElement();
			}
			else
			{
				myPlayerUp = myPlayers.firstElement();
				myStartingPlayer = myPlayers.firstElement();
			}
			myGameBoard.resetBoard();
			return true;
		}
		return false;
	}
	
	 /**
	    * Method to switch players
	    * @return switched player
	    * @author madisongipson */
	public Player switchPlayerUp() 
	{
		int i = myPlayers.indexOf(myPlayerUp); //index of current player
		myPlayerUp = myPlayers.get((i+1)%2); //modulo results in index of other player
		return myPlayerUp; 
	}

	 /**
	    * Method to place a piece
	    * @param column piece wants to be placed into
	    * @return success of placing the piece
	    * @author madisongipson */
	public boolean placePiece(int column)
	{
		myGameBoard.placePiece(column, myPlayerUp.getPieceType()); //piece type of the current player placed in column
		if(myGameBoard.checkAllNull() == false) //if not all spots are filled, then piece was placed
		{
			return true;
		}
		return false;
	}

	public Player getPlayerUp() 
	{
		return myPlayerUp;
	}
	
	public Player getStartingPlayer()
	{
		return myStartingPlayer;
	}

	public Vector<Player> getPlayers()
	{
		return myPlayers;
	}
  
	public void setGameBoard(GameBoard gameboard)
	{
		myGameBoard = gameboard;
	}

	public GameBoard getGameBoard()
	{
		return myGameBoard;
	}
	
}