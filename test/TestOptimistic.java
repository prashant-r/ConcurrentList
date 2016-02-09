import ex3.Optimistic;
import junit.framework.TestCase;


public class TestOptimistic extends TestCase {
	

	Optimistic optimistic;
	
	protected void setUp(){
		optimistic = new Optimistic();
	}

	public void testInsert(){
	  assertTrue(optimistic.insert(3));
	  assertTrue(optimistic.insert(4));
	  assertTrue(optimistic.insert(5));
	  assertTrue(optimistic.contain(3));
	  assertTrue(optimistic.size().get() == optimistic.getSizeByIterating());
	}
	public void testRemove(){
		
		assertTrue(optimistic.insert(3));
		assertTrue(optimistic.insert(4));
		assertTrue(optimistic.insert(5));
		assertTrue(optimistic.remove(3));
		assertTrue(optimistic.remove(4));
		assertTrue(optimistic.remove(5));
		assertTrue(optimistic.size().get() == optimistic.getSizeByIterating());
		
	}
	public void testContains(){
		  assertTrue(optimistic.insert(3));
		  assertTrue(optimistic.insert(4));
		  assertTrue(optimistic.insert(5));
		  assertTrue(optimistic.contain(3));
		  assertTrue(optimistic.contain(4));
		  assertTrue(optimistic.contain(5));
		  assertTrue(optimistic.size().get() == optimistic.getSizeByIterating());
	}

}
