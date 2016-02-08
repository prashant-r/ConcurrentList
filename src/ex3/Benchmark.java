package ex3;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Benchmark {
	private int noThreads;
	private int updateRatio;
	private int duration;
	private int updateOps;
	private int totalOps;
	private int insertOps;
	
	private static final String INSERT = "INSERT";
	private static final String CONTAIN = "CONTAIN";
	private static final String REMOVE = "REMOVE";
	
	public static IntSet intSet;
	public static int range;
	
	public static final AtomicInteger insertSuccess  = new AtomicInteger(0);
	public static final AtomicInteger insertFailure  = new AtomicInteger(0);
	public static final AtomicInteger removeSuccess  = new AtomicInteger(0);
	public static final AtomicInteger removeFailure  = new AtomicInteger(0);
	public static final AtomicInteger containSuccess = new AtomicInteger(0);
	public static final AtomicInteger containFailure = new AtomicInteger(0);
	
	public static void setUp(String schemeToTest, int initSize)
	{
		IntSetFactory intSetFactory = new IntSetFactory();
		Benchmark.intSet = intSetFactory.getIntSet(schemeToTest);
		Benchmark.range = initSize*2;
		for(int a =0 ; a< initSize; a++)
			Benchmark.intSet.insert(a);
	}
	
	Benchmark( int noThreads, int updateRatio, int duration)
	{
		this.noThreads = noThreads;
		this.updateRatio = updateRatio;
		this.duration = duration;
		updateOps = 0;
		totalOps = 0;
	}
	
	public void startBenchmarking()
	{	
		ExecutorService taskExecutor = Executors.newFixedThreadPool(noThreads);
		long startTime = System.currentTimeMillis(); //fetch starting time
		while((System.currentTimeMillis()-startTime)<(duration))
		{
			Random rand = new Random();
			int key = rand.nextInt(Benchmark.range);
			if(updateOrNot())
			{				
				if(rand.nextBoolean())
				{
					insertOps+=1;
					taskExecutor.execute(new BenchMarkThread(INSERT,key));
				}
				else
					taskExecutor.execute(new BenchMarkThread(REMOVE,key));
				updateOps+=1;
				totalOps+=1;
			}
			else
			{
				taskExecutor.execute(new BenchMarkThread(CONTAIN,key));
				totalOps+=1;
			}
		}
		taskExecutor.shutdownNow();
		try {
			  taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
			}
	}

	
	public boolean updateOrNot()
	{
		double updateFrac = (double)updateRatio/100;
		double updateYesFrac = (double)(updateOps+1)/(totalOps+1);
		double updateNoFrac = (double)(updateOps)/(totalOps+1);
		if(Math.abs(updateFrac-updateYesFrac)<= Math.abs(updateFrac-updateNoFrac))
			return true;
		else
			return false;
	}
	
	public void printStatistics()
	{
		System.out.println("***************");
		System.out.println("No. of insert ops " + insertOps);
		System.out.println("No. of contain ops " + (totalOps-updateOps));
		System.out.println("No. of remove ops " + (updateOps- insertOps));
		System.out.println("No. of update ops " + updateOps);
		System.out.println("No. of total ops " + totalOps);
		System.out.println("------------");
		System.out.println("No. of insert success "+ Benchmark.insertSuccess);
		System.out.println("No. of insert failure "+ Benchmark.insertFailure);
		System.out.println("No. of contain success "+ Benchmark.containSuccess);
		System.out.println("No. of contain failure "+ Benchmark.containFailure);
		System.out.println("No. of remove success "+ Benchmark.removeSuccess);
		System.out.println("No. of remove failure "+ Benchmark.removeFailure);
		System.out.println("****************");
	}
}
