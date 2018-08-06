package fairShare;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    
    public static void main(String args[]) throws Exception{
        EF1L();
    }
    
    public static void EF1L() throws Exception{
        boolean EF1L=true;
        String [] filenames = {"Uniform", "UniformOrdered", "Gaussian", "GaussianOrdered"};
        File dir = new File("results_EF1L_GWF_2");
        dir.mkdirs();
        double time;
        for(int i=3; i<=5; i++){
            int n=i;      
            
            for(int j=i; j<=11; j++){
                int m=j;

                new Utils(n,m, EF1L);
                
                for(int q=0; q<4; q++){
                //int q=0;
                    System.out.println("n="+n+" and m="+m);
                    File file = new File(dir, "result_GWF"+filenames[q]+"_"+n+"_"+m+".txt");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("*******n="+n+" and m="+m+"\n");
                    time = 0;
                    double min_alpha = Double.MAX_VALUE;
                    double total_alpha = 0;
                    for(int k=0; k<1000; k++){                    
                        Utils.iterationNumber = k;   
                        
                        if(q==0){
                            Utils.initUniformRandomValuations(); 
                        }
                        else if(q==1){
                            
                            Utils.initUniformRandomValuationsOrderedInstances(); 
                        }
                        else if(q==2){
                            Utils.initGaussianRandomValuations(); 
                        }
                        else {
                            Utils.initGaussianRandomValuationsOrderedInstances(); 
                        }

                        //Utils.valuations.printValuations();
                        long startTime = System.currentTimeMillis();
                        
                        Allocations allocation = EF1L_algo(); 
                        
                        long endTime = System.currentTimeMillis();
                        double timeDiff = (endTime-startTime);
                        time += timeDiff;
                                                
                        Utils.initialize_allocation(allocation.getArray());
                        //Utils.allocations.printAllocations();
                        double alphaGMMS = groupwiseMMS.alphaGMMS();
                        if(min_alpha>alphaGMMS){
                            min_alpha=alphaGMMS;
                        }
                        total_alpha += alphaGMMS;
                        
                        System.out.println(k+","+alphaGMMS+","+(timeDiff)/1000.0+" seconds");
                        bw.write(k+","+alphaGMMS+","+(timeDiff)/1000.0+" seconds\n");   
                    }
                    bw.write("\n Average Time Taken = "+time/1000.0+" milliseconds, min_alpha="+min_alpha+",avg_alpha="+total_alpha/1000.0);
                    bw.close();
                }
            }
        }       
    }
    
    public static void kurokawa() throws Exception{
        File dir = new File("results_kurokawa");
        dir.mkdirs();
        File file = new File(dir, "result.txt");
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        
        new Utils (4,16);
        Utils.initStaticValuations3();
        long startTime = System.currentTimeMillis();
        boolean isGMMS = GMMS_simulations();
        if(!isGMMS){
            Utils.valuations.printValuations(bw);
        }
        long endTime = System.currentTimeMillis();
        double timeDiff = (endTime-startTime);
        System.out.println("The example is :"+isGMMS+", time: "+timeDiff/1000.0); 
        bw.write("\n The example is :"+isGMMS+", time: "+timeDiff/1000.0);
        bw.close();
    }
    
    public static void distributions() throws Exception{
        String [] filenames = {"Uniform", "UniformOrdered", "Gaussian", "GaussianOrdered"};
        File dir = new File("results_Everything");
        dir.mkdirs();
        double time;
        for(int i=3; i<=5; i++){
            int n=i;      
            
            for(int j=i; j<=11; j++){
                int m=j;

                new Utils(n,m);
                for(int q=0; q<4; q++){

                    System.out.println("n="+n+" and m="+m);
                    File file = new File(dir, "result"+filenames[q]+"_"+n+"_"+m+".txt");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("*******n="+n+" and m="+m+"\n");
                    time = 0;
                    for(int k=0; k<1000; k++){                    
                        Utils.iterationNumber = k;
                        if(q==0){
                            Utils.initUniformRandomValuations();
                        }
                        else if(q==1){
                            Utils.initUniformRandomValuationsOrderedInstances();                             
                            Utils.valuations.printValuations();
                        }
                        else if(q==2){
                            Utils.initGaussianRandomValuations(); 
                        }
                        else {
                            Utils.initGaussianRandomValuationsOrderedInstances(); 
                        }
                        
                        //Utils.initStaticValuations();  
                        //Utils.valuations.printValuations();
                        //Utils.initStaticValuationsCounterExample();
                        long startTime = System.currentTimeMillis();
                        boolean isGMMS = GMMS_simulations();
                        if(!isGMMS){
                            Utils.valuations.printValuations(bw);
                        }
                        System.out.print(k+":"+isGMMS);  
                        bw.write(k+":"+isGMMS);
                        long endTime = System.currentTimeMillis();
                        double timeDiff = (endTime-startTime);
                        time += timeDiff;
                        System.out.println(", Total Time Taken = "+(timeDiff)/1000.0+" seconds");
                        bw.write(", Total Time Taken = "+(timeDiff)/1000.0+" seconds\n");
                    }
                    bw.write("\n Average Time Taken = "+time/1000.0+" milliseconds\n");
                    bw.close();
                }
            }
        }       
    }
    
    public static boolean GMMS_simulations() throws Exception{
        HashSet<Items> itemSet = new HashSet<Items>();
        itemSet.addAll(Utils.all_items);     
        
        HashSet<HashSet<HashSet<Items>>> all_allocations = Utils.get_n_partitions(itemSet, Utils.n);  
        
        //System.out.println(all_allocations.size());
        
        
        //System.out.println("All permutations of agent obtained");
        for(HashSet<HashSet<Items>> partition: all_allocations){ 
            //long startTime = System.currentTimeMillis();
            for(ArrayList<Integer> agentOrder: Utils.permute_agents){
                int [] allocations = Utils.createPartitionArray(partition, agentOrder);
                //System.out.println("Partition array created");
                Utils.initialize_allocation(allocations);
                //Utils.allocations.printAllocations();
                if(groupwiseMMS.checkGMMS()){
                    //System.out.println("The allocation is GMMS");
                    //Utils.allocations.printAllocations();
                    return true;
                }
                else{
                    //System.out.println("The allocation is NOT GMMS");
                    
                }
                //System.out.println("*************************");
            } 

            //long endTime = System.currentTimeMillis();
            //System.out.println("Time Taken = "+(endTime-startTime)/1000.0+" seconds");            
        }
        return false;
    }
    
    public static Allocations EF1L_algo(){
        Allocations allocation = new Allocations();
        int [] allocation_array = new int[Utils.all_items.size()];
        for(int i=0; i<allocation_array.length; i++){
            allocation_array[i]=-1;
        }
        DAG dag = new DAG(Utils.all_agents.size());
        
        HashSet<Items> M = new HashSet<Items>();
        M.addAll(Utils.all_items);
        
        while(!M.isEmpty()){      
//            System.out.print("Sources:");
//            for(Integer i: dag.sources){
//                System.out.print(i+", ");
//            }
//            System.out.println();
//            
            //Choose a source agent   
            Integer agent_id = dag.sources.removeFirst();
//            System.out.println("Source agent: "+agent_id);
            double maxValue = -1;
            Items maxItem = null;
            for(Items item: M){
                double value = Utils.valuations.getValuation_for(agent_id, item.getItem_id());
                if(maxValue<value){
                    maxValue = value;
                    maxItem = item;
                }
            }
            
            //Give her the most desirable item
            allocation_array[maxItem.getItem_id()] = agent_id;
//            System.out.println("Give her the item: "+maxItem.getItem_id());
            M.remove(maxItem);
            
            //Update the allocation
            allocation.initialize_allocation(allocation_array);
//            allocation.printAllocations();
            
            //Update DAG
            Agents agent_i = Utils.all_agents.get(agent_id);
            for(Agents agent_j: Utils.all_agents){
//                System.out.println("i="+agent_id+",j="+agent_j.getAgent_id());
                double agent_j_valueFor_j = Utils.totalValueOf_Items(agent_j, allocation.getItems_allocated(agent_j));
                double agent_j_valueFor_i = Utils.totalValueOf_Items(agent_j, allocation.getItems_allocated(agent_i));
//                System.out.println(agent_j_valueFor_j+","+agent_j_valueFor_i);
                if(agent_j_valueFor_j < agent_j_valueFor_i){
//                    System.out.println("Agent "+agent_j.getAgent_id()+" envies "+agent_i.getAgent_id());
                    dag.addEdge(agent_j.getAgent_id(), agent_i.getAgent_id());
                }                

                if(allocation.map_item_to_agent.values().contains(agent_j)){
                    double agent_i_valueFor_j = Utils.totalValueOf_Items(agent_i, allocation.getItems_allocated(agent_j));
                    double agent_i_valueFor_i = Utils.totalValueOf_Items(agent_i, allocation.getItems_allocated(agent_i));
                    if(agent_i_valueFor_i > agent_i_valueFor_j){
//                        System.out.println("Agent "+agent_i.getAgent_id()+" does not envy "+agent_j.getAgent_id());
                        dag.removeEdge(agent_i.getAgent_id(), agent_j.getAgent_id());
                    }
                }
            }
            
//            System.out.println("Adjacency list:");
//            for (int i : dag.adjacencyList.keySet()) {
//              System.out.println(i + " : " + dag.adjacencyList.get(i));
//            }
            
            //Check for cycles. If cycle is present, update allocation again
            while(true){
                LinkedList<Integer> C =  dag.getCycle();
//                System.out.println("Cycle: "+C);            
                
                            
                if(C!=null && C.size()!=0){
                    //Store set of agents who envied 0
                    HashSet<Integer> envied0 = new HashSet<Integer>();
                    for(int j=0; j<Utils.all_agents.size(); j++){
                        if(!C.contains(j)){
                           if(dag.adjacencyList.get(j).contains(C.get(0))){
                               envied0.add(j);
                           }
                        }
                    }
                    
                    //Update adjacency list and allocation and edge within C
                    for(int i=1; i<C.size(); i++){
                        for(Items item: allocation.getItems_allocated(Utils.all_agents.get(C.get(i)))){
//                            System.out.println(item+" given to "+C.get(i-1));
                            allocation_array[item.getItem_id()] = C.get(i-1);
                        }
                        dag.removeEdge(C.get(i-1), C.get(i));  
                        for(int j=0; j<Utils.all_agents.size(); j++){
                            if(!C.contains(j)){
                                boolean envies_i = dag.adjacencyList.get(j).contains(C.get(i));
                                boolean envies_i_1 = dag.adjacencyList.get(j).contains(C.get(i-1));
                                
                                if(envies_i){
                                    dag.adjacencyList.get(j).remove(C.get(i));
                                    if(!envies_i_1){
                                        dag.adjacencyList.get(j).add(C.get(i-1));
                                    }
                                }
                                else{
                                    dag.adjacencyList.get(j).remove(C.get(i-1));
                                }
                            }
                        }
                    }
                    
                    for(Items item: allocation.getItems_allocated(Utils.all_agents.get(C.get(0)))){
//                        System.out.println(item+" given to "+C.get(C.size()-1));
                        allocation_array[item.getItem_id()] = C.get(C.size()-1);
                    }
                    dag.removeEdge(C.get(C.size()-1), C.get(0));
    
                    for(Integer k: envied0){
                        //dag.adjacencyList.get(k).remove(C.get(0));
                        dag.adjacencyList.get(k).add(C.get(C.size()-1));
                    }
                    for(int j=0; j<Utils.all_agents.size(); j++){
                        if(!envied0.contains(j)){
                            dag.adjacencyList.get(j).remove(C.get(C.size()-1));
                        }
                    }
                    
                    //Store the updated allocation
//                    System.out.println("Further update:");
                    allocation.initialize_allocation(allocation_array);
//                    allocation.printAllocations();                
    
                    //Further edge Resolution from agents in cycle to other agents
                    for(int i=1; i<C.size(); i++){
                        for(int j=0; j<Utils.all_agents.size(); j++){
                            if(!C.contains(j)){
                                double agent_i_valueFor_j = Utils.totalValueOf_Items(agent_i, allocation.getItems_allocated(Utils.all_agents.get(j)));
                                double agent_i_valueFor_i = Utils.totalValueOf_Items(agent_i, allocation.getItems_allocated(agent_i));
                                if(agent_i_valueFor_i > agent_i_valueFor_j){
//                                    System.out.println("Agent "+agent_i.getAgent_id()+" does not envy "+j);
                                    dag.removeEdge(agent_i.getAgent_id(), j);
                                } 
                            }
                        }
                    }
                  
                    
                    for(int i=0; i<Utils.all_agents.size(); i++){
                        dag.isNodeAddToSources(new Integer(i));
                    }
                    
//                    System.out.println("After cycle resolution: Adjacency list:");
//                    for (int i : dag.adjacencyList.keySet()) {
//                      System.out.println(i + " : " + dag.adjacencyList.get(i));
//                    }
                }
                else{
//                    System.out.println("No cycle");
                    for(int i=0; i<Utils.all_agents.size(); i++){
                        dag.isNodeAddToSources(new Integer(i));
                    }
                    break;
                } 
            }
            
        }
        
        return allocation;
    }
    
    public static void distributions_Uniform() throws Exception{
        File dir = new File("results_Uniform");
        dir.mkdirs();
        double time;
        for(int i=3; i<=5; i++){
            int n=i;      
            
            for(int j=i; j<=11; j++){
                int m=j;

                new Utils(n,m);

                System.out.println("n="+n+" and m="+m);
                File file = new File(dir, "resultUniform_"+n+"_"+m+".txt");
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("*******n="+n+" and m="+m+"\n");
                time = 0;
                for(int k=0; k<1000; k++){                    
                    Utils.iterationNumber = k;
                    
                    Utils.initUniformRandomValuations(); 
                    long startTime = System.currentTimeMillis();
                    boolean isGMMS = GMMS_simulations();
                    if(!isGMMS){
                        Utils.valuations.printValuations(bw);
                    }
                    System.out.print(k+":"+isGMMS);  
                    bw.write(k+":"+isGMMS);
                    long endTime = System.currentTimeMillis();
                    double timeDiff = (endTime-startTime);
                    time += timeDiff;
                    System.out.println(", Total Time Taken = "+(timeDiff)/1000.0+" seconds");
                    bw.write(", Total Time Taken = "+(timeDiff)/1000.0+" seconds\n");
                }
                bw.write("\n Average Time Taken = "+time/1000.0+" milliseconds\n");
                bw.close();                
            }
        }       
    }
    
    public static void distributions_UniformOrdered() throws Exception{
        File dir = new File("results_UniformOrdered");
        dir.mkdirs();
        double time;
        for(int i=3; i<=5; i++){
            int n=i;      
            
            for(int j=i; j<=11; j++){
                int m=j;

                new Utils(n,m);
                System.out.println("n="+n+" and m="+m);
                File file = new File(dir, "resultUniformOrdered_"+n+"_"+m+".txt");
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("*******n="+n+" and m="+m+"\n");
                time = 0;
                for(int k=0; k<1000; k++){                    
                    Utils.iterationNumber = k;
                    
                    Utils.initUniformRandomValuationsOrderedInstances(); 
                    long startTime = System.currentTimeMillis();
                    boolean isGMMS = GMMS_simulations();
                    if(!isGMMS){
                        Utils.valuations.printValuations(bw);
                    }
                    System.out.print(k+":"+isGMMS);  
                    bw.write(k+":"+isGMMS);
                    long endTime = System.currentTimeMillis();
                    double timeDiff = (endTime-startTime);
                    time += timeDiff;
                    System.out.println(", Total Time Taken = "+(timeDiff)/1000.0+" seconds");
                    bw.write(", Total Time Taken = "+(timeDiff)/1000.0+" seconds\n");
                }
                bw.write("\n Average Time Taken = "+time/1000.0+" milliseconds\n");
                bw.close();                
            }
        }       
    }
    
    public static void distributions_Gaussian() throws Exception{
        File dir = new File("results_Gaussian");
        dir.mkdirs();
        double time;
        for(int i=3; i<=5; i++){
            int n=i;      
            
            for(int j=i; j<=11; j++){
                int m=j;

                new Utils(n,m);

                System.out.println("n="+n+" and m="+m);
                File file = new File(dir, "resultGaussian_"+n+"_"+m+".txt");
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("*******n="+n+" and m="+m+"\n");
                time = 0;
                for(int k=0; k<1000; k++){                    
                    Utils.iterationNumber = k;
                    
                    Utils.initGaussianRandomValuations(); 
                    long startTime = System.currentTimeMillis();
                    boolean isGMMS = GMMS_simulations();
                    if(!isGMMS){
                        Utils.valuations.printValuations(bw);
                    }
                    System.out.print(k+":"+isGMMS);  
                    bw.write(k+":"+isGMMS);
                    long endTime = System.currentTimeMillis();
                    double timeDiff = (endTime-startTime);
                    time += timeDiff;
                    System.out.println(", Total Time Taken = "+(timeDiff)/1000.0+" seconds");
                    bw.write(", Total Time Taken = "+(timeDiff)/1000.0+" seconds\n");
                }
                bw.write("\n Average Time Taken = "+time/1000.0+" milliseconds\n");
                bw.close();                
            }
        }       
    }
    
    public static void distributions_GaussianOrdered() throws Exception{
        File dir = new File("results_GaussianOrdered");
        dir.mkdirs();
        double time;
        for(int i=3; i<=5; i++){
            int n=i;      
            
            for(int j=i; j<=11; j++){
                int m=j;

                new Utils(n,m);
                System.out.println("n="+n+" and m="+m);
                File file = new File(dir, "resultGaussianOrdered_"+n+"_"+m+".txt");
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("*******n="+n+" and m="+m+"\n");
                time = 0;
                for(int k=0; k<1000; k++){                    
                    Utils.iterationNumber = k;
                    
                    Utils.initGaussianRandomValuationsOrderedInstances(); 
                    long startTime = System.currentTimeMillis();
                    boolean isGMMS = GMMS_simulations();
                    if(!isGMMS){
                        Utils.valuations.printValuations(bw);
                    }
                    System.out.print(k+":"+isGMMS);  
                    bw.write(k+":"+isGMMS);
                    long endTime = System.currentTimeMillis();
                    double timeDiff = (endTime-startTime);
                    time += timeDiff;
                    System.out.println(", Total Time Taken = "+(timeDiff)/1000.0+" seconds");
                    bw.write(", Total Time Taken = "+(timeDiff)/1000.0+" seconds\n");
                }
                bw.write("\n Average Time Taken = "+time/1000.0+" milliseconds\n");
                bw.close();                
            }
        }       
    }
}
