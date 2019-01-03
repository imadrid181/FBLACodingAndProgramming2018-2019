import java.util.LinkedList;

public class Database{
    private LinkedList listObject;
    private int spaceOccupied;
    private Boolean isEmpty;

    public Database(){
        this.listObject = new LinkedList<Object>();
        this.spaceOccupied = 0;
        this.isEmpty = false;
    }

    public boolean put(Object o){
        if(o == null) {
            return false; 
        }
        
        if(this.listObject.contains(o)){
            return false;
        }
            
        this.listObject.add(o);
        this.spaceOccupied++;
        this.isEmpty = false;
        return true;
    }


    public boolean contains(Object o){
        if(o.equals(null))
            return false;
        
        if(this.isEmpty == true)
            return false; 

        if(this.listObject.contains(o)){
            return true;
        }
        else
            return false;
    }

    public boolean remove(Object o){
        if(o.equals(null))
            return false;

        if(!this.listObject.contains(o))
            return false;
        else {
            this.listObject.remove(o);
            this.spaceOccupied--;
            return true;
        }
        
    }

    public int getSpaceOccupied(){
        return this.spaceOccupied;
    }
    
    public Object get(int position){
        return this.listObject.get(position);
    }


}