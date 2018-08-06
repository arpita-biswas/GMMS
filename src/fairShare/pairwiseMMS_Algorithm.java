package fairShare;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.Random;
//
public class pairwiseMMS_Algorithm {
//	public int n; //Number of agents
//	public int m; //Number of goods
//	public ArrayList<Agents> all_agents; //List of agents
//	public ArrayList<Items> all_items; //List of items
//	Valuations valuations;
//	Allocations allocations;
//	
//	public pairwiseMMS_Algorithm(int n, int m){
//		this.n = n;
//		all_agents = new ArrayList<Agents>();
//		for(int i=0; i<n ; i++){
//			Agents a = new Agents(i);
//			all_agents.add(a);
//		}
//		this.m = m;
//		all_items = new ArrayList<Items>();
//		for(int i=0; i<m ; i++){
//			Items itm = new Items(i);
//			all_items.add(itm);
//		}
//		initStaticValuations();
//	}
//	
//	public void initStaticValuations(){
//		
//		double B [][] = {{1,1,1,1},{1,1,1,1},{1,1,1,1}};
//		double O [][] = {{17,25,12,1},{2,22,3,28},{11,0,21,23}};
//		double E[][][] = 
//		{	
//			{{3,-1,-1,-1},{0,0,0,0},{0,0,0,0}},
//			{{3,-1,0,0},{-1,0,0,0},{-1,0,0,0}},
//			{{3,0,-1,0},{0,0,-1,0},{0,0,0,-1}}
//		
//		};
////		double [ ] [ ] valuations = 
////			{   { 20, 18, 22, 20, 16 },
////                { 18, 20, 18, 21, 20 },
////                { 16, 18, 16, 20, 24 }
////            };
//			
//		for(Agents agent: all_agents){
//			int k = agent.getAgent_id();
//			for(Items item: all_items){
//				int i = item.getItem_id()/4;
//				int j = item.getItem_id()%4;
//				valuations.setValuation_for(agent, item, B[i][j]*100000 + O[i][j]*1000 + E[k][i][j]);
//			}			
//		}
//	}
//	
//	public void initRandomValuations(){
//		Random rnd = new Random ();
//		int min = 1;
//		int max = 100;
//		rnd.setSeed(1234);
//		for(Agents agent: all_agents){
//			for(Items item: all_items){
//				valuations.setValuation_for(agent, item, rnd.nextInt(max - min + 1) + min);
//			}			
//		}
//	}
//	
//	public static void main(String args[]) throws Exception{
//		pairwiseMMS_Algorithm pmms = new pairwiseMMS_Algorithm(3,12);
//		System.out.println("Number of agents: "+pmms.all_agents.size());
//		System.out.println("Number of items: "+pmms.all_items.size());
//		System.out.println();
//		for(Agents a: pmms.all_agents){
//			System.out.println("Agent "+a.getAgent_id()+"-----------");
//			for(Items item: pmms.all_items){
//				System.out.println("*****Item "+item.getItem_id()+", value: "+pmms.valuations.getValuation_for(a, item));
//			}
//		}		
//		int allocation_vector [][]= {{0,2,6,11},{1,4,7,9},{3,5,8,10}};
//		for(int i=0; i<3; i++){
//			for(int j=0; j<4; j++){
//				pmms.all_agents.get(i).allocate_item(pmms.all_items.get(allocation_vector[pmms.all_agents.get(i).getAgent_id()][j]));
//			}
//		}
//		
//		
//		System.out.println(pmms.check_pmms(pmms.all_agents.get(0), pmms.all_agents.get(2)));
////		System.out.println("Check whether a given allocation is pairwise-mms");
////		int [] items_array = new int[pmms.m];
////		for(int i = 0; i<pmms.m; i++){
////			items_array[i] = i;
////		}
////		int[][][] partitions = pmms.getAllPartitions(items_array);
////		System.out.println("Partitions:");
////		for(int i = 0; i<partitions.length; i++){
////		//for(int i = 0; i<5; i++){
////			LinkedList<HashSet<Items>> partition = new LinkedList<HashSet<Items>>();
////			for(int j = 0; j<partitions[i].length; j++){
////				HashSet<Items> item_set = new HashSet<Items>();
////				for(int k=0; k<partitions[i][j].length; k++){
////					item_set.add(pmms.all_items.get(partitions[i][j][k]));
////					System.out.println("["+i+"]["+j+"]["+k+"]="+partitions[i][j][k]);
////				}
////				partition.add(item_set);
////			}
////			if(partition.size()>pmms.n){
////				continue;
////			}
////			for(int l = partition.size(); l<pmms.n; l++){
////				HashSet<Items> item_set = new HashSet<Items>();
////				partition.add(item_set);
////			}
////			System.out.println("Size of partition: "+partition.size());
////			//Allocate items to agents
////
////			for(int x = 0; x < partition.size(); x++){
////				for(Items itm: partition.get(x)){
////					pmms.all_agents.get(x).allocate_item(itm);
////				}
////			}
////			//print allocations
////			for(Agents a: pmms.all_agents){
////				System.out.print("\n"+a.getAgent_id()+"-> (");
////				for(Items itm: a.getAllocated_items()){
////					System.out.print(itm.getItem_id()+",");
////				}
////				System.out.println(")");
////			}
////			
//////			pmms.algo_pmms();
//////			System.out.println("After Agorithm......");
//////			for(Agents a: pmms.all_agents){
//////				System.out.print("\n"+a.getAgent_id()+"-> (");
//////				for(Items itm: a.getAllocated_items()){
//////					System.out.print(itm.getItem_id()+",");
//////				}
//////				System.out.println(")");
//////			}
////			
////			for(Agents a: pmms.all_agents){
////				a.allocated_items.clear();
////			}
////			System.out.println("*******************************************************");
////		}		
//	}
//	
//	public int[][][] getAllPartitions(int[] array) throws Exception {
//	    int[][][] res = new int[0][][];
//	    int n = 1;
//	    for (int i = 0; i < array.length; i++) {
//	        n *= 2;
//	    }
//	    for (int i = 1; i < n; i += 2) {
//	        boolean[] contains = new boolean[array.length];
//	        int length = 0;
//	        int k = i;
//	        for (int j = 0; j < array.length; j++) {
//	            contains[j] = k % 2 == 1;
//	            length += k % 2;
//	            k /= 2;
//	        }
//	        int[] firstPart = new int[length];
//	        int[] secondPart = new int[array.length - length];
//	        int p = 0;
//	        int q = 0;
//	        for (int j = 0; j < array.length; j++) {
//	            if (contains[j]) {
//	                firstPart[p++] = array[j];
//	            } else {
//	                secondPart[q++] = array[j];
//	            }
//	        }
//	        int[][][] partitions;
//	        if (length == array.length) {
//	            partitions = new int[][][] {{firstPart}};
//	        }
//	        else {
//	            partitions = getAllPartitions(secondPart);
//	            for (int j = 0; j < partitions.length; j++) {
//	                int[][] partition = new int[partitions[j].length + 1][];
//	                partition[0] = firstPart;
//	                System.arraycopy(partitions[j], 0, partition, 1, partitions[j].length);
//	                partitions[j] = partition;
//	            }
//	        }
//	        int[][][] newRes = new int[res.length + partitions.length][][];
//	        System.arraycopy(res, 0, newRes, 0, res.length);
//	        System.arraycopy(partitions, 0, newRes, res.length, partitions.length);
//	        res = newRes;
//	    }
//	    return res;
//	}
//	
//	public void algo_pmms() throws Exception{
//		int flag=1;
//		while(flag!=0){
//			flag = 0;
//			for (int i = 0; i< this.n ; i++){
//				for(int j = i+1; j <this.n ; j++){
//					boolean r = ensure_pmms(i,j);
//					if(!r){
//						flag=1;
//					}
//				}
//			}
//		}
//	}
//	
//	public boolean ensure_pmms(int i, int j) throws Exception{
//		ArrayList<Items> items = new ArrayList<Items>();
//		for(Items itm1: this.all_agents.get(i).allocated_items){
//			items.add(itm1);
//		}
//		for(Items itm2: this.all_agents.get(j).allocated_items){
//			items.add(itm2);
//		}
//		
//		System.out.println("Items for agent "+i+" and "+j);
//		for(Items it: items){
//			System.out.print(it.getItem_id()+",");
//		}
//		System.out.println();
//		
//		double v1 = this.all_agents.get(i).getValuation_for_set(this.all_agents.get(i).allocated_items);
//		double v2 = this.all_agents.get(j).getValuation_for_set(this.all_agents.get(j).allocated_items);
//		System.out.println("The value of agent "+i+" is "+v1);
//		System.out.println("The value of agent "+j+" is "+v2);
//
//		HashSet<Items> maxmin_itemset1 = new HashSet<Items>();
//		HashSet<Items> maxmin_itemset2 = new HashSet<Items>();
//		double maxmin_agent1 = this.mms(i, items, maxmin_itemset1, maxmin_itemset2);
//		double maxmin_agent2 = this.mms(j, items);
//		System.out.println("MMS for "+i+" is "+maxmin_agent1);
//		System.out.println("MMS for "+j+" is "+maxmin_agent2);
//		
//		if(v1>=maxmin_agent1 && v2>=maxmin_agent2){
//			System.out.println("This is pairwise max min share for agents "+i+" and "+j);
//			return true;
//		}
//		else{
//			System.out.println("Finding a pairwise max min share allocation for agents"+i+" and "+j);
//			System.out.println("maximin_itemset1");
//			for(Items it: maxmin_itemset1){
//				System.out.print(it.getItem_id()+",");
//			}
//			System.out.println();
//			System.out.println("maximin_itemset2");
//			for(Items it: maxmin_itemset2){
//				System.out.print(it.getItem_id()+",");
//			}
//			System.out.println();
//			
//			double val1 = this.all_agents.get(j).getValuation_for_set(maxmin_itemset1);
//			double val2 = this.all_agents.get(j).getValuation_for_set(maxmin_itemset2);
//			if(val1>val2){
//				this.all_agents.get(j).allocated_items.clear();
//				for(Items itm: maxmin_itemset1){
//					this.all_agents.get(j).allocate_item(itm);
//				}
//				this.all_agents.get(i).allocated_items.clear();
//				for(Items itm: maxmin_itemset2){
//					this.all_agents.get(i).allocate_item(itm);
//				}				
//			}
//			else{
//				this.all_agents.get(j).allocated_items.clear();
//				for(Items itm: maxmin_itemset2){
//					this.all_agents.get(j).allocate_item(itm);
//				}
//				this.all_agents.get(i).allocated_items.clear();
//				for(Items itm: maxmin_itemset1){
//					this.all_agents.get(i).allocate_item(itm);
//				}				
//			}
//			return false;
//		}
//		
//	}
//	
//	public double mms(int i, ArrayList<Items> items, HashSet<Items> maxmin_itemset1, HashSet<Items> maxmin_itemset2){
//		System.out.println("Computing agent "+i+" MMS value");
//		double maxmin = Double.MIN_VALUE;
//		for(int k = 0; k< Math.pow(2, items.size()); k++){
//			HashSet<Items> itemset1 = new HashSet<Items>();
//			HashSet<Items> itemset2 = new HashSet<Items>();
//			int l = 1;
//			for(int h=0; h<items.size(); h++){
//				if((k & l) == 0){
//					itemset1.add(items.get(h));
//				}
//				else{
//					itemset2.add(items.get(h));
//				}
//				l = l<<1;
//			}
//			
//			System.out.println("Items in set 1");
//			for(Items it: itemset1){
//				System.out.print(it.getItem_id()+",");
//			}
//			System.out.println("\nItems in set 2");
//			for(Items it: itemset2){
//				System.out.print(it.getItem_id()+",");
//			}
//			System.out.println();
//			double value;
//			double value_set1 = this.all_agents.get(i).getValuation_for_set(itemset1);
//			double value_set2 = this.all_agents.get(i).getValuation_for_set(itemset2);
//			System.out.println("Agent "+i+"s value for the sets are "+value_set1+" and "+value_set2);
//			if(value_set1>value_set2){
//				value = value_set2;
//			}
//			else{
//				value = value_set1;
//			}
//			
//			if(maxmin < value){
//				System.out.println("MAXMIN Obtained");
//				maxmin = value;
//				maxmin_itemset1.clear();
//				maxmin_itemset1.addAll(itemset1);
//				maxmin_itemset2.clear();
//				maxmin_itemset2.addAll(itemset2);
//			}
//		}
//		return maxmin;
//	}
//	public double mms(int i, ArrayList<Items> items){
//		double maxmin = Double.MIN_VALUE;
//		
//		for(int k = 0; k< Math.pow(2, items.size()); k++){
//			HashSet<Items> itemset1 = new HashSet<Items>();
//			HashSet<Items> itemset2 = new HashSet<Items>();
//			int l = 1;
//			for(int h=0; h<items.size(); h++){
//				if((k & l) == 0){
//					itemset1.add(items.get(h));
//				}
//				else{
//					itemset2.add(items.get(h));
//				}
//				l = l<<1;
//			}
//			
//			double value;
//			double value_set1 = this.all_agents.get(i).getValuation_for_set(itemset1);
//			double value_set2 = this.all_agents.get(i).getValuation_for_set(itemset2);
//			if(value_set1>value_set2){
//				value = value_set2;
//			}
//			else{
//				value = value_set1;
//			}
//			
//			if(maxmin < value){
//				maxmin = value;
//			}
//		}
//		return maxmin;
//	}
//	
//	public boolean check_pmms(Agents ai, Agents aj){
//		ArrayList<Items> itemList = new ArrayList<Items>();
//		itemList.addAll(ai.getAllocated_items());
//		for(Items itm: aj.getAllocated_items()){
//			if(!itemList.contains(itm)){
//				itemList.add(itm);
//			}
//		}
//
//		double max_i = Double.MIN_VALUE;
//		double max_j = Double.MIN_VALUE;
//
//		//Create all possible 2-partitions
//		HashSet<Items> itemsToAgent_i = new HashSet<Items>();
//		HashSet<Items> itemsToAgent_j = new HashSet<Items>();
//		for(int i=1; i<Math.pow(2, itemList.size())-2; i++){
//			itemsToAgent_i.clear();
//			itemsToAgent_j.clear();
//			//create a partition 
//			//System.out.println("Create a partition");
//			int k=i;
//			for(int j =0; j<itemList.size(); j++){
//				if(k%2 == 0){
//					itemsToAgent_i.add(itemList.get(j));
//					//System.out.println("Item "+itemList.get(j).getItem_id()+" to 1");
//				}
//				else{
//					itemsToAgent_j.add(itemList.get(j));
//					//System.out.println("Item "+itemList.get(j).getItem_id()+" to 2");
//				}				
//				k = k/2;
//			}
//			//System.out.println();
//			double value_i_1 = ai.totalValueOf_Items(itemsToAgent_i);
//			double value_i_2 = ai.totalValueOf_Items(itemsToAgent_j);
//			
//			if(value_i_1>max_i && value_i_2>max_i){
//				max_i = Math.min(value_i_1, value_i_2);
//				System.out.print("MaxValue for 1:"+max_i+"-> ");
//				for(Items itm: itemsToAgent_i){
//					System.out.print(itm.getItem_id()+",");
//				}
//				System.out.println();
//			}
//			
//			double value_j_1 = aj.totalValueOf_Items(itemsToAgent_i);
//			double value_j_2 = aj.totalValueOf_Items(itemsToAgent_j);
//			if(value_j_1>max_j && value_j_2>max_j){
//				max_j = Math.min(value_j_1, value_j_2);
//				System.out.print("MaxValue for 2:"+max_j+"-> ");
//				for(Items itm: itemsToAgent_j){
//					System.out.print(itm.getItem_id()+",");
//				}
//				System.out.println();
//			}
//		}
//		
//		if(ai.totalValueOf_allocatedItems()<max_i || aj.totalValueOf_allocatedItems()<max_j){
//			return false;
//		}
//		else{
//			return true;
//		}
//	}
//
}
