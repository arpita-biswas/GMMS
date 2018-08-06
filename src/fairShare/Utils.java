package fairShare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Utils contains all static parameters of the environment
 *      + n = number of agents
 *      + m = number of items
 *      + all_agents = list of agents
 *      + all_items = list of items
 *      + allocations = an object of the Allocation class
 *      + valuations = and object of the Valuation class
 *      + powerSets_agent = A map that contains the power-set of a set of agents (key: set of agents, value: power-set)
 *      + map_partitions_by_itemSet = A map that contains all possible partitions (of size <= n) for a set of items (key: item-set, value: all possible k partitions where k<=n )
 *      + permute_agents = A HashSet of all possible permutations of the agents
 * 
 * + initAgents(n) takes n as input and initializes n, all_agents, powerSets_agent, permute_agents.
 * + initItems(m) takes m as input and initializes m, all_items, map_partitions_by_itemSet.
 * + initUniformRandomValuations() assigns each agent a random value (~Uniform[0,1]) for each item.
 * + initGaussianRandomValuations() assigns each agent a random value (~Gaussian(mean=0.5, sd=0.1)) for each item.
 * + initStaticValuations() initializes the Allocation object with a static allocation.
 * + initAllocations() initializes the Allocation object "allocations" with the given array.
 * + totalValueOf_allocatedItems(agent) returns the total value of items allocated to "agent" in "allocations" under "valuations".
 * + totalValue_of_items(agent, itemSet) returns the total value of "itemSet" to "agent" under "valuations".
 * + powerSet(agentSet) returns the power-set of the set of agents "agentSet" and also stores it in "powerSets_agent".
 * + get_n_partitions(itemSet, n) returns all n-partitions of itemSet and also stores all k-partitions (k<=n) of "itemSet" in "map_partitions_by_itemSet".
 *
 * @author Arpita
 *
 */

public class Utils {
    public static int n; //Number of agents
    public static int m; //Number of goods
    public static ArrayList<Agents> all_agents; //List of agents
    public static ArrayList<Items> all_items; //List of items
    public static Allocations allocations;
    public static Valuations valuations;
    public static HashMap<HashSet<Items>, HashMap<Integer, HashSet<HashSet<HashSet<Items>>>>> map_partitions_by_itemSet;
    public static HashMap<HashSet<Agents>, HashSet<HashSet<Agents>>> powerSets_agents;
    public static HashSet<ArrayList<Integer>> permute_agents;
    
    public static int iterationNumber;
    
    public Utils(int n, int m){     
          Utils.n = n;
          Utils.all_agents = new ArrayList<Agents>();
          for(int i=0; i<n ; i++){
              Agents a = new Agents(i);
              Utils.all_agents.add(a);
          }
          Utils.powerSets_agents = new HashMap<HashSet<Agents>, HashSet<HashSet<Agents>>>();
          HashSet<Agents> main_agentSet = new HashSet<Agents>();
          main_agentSet.addAll(Utils.all_agents);
          Utils.powerSet(main_agentSet);
          
          Utils.permute_agents = new HashSet<ArrayList<Integer>>();
          List<Integer> agent_list = new ArrayList<Integer>();
          for(int i=0; i<Utils.n; i++){
              agent_list.add(i);
          }
          Utils.permute(agent_list, 0);
          
          Utils.m = m;
          Utils.all_items = new ArrayList<Items>();
          for(int i=0; i<m ; i++){
              Items itm = new Items(i);
              Utils.all_items.add(itm);
          }
          Utils.map_partitions_by_itemSet = new HashMap<HashSet<Items>, HashMap<Integer, HashSet<HashSet<HashSet<Items>>>>>();
            
    }
    
    public Utils(int n, int m, boolean flag){     
        Utils.n = n;
        Utils.all_agents = new ArrayList<Agents>();
        for(int i=0; i<n ; i++){
            Agents a = new Agents(i);
            Utils.all_agents.add(a);
        }       
        
        Utils.powerSets_agents = new HashMap<HashSet<Agents>, HashSet<HashSet<Agents>>>();
        HashSet<Agents> main_agentSet = new HashSet<Agents>();
        main_agentSet.addAll(Utils.all_agents);
        Utils.powerSet(main_agentSet);
        
        Utils.m = m;
        Utils.all_items = new ArrayList<Items>();
        for(int i=0; i<m ; i++){
            Items itm = new Items(i);
            Utils.all_items.add(itm);
        }      
        Utils.map_partitions_by_itemSet = new HashMap<HashSet<Items>, HashMap<Integer, HashSet<HashSet<HashSet<Items>>>>>();

  }
    
//    public static void initAgents(int n){
//        Utils.n = n;
//        Utils.all_agents = new ArrayList<Agents>();
//        for(int i=0; i<n ; i++){
//            Agents a = new Agents(i);
//            Utils.all_agents.add(a);
//        }
//        Utils.powerSets_agents = new HashMap<HashSet<Agents>, HashSet<HashSet<Agents>>>();
//        HashSet<Agents> main_agentSet = new HashSet<Agents>();
//        main_agentSet.addAll(Utils.all_agents);
//        Utils.powerSet(main_agentSet);
//        
//        Utils.permute_agents = new HashSet<ArrayList<Integer>>();
//        List<Integer> agent_list = new ArrayList<Integer>();
//        for(int i=0; i<Utils.n; i++){
//            agent_list.add(i);
//        }
//        Utils.permute(agent_list, 0);
//        
//    }
//    
//    public static void initItems(int m){
//        Utils.m = m;
//        Utils.all_items = new ArrayList<Items>();
//        for(int i=0; i<m ; i++){
//            Items itm = new Items(i);
//            Utils.all_items.add(itm);
//        }
//        Utils.map_partitions_by_itemSet = new HashMap<HashSet<Items>, HashMap<Integer, HashSet<HashSet<HashSet<Items>>>>>();
//        
//    }
    
    public static void initUniformRandomValuations(){
        Random rnd = new Random ();
//        int min = 0;
//        int max = 1;
        rnd.setSeed(1234+Utils.iterationNumber);
        valuations = new Valuations();
        for(Agents agent: Utils.all_agents){
            for(Items item: Utils.all_items){
                valuations.setValuation_for(agent, item, rnd.nextDouble());
            }           
        }
    }
    
    public static void initUniformRandomValuationsOrderedInstances(){
        Random rnd = new Random ();
//        int min = 0;
//        int max = 1;
        rnd.setSeed(1234+Utils.iterationNumber);
        valuations = new Valuations();
        for(Agents agent: Utils.all_agents){
            double [] array = new double[Utils.m];
            for(int i=0; i<Utils.m; i++){
                array[i] = rnd.nextDouble();
            }
            Arrays.sort(array);
            for(int i=0; i<Utils.m; i++){
                valuations.setValuation_for(agent, Utils.all_items.get(i), array[i]);
            }           
        }
    }
    
    public static void initGaussianRandomValuations(){
        Random rnd = new Random ();
        double mean = 0.5;
        double sd = 0.1;
        rnd.setSeed(1234+Utils.iterationNumber);
        valuations = new Valuations();
        for(Agents agent: Utils.all_agents){
            for(Items item: Utils.all_items){
                valuations.setValuation_for(agent, item, Math.max(0,rnd.nextGaussian()*sd + mean));
            }           
        }
    }
    
    public static void initGaussianRandomValuationsOrderedInstances(){
        Random rnd = new Random ();
        double mean = 0.5;
        double sd = 0.1;
        rnd.setSeed(1234+Utils.iterationNumber);
        valuations = new Valuations();
        for(Agents agent: Utils.all_agents){
            double [] array = new double[Utils.m];
            for(int i=0; i<Utils.m; i++){
                array[i] = Math.max(0, rnd.nextGaussian()*sd + mean);
            }
            Arrays.sort(array);
            for(int i=0; i<Utils.m; i++){
                valuations.setValuation_for(agent, Utils.all_items.get(i), array[i]);
            }           
        }
    }
    
    public static void initStaticValuations(){
          valuations = new Valuations();
          double B [][] = {{1,1,1,1},{1,1,1,1},{1,1,1,1}};
          double O [][] = {{17,25,12,1},{2,22,3,28},{11,0,21,23}};
          double E[][][] = 
          {   
              {{3,-1,-1,-1},{0,0,0,0},{0,0,0,0}},
              {{3,-1,0,0},{-1,0,0,0},{-1,0,0,0}},
              {{3,0,-1,0},{0,0,-1,0},{0,0,0,-1}}
          
          };
              
          for(Agents agent: all_agents){
              int k = agent.getAgent_id();
              for(Items item: all_items){
                  int i = item.getItem_id()/4;
                  int j = item.getItem_id()%4;
                  valuations.setValuation_for(agent, item, B[i][j]*100000 + O[i][j]*1000 + E[k][i][j]);
              }           
          }
    }
    
    public static void initStaticValuations3(){
        valuations = new Valuations();
        double S [][] = {
                            {7.0/8,     0,      0,      1.0/8},
                            {0,         3.0/4,  0,      1.0/4},
                            {0,         0,      1.0/2,  1.0/2},
                            {1.0/8,     1.0/4,  1.0/2,  1.0/8}
                        };
        
        double e = 1.0/16;
        double e2 = Math.pow(e,2);
        double e3 = Math.pow(e,3);
        double e4 = Math.pow(e,4);
        double T [][] = {
                            {  0,    e4,     0,      -e4},
                            { e3,     0, e2-e3,      -e2},
                            {  0,  e-e4,     0,     e4-e},
                            {-e3,    -e, e3-e2,     e2+e}                
                        };
        
        double e_tilde = 1.0/64;
        double E1[][] = {   
                            {0,0,0, - e_tilde},
                            {0,0,0, - e_tilde}, 
                            {0,0,0, - e_tilde},
                            {0,0,0,-3*e_tilde}
                        };
        double E2[][] = {   
                {       0,        0,        0,          0},
                {       0,        0,        0,          0},
                {       0,        0,        0,          0},
                {-e_tilde, -e_tilde, -e_tilde, -3*e_tilde}
            };
        
        double M1 [][] = new double [4][4];
        double M2 [][] = new double [4][4];
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                M1[i][j] = S[i][j] + T[i][j] + E1[i][j];
                M2[i][j] = S[i][j] + T[i][j] + E2[i][j];
            }
        }
        
        for(Items item: all_items){
            int item_id = item.getItem_id();
            int i = item_id/4;
            int j = item_id%4;
            valuations.setValuation_for(0, item_id, M1[i][j]);
            valuations.setValuation_for(1, item_id, M1[i][j]);
            valuations.setValuation_for(2, item_id, M2[i][j]);
            valuations.setValuation_for(3, item_id, M2[i][j]);
        }
                    

    }
    
    public static void initStaticValuations2(){
        valuations = new Valuations();
        double V[][] = 
        {   
            {8,3,7,4,7,4},
            {11,11,4,4,0,0},
            {8,3,7,4,7,4}
        
        };            
        for(int i=0; i<3; i++){
            for(int j=0; j<6; j++){
                valuations.setValuation_for(Utils.all_agents.get(i), Utils.all_items.get(j), V[i][j]);
            }           
        }
    }
    
    
    
    public static void initStaticValuationsCounterExample(){
        valuations = new Valuations();
        double V[][] = 
        {   
            {11,11,4,4,2,1},
            {11,11,4,4,2,1},
            {11,11,4,4,2,1}
        
        };            
        for(int i=0; i<3; i++){
            for(int j=0; j<6; j++){
                valuations.setValuation_for(Utils.all_agents.get(i), Utils.all_items.get(j), V[i][j]);
            }           
        }
    }

    public static void initialize_allocation(int [] allocations){
        Utils.allocations = new Allocations();
        Utils.allocations.initialize_allocation(allocations);
    }
    
    public static double totalValueOf_allocatedItems(Agents agent){
        return Utils.totalValueOf_Items(agent, Utils.allocations.getItems_allocated(agent));
    }
    
    public static double totalValueOf_Items(Agents agent, HashSet<Items> itemSet){
        return valuations.getValuation_for_set(agent, itemSet);
    }
    
    public static HashSet<HashSet<HashSet<Items>>> get_n_partitions(HashSet<Items> itemSet, int n) throws Exception{
        if(itemSet == null || itemSet.size() < n){
            System.out.println("Number of items less than number of agents");
            return null;
        }
        
        if(map_partitions_by_itemSet.containsKey(itemSet)){
            if(map_partitions_by_itemSet.get(itemSet).containsKey(n)){
                //System.out.println("Got it");
                return map_partitions_by_itemSet.get(itemSet).get(n);
            }
        }
        //System.out.println("not found");
        int [] set = new int[itemSet.size()];
        int item_count = 0;
        for(Items item: itemSet){
            set[item_count] = item.getItem_id();
            item_count++;
        }
        
        return getAllPartitions(set, n);        
    }
    
    private static HashSet<HashSet<HashSet<Items>>> getAllPartitions (int [] array, int n) throws Exception{
        //System.out.println("getAllPartitions");
        int [][][] res = getAllPartitionsHelper(array);
        
        HashSet<Items> items = new HashSet<Items>();
        for(int i=0; i<array.length; i++){
            items.add(Utils.all_items.get(array[i]));
        }
        
        HashMap<Integer, HashSet<HashSet<HashSet<Items>>>> map_n_partitions = new HashMap<Integer, HashSet<HashSet<HashSet<Items>>>>();

        for(int i=0; i<res.length; i++){
            if(res[i].length<=n){
                HashSet<HashSet<Items>> partition = new HashSet<HashSet<Items>>();
                //System.out.println("i="+i);
                for(int j=0; j<res[i].length; j++){
                    HashSet<Items> a_part = new HashSet<Items>();
                    //System.out.println("\t j="+j);
                    for(int k=0; k<res[i][j].length; k++){
                        //System.out.println("\t \t k="+res[i][j][k]);
                        a_part.add(Utils.all_items.get(res[i][j][k]));
                    }
                    partition.add(a_part);
                }
                if(map_n_partitions.containsKey(res[i].length)){
                    HashSet<HashSet<HashSet<Items>>> temp_n_partitions = map_n_partitions.get(res[i].length);
                    temp_n_partitions.add(partition);
                }
                else{
                    HashSet<HashSet<HashSet<Items>>> temp_n_partitions = new HashSet<HashSet<HashSet<Items>>>();
                    temp_n_partitions.add(partition);
                    map_n_partitions.put(res[i].length, temp_n_partitions);
                }
            }            
        }
        map_partitions_by_itemSet.put(items, map_n_partitions);
        return map_n_partitions.get(n);
    }    
    
    private static int[][][] getAllPartitionsHelper(int[] array) throws Exception {
        int[][][] res = new int[0][][];
        int n = 1;
        for (int i = 0; i < array.length; i++) {
            n *= 2;
        }
        for (int i = 1; i < n; i += 2) {
            boolean[] contains = new boolean[array.length];
            int length = 0;
            int k = i;
            for (int j = 0; j < array.length; j++) {
                contains[j] = k % 2 == 1;
                length += k % 2;
                k /= 2;
            }
            int[] firstPart = new int[length];
            int[] secondPart = new int[array.length - length];
            int p = 0;
            int q = 0;
            for (int j = 0; j < array.length; j++) {
                if (contains[j]) {
                    firstPart[p++] = array[j];
                } else {
                    secondPart[q++] = array[j];
                }
            }
            int[][][] partitions;
            if (length == array.length) {
                partitions = new int[][][] {{firstPart}};
            }
            else {
                partitions = getAllPartitionsHelper(secondPart);
                for (int j = 0; j < partitions.length; j++) {
                    int[][] partition = new int[partitions[j].length + 1][];
                    partition[0] = firstPart;
                    System.arraycopy(partitions[j], 0, partition, 1, partitions[j].length);
                    partitions[j] = partition;
                }
            }
            int[][][] newRes = new int[res.length + partitions.length][][];
            System.arraycopy(res, 0, newRes, 0, res.length);
            System.arraycopy(partitions, 0, newRes, res.length, partitions.length);
            res = newRes;
        }        
        return res;
    }     
        
    private static HashSet<HashSet<Agents>> powerSet(HashSet<Agents> agentSet){
        if(Utils.powerSets_agents.containsKey(agentSet)){
            return Utils.powerSets_agents.get(agentSet);
        }
        HashSet<HashSet<Agents>> powerSet = new HashSet<HashSet<Agents>>();
        if(agentSet.size() <= 2){
            powerSet.add(agentSet);
            Utils.powerSets_agents.put(agentSet, powerSet);
            return powerSet;
        }
        for(Agents agent: agentSet){
            HashSet<Agents> temp_agentSet = new HashSet<Agents>();
            temp_agentSet.addAll(agentSet);
            temp_agentSet.remove(agent);
            powerSet.addAll(powerSet(temp_agentSet));
            for(HashSet<Agents> set: powerSet(temp_agentSet)){
                HashSet<Agents> newSet = new HashSet<Agents>();
                newSet.addAll(set);
                newSet.add(agent);
                powerSet.add(newSet);
            }            
        }
        Utils.powerSets_agents.put(agentSet, powerSet);
        return powerSet;
    }       
    
    public static int [] createPartitionArray(HashSet<HashSet<Items>> partition, ArrayList<Integer> agentOrder){
        int [] allocations = new int[Utils.m];
        int agent_id = 0;
        for(HashSet<Items> itemSet: partition){
            for(Items item: itemSet){
                allocations[item.getItem_id()] = agentOrder.get(agent_id);
            }
            agent_id++;
        }
        return allocations;
    }
    
    private static void permute(java.util.List<Integer> arr, int k){
        for(int i = k; i < arr.size(); i++){
            java.util.Collections.swap(arr, i, k);
            permute(arr, k+1);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size()-1){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.addAll(arr);
            Utils.permute_agents.add(list);
        }
    }
}
