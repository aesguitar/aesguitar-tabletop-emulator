package util;

public class Math {

	public static int abs(int val)
	{
		if(val < 0)
			return -val;
		return val;
	}

	public static long abs(long val)
	{
		if(val < 0)
			return -val;
		return val;
	}

	public static short abs(short val)
	{
		if(val < 0)
			return (short) -val;
		return val;
	}
	public static float abs(float val)
	{
		if(val < 0)
			return -val;
		return val;
	}
	
	public static double abs(double val)
	{
		if(val < 0)
			return -val;
		return val;
	}
	
	public static int sum(int[] vals)
	{
		int sum = 0;
		for(int i = 0; i < vals.length; i++)
		{
			sum += vals[i];
		}
		return sum;
	}
	
	public static double sum(double[] vals)
	{
		double sum = 0;
		for(int i = 0; i < vals.length; i++)
			sum+=vals[i];
		
		return sum;
	}
	
	public static double average(int[] vals)
	{
		return sum(vals)*1.0/vals.length;
	}
	
	public static double average(double[] vals)
	{
		return sum(vals)*1.0/vals.length;
	}
	
}
