import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class Database {
    private LinkedList<EBook> listObject;
    private int spaceOccupied;
    private Boolean isEmpty;
    private File storage;
    private FileWriter fw;
    private BufferedWriter output;
    private FileReader fr;
    private BufferedReader reader;

    /**
     * Default constructor for the Database. Creates Linked List, sets spaceOccupied to 0, set isEmpty to 
     * false, creates a txt file for storage file with the name Default.txt, creates FileWriter, BufferedWriter, FileReader,
     * and BufferedReader for the storage file. 
     * @throws IOException If the file is not found.
     */
    public Database() throws IOException {
        this.listObject = new LinkedList<EBook>();
        this.spaceOccupied = 0;
        this.isEmpty = false;
        this.storage = new File("Default.txt");
    }

    /**
     * Constructor for the Database that takes a String parameter of the name of the storage file. Creates 
     * Linked List, sets spaceOccupied to 0, set isEmpty to false, creates a txt file for storage with the 
     * name from String provided, creates FileWriter, BufferedWriter, FileReader, and BufferedReader for 
     * the storage file. 
     * @param fileName The name of the storage file.
     * @throws IOException If the file is not found.
     */
    public Database(String fileName) throws IOException {
        this.listObject = new LinkedList<EBook>();
        this.spaceOccupied = 0;
        this.isEmpty = false;
        this.storage = new File(fileName+".txt");
    }

    /**
     * Retrieves the number of items stored in the Linked List.
     * @return The number of items stored in the Linked List.
     */
    public int getSpaceOccupied() {
        return this.spaceOccupied;
    }

    /**
     * Retrieves if the Linked List is empty and has nothing stored in it.
     * @return True if the Linked List is empty. False if it is not.
     */
    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    /**
     * Retrieves a object from a certain position in the LinkedList.
     * @param position The position of the object you want to retrieve.
     * @return The object at the position in the LinkedList.
     */
    public Object get(int position) {
        return this.listObject.get(position);
    }

    /**
     * Inserts an object into the Linked List. First it checks if the object is null and if the object is 
     * already in the Linked List. If it fails the first two checks then the method is treminated. If not
     * it sets isEmpty to true if it is false, adds the object to the Linked List, increases the 
     * spaceOccupied by 1 and then writes the toString of the object to the file.
     * @param o The EBook that you want to insert into the Linked List.
     * @return True if the object was successfuly put in. False if it is not.
     */
    public boolean put(EBook e) throws IOException {
        if(e == null) {
            return false; 
        }
        
        if(this.listObject.contains(e)) {
            return false;
        }

        if(this.isEmpty == false) {
            this.isEmpty = true;
        }
        
        fw = new FileWriter(this.storage, true);
        output = new BufferedWriter(fw);

        this.listObject.add(e);
        this.spaceOccupied++;

        output.write(e.toString());
        output.write(System.lineSeparator());
        output.flush();
        output.close();
        return true;
    }

    /**
     * Searches the Linked List to see if a specific EBook is in it.
     * @param e The EBook that will be checked to see if it is in the Linked List.
     * @return True if the EBook is in the Linked List. False if it is not.
     */
    public boolean contains(EBook e) {
        if(e.equals(null))
            return false;
        
        if(this.isEmpty == true)
            return false; 

        if(this.listObject.contains(e))
            return true;
        
        else
            return false;
    }

    /**
     * ISSUE. LOOK MORE INTO IT
     */
    public boolean remove(EBook o) throws IOException{
        if(o.equals(null))
            return false;

        if(!this.listObject.contains(o))
            return false;

        else {
            fr = new FileReader(this.storage);
            reader = new BufferedReader(fr);
            
            File tempFile = new File("Temp.txt");
            
            BufferedWriter tempWritter = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = o.toString();
            String currentLine;

            while((currentLine = reader.readLine().trim()) != null){
                String line = currentLine.trim();
                if(!line.equals(lineToRemove)) {
                    tempWritter.write(currentLine);
                    tempWritter.write(System.lineSeparator());
                }
            }

            tempWritter.close();
            reader.close();
            System.out.println(tempFile.renameTo(this.storage));
            this.storage = tempFile;

            this.listObject.remove(o);
            this.spaceOccupied--;
            return true;
        }
        
    }

    public boolean searchForRedemptionCodes(String redemptionCode) {
        if(this.isEmpty)
            return false;
        else {
            for(int i = 0; i < this.listObject.size(); i++) {
                String temp = this.listObject.get(i).getRedemptionCode();
                if(temp.equals(redemptionCode))
                    return true;
            }
            return false;
        }
    }
    
    /**
     * Fills the linked list with E-Books form the file.
     * @param FileName The name of the file you want to populate from.
     * @return The number of E-Books added to the Linked List.
     * @throws IOException If the file does not exist.
     */
    public int populateFromFile(String FileName) throws IOException {
        try {
            File inputFile = new File(FileName);
            BufferedReader tempReader = new BufferedReader(new FileReader(inputFile));
            String currentLine;

            while((currentLine = tempReader.readLine()) != null){
                String[] values = currentLine.split(",");

                if(values.length == 6) {
                    EBook temp = new EBook(values, this.spaceOccupied);
                    this.listObject.add(temp);
                    this.spaceOccupied++;
                }
                else {
                    EBook temp = new EBook(values, this.spaceOccupied);
                    this.listObject.add(temp);
                    this.spaceOccupied++;
                }

            }

            tempReader.close();
            return this.spaceOccupied;

        } catch(FileNotFoundException e){
            return 0;
        }
    }


}