package connectmodel;

/**
 * @purpose ComputerPlayer class extends the Player class. ComputerPlayer is a Player 
 * not controlled by the user, that plays against the Player.  
 * @author madisongipson
 *
 * @datedue March 27, 2019, 11:59pm
 * 
 * @input ComputerPlayer's name and piece type. 
 * @output Move that ComputerPlayer should take next.
 */

public class ComputerPlayer extends Player
{
	private GameBoard myGameBoard;
	private PieceType myType;
	
	//Constructor
	public ComputerPlayer(String name, PieceType type, GameBoard gameboard)
	{
		super(name, type);
		myType = type;
		myGameBoard = gameboard;
	}

	/**
	* Method to determine next move that ComputerPlayer should take
	* @return Column that ComputerPlayer should place next piece into
	* @author madisongipson
	* */
	public int nextMove()
	{
		return myGameBoard.findBestMoveColumn(getPieceType());
	}
	
	public GameBoard getMyGameBoard()
	{
		return myGameBoard;
	}
	
	public void setMyGameBoard(GameBoard myGameBoard)
	{
		this.myGameBoard = myGameBoard;
	}
}