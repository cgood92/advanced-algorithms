package DisjointSets;

/**
 * Datatype for a set
 * 
 * @author Radhouane 
 * @version 2/23/2020
 */
public class Set
{
    Item head;
    Item tail;
    
    //Empty set
    public Set(){
        head = null; tail = null;
    }
    
    //Singleton
    public Set(Item x){
        x.setContainer(this);
        this.head = x;
        this.tail = x;
    }
    
    public void setHead(Item head){
        this.head = head;
    }
    public void setTail(Item tail){
        this.tail = tail;
    }
    public Item getHead(){
        return head;
    }
    public Item getTail(){
        return tail;
    }
    
    public void merge(Set other){
        getTail().setNext(other.getHead());
        
        Item current = other.getHead();
        
        while(current != null){
            current.setContainer(this);
            current = current.getNext();
        }
        
        setTail(other.getTail());
    }
    
    public Item find(char x){
        Item current = getHead();
        while(current != null){
            //System.out.println("Looping in find with current = " + current);
            if(current.getValue() == x) return current;
            current = current.getNext();
        }
        //System.out.println("Leaving find()");
        return current;
    }
    
    public String toString(){
        String s = "";
        Item current = head;
        while(current != null){
            s += current.toString();
            current = current.getNext();
        }
        return s;
    }
}
