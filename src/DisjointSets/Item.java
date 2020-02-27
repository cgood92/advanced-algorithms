package DisjointSets;

/**
 * Data type for the items (objects) to be added to a disjoint set
 * 
 * @author Radhouane
 * @version 2/23/2020
 */
public class Item
{
    private char value;
    private Set container;
    private Item next;
    
    public Item(char value, Set container, Item next){
        this.value = value;
        this.container = container;
        this.next = next;
    }
    
    public void setValue(char value){this.value = value;}
    public void setContainer(Set container){this.container = container;}
    public void setNext(Item next){this.next = next;}
    
    public char getValue(){return value;}
    public Set getContainer(){return container;}
    public Item getNext(){return next;}
    
    public String toString(){return " " + value + " ";}
}
