package connectmodel;

/**
 * @purpose PieceType class is an enumeration that holds the different piece types
 * available for use. 
 * @author madisongipson
 *
 * @datedue March 27, 2019, 11:59pm
 * 
 * @input 
 * @output 
 */

public enum PieceType
{
	RED ("Red"),
	BLACK ("Black"),
	GREEN ("Green"),
	YELLOW ("Yellow");
    
	private String myType;
	
	/**
     * Method to assign type passed in as the player's type
     * @param piece type
     * @return piecetype of player
     * @author madisongipson */
    private PieceType(String type) 
    {
    	myType = type; 
    }
    
    public String getType()
    {
    	return myType;
    }
}