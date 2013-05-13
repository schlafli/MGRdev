

+can_perform(Agent, Task)[source(A)].
	

+?who_can_do(Task, Agents)
<-
	.findall(Agent, can_perform(Agent, Task)[source(_)], Ag);
	env.random_n(Ag, 10, Agents);
	//.print("MM responding");
	//.print(Agents);
	.