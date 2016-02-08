package ex3;

public class BenchMarkThread implements Runnable{
	
	private String command;
	private int key;
	public BenchMarkThread(String command, int key)
	{
		this.command = command;
		this.key = key;
	}	
	@Override
	public void run() {
		boolean result = false;
		if(command.equalsIgnoreCase("CONTAIN")){
			result = Benchmark.intSet.contain(key);
			if(result)
				Benchmark.containSuccess.incrementAndGet();
			else
				Benchmark.containFailure.incrementAndGet();
			}
		else if(command.equalsIgnoreCase("REMOVE")){
			result = Benchmark.intSet.remove(key);
			if(result)
				Benchmark.removeSuccess.incrementAndGet();
			else
				Benchmark.removeFailure.incrementAndGet();
		}
		else if(command.equalsIgnoreCase("INSERT")){
			result = Benchmark.intSet.insert(key);
			if(result)
				Benchmark.insertSuccess.incrementAndGet();
			else
				Benchmark.insertFailure.incrementAndGet();
			}
		else
			System.out.println("Command is non-compliant. Investigate.." + command);
	}
}
