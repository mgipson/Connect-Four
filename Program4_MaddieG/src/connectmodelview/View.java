package connectmodelview;
/**
 * View class controls the display of the game.
 * @author madisongipson
 */

import java.awt.*;
import java.lang.reflect.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import connectmodel.PieceType;
import connectmodelview.ButtonListener;
import connectmodelview.Can;

@SuppressWarnings("serial")
public class View 
{
    //////////////////////
    //    Properties    //
    //////////////////////
	
	private Can[][] myBoard;
	private ButtonListener[] myColumnListener; //listens for the different columns
	private Panel myBoardPanel;
	
	private Label myPlayerName;
	private Label myPlayerColor;
	private Label myPlayerScore;
	private Panel myLeftPanel;
	
	private Label myComputerName;
	private Label myComputerColor;
	private Label myComputerScore;
	private Panel myRightPanel;
	
	private TextArea myMessageField;
	private final static String newline = "\n";
	private ButtonListener myStartGameListener;
	private Button myStartGameButton;
	private Panel myBottomPanel;
	
	private int myWinLength;
	private int myNumColumns;
	private int myNumRows;
	
	private String myName;
	private String myPieceTypeString;
	private PieceType myPieceType;
	private String otherPieceTypeString;
	private PieceType otherPieceType;
	
	String[] myPieceTypes = { "RED", "BLACK", "GREEN", "YELLOW"};
	
	private JPanel myPieceTypeOptionPanel;
	private JRadioButton myRedPieceType;
	private JRadioButton myBlackPieceType;
	private JRadioButton myGreenPieceType;
	private JRadioButton myYellowPieceType;

	
	private Controller myController;

    private Image myBlankImage;
    private Image myRedCircle;
    private Image myBlackCircle;
    private Image myGreenCircle;
    private Image myYellowCircle;
    
    ///////////////////////
    //      Methods      //
    ///////////////////////
    
    /**
     * View constructor used to lay out the view
     *
     * <pre>
     * pre:  none
     * post: the view is set up and initialized
     * </pre>
     */
    public View(Controller controller)
    {
        String value;
        myController = controller;
        myWinLength = myController.getMyWinLength();
        myNumRows = myController.getMyNumRows();
        myNumColumns = myController.getMyNumColumns();
        getInfo();

        JFrame frame = new JFrame("Connect 4");
        
        frame.setSize(775,775);
        frame.setLayout(null);
        frame.setBackground(Color.white);
        frame.setResizable(false);
        
        myBlankImage = Toolkit.getDefaultToolkit().getImage("images/blank.jpg");
        myRedCircle = Toolkit.getDefaultToolkit().getImage("images/red.png");
        myBlackCircle = Toolkit.getDefaultToolkit().getImage("images/black.png");
        myGreenCircle = Toolkit.getDefaultToolkit().getImage("images/green.png");
        myYellowCircle = Toolkit.getDefaultToolkit().getImage("images/yellow.png");

        myBoard = new Can[myNumRows][myNumColumns];     
        myColumnListener = new ButtonListener[myNumColumns];
        myBoardPanel = new Panel(new GridLayout(myNumRows, myNumColumns));
        myBoardPanel.setSize(75*myNumRows, 75*myNumColumns); //450x525
        myBoardPanel.setLocation(125, 50);
        
        //assigns a blank image to each spot in the board
        for(int i=myNumRows-1; i > -1; i--)
    	{
        	for(int j=0; j < myNumColumns; j++) 
        	{
        		 myBoard[i][j] = new Can(myBlankImage);
                 myBoardPanel.add(myBoard[i][j]);	
        	}
        } 
         
        
    	myPlayerName = new Label("Name: "+getMyName());
    	myPlayerColor = new Label("Color: "+ getMyPieceType());
    	myPlayerScore = new Label("Score: 0");
    	
    	myLeftPanel = new Panel(new FlowLayout());
    	myLeftPanel.setSize(125, 125);
    	myLeftPanel.setLocation(0, 0);
    	myLeftPanel.add(myPlayerName);
    	myLeftPanel.add(myPlayerColor);
    	myLeftPanel.add(myPlayerScore);
    	
    	myComputerName = new Label("Name: Opponent");
    	myComputerColor = new Label("Color: "+ getMyOtherType());
    	myComputerScore = new Label("Score: 0");
    	
    	myRightPanel = new Panel(new FlowLayout());
    	myRightPanel.setSize(125, 125);
    	myRightPanel.setLocation(600, 0);
    	myRightPanel.add(myComputerName);
    	myRightPanel.add(myComputerColor);
    	myRightPanel.add(myComputerScore);
    	
    	myMessageField = new TextArea(5, 50);
    	JScrollPane scrollPane = new JScrollPane(myMessageField);
    	scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	myMessageField.setEditable(false);
    	myStartGameButton = new Button("Start Game");
    	
    	
    	myBottomPanel = new Panel(new FlowLayout());
    	myBottomPanel.add(myMessageField);
    	myBottomPanel.add(myStartGameButton);
    	myBottomPanel.setSize(525, 200);
    	myBottomPanel.setLocation(100, 575);
    	
    	frame.add(myLeftPanel);
    	frame.add(myRightPanel);
    	frame.add(myBoardPanel);
    	frame.add(myBottomPanel);
    
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.associateListeners();
    }
    
    /**
     * Associates each component's listener with the controller
     * and the correct method to invoke when triggered.
     *
     * <pre>
     * pre:  the controller class has be instantiated
     * post: all listeners have been associated to the controller
     *       and the method it must invoke
     * </pre>
     */
    public void associateListeners()
    {
        Class<? extends Controller> controllerClass;
        Method startGameMethod,
        		placePieceMethod;
        
        Class<?>[] classArgs;

        controllerClass = myController.getClass();
        
        startGameMethod = null;
        placePieceMethod = null;
        classArgs = new Class[1];
        
        try
        {
           classArgs[0] = Class.forName("java.lang.Integer");
        }
        catch(ClassNotFoundException e)
        {
           String error;
           error = e.toString();
           System.out.println(error);
        }
        try
        {
           startGameMethod = controllerClass.getMethod("startGame",(Class<?>[])null);   
           placePieceMethod = controllerClass.getMethod("placePiece",classArgs);
           
        }
        catch(NoSuchMethodException exception)
        {
           String error;

           error = exception.toString();
           System.out.println(error);
        }
        catch(SecurityException exception)
        {
           String error;

           error = exception.toString();
           System.out.println(error);
        }
        
        myStartGameListener = new ButtonListener(myController, startGameMethod, null);
        myStartGameButton.addMouseListener(myStartGameListener);
        
        Integer[] args;
        
        //loop through entire board
        //for every row in a column, assign it to a column
        for(int i=0; i<myNumColumns; i++)
        {
        	args = new Integer[1];
        	args[0] = new Integer(i);
        	myColumnListener[i] = new ButtonListener(myController, placePieceMethod, args);
        	for(int j=0; j<myNumRows; j++)
			{
        		myBoard[j][i].addMouseListener(myColumnListener[i]);
			}
        }
    }

	/**
	* Method to assign user input to variables
	* @author Madison Gipson */
	public void getInfo() 
	{
		myName = JOptionPane.showInputDialog("What is your username?");
		myPieceTypeString = (String) JOptionPane.showInputDialog(null, "Choose your piece color.",
		        "Piece Color Input", JOptionPane.QUESTION_MESSAGE, null, myPieceTypes, myPieceTypes[0]);
		otherPieceTypeString = (String) JOptionPane.showInputDialog(null, "Choose opponent's piece color.",
				"Piece Color Input", JOptionPane.QUESTION_MESSAGE, null, myPieceTypes, myPieceTypes[1]);		
	}
    
	/**
	* Method to update score labels based on updated scores from Controller.
	* @author Madison Gipson */
	public void updateScoreLabels()
	{
		myPlayerScore.setText("Score: "+ myController.getMyPlayerScore());
		myComputerScore.setText("Score: "+ myController.getComputerPlayerScore());
	}
	
	/**
	* Method to update the message field with whatever message.
	* @author Madison Gipson */
	public void updateMessageField(String string)
	{
		myMessageField.setText(string + newline);
	}
	
	/**
	* Method to update the name label based on updated name 
	* from Controller- changes when name is invalid.
	* @author Madison Gipson */
	public void updateNameLabel()
	{
		myPlayerName.setText("Name: "+myController.getMyPlayerName());
	}
	
	/**
	* Switches image at each spot in board (canvas) to match
	* the color of the piece currently being placed.
	* @author Madison Gipson */
    public void switchImage(int col, int row)
    {
    	if(myController.getPieceType() == PieceType.RED)
    	{
    		myBoard[row][col].setImage(myRedCircle);
    	}
    	else if(myController.getPieceType() == PieceType.BLACK)
    	{
    		myBoard[row][col].setImage(myBlackCircle);
    	}
    	else if(myController.getPieceType() == PieceType.GREEN)
    	{
    		myBoard[row][col].setImage(myGreenCircle);
    	}
    	else if(myController.getPieceType() == PieceType.YELLOW)
    	{
    		myBoard[row][col].setImage(myYellowCircle);
    	}    	
    }
    
    /**
	* Highlights the entire win length.
	* @author Madison Gipson */
    public void highlightWin(Point winBegin, Point winEnd, Point secondPoint, Point thirdPoint) 
    {
    	myBoard[winBegin.y][winBegin.x].setBackground(Color.BLUE);
    	myBoard[winEnd.y][winEnd.x].setBackground(Color.BLUE);
    	myBoard[secondPoint.y][secondPoint.x].setBackground(Color.BLUE);
    	myBoard[thirdPoint.y][thirdPoint.x].setBackground(Color.BLUE);
    }
   
    public void setMyName(String string)
    {
    	myName = string;
    }
    
    public String getMyName()
	{
		return myName; 
	}
    
	public PieceType getMyPieceType()
	{
		myPieceType = PieceType.valueOf(myPieceTypeString);
		return myPieceType;
	}
	
	public PieceType getMyOtherType()
	{
		otherPieceType = PieceType.valueOf(otherPieceTypeString);
		return otherPieceType;
	}
	
	/**
	* Assigns all of the images and backgrounds to be blank again,
	* just like at the beginning.
	* @author Madison Gipson */
	public void resetBoard()
	{
		for(int i=myNumRows-1; i > -1; i--)
    	{
        	for(int j=0; j < myNumColumns; j++) 
        	{
        		 myBoard[i][j].setImage(myBlankImage);
        		 myBoard[i][j].setBackground(null);
        	}
    	}
	}
}