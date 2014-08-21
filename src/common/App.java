package common;

import seeker.SeekerApp;
import database.DatabaseHelper;

public class App {

	public static void main(String [] args) 
	{

		DatabaseHelper dbConnection = new DatabaseHelper();
		
        SeekerApp seeker = new SeekerApp( dbConnection );
        seeker.init();
        seeker.start();
        
        return;
	}
	
}
