package DisjointSets;

/**
 * Data type for the users (objects) to be added to a disjoint set
 * 
 * @author Radhouane
 * @version 2/23/2020
 */
public class User
{
    private String value;
    private Set container;
    private User next;
    
    public User(String value){
        this.value = value;
    }
    
    public void setValue(String value){this.value = value;}
    public void setContainer(Set container){this.container = container;}
    public void setNext(User next){this.next = next;}
    
    public String getValue(){return value;}
    public Set getContainer(){return container;}
    public User getNext(){return next;}
    
    public String toString(){return " " + value + " ";}
}
