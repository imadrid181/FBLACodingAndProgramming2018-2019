/** 
 * A E-Book object which contains the name of the E-Book, what class it's used for, the redemption code, if the books been redemed, and the owner of the E-Book.
 * Each E-Book can only be redemed once and can only have one owner.
*/
public class EBook {
    private String bookName;
    private String classFor;
    private String redemptionCode;
    private boolean redemptionStatus;
    private Student owner;
    private int rowLocation; 

    /** 
     *Default Constructor for an E-Book Object. Sets all elements to null and the boolean to false.
     */
    public EBook() {
        this.bookName = null;
        this.classFor = null;
        this.redemptionCode = null;
        this.redemptionStatus = false;
        this.owner = null;
        this.rowLocation = 0;
    }

    /**
     * Constructs an EBook Object for a E-Book that has not been redemed and has no owner.
     * @param name The name of the E-Book.
     * @param classFor The name of the class that the E-Book is used for.
     * @param code The redemption code of the E-Book.
     */
    public EBook(String name, String classFor, String code, int rowLocation){
        this.bookName = name;
        this.classFor = classFor;
        this.redemptionCode = code;
        this.redemptionStatus = false;
        this.owner = null;
        this.rowLocation = rowLocation;
    }

    /**
     * Constructs an E-Book Object for a E-Book that has been redemed and has an owner.
     * @param name The name of the E-Book.
     * @param classFor The name of the class that the E-Book is used for.
     * @param code The redemption code of the E-Book.
     * @param owner The owner of the E-Book.
     */
    public EBook(String name, String classFor, String code, Student owner, int rowLocation){
        this.bookName = name;
        this.classFor = classFor;
        this.redemptionCode = code;
        this.redemptionStatus = true;
        this.owner = owner;
        this.rowLocation = rowLocation;
    }

    /**
     * Retrieves the name of the E-Book.
     * @return The Name of the E-Book.
     */
    public String getBookName(){
        return this.bookName;
    }

    /**
     * Retrieves the name of the class that the E-Book is used for.
     * @return The name of the class that the E-Book is used for.
     */
    public String getClassFor(){
        return this.classFor;
    }

    /**
     * Retrieves the redemption code of the E-Book.
     * @return The redemption code of the E-Book.
     */
    public String getRedemptionCode(){
        return this.redemptionCode;
    }

    /**
     * Retrieves the redemption status of the E-Book.
     * @return The redemption status of the E-Book.
     */
    public boolean getRedemptionStatus(){
        return this.redemptionStatus;
    }

    /**
     * Retrieves the owner, which is a student object, of the E-Book.
     * @return The owner of the E-Book.
     */
    public Student getOwner(){
        return this.owner;
    }

    public int getRowLocation(){
        return this.rowLocation;
    }

    /**
     * Changes the name of the E-Book
     * @param bookName The new name of the E-Book.
     */
    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    /**
     * Changes the name of the class that the E-Book is used for.
     * @param classFor The new name of the class that the E-Book is used for.
     */
    public void setClassFor(String classFor){
        this.classFor = classFor;
    }

    /**
     * Changes the redemption code of the E-Book.
     * @param redemptionCode The new redemption code of the E-Book.
     */
    public void setRedemptionCode(String redemptionCode){
        this.redemptionCode = redemptionCode;
    }

    /**
     * Changes the redemption status of the E-Book.
     * @param redemptionStatus The new redemption status of the E-Book.
     */
    public void setRedemptionStatus(boolean redemptionStatus){
        this.redemptionStatus = redemptionStatus;
    }

    /**
     * Changes the owner of the E-Book.
     * @param owner The new owner of the E-Book.
     */
    public void setOwner(Student owner){
        this.owner = owner;
    }

    public void setRowLocation(int rowLocation){
        this.rowLocation = rowLocation;
    }

    /**
     * Returns all the information of the E-Book in a string.
     * @return String containing all the information of the E-Book.
     */
    public String toString(){
        if(this.owner != null){
            String book =  "Name: "+this.bookName+". Book is for "+this.classFor+". Redemption code is "+this.redemptionCode+". Redemption status is "
                            +this.redemptionStatus+". The owner of the book is"+this.owner.getName();
            return book;
        }
        else{
            String book =  "Name: "+this.bookName+". Redemption code is: "+this.redemptionCode;
            return book;
        }
        
    }    
}