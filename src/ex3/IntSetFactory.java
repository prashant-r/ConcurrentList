
package ex3;

public class IntSetFactory {

	/*
	 * Factory pattern to return type of IntSet
	 */
	public IntSet getIntSet(String intSetType){
		      if(intSetType == null){
		         return null;
		      }		
		      if(intSetType.equalsIgnoreCase("coarse")){
		         return new CoarseGrained();
		         
		      } else if(intSetType.equalsIgnoreCase("hoh")){
		         return new HandOverHand();
		         
		      } else if(intSetType.equalsIgnoreCase("optimistic")){
		         return new Optimistic();
		      }
		      
		      return null;
		   }
}
