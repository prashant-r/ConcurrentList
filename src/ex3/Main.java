package ex3;

public class Main {
	
	public static void main(String args[])
	{
		Integer noThreads = null;
		Integer updateRatio = null;
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
		        	schemeToTest = args[i++];
		            break;
		        default:
		            break;
		        }
		    }
		    System.out.println(noThreads);
		    System.out.println(updateRatio);
		    System.out.println(initSize);
		    System.out.println(duration);
		    System.out.println(schemeToTest);
	}

	public static boolean validateUpdateRatio(int updateRatio)
	{
		return updateRatio>=0 && updateRatio <=100;
	}

}

