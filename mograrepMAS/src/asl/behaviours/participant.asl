


!participant_start.


+!participant_start: true
<-
	.my_name(Me);
	.send(matchmaker, tell, can_perform(Me, task))
	.



+cfp([PlanID, TaskName, Plan])[source(A)]

<-
	if(not plan(PlanID, _, _))
	{
		if(A==self)
		{
			.my_name(Me);
			+plan(PlanID, Plan, Me); //This is so we always have the full name of the issuing agent in the plan belief (and not self)
		}else
		{
			+plan(PlanID, Plan, A);
		}
	}
	!!decide_on_participation(PlanID, TaskName);
.




+!decide_on_participation(PlanID, TaskName)
<-
	
	//?plan(PlanID, Plan, _);
	//?get_task(TaskName, Plan, Task);
	
	!decide_yes(PlanID, TaskName);
.

+!decide_yes(PlanID, TaskName)
<-
	?plan(PlanID, _, Sender);
	Price = 100;
	.my_name(Me);
	.send(Sender, tell, propose(PlanID, TaskName, Me, Price ));
	+proposal(PlanID, TaskName, Me, Price);
.

+!remove_cfp(PlanID, TaskName)
<-
	-cfp([PlanID, TaskName|_])[source(_)];
	//-plan(PlanID, _, _);
	
	if(not have_role(PlanID, _) ){
		.count(cfp([PlanID | _]), Total);
		if(Total=0)
		{	
			-plan(PlanID, _, _);
		}	
	}
	
	.


+reject_proposal(PlanID, TaskName)[source(A)]
<-
	?proposal(PlanID, TaskName, Me, Price);
	-reject_proposal(PlanID, TaskName)[source(A)];
	-proposal(PlanID, TaskName, Me, Price);
	!remove_cfp(PlanID, TaskName);
.


+accept_proposal(PlanID, TaskName, Bid)[source(A)]
<-
	-accept_proposal(PlanID, TaskName, Bid)[source(A)];
	+have_role(PlanID, TaskName);
	-cfp([PlanID, TaskName|_])[source(_)];
	.




+!do_task(PlanID, TaskName)[source(_)]:
	have_role(PlanID, TaskName)
<-
	
	-have_role(PlanID, TaskName);
	?plan(PlanID, Plan, Sender);
	?get_task(TaskName, Plan, Task);
	
	
	//TODO: environment.execute task
	ExecTask = Task;
	//
	.my_name(Me);
	.send(Sender, tell, done_task(PlanID, TaskName, ExecTask, Me));
	
	
	.
	
	


	
+!do_task(PlanID, TaskName)[source(Sender)]:
	not have_role(PlanID, TaskName)
	<-
	//.print("Err, I have been told to do a task I haven't agreed on");
	.wait(500);
	!!do_task(PlanID, TaskName);
	.	
	
	
+!remove_proposals(PlanID):
	not proposal(PlanID, _, _, _)
	<-
	true.	

+!remove_proposals(PlanID):
	proposal(PlanID, _, _, _)
	<-
	-proposal(PlanID, _, _, _);
	!remove_proposals(PlanID);
	.


+!post_plan_cleanup(PlanID)
	<-
	!remove_proposals(PlanID);
	-plan(PlanID,_,_);
	.
	
