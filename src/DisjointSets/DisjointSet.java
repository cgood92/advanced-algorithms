package DisjointSets;

/**
 * A Disjoint sets type
 * 
 * @author Radhouane 
 * @version 2/23/2020
 */
import java.util.*;
public class DisjointSet
{
    ArrayList<Set> sets;
    
    public DisjointSet(){
        sets = new ArrayList<Set>();
    }
    
    public void make_set(User x){//O(1)
        sets.add(new Set(x)); 
    }
    
    public User find_set(User x){
        return x.getContainer().getHead();
        
    }
    public void union(String x, String y){
         for(int i = 0; i < sets.size(); i++){
             for(int j = i + 1; j < sets.size(); j++)
             {
                 if(sets.get(i).find(x) != null &&
                    sets.get(j).find(y) != null){
                     sets.get(i).merge(sets.get(j));
                     sets.remove(j);
                     return;
                 }
             }
         }
    }
    
    public String toString(){
        String str = "";
        for(Set s: sets) str += "{" + s.toString() + "}" + "\t";
        return str;
    }
    
}
