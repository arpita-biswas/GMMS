package fairShare;

import java.util.HashMap;
import java.util.HashSet;

public class groupwiseMMS {
    static boolean checkGMMS_singleGroup(HashMap<Agents, HashSet<Items>> allocations) throws Exception{
        if(allocations == null || allocations.size() <= 1){
            System.out.println("size <= 1");
            return true;
        }
        HashSet<Items> items = new HashSet<>();
        for(Agents agent: allocations.keySet()){
            items.addAll(allocations.get(agent));
        }
        for(Agents agent: allocations.keySet()){
            double mms = findMMS(agent, allocations.size(), items);
            if(Utils.valuations.getValuation_for_set(agent, allocations.get(agent)) < mms){
                //System.out.println("violated for "+agent.getAgent_id()+", mms = "+mms+", allocation="+Utils.totalValueOf_allocatedItems(agent));
                //System.out.println("AgentsSet:"+allocations.keySet());
                return false;
            }
        }
        return true;
    }
    
    
    static double findMMS(Agents agent, int n, HashSet<Items> items) throws Exception{
        double max = Double.MIN_VALUE;
        //HashSet<Items> max_a_part = new HashSet<Items>();
        for(HashSet<HashSet<Items>> partition: Utils.get_n_partitions(items, n)){
            double min = Double.MAX_VALUE;
            //HashSet<Items> max_a_part2 = new HashSet<Items>();
            for(HashSet<Items> a_part: partition){
                double value_for_a_part = Utils.valuations.getValuation_for_set(agent, a_part);
                if(min > value_for_a_part){
                    min = value_for_a_part;
                    //max_a_part2 = a_part;
                }
            }
            if(max<min){
                max = min;
                //max_a_part = max_a_part2;
            }
        }

        //System.out.println(agent.getAgent_id()+":"+max_a_part+" among "+items);
        return max;
    }
    
    static boolean checkGMMS() throws Exception{
        HashSet<Agents> main_agentSet = new HashSet<Agents>();
        main_agentSet.addAll(Utils.all_agents);
        for(HashSet<Agents> agentSet: Utils.powerSets_agents.get(main_agentSet)){
            HashMap<Agents, HashSet<Items>> subset_allocations = new HashMap<Agents, HashSet<Items>>();
            for(Agents agent: agentSet){
                subset_allocations.put(agent, Utils.allocations.getItems_allocated(agent));
            }
            if(!checkGMMS_singleGroup(subset_allocations)){
                return false;
            }
        }
        return true;
    }
    
    static double alphaGMMS() throws Exception{
        HashSet<Agents> main_agentSet = new HashSet<Agents>();
        main_agentSet.addAll(Utils.all_agents);
        double min_alpha = Double.MAX_VALUE;
        for(HashSet<Agents> agentSet: Utils.powerSets_agents.get(main_agentSet)){
            HashMap<Agents, HashSet<Items>> subset_allocations = new HashMap<Agents, HashSet<Items>>();
            for(Agents agent: agentSet){
                subset_allocations.put(agent, Utils.allocations.getItems_allocated(agent));
            }
            double alpha = alphaGMMS_singleGroup(subset_allocations);
            if(min_alpha>alpha){
                min_alpha=alpha;
            }
        }
        return min_alpha;
    }
    
    static double alphaGMMS_singleGroup(HashMap<Agents, HashSet<Items>> allocations) throws Exception{
        if(allocations == null || allocations.size() <= 1){
            System.out.println("size <= 1");
            return -1.0;
        }
        HashSet<Items> items = new HashSet<>();
        for(Agents agent: allocations.keySet()){
            items.addAll(allocations.get(agent));
        }
        for(Agents agent: allocations.keySet()){
            double mms = findMMS(agent, allocations.size(), items);
            if(Utils.valuations.getValuation_for_set(agent, allocations.get(agent)) < mms){
                //System.out.println("violated for "+agent.getAgent_id()+", mms = "+mms+", allocation="+Utils.totalValueOf_allocatedItems(agent));
                //System.out.println("AgentsSet:"+allocations.keySet());
                return Utils.valuations.getValuation_for_set(agent, allocations.get(agent))/mms;
            }
        }
        return 1.0;
    }

   

}
