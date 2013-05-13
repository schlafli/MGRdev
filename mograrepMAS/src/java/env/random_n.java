// Internal action code for project mograrepMAS

package env;

import java.util.Random;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class random_n extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
    	ListTerm in = (ListTerm)args[0];
    	int count = (int) ((NumberTerm)args[1]).solve();
    	
    	ListTermImpl a = new ListTermImpl();
    	ListTermImpl old = new ListTermImpl();
    	
    	Random r = new Random();
    	
    	for(Term t: in)
    	{
    		old.add(t);
    	}
    	
    	if(count>old.size())
    		count = old.size();
    	
    	while(count>0)
    	{
    		a.add(old.remove(r.nextInt(old.size())));
    		count--;
    	}
    	
    	un.unifies(a, args[2]);
    	
    	
        // everything ok, so returns true
        return true;
    }
}
