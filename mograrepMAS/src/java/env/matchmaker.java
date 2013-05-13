// Internal action code for project mograrepMAS

package env;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class matchmaker extends DefaultInternalAction {
	
	/*	Matchmaker actions:
	 * 	start (XMLFile)
	 * 	register (Agent, Action(OWLClass))
	 * 	query(
	 * 
	 */
	
	
	/*
	 * I should decide on the information that is passed to agents	
	 * 
	 * 
	 */
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
    	if(args.length>0)
    	{
    		StringTerm st = (StringTerm)args[0];

            ts.getAg().getLogger().info("executing internal action 'env.matchmaker' function '"+st.getString()+"'");
    	}else
    	{

            ts.getAg().getLogger().info("executing internal action 'env.matchmaker'");
    		
    	}
        if (true) { // just to show how to throw another kind of exception
            throw new JasonException("not implemented!");
        }
        
        // everything ok, so returns true
        return true;
    }
    
    
    
    
}
