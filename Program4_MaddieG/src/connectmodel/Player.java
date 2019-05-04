package connectmodel;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @purpose Player class controls name, piece type, and score of players.
 * @author madisongipson
 *
 * @datedue March 27, 2019, 11:59pm
 * 
 * @input Player name and piece type.
 * @output 
 */

public class Player 
{
	public static final String DEFAULT_NAME = "JohnCena";
	private String myName;
	private int myNumWins;
	private PieceType myPieceType;

	
	public Player(String name, PieceType type)
	{
		if(validateName(name)==false || name.isEmpty()) //invalid name sets to default name
		{
			myName = DEFAULT_NAME;
		}
		else
		{
			myName = name;
		}
		myPieceType = type; 
		myNumWins = 0;
	}

	 /**
	    * Method to validate the player's name
	    * @param name of player
	    * @return truth value of name being valid according to rules
	    * @author madisongipson */
	private boolean validateName(String name) 
	{
		//name must only contain a-z, A-Z, or 0-9, no special characters
		return name.toString().matches("^[a-zA-Z0-9]+$");
	}

	 /**
	    * Method to add to player's score
	    * @author madisongipson */
	public void incrementScore() 
	{
		myNumWins = myNumWins + 1;
	}
	
	public PieceType getPieceType()
	{
		return myPieceType;
	}

	public String getName() 
	{
		return myName;
	}

	public int getNumWins()
	{
		return myNumWins;
	}
	
	public int getScore()
	{
		return myNumWins;
	}
}