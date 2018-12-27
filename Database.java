
public class Database{
    private Object[] array;
    private int capacity;
    private int spaceOccupied;
    private float loadFactor;

    public Database(){
        this.array = new Object[10];
        this.capacity = 10;
        this.spaceOccupied = 0;
        this.loadFactor = 0.75f;
    }

    public Database(int capacity){
        this.array = new Object[capacity];
        this.capacity = capacity;
        this.spaceOccupied = 0;
        this.loadFactor = 0.75f;
    }

    public Database(int capacity, float loadFactor){
        this.array = new Object[capacity];
        this.capacity = capacity;
        this.spaceOccupied = 0;
        this.loadFactor = 0.75f;
    }

    public boolean put(Object o){
        if(o.equals(null))
            return false;
        if((this.spaceOccupied / this.capacity) > this.loadFactor)
            this.rehash();
        int position = o.hashCode() % this.capacity;
        while(this.array[position] != null){
            if(position >= this.capacity)
                position = 0;
            else
                position++;
        }
        this.array[position] = o;
        return true;
    }

    public void rehash(){
        Object[] oldArray = new Object[this.capacity];

        for(int i = 0; i < this.capacity; i++)
            oldArray[i] = this.array[i];

        this.capacity = this.capacity * 2;
        this.array = new Object[capacity];

        for(int i = 0; i < oldArray.length; i++){
            if(oldArray[i] != null)
                this.put(oldArray[i]);
        }
    }

    public boolean contains(Object o){
        if(o.equals(null))
            return false;

        int position = o.hashCode() % this.capacity;
        while(this.array[position] != null){
            if(position >= this.capacity)
                position = 0;
            if(this.array[position].equals(o)){
                return true;
            }
            else
                position++;
        }
        return false;
    }

    public boolean remove(Object o){
        if(o.equals(null))
            return false;
        int position = o.hashCode() % this.capacity;
        while(this.array[position] != null){
            if(this.array[position].equals(o)){
                this.array[position] = null;
                this.spaceOccupied--;
                return true;
            }
            else
             position++;
        }
        return false;
        
    }

    public boolean removeAtPosition(int position){
        if(position >= this.capacity)
            return false;
        this.array[position] = null;
        this.spaceOccupied--;
        return true;
    }

    public Object get(int position){
        return this.array[position];
    }


}