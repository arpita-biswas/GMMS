package fairShare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Valuations class represents the valuation of each agent for each item in a 2D array "valuations"
 * + Constructor Valuations(double [][] values) expects a 2D array to initialize "valuations"
 * + getValuations() returns the 2D array "valuations"
 * + setValuation_for(agent, item, value): sets "value" for a single "agent" and a single "item"
 * + setValuation_for(agent_id, item_id, value): sets "value" for agent_id and item_id
 * + getValuation_for(agent, item): returns "value" for a single "agent" and a single "item"
 * + getValuation_for(agent_id, item_id): returns "value" for agent_id and item_id
 * + getValuation_for(agent, itemSet): returns "value" for "agent" and the set of items "itemSet"
 * + getValuation_for(agent_id, itemSet): returns "value" for agent_id and the set of items "itemSet"
 * + printValuations(): prints the values of each agent for each item.
 * 
 * @author Arpita
 *
 */

public class Valuations {
    private double [][] valuations;
    
    public Valuations(){
        valuations = new double[Utils.n][Utils.m];
    }
    
    public Valuations(double[][] values){
        if(values.length!=Utils.n){
            System.out.println("values: #agents mismatch");
            return;
        }
        for(int i=0; i<values.length; i++){
            if(values[i].length != Utils.m){
                System.out.println("values: #items mismatch");
                return;
            }
        }
        for(int i=0; i<Utils.n; i++){
            for(int j=0; j<Utils.m; j++){
                this.valuations[i][j] = values[i][j];
            }
        }
    }
    
    public double[][] getValuations() {
        return this.valuations;
    }

    public void setValuation_for(Agents agent, Items item, double value){
        this.valuations[agent.getAgent_id()][item.getItem_id()] = value;
    }
    
    public void setValuation_for(int agent_id, int item_id, double value){
        if(agent_id >= 0 && agent_id <Utils.n && item_id >= 0 && item_id < Utils.m){
            this.valuations[agent_id][item_id] = value;
        }
        else{
            System.out.println("Agent-Item value not found: ("+agent_id+","+item_id+")");
        }
    }
    
    public double getValuation_for(Agents agent, Items item){
        return this.valuations[agent.getAgent_id()][item.getItem_id()];
    }
    
    public double getValuation_for(int agent_id, int item_id){
        if(agent_id >= 0 && agent_id <Utils.n && item_id >= 0 && item_id < Utils.m){
            return this.valuations[agent_id][item_id];
        }
        else{
            System.out.println("Agent-Item value not found("+agent_id+","+item_id+")");
            return -1;
        }
    }
    
    public double getValuation_for_set(Agents agent, Set<Items> items){
        double sum = 0.0;
        for(Items item: items){
            sum+=getValuation_for(agent, item);
        }
        return sum;
    }
    
    public double getValuation_for_set(int agent_id, Set<Items> items){
        return getValuation_for_set(Utils.all_agents.get(agent_id), items);
    }
    
    public void printValuations(){
        for(int i=0; i<Utils.n; i++){
            System.out.println("Agent "+i+" valuations :");
            for(int j=0; j<Utils.m; j++){
                System.out.println(j+" = "+valuations[i][j]);
            }
        }
    }
    
    public void printValuations(BufferedWriter bw) throws IOException{
        for(int i=0; i<Utils.n; i++){
            bw.write("Agent "+i+" valuations :\n");
            System.out.println("Agent "+i+" valuations :");
            for(int j=0; j<Utils.m; j++){
                System.out.println(j+" = "+valuations[i][j]);
                bw.write(j+" = "+valuations[i][j]+"\n");
            }
        }
    }
}
