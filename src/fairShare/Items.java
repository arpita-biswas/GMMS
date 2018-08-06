package fairShare;

/**
 * Items class represents an item, identified by item_id
 * @author Arpita
 *
 */

public class Items {
	private int item_id;

	public Items(int id){
		item_id = id;
	}
    
    public int getItem_id() {
        return item_id;
    }

    @Override
    public String toString(){
        return "Item:"+this.item_id;        
    }
    
    @Override
    public boolean equals(Object o){
        Items item = (Items)o;
        if(this.item_id == item.item_id){
            return true;
        }
        return false;       
    }
	
}
