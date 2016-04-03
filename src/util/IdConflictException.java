package util;

public class IdConflictException extends Exception {
	//Parameterless Constructor
    public IdConflictException() {}

    //Constructor that accepts a message
    public IdConflictException(String message)
    {
       super(message);
    }
}
