
distinct([],[]).
distinct([H|T],C) :- .member(H,T) & distinct(T,C).
distinct([H|T],[H|C]) :- distinct(T,C).


intersection([X|Xt],Y,[X|Z]):- .member(X,Y) & intersection(Xt,Y,Z). 
intersection([X|Xt],Y,Z):- not .member(X,Y) & intersection(Xt,Y,Z). 
intersection([], Y, []).

reverse([X|Y],Z,W) :- reverse(Y,[X|Z],W).
reverse([],X,X).


sum([H|T], N)
:- sum(T, Y) &
	N = (H + Y). 
sum([], 0).


get_task(TaskName, [[TaskName,Task]|T],  Task).
get_task(TaskName, [H|T], Task):- get_task(TaskName, T, Task).



+?money(X):
	not money(_)
	<-
	+money(0);
	X=0;
	.

+?money(X):
	money(X)
	<-
	true.

@money_sub[atomic]	
+!money_sub(Amount)
<-
	?money(X);
	-+money(X-Amount);
	.
@money_add[atomic]	
+!money_add(Amount)
<-
	?money(X);
	-+money(X+Amount);
	.



+!send_money(Agent, Amount)
<-
	!money_sub(Amount);
	.send(Agent, achieve, receive_money(Amount));
	.

+!receive_money(Amount)[source(Sender)]
<-
	!money_add(Amount);
	.	


