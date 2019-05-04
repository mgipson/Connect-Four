package connectmodelview;
public class ConnectFour
{
    // Properties
    private Controller myController;
    
    // Methods
    public static void main(String[] args)
    {
        new ConnectFour();
    }
    
    public ConnectFour()
    {
        setController(new Controller());
    }

	public void setController(Controller controller) 
	{
		myController = controller;
	}

	public Controller getController() {
		return myController;
	}
}