package ex3;

public class Main {
	
	public static final int THREAD_INIT = 0;
	public static void main(String args[])
	{
		Integer noThreads = null;
		Integer updateRatio = 50;
		Integer initSize = null;
		Integer duration = null;
		String 	schemeToTest = null;
		    for (int i = 0; i < args.length; i++) {
		        switch (args[i]) {
		        case "-t":
		        	noThreads = Integer.parseInt(args[++i]);
		            break;
		        case "-u":
		        	updateRatio = Integer.parseInt(args[++i]);
		            break;
		        case "-i":
		        	initSize = Integer.parseInt(args[++i]);
		            break;
		        case "-d":
		        	duration = Integer.parseInt(args[++i]);
		            break;
		        case "-b":
		        	schemeToTest = args[++i];
		            break;
		        default:
		            break;
		        }
		    }   
		    
		    if(!validateNoOfThreads(noThreads))
        	{
        		System.out.println("No. of threads must be [0,INT_MAX] | Your input was " + noThreads);
        		System.exit(-1);
        	}
			if(!validateUpdateRatio(updateRatio))
        	{
        		System.out.println("Update ratio must be [0,100] | Your input was " + updateRatio);
        		System.exit(-1);
        	}
			if(!validateInitSize(initSize))
        	{
        		System.out.println("InitSize must be [0,INT_MAX/2]| Your input was " + initSize);
        		System.exit(-1);
        	}
			if(!validateDuration(duration))
        	{
        		System.out.println("Duration must be [0,INT_MAX] | Your input was " + duration);
        		System.exit(-1);
        	}
			if(!validateSchemeToTest(schemeToTest)){
        		System.out.println("Scheme to test must be either {coarse, hoh, optimistic}| Your input was " + schemeToTest);
        		System.exit(-1);
        	}
			
			Benchmark.setUp(schemeToTest, initSize);
			Benchmark benchmark = new Benchmark(noThreads, updateRatio, duration);
			benchmark.startBenchmarking();
			benchmark.printStatistics();
			System.exit(1);
			
	}

	public static boolean validateDuration(Integer duration)
	{
		if(duration == null)
			return false;
		if(duration <1 || duration> Integer.MAX_VALUE)
				return false;
		return true;
	}
	
	public static boolean validateNoOfThreads(Integer noThreads)
	{
		if(noThreads == null)
			return false;
		if(noThreads <1 || noThreads> Integer.MAX_VALUE)
				return false;
		return true;
	}
	public static boolean validateInitSize(Integer initSize)
	{
		if(initSize == null)
			return false;
		if(initSize <0 || initSize> Integer.MAX_VALUE/2)
				return false;
		return true;
	}
	public static boolean validateSchemeToTest(String s)
	{
		if(s.trim().equalsIgnoreCase("coarse") ||  s.trim().equalsIgnoreCase("hoh") || s.trim().equalsIgnoreCase("optimistic"))
				return true;
		return false;
	}
	
	public static boolean validateUpdateRatio(Integer updateRatio)
	{
		if(updateRatio != null)
			if(updateRatio>=0 && updateRatio <=100)
				return true;
		return false;
	}

}

