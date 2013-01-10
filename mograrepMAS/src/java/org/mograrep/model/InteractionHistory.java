package org.mograrep.model;

import java.util.ArrayList;
import java.util.HashMap;


import org.javatuples.Pair;
import org.mograrep.kbrep.AgentRef;

public class InteractionHistory {
	
	HashMap<AgentRef, ArrayList<Pair<ContextInformation, ContextInformation>>> performedMap;
	
	public InteractionHistory()
	{
		performedMap = new HashMap<>();
	}
	
	public void addInteraction(AgentRef agent, ContextInformation agreed, ContextInformation performed)
	{
		if(!performedMap.containsKey(agent))
		{
			performedMap.put(agent, new ArrayList<Pair<ContextInformation, ContextInformation>>());
		}
		
		ArrayList<Pair<ContextInformation, ContextInformation>> agentHistory = performedMap.get(agent);
		agentHistory.add(new Pair<ContextInformation, ContextInformation>(agreed, performed));
	}

	public final ArrayList<Pair<ContextInformation, ContextInformation>> getInteractions(AgentRef agent)
	{
		if(performedMap.containsKey(agent))
		{
			return performedMap.get(agent);
		}else
		{
			return null;
		}
	}
}
