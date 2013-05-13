// Internal action code for project mograrepMAS

package env;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class first_n extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
    	ListTerm in = (ListTerm)args[0];
    	int count = (int) ((NumberTerm)args[1]).solve();
    	
    	ListTermImpl a = new ListTermImpl();
    	
    	for(Term t : in)
    	{
    		if(count<=0)
    			break;
    		a.append(t);
    		count --;
    	}
    	
    	un.unifies(a, args[2]);
    	
    	
        // everything ok, so returns true
        return true;
    }
}
