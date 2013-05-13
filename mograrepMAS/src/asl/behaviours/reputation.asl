

//TODO: load preferences
//TODO: changing preferences per tick

+!r_calc_ag_perf(Agent, ATask, PTask)
<-
	PTask = ATask;
.


+!r_eval_ag_perf(Agent, ATask, PTask, Result)
<-
	Result = 1;
	.



+!r_calc_eval_ag(Agent, Task, Result)
<-
	!r_calc_ag_perf(Agent, Task, PTask);
	!r_eval_ag_perf(Agent, Task, PTask, Result);
.


+!r_update_model([]).
+!r_update_model([[Agent,ATask,PTask|Other]|Evals])	
	<-
	!r_update_model(Evals);
	.