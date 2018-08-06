package fairShare;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DAG {
  private int numVertices;
  public Map<Integer, List<Integer>> adjacencyList;
  LinkedList<Integer> sources;
  private boolean hasCycle;
  private LinkedList<Integer> cycleVertices;
  private boolean visited[];
  private boolean onstack[];
  private int cycleVertex = -1;


  public DAG(int numVertices) {
    this.numVertices = numVertices;
    adjacencyList = new HashMap<Integer, List<Integer>>();
    sources = new LinkedList<Integer>();
    for (int i = 0; i < numVertices; i++) {
      adjacencyList.put(i, new LinkedList<Integer>());
      sources.add(new Integer(i));
    } 
    initParamsForCycle();
  }
  
  public void initParamsForCycle(){
      visited = new boolean[numVertices];
      onstack = new boolean[numVertices];
      cycleVertices = new LinkedList<>(); 
      hasCycle = false;
      cycleVertex = -1;
  }

  public void addEdge(int src, int dest) {
    List<Integer> edgeList = adjacencyList.get(src);
    if(!edgeList.contains(dest))
        edgeList.add(dest);
    if(sources.contains(dest))
        sources.remove(sources.indexOf(dest));
  }

  public void removeEdge(int src, int dest) {
      List<Integer> edgeList = adjacencyList.get(src);
      if(edgeList.size()>0 && edgeList.contains(dest)){
          edgeList.remove(edgeList.indexOf(dest));
      }          
  }
  
  public void isNodeAddToSources(Integer node){
      //System.out.println("Node: "+node);
      //System.out.println(adjacencyList.values());
      if(!sources.contains(node)){
          boolean isEnvied = false;
          for(List<Integer> enviedAgents: adjacencyList.values()){
              //System.out.println(enviedAgents);
              if(enviedAgents.contains(node)){
                  isEnvied = true;
                  break;
              }
          } 
          if(!isEnvied){
              sources.add(new Integer(node));
          }
      }
  }
  
  public void findCycle(int u) {
      //System.out.println("FindCycle: "+u);
      visited[u] = true;
      onstack[u] = true;

      for (int n : adjacencyList.get(u)) {
        if(!visited[n]) {
          findCycle(n);
          if (hasCycle && !cycleVertices.contains(cycleVertex)) {
            cycleVertices.add(n);
            break;
          }
        } else if (onstack[n]) {
          hasCycle = true;
          cycleVertex = n;
          break;
        }
      }
      onstack[u] = false;
      if (u == cycleVertex) {
        cycleVertices.add(u);
      }
    }
  

      public LinkedList<Integer> getCycle() {
        //System.out.println("In getCycle");
        initParamsForCycle();
        for (int i : adjacencyList.keySet()) {
          //System.out.println("i="+i+", hasCycle:"+hasCycle+", visited:"+visited[i]);
          if (!hasCycle && !visited[i]) {
            findCycle(i);
          }
        }
        if (hasCycle) {
          // Reverse the list as the cycleVertices is populated
          // in reverse order by findCycle.
          Iterator<Integer> x = cycleVertices.descendingIterator();
          LinkedList<Integer> reverseList = new LinkedList<>();
          //reverseList.add(source);
          while (x.hasNext()) {
            reverseList.add((Integer) x.next());
          }
    
          cycleVertices = reverseList;
          return cycleVertices;
        }
        return null;
      }


    public LinkedList<Integer> getCycle(int source) {
      findCycle(source);
      if (hasCycle) {
        // Reverse the list as the cycleVertices is populated
        // in reverse order by findCycle.
        Iterator<Integer> x = cycleVertices.descendingIterator();
        LinkedList<Integer> reverseList = new LinkedList<>();
        //reverseList.add(source);
        while (x.hasNext()) {
          reverseList.add((Integer) x.next());
        }
        cycleVertices = reverseList;
        return cycleVertices;
      }
      return null;
    }


//  public static void main(String[] args) {
//      DAG dg = new DAG(3);
//      dg.addEdge(1,2);
//      dg.addEdge(2,1);
//
//      for (int i : dg.adjacencyList.keySet()) {
//        System.out.println(i + " : " + dg.adjacencyList.get(i));
//      }
//
//      LinkedList<Integer> cycle = dg.getCycle();
//
//      System.out.println("Cycle: " + cycle);
//
//  }
}
