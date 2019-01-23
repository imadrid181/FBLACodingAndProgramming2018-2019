/**
 * Student Object that holds the name of a student, the current grade level of the student, and a list of E-Books that the students own.
 */
public class Student {
    private String name;
    private int gradeLevel;

    /**
     * Default constructor for the Student Object. Sets the name to null, the grade level to 0 and creates the list containing the E-Books owned by the student.
     */
    public Student() {
        this.name = null;
        this.gradeLevel = 0;
    }

    /**
     * Constructs a Student Object with the name and grade level of the student.
     * @param Name The name of the Student.
     * @param Grade The grade level of the Student.
     */
    public Student(String Name, int Grade){
        this.name = Name;
        this.gradeLevel = Grade;
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
     * Checks if the Student Object is equal to another Student Object by comparing all fields of the two objects.
     * @param s Another Student Object.
     * @return True if the Objects are the same, False if the Objects are different.
     */
    public boolean equals(Student s){
        try{
            if(this.name.equals(s.getName())){
                if(this.gradeLevel == s.getGradeLevel()){
                        return true;
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
        String student = this.name+", "+this.gradeLevel;
        return student;
    }
}