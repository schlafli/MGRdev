// Agent sample_agent in project mograrepMAS

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : 
true 
<- 
.print("hello world.");
env.matchmaker("register");

.
