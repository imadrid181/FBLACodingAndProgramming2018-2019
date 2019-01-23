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
     * @param rowLocation The row that the E-Book will be shown in the JTable.
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
     * Constructs an E-Book Object for a E-Book that has been redeemed and has an owner.
     * @param name The name of the E-Book.
     * @param classFor The name of the class that the E-Book is used for.
     * @param code The redemption code of the E-Book.
     * @param owner The owner of the E-Book.
     * @param rowLocation The row that the E-Book will be shown in the JTable.
     */
    public EBook(String name, String classFor, String code, Student owner, int rowLocation){
        this.bookName = name;
        this.classFor = classFor;
        this.redemptionCode = code;
        this.redemptionStatus = true;
        this.owner = owner;
        this.rowLocation = rowLocation;
    }

    public EBook(String[] values, int rowNumber){
        this.bookName = values[0].trim();
        this.classFor = values[1].trim();
        this.redemptionCode = values[2].trim();
        this.redemptionStatus = Boolean.parseBoolean(values[3].trim());
        this.owner = new Student(values[4].trim(), Integer.parseInt(values[5].trim()));
        this.rowLocation = rowNumber;
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

    /**
     * Retrieves the row location of the E-Book.
     * @return The row location of the E-Book.
     */
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

    /**
     * Changes the row location of the E-Book.
     * @param rowLocation The new row location of the E-Book.
     */
    public void setRowLocation(int rowLocation){
        this.rowLocation = rowLocation;
    }

    /**
     * Compares two E-Books to see if they are the same.
     * @param s Another E-Book.
     * @return True if E-Books are the same. False if E-Books are different.
     */
    public boolean equals(EBook s){
        try{
            if(this.bookName.equals(s.getBookName())){
                if(this.classFor.equals(s.getClassFor())){
                    if(this.redemptionCode.equals(s.getRedemptionCode())){
                        if(this.redemptionStatus == s.getRedemptionStatus()){
                            if(this.owner.equals(s.getOwner())){
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     * Returns all the information of the E-Book in a string. Add information of Owner if there is a owner.
     * @return String containing all the information of the E-Book.
     */
    public String toString(){
        if(this.owner != null){
            String book =  this.bookName+", "+this.classFor+", "+this.redemptionCode+", "+this.redemptionStatus+", "+this.owner.toString();
            return book;
        }
        else{
            String book =  this.bookName+", "+this.classFor+", "+this.redemptionCode+", "+this.redemptionStatus;
            return book;
        }
        
    }    
}