package connectmodel;

import java.awt.Point;
import java.util.Arrays;
import java.util.Vector;

import connectmodel.PieceType;

/**
 * @purpose GameBoard controls all gameboard-related functions for the Connect Four Game.
 * This includes placing pieces, tracking the last piece placed, resetting the board, 
 * checking the board, counting length of pieces on boards, checking for wins, 
 * and finding the best possible move. 
 * @author madisongipson
 *
 * @datedue March 27, 2019, 11:59pm
 * 
 * @input Number of rows & columns, win length, and types of pieces being used.
 * @output 
 */


public class GameBoard
{
    private int myNumRows;
    private int myNumColumns;
    private PieceType[][] myBoard;
    private int myNumTypes;
    private int myWinLength;
    private Point myLastPoint;
    private Vector<PieceType> myTypes;
    private Point myWinBegin;
    private Point myWinEnd;
    private boolean myIsAWin;
    private int myLastRow;
	
    public GameBoard(int rows, int cols, int winLength, PieceType[] types) 
    {
    	myNumRows = rows;
    	myNumColumns = cols;
    	myWinLength = winLength;
    	myBoard = new PieceType[myNumColumns][myNumRows];
    	PieceType[] myPieces = new PieceType[myNumTypes]; 
    	myTypes = new Vector<PieceType>(Arrays.asList(myPieces));
    	myTypes.add(types[0]);
    	if(myNumRows>1 && myNumColumns>1)
    	{
    		myTypes.add(types[1]);	
    	}
    	myNumTypes = myTypes.size(); 
    }
	
    /**
    * Method to place a piece in a valid column & an empty spot
    * @param column piece wants to be placed into & piece type of piece being placed
    * @return success of placing the piece in desired column
    * @author madisongipson */
    public boolean placePiece(int col, PieceType type)
    {
    	if(col < myNumColumns && col > -1) //ensure column is within bounds
    	{
    		for(int i=0; i< myNumRows; i++) //loop through rows of column
    		{
    			if(myBoard[col][i] == null) //check if spot if empty
    			{
    				myBoard[col][i] = type; //assign given type to spot
    				myLastPoint = new Point(i, col); //assign last point to spot
    				myLastRow = i;
    				return true;
    			}
    		}
    	}
	    return false;
    }
	
    /**
     * Method to reset the entire board so all spots are null (empty)
     * @author madisongipson */
    public void resetBoard() 
    {
    	for(int i=0; i< myNumColumns; i++) //loop through all columns
 	   {
 		   for(int j=0; j< myNumRows; j++) //loop through all rows
 		   {
 			   if(myBoard[i][j] != null) //check if spot if filled
 			   {
 				   myBoard[i][j] = null; //if spot is filled, empty spot
 			   }
 		   }
 	   }
 	   
    }
	
    /**
     * Method to check if there is a win of any direction type
     * @return truth value of win status
     * @author madisongipson */
    public boolean checkIfWin() 
    {
    	if(myLastPoint != null) { //if there exists a last point
    		if(checkHorizontalWin()== true || checkVerticalWin()==true || checkDiagonalWin()== true)
        	{
        		myIsAWin = true; //if any type of win happens, there is a win
        	}
        	else
        	{
        		myIsAWin = false;
        	}	
    	}
    	return myIsAWin;
    }
	
    /**
     * Method to find the best possible move to either extend length or block
     * @param piece type of piece being placed
     * @return column number that would yield the best move
     * @author madisongipson */
    public int findBestMoveColumn(PieceType type)
    {
    	PieceType opponentType = type == myTypes.firstElement()? myTypes.lastElement():myTypes.firstElement();
    	for(int cols=0; cols<myNumColumns; cols++)
    	{    		
    		if(countVerticalLengthIfPiecePlaced(cols, type) == myWinLength || 
    				countHorizontalLengthIfPiecePlaced(cols, type) == myWinLength || 
    				countDiagonalLengthIfPiecePlaced(cols, type) == myWinLength)
    		{
    			return cols;
    		}
    	}
    	for(int cols=0; cols<myNumColumns; cols++)
    	{
    		if(countVerticalLengthIfPiecePlaced(cols, opponentType) == myWinLength || 
    				countHorizontalLengthIfPiecePlaced(cols, opponentType) == myWinLength || 
    				countDiagonalLengthIfPiecePlaced(cols, opponentType) == myWinLength)
    		{
    			return cols;
    		}
    	}
    	int bestMove = 0;
    	int longest = 0;
    	for(int cols=0; cols<myNumColumns; cols++)
    	{
    		int currentLength = countVerticalLengthIfPiecePlaced(cols, type);
    		currentLength +=  countHorizontalLengthIfPiecePlaced(cols, type);
    		currentLength += countDiagonalLengthIfPiecePlaced(cols, type);
    		if(currentLength > longest)
    		{
    			longest = currentLength;
    			bestMove = cols;
    		}
    	}
	    return bestMove;
    }
	
    /**
     * Method to check if there is a vertical win, determines if vertical length is equal to win length
     * @return success of vertical win
     * @author madisongipson */
    public boolean checkVerticalWin() 
    {
    	if(myLastPoint != null)
    	{
    	for(int rows=0; rows<myNumRows-myWinLength+1; rows++)
    	{	
    		for(int cols=0; cols<myNumColumns; cols++)
    		{
    			if(myBoard[cols][rows] != null)
    			{
    				for(int win=1; win<myWinLength; win++)
    				{
    					myWinBegin = new Point(cols, rows);
    					if(myBoard[cols][rows] != myBoard[cols][rows+win])
    					{
    						myWinBegin = null;
    						break;
    					}
    					if(win == myWinLength-1)
    					{
    						myWinEnd = new Point(cols, rows+myWinLength-1);
    						return true;
    					}
    				}
    			}
    		}
    	}
    	}
	    return false;
    }
	
    /**
     * Method to check if there is a horizontal win, determines if horizontal length is equal to win length
     * @return success of horizontal win
     * @author madisongipson */
    public boolean checkHorizontalWin() 
    {
    	if(myLastPoint != null)
    	{	
    		int rows = myLastPoint.x; 
    		for(int cols=0; cols<myNumColumns-myWinLength+1; cols++)
    		{
    			if(myBoard[cols][rows] != null)
    			{
    				for(int win=1; win<myWinLength; win++)
    				{
    					myWinBegin = new Point(cols, rows);
    					if(myBoard[cols][rows] != myBoard[cols+win][rows])
    					{
    						myWinBegin = null;
    						break;
    					}
    					if(win == myWinLength-1)
    					{
    						myWinEnd = new Point(cols+myWinLength-1, rows);
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
	
    /**
     * Method to check if there is a diagonal win, determines if diagonal length is equal to win length
     * @return success of diagonal win
     * @author madisongipson */
    public boolean checkDiagonalWin() 
    {
    	for(int rows=0; rows<myNumRows; rows++)
    	{	
    		for(int cols=0; cols<myNumColumns; cols++)
    		{
    			if(myBoard[cols][rows] != null)
    			{
    				for(int win=1; win<myWinLength; win++)
    				{
    					myWinBegin = new Point(cols, rows);
    					if((cols+win) == myNumColumns || (rows+win) == myNumRows || myBoard[cols][rows] != myBoard[cols+win][rows+win])
    					{
    						myWinBegin = null;
    						break;
    					}
    					if(win == myWinLength-1)
    					{
    						myWinEnd = new Point(cols+myWinLength-1, rows+myWinLength-1);
    						return true;
    					}
    				}
    				for(int win=1; win<myWinLength; win++)
    				{
    					myWinBegin = new Point(cols, rows);
    					if((cols+win)==myNumColumns || rows-win==-1 || myBoard[cols][rows] != myBoard[cols+win][rows-win])
    					{
    						myWinBegin = null;
    						break;
    					}
    					if(win == myWinLength-1)
    					{
    						myWinEnd = new Point(cols+myWinLength-1, rows-myWinLength+1);
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Method to count the horizontal length that connects with the last piece placed
     * @param column of piece placed & piece type of piece placed
     * @return horizontal length
     * @author madisongipson */
    private int countHorizontalLengthIfPiecePlaced(int col, PieceType type) 
    {
    	int horizontalLength = 0;
    	int placeHolder = 0;
    	for(int rows = myNumRows-1; rows>0; rows--)
    	{
    		if(myBoard[col][rows] == null && myBoard[col][rows-1] != null)
    		{
    			placeHolder = rows;
    			break;
    		}
    	}
    	for(int cols = col-1; cols>-1; cols--)
    	{
    		if(myBoard[cols][placeHolder] == type)
    		{
    			horizontalLength++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	for(int cols = col+1; cols<myNumColumns; cols++)
    	{
    		if(myBoard[cols][placeHolder] == type)
    		{
    			horizontalLength++;
    		}
    		else
    		{
    			break;
    		}
    	}
	    return horizontalLength;
    }
	
    /**
     * Method to count the vertical length that connects with the last piece placed
     * @param column of piece placed & piece type of piece placed
     * @return vertical length
     * @author madisongipson */
    private int countVerticalLengthIfPiecePlaced(int col, PieceType type) 
    {
    	int verticalLength = 1;
    	int placeHolder = 0;
    	for(int rows = myNumRows-1; rows>0; rows--)
    	{
    		if(myBoard[col][rows] == null && myBoard[col][rows-1] != null)
    		{
    			placeHolder = rows;
    			break;
    		}
    	}
    	for(int rows = placeHolder-1; rows>-1; rows--)
    	{
    		if(myBoard[col][rows] == type)
    		{
    			verticalLength++;
    		}
    		else
    		{
    			break;
    		}
    	}
	    return verticalLength;
    }
	
    /**
     * Method to count the diagonal length that connects with the last piece placed
     * @param column of piece placed & piece type of piece placed
     * @return diagonal length
     * @author madisongipson */
    private int countDiagonalLengthIfPiecePlaced(int col, PieceType type) 
    {
    	int diagonalRightLength = 1;
    	int diagonalLeftLength = 1;
    	int placeHolder = 0;
    	for(int rows = myNumRows-1; rows>0; rows--)
    	{
    		if(myBoard[col][rows] == null && myBoard[col][rows-1] != null)
    		{
    			placeHolder = rows;
    			break;
    		}
    	}
    	for(int delta = 1; delta<myWinLength; delta++)
    	{
    		if(col+delta<myNumColumns && placeHolder+delta<myNumRows && myBoard[col+delta][placeHolder+delta] == type)
    		{
    			diagonalRightLength++;
    		}
    		else
    			break;
    	}
    	for(int delta = 1; delta<myWinLength; delta++)
    	{
    		if(col-delta>-1 && placeHolder-delta>-1 && myBoard[col-delta][placeHolder-delta] == type)
    		{
    			diagonalRightLength++;
    		}
    		else
    			break;
    	}
    	for(int delta = 1; delta<myWinLength; delta++)
    	{
    		if(col+delta<myNumColumns && placeHolder-delta>-1 && myBoard[col+delta][placeHolder-delta] == type)
    		{
    			diagonalLeftLength++;
    		}
    		else
    			break;
    	}
    	for(int delta = 1; delta<myWinLength; delta++)
    	{
    		if(col-delta>-1 && placeHolder+delta<myNumRows && myBoard[col-delta][placeHolder+delta] == type)
    		{
    			diagonalLeftLength++;
    		}
    		else
    			break;
    	}
    	return (diagonalRightLength>diagonalLeftLength)? diagonalRightLength:diagonalLeftLength;   
    }
	
    public Vector<PieceType> getTypes() 
    {
	    return myTypes;
    }
	
    public Point getWinBegin() 
    {
    	return myWinBegin;
    }
	
    public Point getWinEnd()
    {
    	return myWinEnd;
    }
	
    public Point getLastPoint() 
    {
	    return myLastPoint;
    }
    
    public int getLastRow()
    {
    	return myLastRow;
    }
	
    public PieceType getPieceOnBoard(Point point) 
    {
    	return myBoard[point.x][point.y]; //x and y values of point passed in
    }
	
    public PieceType[][] getBoard() 
    {
	    return myBoard;
    }
    
    public int getWinLength()
    {
    	return myWinLength;
    }
	
    /**
     * Method to check if the board is full
     * @return status of the board being full
     * @author madisongipson */
    public boolean isBoardFull() 
    {
	   for(int i=0; i< myNumColumns; i++) //loop through columns
	   {
		   for(int j=0; j< myNumRows; j++) //loop through rows
		   {
			   if(myBoard[i][j] == null) //check if spot is empty
			   {
				   return false; //if spot is empty, then board cannot be full
			   }
		   }
	   }
	   return true;
    }
	
    /**
     * Method to check if the column is full
     * @param column being checked
     * @return status of the column being full
     * @author madisongipson */
    public boolean isColumnFull(int col) 
    {
    	for(int i=0; i< myNumRows; i++) //loop through rows
 	   {
    		if(myBoard[col][i] == null) //check if spot is empty in all rows of passed in column
    		{
    			return false; //if spot is empty, then column cannot be full
    		}
 	   }
    	return true;
    }
	
    public boolean getIsAWin() 
    {
	    return myIsAWin;
    }
    
    /**
     * Method to check if the board is empty
     * @return status of the board being empty
     * @author madisongipson */
    public boolean checkAllNull()
    {
    	for(int i=0; i< myNumColumns; i++) //loop through columns
 	    {
 		   for(int j=0; j< myNumRows; j++) //loop through rows
 		   {
 			   if(myBoard[i][j] != null) //check if spot is filled
 			   {
 				   return false; //if spot is filled, then all spots cannot be null
 			   }
 		   }
 	   }
       myIsAWin= false;
 	   return true;
    }
}