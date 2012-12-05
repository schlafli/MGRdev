package org.mograrep.model.idgs;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;
/**
 * This class is a helper to match chains of IRIs
 *
 * @author Michael
 *
 */
public class IDGMatchHelper {
	/**
	 * Checks the final item of the chain
	 * @param chain The chain of IRIs
	 * @param match The IRI to test for
	 * @return 1 if the final item matches, 0 otherwise
	 */
	public static double matchFinal(List<IRI> chain, IRI match){
		if(chain!=null && chain.size()>0){
			IRI last = chain.get(chain.size()-1);
			if(matchIRIs(last, match)){
				return 1;
			}
		}
		return 0;
		
	}
	
	
	public static boolean matchIRIs(IRI first, IRI second)
	{
		return first.toQuotedString().equals(second.toQuotedString());
	}
	
	/**
	 * Checks to see if the final item in sourceChain, is one of the IRIs in finalMatch
	 * @param sourceChain The chain of IRIs
	 * @param finalMatch The set/list of IRIs to test for
	 * @return 1 if final IRI in the source chain is in finalMatch
	 */
	public static double matchEndMultiple(List<IRI> sourceChain, List<IRI> finalMatch)
	{
		if(sourceChain.size()<1){
			return 0;
		}
		IRI last = sourceChain.get(sourceChain.size());
		
		for( IRI i: finalMatch)
		{
			if(matchIRIs(i, last))
			{
				return 1;
			}
		}
		return 0;
		
	}
	/**
	 * Test if the last n=matchChain.Size() elements of the SourceChain equal the elements in the matchChain. Also java really needs anonymous functions
	 * @param sourceChain The source chain of IRIs
	 * @param matchChain The chain of IRIs to test
	 * @return 1 if the last n elements of sourceChain equal the elements in matchChain
	 */
	public static double matchEndChain(List<IRI> sourceChain, List<IRI> matchChain)
	{
		if(sourceChain.size()<matchChain.size())
		{
			//System.err.println("cannot match IRI chains, match target list larget than source");
			return 0;
		}
		int offset = matchChain.size()-sourceChain.size();
		for(int i=0;i<matchChain.size();i++)
		{
			IRI match = matchChain.get(i);
			if(!matchIRIs(match, sourceChain.get(i+offset)))
			{
				return 0;
			}
			
		}
		return 1;
		
	}
}
