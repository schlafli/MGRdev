// Agent director in project mograrepMAS

/* Initial beliefs and rules */


step(0).
end_step(100).
//debug.

/* Initial goals */

!start.

/* Plans */

+!start : true <- 
	.print("'Olla");
	.at("now +5 seconds", {+!run_round });
	.



@ns1 [atomic]
+?next_step(Step): true
	<-
	?step(N);
	-step(N);
	Step = N + 1;
	+step(Step);
	.
	


+plays(Agent, contractor)[source(A)].
	
+plays(Agent, Role)[source(A)]
<-
	.print("Don't care about role!=contractor");
	.
	
	
+!run_round
	<-
	
	?next_step(Step);
	
	+round_running(Step);
	.print("Step ", Step);
	.findall(X, plays(X, contractor), Ag);
	env.random_n(Ag, 1, Agents);
	.length(Agents, N);
	+step_actions(Step, N);
	//.print(Agents);
	.send(Agents, achieve, start_step(Step));
	.wait(200);
	
	.send(Agents, achieve, add_plan("plan1", [
		["task_name_1", task],
		["task_name_2", task],
		["task_name_3", task],
		["task_name_4", task]
		
	]));
	.


+!countdown(0).
+!countdown(N)
	<-
	.print(N);
	.wait(1000);
	!countdown(N-1)
	.

+!finish_step(Step)
<-
	.findall(A, done_plan(Step, _, A), Agents);
	!remove_step_done_plan(Step);
	if(debug)
	{
		.print("finished step, about to sent continue");
		//!countdown(5);
	}
	.send(Agents, achieve, end_of_step(Step));
	.



+!remove_step_done_plan(Step):
	not done_plan(Step, _, _)
	<-
	true.
	
+!remove_step_done_plan(Step):
	done_plan(Step, PlanName, Agent)
	<-
	-done_plan(Step, PlanName, Agent)[source(_)];
	!remove_step_done_plan(Step);
	.


+done_plan(Step, PlanName, Agent)
<-
	//-done_plan(PlanName)[source(Ag)];
	//.print(Ag, " is done with ", PlanName);
	//step done
	?step(Step);
	?inc_counter(done_plan, Step, X);
	if(step_actions(Step, X))
	{
		-counter(done_plan, Step, _);
		!!finish_step(Step);
	}
	.



@incC2[atomic]
+?inc_counter(Type, Step, Count)
	<-
	if(counter(Type, Step, X))
	{
		-counter(Type, Step, X);
		Count = X+1;
	}else
	{
		Count = 1;
	}
	+counter(Type, Step, Count);
	.



+eos_done(Step)[source(Agent)]
<-
	//.print(Ag, " has completed step ", Step);
	!!eos_done(Step, Agent);
	.


@eosd1[atomic]
+!eos_done(Step, Agent)
<-
	-eos_done(Step)[source(Agent)];
	
	?step(Step);
	?inc_counter(endofstep,Step, X);
	?step_actions(Step, Actions);
	
	if(X==Actions)
	{
		-counter(endofstep, Step, _);
		!!end_round(Step);
	}
	.

@er1[atomic]
+!end_round(Step):
	round_running(Step)
	<-
	-round_running(Step);
	-step_actions(Step, _);
	
	
	if(end_step(Step))
	{
		.print("Experiment Done");
	}else
	{
		if(debug)
		{
			.print("Round ", Step, " done");
			.wait(2000);
		}
		!!run_round;
	}
	
	.	
	

	
	
	