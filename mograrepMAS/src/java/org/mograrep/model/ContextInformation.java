package org.mograrep.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.IRI;


public class ContextInformation {

	public IRI getName() {
		return name;
	}

	public IRI getType() {
		return type;
	}

	private IRI name;
	private IRI type;

	protected boolean isAction;

	protected List<ContextInformation> hasContext;


	//ContextInformation hasMinimum;

	protected List<ContextInformation> parents;


	protected TreeSet<ContextData> hasValues;


	public List<ContextInformation> flatten()
	{
		return flatten(false);
	}

	public List<ContextInformation> flatten(boolean withCopy)
	{
		ArrayList<ContextInformation> ret = new ArrayList<>();
		if(this.hasValues())
		{
			if(withCopy)
			{
				ret.add(copyValues());
			}else
			{
				ret.add(this);
			}
		}
		if(hasContext())
		{
			for(ContextInformation child: hasContext)
			{
				ret.addAll(child.flatten());
			}
		}
		return ret;
	}

	public ContextInformation copyValues()
	{
		ContextInformation ret = new ContextInformation(name, type);
		ret.isAction = isAction();
		ret.addParents(parents);

		if(this.hasValues())
		{
			ret.checkHasValues();
			for(ContextData cdv:hasValues)
			{
				ret.addValue(cdv.deepCopy());
			}

		}
		return ret;
	}

	public ContextInformation copy()
	{
		return deepCopy(new ArrayList<ContextInformation>());

	}



	private ContextInformation deepCopy(ArrayList<ContextInformation> parentCI){
		ContextInformation ret = new ContextInformation(name, type);
		ret.isAction = isAction();

		ret.addParents(parentCI);
		ArrayList<ContextInformation> p2 = new ArrayList<>(parentCI);
		p2.add(ret);

		if(this.hasContext())
		{
			ret.hasContext = new ArrayList<>(hasContext.size());
			for(ContextInformation child: hasContext)
			{
				ret.hasContext.add(child.deepCopy(p2));
			}
		}
		if(this.hasValues())
		{
			ret.checkHasValues();
			for(ContextData cdv:hasValues)
			{
				ret.addValue(cdv.deepCopy());
			}

		}
		return ret;
	}


	public ContextInformation(IRI name, IRI type){
		this.name = name;
		this.type = type;
	}

	public boolean sameType(ContextInformation comp){
		return /*name.equals(comp.name) &&*/ type.equals(comp.type);
	}

	public boolean isAction(){
		return isAction;
	}

	public void setAction(boolean isAction){
		this.isAction = isAction;
	}

	private void checkHasValues(){
		if(hasValues == null){
			hasValues = new TreeSet<ContextData>(new Comparator<ContextData>() {
				public int compare(ContextData o1, ContextData o2) {
					return o1.getDataProperty().compareTo(o2.getDataProperty());

				}
			});
		}
	}

	public boolean addValue(ContextData value){
		checkHasValues();
		return hasValues.add(value);
	}

	public TreeSet<ContextData> getValues(){
		return hasValues;
	}

	public boolean hasValues(){
		return hasValues!=null;
	}

	public boolean hasContext(){
		return hasContext!=null;
	}

	public List<ContextInformation> getContextInformationList(){
		return hasContext;
	}

	public void addParents(List<ContextInformation> parents){
		checkParents();
		this.parents.addAll(parents);
	}

	public void addParent(ContextInformation parent){
		checkParents();
		this.parents.add(parent);
	}

	public void checkParents(){
		if(this.parents==null){
			this.parents = new ArrayList<ContextInformation>();
		}
	}

	public List<ContextInformation> getParents(){
		return parents;
	}

	public void addContextInformation(ContextInformation q){
		if(hasContext==null){
			hasContext = new ArrayList<ContextInformation>();
		}
		q.addParents(parents);
		q.addParent(this);

		hasContext.add(q);
	}

	public void addContextInformation(List<ContextInformation> q){
		if(hasContext==null){
			hasContext = new ArrayList<ContextInformation>();
		}

		for(ContextInformation i:q){
			addContextInformation(i);
		}
	}


	private String getStringTabs(int tabs, String tab){
		String ret = "";
		for(int i=0;i<tabs;i++){
			ret+=tab;
		}
		return ret;
	}

	public List<IRI> getInverseTypeChain(){
		ArrayList<IRI> tc = new ArrayList<IRI>();
		tc.add(getType());
		for(int i=parents.size()-1;i>=0;i--){
			tc.add(parents.get(i).getType());
		}
		return tc;
	}

	public List<IRI> getTypeChain(){
		ArrayList<IRI> tc = new ArrayList<IRI>();
		for(ContextInformation parent: parents){
			tc.add(parent.getType());
		}
		tc.add(getType());
		return tc;
	}

	public String getFormattedView(int tabs, String tab){
		String indent = getStringTabs(tabs, tab);
		String ret=indent;
		ret += name.getFragment()+"("+type.getFragment()+")"+((this.isAction)?"_action":"") + " \n";
		indent = getStringTabs(tabs+1, tab);
		if(hasContext()){
			for(ContextInformation ci: hasContext){
				//ret += indent +"hasQuality some\n";
				ret += ci.getFormattedView(tabs+2, tab);	
			}
		}
		if(hasValues()){
			for(ContextData cd: hasValues){
				ret += indent + cd.toString()+"\n";
			}
		}
		return ret;
	}

	public String toString()
	{
		String ret="";
		if(parents!=null)
		{
			for(ContextInformation ci: parents)
			{
				ret+=ci.name.toString()+"("+ci.type.toString()+")->";
			}
		}
		ret+=name+"("+type+") props:" + ((hasValues!=null)?hasValues.size():0);

		return ret;
	}
}
