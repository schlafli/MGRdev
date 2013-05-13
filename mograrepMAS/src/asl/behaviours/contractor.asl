/*
 * This is the contractor behaviour
 * 
 * 
 * 
 * 
 */



money(10000).

planID(0). //initial planID

running_plans(0). //number of currently running plans

all_team_members_responded(PlanID)
:- 	.count(aok(PlanID,_),OK) &
	.count(problems(PlanID,_,_),Prob) &
	team_ass(PlanID, Team) &
	distinct(Team, Individuals) &
	.length(Individuals, Ln) &
	Ln = (OK+Prob).



all_proposals_received(PlanID):-
	.count(propose(PlanID,_,_,_), NO) &           // number of proposes received
    .count(refuse(PlanID,_,_), NR) &
     total_msgs(PlanID, Tot)  &
     Tot = (NO+NR).


!contractor_start.

+!contractor_start: //Do the initial registration
	true
	<-
	.my_name(Me);
	.send(director, tell, plays(Me, contractor));
	
	//************
	//FILE PREFIX
	//************
	
	//env.start_log(def);
	
	//!!start_contract;
	//?plays(directory, Dir); //Find out who does the contractor directory
	//.my_name(Me);
	//.send(Dir, tell, plays(contractor, Me));//advertise self as being contractor
	.



@ipidc [atomic]
+?newPlanID(ID): true
	<-
	?planID(N);
	-planID(N);
	ID = N + 1;
	+planID(ID);
	.
	

+!start_contract:
	true
	<-
	//select a random plan to perform
	!add_plan("plan1", [
		["task_name_1", task],
		["task_name_2", task],
		["task_name_3", task],
		["task_name_4", task]
		
	])
.

+!start_step(Step)[source(A)]
<-
	+current_step(Step);
	.

+!add_plan(PlanName, Plan)[source(A)]
	<-
	?newPlanID(NewID);
	.my_name(MyName);
	.concat("", PlanName,"_", MyName, "_", NewID, PlanID);
	//now PlanID has the complete name
	
	+plan_name(PlanID, PlanName);
	+tcnp_stage(PlanID, matchmaker);
	//+plan_steps(PlanID, 0);
	
	//?getTasks(Plan, Tasks);
	+plan(PlanID, Plan);
	
	?current_step(Step);
	+step_plan(Step, PlanID);
	
	//matchmaker
	
	!find_participants(PlanID, Plan);
	
	.




//TODO: Possible bottleneck
+!query_mm(Task, Agents)
	<-
	.send(matchmaker, askOne, who_can_do(Task, Agents),who_can_do(Task, Agents));
	.

+!find_participants(PlanID, [])
	<-
	.findall(Count, agents_for_task(PlanID,_,_,Count), Total);
	?sum(Total, N);
	+total_msgs(PlanID, N);
	-tcnp_stage(PlanID, matchmaker);
	+tcnp_stage(PlanID, started);
	
	!!start_contracting(PlanID);
	.


+!find_participants(PlanID, [[TaskName, Task]|T])
	<-
	!query_mm(Task, Agents);
	.length(Agents, N);
	if(N==0)
	{
		.print("cannot find participants to do task");
		//cleanup and quit since this means we have a plan in rotation that has no agents to do it (at the moment)
	}else{
		+agents_for_task(PlanID, TaskName, Agents, N);
		!find_participants(PlanID, T);	
	}
	.
	


+!start_contracting(PlanID):
	tcnp_stage(PlanID, started)
	<-
	-tcnp_stage(PlanID, started);
	+tcnp_stage(PlanID, propose);
	?plan(PlanID, Plan);
	!advertise_roles(PlanID, Plan);
	.



+!advertise_roles(PlanID, [])
<-
-tcnp_stage(PlanID, propose);
+tcnp_stage(PlanID, contract)
.


+!advertise_roles(PlanID, [[TaskName, _]|T]):
	tcnp_stage(PlanID, propose)
	<-
	?plan(PlanID, Plan);
	?agents_for_task(PlanID, TaskName, Agents, N);
	!issue_cfp(Agents, [PlanID, TaskName, Plan]);
	!advertise_roles(PlanID, T);
	.



+!issue_cfp(Agents, Info)
	<-
	.send(Agents, tell, cfp(Info));
	.

+propose(PlanID, TaskName, Name, Bid)
	<-
	if(debug){
		.print("Agent: {",Name ,"} can do task {", RoleID, "} in plan {", PlanID,"} bid {",Bid,"}");
	}
	
	if(all_proposals_received(PlanID)){
		!!contract_roles(PlanID);
	}
	.

	
+refuse(PlanID, TaskName, Name)
	<-
	if(debug){
		.print("Agent: {",Name ,"} can't do task {", RoleID, "} in plan {", PlanID,"}");
	}
	
	if(all_proposals_received(PlanID)){
		!!contract_roles(PlanID);
	}
	.


@lc1[atomic]
+!contract_roles(PlanID):
	tcnp_stage(PlanID, contract)
	<-
	-tcnp_stage(PlanID, contract);
	+tcnp_stage(PlanID, selection);
	-total_msgs(PlanID, _);
	?plan(PlanID, Plan);
	.findall(TName, propose(PlanID, TName,_,_), TaskProposals);
	?distinct(TaskProposals, Dis);
	.length(Dis, Len);
	if(.length(Plan, Len))
	{
		!!select_agents(PlanID);
	}else
	{
		.print("Cannot do plan. No proposals for some tasks");
		!reject_all(PlanID);
	}
	
	.


+!contract_roles(PlanID).
//	<-
//	.print("fallhrough");
//	.



+!send_reject(Agent, PlanID, TaskName)
	<-
	.send(Agent, tell, reject_proposal(PlanID, TaskName));
	.



+!reject_all(PlanID):
	not propose(PlanID,_,_,_)
	<-
	if(debug){
		.print("Done rejecting");
	}
	.

+!reject_all(PlanID):
	propose(PlanID, TaskName, Agent, Bid)
	<-
	-propose(PlanID, TaskName, Agent, Bid)[source(_)];
	!!send_reject(Agent, PlanID, TaskName); //Send a reject whenever
	!reject_all(PlanID);
	.


+!remove_mm_info(PlanID):
	agents_for_task(PlanID, TaskName, _,_)
	<-
	-agents_for_task(PlanID, TaskName, _,_)[source(_)];
	!remove_mm_info(PlanID);
	.
	
+!remove_mm_info(PlanID):
	not agents_for_task(PlanID, _, _,_)
	<-
	true
	.
	

+!select_agents(PlanID):
	tcnp_stage(PlanID, selection)
	<-
	//?plan(PlanID, Plan);
	!get_team(PlanID, TeamA, Success);
	if(Success)
	{
		!convert_team_proposals(PlanID, TeamA);		
		!remove_refusals(PlanID);
		!reject_all(PlanID);
		!remove_mm_info(PlanID);
		
		-tcnp_stage(PlanID, selection);
		+tcnp_stage(PlanID, verification);
		!!verify_team(PlanID);
	}else
	{
		!remove_refusals(PlanID);
		!reject_all(PlanID);
		!remove_mm_info(PlanID);
		.print("Failed get_team");
		
		//failure intend
	}
			
	
	.


+!convert_team_proposals(PlanID, []).
+!convert_team_proposals(PlanID, [[TaskName, Agent]|Rest])
	<-
	?propose(PlanID, TaskName, Agent, Bid);
	-propose(PlanID, TaskName, Agent, Bid)[source(_)];
	+team_assignment(PlanID, TaskName, Agent, Bid);
	!convert_team_proposals(PlanID, Rest);
	.



+!notify_others.



+!remove_refusals(PlanID):
	refuse(PlanID, TName, Ag)
<-
	-refuse(PlanID, TName, Ag);
	!remove_refusals(PlanID);
	.
	
+!remove_refusals(PlanID):
	not refuse(PlanID,_,_)
	<-
	true
	.

+!get_team(PlanID, TeamAssignment, Success)
<-
	?plan(PlanID, Plan);
	!rec_gt(PlanID, Plan, TeamAssignment);
	Success = true;
	.

	


+!rec_gt(PlanID, [], []).
+!rec_gt(PlanID, [[TaskName|_]|Rest], [[TaskName,Agent]|Ta])
<-
	!rec_gt(PlanID, Rest, Ta);
	!get_place(PlanID, TaskName, Agent);
.



+!get_place(PlanID, TaskName, Agent)
<-
	?plan(PlanID, Plan);
	?get_task(TaskName, Plan, Task);
	.findall([Ag, Bid], propose(PlanID, TaskName, Ag, Bid), AgBids);
	!calculate_agents_rep(AgBids, Task, RepResult);
	!select_agent(RepResult, [Agent,_,_]);
	.



+!select_agent([Bid|[]], Bid).
+!select_agent([[CAg, CBid, CRep]|Rest],BBid)
<-
	!select_agent(Rest, [MAg,MBid,MRep]);
	if(CRep>MRep)
	{
		BBid = [CAg, CBid, CRep];
	}else
	{
		BBid = [MAg, MBid, MRep];
	}
.

+!calculate_agents_rep([],_,[]).
+!calculate_agents_rep([[Agent, Bid]|R], Task, [[Agent, Bid, Rep]|Result])
<-
	!r_calc_eval_ag(Agent, Task, Rep);
	!calculate_agents_rep(R, Task, Result);
.


+!verify_team(PlanID):
	tcnp_stage(PlanID, verification)
<-
	if(debug){
		.print("Verify Team");
	}
	//TODO: Some team verification
	
	
	-tcnp_stage(PlanID, verification);
	+tcnp_stage(PlanID, accept);
	!accept_team_members(PlanID);
	.



+!accept_team_members(PlanID):
	tcnp_stage(PlanID, accept)
<-
	?plan(PlanID, Plan);
	!send_accepts(PlanID, Plan);
	
	-tcnp_stage(PlanID, accept);
	+tcnp_stage(PlanID, done);
	
	if(debug){
		.print("Ready_to_run");
	}
	
	
	-tcnp_stage(PlanID, done); //I am just adding and removing this for logging purposes
	
	!!run_plan(PlanID);
	
	.
	

+!send_accept(Agent, PlanID, TaskName, Bid)
<-
	.send(Agent, tell, accept_proposal(PlanID, TaskName, Bid));
	.
	
	


+!send_accepts(PlanID, []).
+!send_accepts(PlanID, [[TaskName|_]|Rest])
<-
	?team_assignment(PlanID, TaskName, Agent, Bid);
	!send_accept(Agent, PlanID, TaskName, Bid);
	!send_accepts(PlanID, Rest);
	.



+!run_plan(PlanID)
	<-
	?plan(PlanID, Plan);
	+plan_remaining(PlanID, Plan);
	!run_plan_step(PlanID);
	.



+!run_plan_step(PlanID):
	plan_remaining(PlanID, [])
<-
	-plan_remaining(PlanID, _);
	if(debug){
		.print("Done running plan!");
	}
	!!post_plan_actions(PlanID);
	.


+!run_plan_step(PlanID):
	plan_remaining(PlanID, [[TaskName, _]|Rest])
	<-
	-plan_remaining(PlanID,_);
	+plan_remaining(PlanID, Rest);
	?team_assignment(PlanID, TaskName, Agent, _);
	.send(Agent, achieve, do_task(PlanID, TaskName));


	//queue response
	//TODO: possibly add timeout
	.


+done_task(PlanID, TaskName, PTask, Agent)[source(_)]
<-
	-done_task(PlanID, TaskName, PTask, Agent)[source(_)];
	?team_assignment(PlanID, TaskName, Agent, Bid);
	?plan(PlanID, Plan);
	?get_task(TaskName, Plan, ATask);
	+task_performed(PlanID, ATask, PTask, Agent, Bid);
	
	!!run_plan_step(PlanID);
	.


+!post_plan_actions(PlanID)
<-
	!dish_out_cash(PlanID);
	
	//remove team assignments
	
	
	
	
	//-plan(PlanID, _);
	
	?plan_name(PlanID, PlanName);
	?current_step(Step);
	.my_name(Me);
	.send(director, tell, done_plan(Step, PlanName, Me));
	
	.



+!update_reputation_model(PlanID)
	<-
	.findall(A, task_performed(PlanID, _, _, A, _), Agents);
	.my_name(Me);
	.concat(Agents, [Me], T_Agents);
	?distinct(T_Agents, DAg);
	.findall([Agent,ATask,PTask, Bid], task_performed(PlanID, ATask, PTask, Agent, Bid), PlanPerformance);
	!remove_performed(PlanID);
	//.print(DAg);
	.send(DAg, achieve, r_update_model(PlanPerformance));
	.




+!remove_performed(PlanID):
	not task_performed(PlanID,_,_,_,_)
	<-
	true.

+!remove_performed(PlanID):
	task_performed(PlanID,_,_,_,_)
	<-
	-task_performed(PlanID,_,_,_,_);
	!remove_performed(PlanID);
	.



+!remove_team_assignment(PlanID):
	not team_assignment(PlanID, _, _, _)
	<-
	true.
	
+!remove_team_assignment(PlanID):
	team_assignment(PlanID, _, _, _)
	<-
	-team_assignment(PlanID, _, _, _);
	!remove_team_assignment(PlanID);
	.




+!dish_out_cash(PlanID)
	<-
	?plan(PlanID, Plan);
	!doc_plan(PlanID, Plan);
	.

+!doc_plan(PlanID, []).
+!doc_plan(PlanID, [[TaskName|_]|Rest])
	<-
	?team_assignment(PlanID, TaskName, Agent, Bid);
	!send_money(Agent, Bid);
	
	!doc_plan(PlanID, Rest);
	.
	


+!rec_eos(Step):
	step_plan(Step, PlanID)
	<-
	.findall(Ag, team_assignment(PlanID, _, Ag, _), Ags);
	?distinct(Ags, DAg);
	.send(DAg, achieve, post_plan_cleanup(PlanID));
	!update_reputation_model(PlanID);
	!remove_team_assignment(PlanID);
	-plan_name(PlanID, _);
	-plan(PlanID, _);
	-step_plan(Step, PlanID);
	.
	
	
+!rec_eos(Step):
	not step_plan(Step, PlanID)
	<-
	true	
	.
	
	
	
	

+!end_of_step(Step)[source(A)]:
	current_step(Step)
<-
	!rec_eos(Step);
	-current_step(Step);
	.send(director, tell, eos_done(Step));
	.

+!end_of_step(Step)[source(A)]:
	not current_step(Step)
<-
	.print("eos rx twice");
	.
	
	


	
	