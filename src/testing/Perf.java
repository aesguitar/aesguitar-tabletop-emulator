package testing;

import java.util.Random;

import util.Timer;

public class Perf {
	public static void main(String[] args)
	{
		Timer t = new Timer();
		Random r = new Random();
		int it = 5;
		int x, y;
		int[] avg1 = new int[it], avg2 = new int[it];

		for(int j = 0; j < it; j++)
		{
			t.start();
			for(int i = 0; i < 1000000; i++)
			{
				x = r.nextInt(21)-10;
				y = Math.abs(x);
			}
			t.end();
			
			avg1[j] = (int) t.difference();

			//System.out.println("Native method: " + t.difference());

			t.start();
			for(int i = 0; i < 1000000; i++)
			{
				x = r.nextInt(21)-10;
				y = util.Math.abs(x);
			}
			t.end();
			//System.out.println("Non-native method: " + t.difference());
			
			avg2[j] = (int) t.difference();
		}
		
		System.out.println("java.Math:\t" + util.Math.average(avg1));
		System.out.println("util.Math:\t" + util.Math.average(avg2));

	}

}
