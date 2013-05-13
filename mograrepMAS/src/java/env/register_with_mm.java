// Internal action code for project mograrepMAS

package env;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class register_with_mm extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'env.register_with_mm'");
        if (true) { // just to show how to throw another kind of exception
            throw new JasonException("not implemented!");
        }
        
        // everything ok, so returns true
        return true;
    }
}
