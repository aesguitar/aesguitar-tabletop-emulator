package util;

public class Timer {
	
	private long startTime = 0, endTime = 0;
	private long ds = 0;
	
	public Timer(){}
	
	public long start()
	{
		startTime = System.currentTimeMillis();
		return startTime;
	}
	
	public long end()
	{
		endTime = System.currentTimeMillis();
		return endTime;
	}
	
	public long difference()
	{
		return endTime - startTime;
	}
	
	public long lapTime()
	{
		return System.currentTimeMillis()-startTime;
	}
	
	public void delay(long millis)
	{
		ds = System.currentTimeMillis();
		while(System.currentTimeMillis()-ds < millis)
		{}
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
