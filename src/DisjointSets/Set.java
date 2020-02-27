package DisjointSets;

/**
 * Datatype for a set
 * 
 * @author Radhouane 
 * @version 2/23/2020
 */
public class Set
{
    User head;
    User tail;
    
    public Set(){
        head = null; tail = null;
    }
    
    public Set(User x){
        x.setContainer(this);
        this.head = x;
        this.tail = x;
    }
    
    public void setHead(User head){
        this.head = head;
    }
    public void setTail(User tail){
        this.tail = tail;
    }
    public User getHead(){
        return head;
    }
    public User getTail(){
        return tail;
    }
    
    public void merge(Set other){
        getTail().setNext(other.getHead());
        
        User current = other.getHead();
        
        while(current != null){
            current.setContainer(this);
            current = current.getNext();
        }
        
        setTail(other.getTail());
    }
    
    public User find(String x){
        User current = getHead();
        while(current != null){
            if(current.getValue() == x) return current;
            current = current.getNext();
        }
        return current;
    }
    
    public String toString(){
        String s = "";
        User current = head;
        while(current != null){
            s += current.toString();
            current = current.getNext();
        }
        return s;
    }
}
