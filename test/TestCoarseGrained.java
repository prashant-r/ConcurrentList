import ex3.CoarseGrained;
import junit.framework.TestCase;

public class TestCoarseGrained extends TestCase {
	
	CoarseGrained coarseGrained;
	
	protected void setUp(){
		coarseGrained = new CoarseGrained();
	}

	public void testInsert(){
	  assertTrue(coarseGrained.insert(3));
	  assertTrue(coarseGrained.insert(4));
	  assertTrue(coarseGrained.insert(5));
	  assertTrue(coarseGrained.contain(3));
	}
	public void testRemove(){
		
		assertTrue(coarseGrained.insert(3));
		assertTrue(coarseGrained.insert(4));
		assertTrue(coarseGrained.insert(5));
		assertTrue(coarseGrained.remove(3));
		assertTrue(coarseGrained.remove(4));
		assertTrue(coarseGrained.remove(5));
		
	}
	public void testContains(){
		  assertTrue(coarseGrained.insert(3));
		  assertTrue(coarseGrained.insert(4));
		  assertTrue(coarseGrained.insert(5));
		  assertTrue(coarseGrained.contain(3));
		  assertTrue(coarseGrained.contain(4));
		  assertTrue(coarseGrained.contain(5));
	}
}
