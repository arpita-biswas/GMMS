package fairShare;

/**
 * Agents class represents an agent, identified by agent_id
 * @author Arpita
 *
 */

public class Agents {
	private int agent_id;
	
	public Agents(int id){
		this.agent_id = id;
	}
		
	public int getAgent_id() {
		return this.agent_id;
	}
	
	@Override
	public String toString(){
        return "Agent:"+this.agent_id;	    
	}
	
	@Override
    public boolean equals(Object o){
        Agents agent = (Agents)o;
        if(this.agent_id == agent.agent_id){
            return true;
        }
        return false;       
    }
}
