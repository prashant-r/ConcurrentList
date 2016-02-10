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
	/*
	 * Atomic integers to collect statistics
	 */
	public static final AtomicInteger insertSuccess  = new AtomicInteger(0);
	public static final AtomicInteger insertFailure  = new AtomicInteger(0);
	public static final AtomicInteger removeSuccess  = new AtomicInteger(0);
	public static final AtomicInteger removeFailure  = new AtomicInteger(0);
	public static final AtomicInteger containSuccess = new AtomicInteger(0);
	public static final AtomicInteger containFailure = new AtomicInteger(0);
	/*
	 * initializes variables 
	 * and sets up the list to contain the prerequisite 
	 * no. of initial elements specified by the user. 
	 * element keys to be inserted are selected randomly.
	 */
	public static void setUp(String schemeToTest, int initSize)
	{
		IntSetFactory intSetFactory = new IntSetFactory();
		Benchmark.intSet = intSetFactory.getIntSet(schemeToTest);
		Benchmark.range = initSize*2;
		Random rand = new Random();
		int counter = 0;
		int keyToAdd;
		while(counter < initSize)
		{
			keyToAdd = rand.nextInt(2*initSize);
			if(!Benchmark.intSet.contain(keyToAdd))
				{
					Benchmark.intSet.insert(keyToAdd);
					++counter;
				}
		}	
	}
	
	Benchmark( int noThreads, int updateRatio, int duration)
	{
		this.noThreads = noThreads;
		this.updateRatio = updateRatio;
		this.duration = duration;
		updateOps = 0;
		totalOps = 0;
	}
	/*
	 * Function called my main thead to start benchmarking 
	 * process that spawns the given no. of BenchMarkThreads
	 */
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
			  taskExecutor.awaitTermination(2000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	/*
	 * Decides whether next chosen operation is an update or not
	 * by minimizing the error that's defined by currentRatio - updateRatio.
	 */
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
	/*
	 * Prints statistics to console for this run
	 */
	public void printStatistics()
	{
		System.out.println("***************");
		System.out.println("No. of insert ops " + insertOps);
		System.out.println("No. of contain ops " + (totalOps-updateOps));
		System.out.println("No. of remove ops " + (updateOps- insertOps));
		System.out.println("No. of update ops " + updateOps);
		System.out.println("No. of total ops " + totalOps);
		System.out.println("No. of insert success "+ Benchmark.insertSuccess.get());
		System.out.println("No. of insert failure "+ Benchmark.insertFailure.get());
		System.out.println("No. of contain success "+ Benchmark.containSuccess.get());
		System.out.println("No. of contain failure "+ Benchmark.containFailure.get());
		System.out.println("No. of remove success "+ Benchmark.removeSuccess.get());
		System.out.println("No. of remove failure "+ Benchmark.removeFailure.get());
		System.out.println("No. of Total success/failure " + (Benchmark.removeFailure.get() + Benchmark.removeSuccess.get() + Benchmark.insertSuccess.get() +Benchmark.insertFailure.get()+ Benchmark.containSuccess.get()+ Benchmark.containFailure.get()));
		System.out.println("Size of the List is " + Benchmark.intSet.size().get());
		System.out.println("Throughput is " + ( (Benchmark.removeFailure.get() + Benchmark.removeSuccess.get() + Benchmark.insertSuccess.get() +Benchmark.insertFailure.get()+ Benchmark.containSuccess.get()+ Benchmark.containFailure.get())/5));
		System.out.println("***************");
	}
}
