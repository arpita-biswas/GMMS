package fairShare;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Allocations class represents the allocations of items to agents.
 * It contains two maps:
 *          + map_item_to_agent: which agent (value) is being allocated an item (key)
 *          + map_agent_to_itemSet: which itemSet (value) are allocated to an agent (key)
 * + initialize_allocation(int [] allocations): takes array "allocations" and creates the two maps.
 * + getItems_allocated(Agents agent): returns the set of items allocated to "agent"
 * + getAgent_allocated(Items item): return the agent who has been allocated this "item"
 * + printAllocations(): prints the allocations.
 * 
 * @author Arpita
 *
 */

public class Allocations {
    public HashMap<Items, Agents> map_item_to_agent;
    public HashMap<Agents, HashSet<Items>> map_agent_to_itemSet;
    
    public Allocations(){
        map_item_to_agent = new HashMap<Items, Agents>();
        map_agent_to_itemSet = new HashMap<Agents, HashSet<Items>>();
        for(int i=0;i<Utils.n; i++){
            HashSet<Items> allocated_items = new HashSet<Items>();
            map_agent_to_itemSet.put(Utils.all_agents.get(i), allocated_items);
        }
    }
    
    public void initialize_allocation(int [] allocations){
        if(allocations == null || allocations.length!=Utils.m){
            System.out.println("The map is null or all items are not allocated");
            return;
        }
        
        map_item_to_agent.clear();
        map_agent_to_itemSet.clear();
        for(int i=0;i<Utils.n; i++){
            HashSet<Items> allocated_items = new HashSet<Items>();
            map_agent_to_itemSet.put(Utils.all_agents.get(i), allocated_items);
        }
        
        for(int i=0; i<Utils.m; i++){
            if(allocations[i]!=-1){
                map_item_to_agent.put(Utils.all_items.get(i), Utils.all_agents.get(allocations[i]));
            }
        }
            
        
        for(Items item: map_item_to_agent.keySet()){
            map_agent_to_itemSet.get(map_item_to_agent.get(item)).add(item);
        }
    }
    
    public HashSet<Items> getItems_allocated(Agents agent) {
      return map_agent_to_itemSet.get(agent);
    }   
    
    public Agents getAgent_allocated(Items item) {
        return map_item_to_agent.get(item);
    } 
    
    public int [] getArray(){
        int [] array = new int[Utils.m];
        for(Items item: map_item_to_agent.keySet()){
            array[item.getItem_id()] = map_item_to_agent.get(item).getAgent_id();
        }
        return array;
    }
    
    public void printAllocations(){
        //System.out.println(map_item_to_agent.toString());
        System.out.println(map_agent_to_itemSet.toString());
    }
}
