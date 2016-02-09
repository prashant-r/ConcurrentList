import ex3.HandOverHand;
import junit.framework.TestCase;


public class TestHandOverHand extends TestCase {
	

	HandOverHand handOverHand;
	
	protected void setUp(){
		handOverHand = new HandOverHand();
	}

	public void testInsert(){
	  assertTrue(handOverHand.insert(3));
	  assertTrue(handOverHand.insert(4));
	  assertTrue(handOverHand.insert(5));
	  assertTrue(handOverHand.contain(3));
	  assertTrue(handOverHand.size().get() == handOverHand.getSizeByIterating());
	}
	public void testRemove(){
		
		assertTrue(handOverHand.insert(3));
		assertTrue(handOverHand.insert(4));
		assertTrue(handOverHand.insert(5));
		assertTrue(handOverHand.remove(3));
		assertTrue(handOverHand.remove(4));
		assertTrue(handOverHand.remove(5));
		assertTrue(handOverHand.size().get() == handOverHand.getSizeByIterating());	
	}
	public void testContains(){
		  assertTrue(handOverHand.insert(3));
		  assertTrue(handOverHand.insert(4));
		  assertTrue(handOverHand.insert(5));
		  assertTrue(handOverHand.contain(3));
		  assertTrue(handOverHand.contain(4));
		  assertTrue(handOverHand.contain(5));
		  assertTrue(handOverHand.size().get() == handOverHand.getSizeByIterating());
	}

}
