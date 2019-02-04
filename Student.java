import java.util.*;
/**
 * Student Object that holds the name of a student, the current grade level of the student, and a list of E-Books that the students own.
 */
public class Student {
    private String name;
    private int gradeLevel;
    private List booksOwned;

    /**
     * Default constructor for the Student Object. Sets the name to null, the grade level to 0 and creates the list containing the E-Books owned by the student.
     */
    public Student() {
        this.name = null;
        this.gradeLevel = 0;
        this.booksOwned = new LinkedList<EBook>();
    }

    /**
     * Constructs a Student Object with the name and grade level of the student.
     * @param Name The name of the Student.
     * @param Grade The grade level of the Student.
     */
    public Student(String Name, int Grade){
        this.name = Name;
        this.gradeLevel = Grade;
        this.booksOwned =  new LinkedList<EBook>();
    } 

    /**
     * Adds a E-Book to the list of E-Books that the Student owns.
     * @param book The E-Book that will be added to list of books owned.
     */
    public void AddBook(EBook book){
        this.booksOwned.add(book);
    }

    /**
     * Retrieves the name of the Student.
     * @return The name of the Student.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retrieves the grade level of the Student.
     * @return  The grade level of the Student.
     */
    public int getGradeLevel(){
        return this.gradeLevel;
    }

    /**
     * Retrieves the E-Book at a certain position in the list.
     * @param position The position where the E-Book you want to retrieve is located.
     * @return The E-Book at the position.
     */
    public EBook getBookAtPosition(int position){
        return (EBook)(this.booksOwned.get(position));
    }

    /**
     * Changes the name of the Student.
     * @param Name The new name of the Student.
     */
    public void SetName(String Name){
        this.name = Name;
    }

    /**
     * Changes the grade level of the Student.
     * @param Grade The new grade level of the Student.
     */
    public void SetGradeLevel(int Grade){
        this.gradeLevel = Grade;
    }

    /**
     * Changes the E-Book at a position in the list of books owned.
     * @param position The position of the E-Book that you want to change.
     * @param NewEBook The new E-Book.
     */
    public void setBookAtPosition(int position, EBook NewEBook){
        this.booksOwned.set(position, NewEBook);
    }

    /**
     * Checks if the Student Object is equal to another Student Object by comparing all fields of the two objects.
     * @param s Another Student Object.
     * @return True if the Objects are the same, False if the Objects are different.
     */
    public boolean equals(Student s){
        try{
            if(this.name.equals(s.getName())){
                if(this.gradeLevel == s.getGradeLevel()){
                    for(int i = 0; i < this.booksOwned.size(); i++){
                        if(!this.getBookAtPosition(i).equals(s.getBookAtPosition(i))){
                            return false;
                        }
                        return true;
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
     * Returns all the information of the Student in a String.
     * @return A string containing all the information of the Student.
     */
    public String toString(){
        String student = this.name+", "+this.gradeLevel+", "+this.booksOwned.size();
        return student;
    }
} 