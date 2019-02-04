import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * The Database Objects has a Linked List ot hold the objects and a File to store all the information.
 * The File also has the BufferedWriter along with a BufferedReader. 
 */
public class Database {
    private LinkedList<Object> listObject;
    private int spaceOccupied;
    private Boolean isEmpty;
    private File storage;
    private FileWriter fw;
    private BufferedWriter output;
    private FileReader fr;
    private BufferedReader reader;

    /**
     * Constructor for Database that creates the Linked List, File and BufferedWritter and BufferedReader.
     * @param fileName The name of the storage File.
     * @throws IOException If the file cannot be created or is not found.
     */
    public Database(String fileName) throws IOException{
        this.listObject = new LinkedList<Object>();
        this.spaceOccupied = 0;
        this.isEmpty = false;
        this.storage = new File(fileName+".txt");
        this.fw = new FileWriter(storage, true);
        this.output = new BufferedWriter(fw);
        this.fr = new FileReader(storage);
        this.reader =  new BufferedReader(fr);
    }

    /**
     * Inserts an object into the Linked List and writes its' information in the storage File.
     * @param o The object you want to insert.
     * @return True if successful, False if not.
     * @throws IOException If the storage File is not found.
     */
    public boolean put(Object o) throws IOException{
        if(o == null) {
            return false; 
        }
        
        if(this.listObject.contains(o)){
            return false;
        }
            
        this.listObject.add(o);
        this.spaceOccupied++;
        this.isEmpty = false;
        this.output.write(o.toString());
        this.output.write(System.lineSeparator());
        this.output.flush();
        return true;
    }

    /**
     * Checks to if a certain object is stored in the Database.
     * @param o The object you want to check.
     * @return True if the object is in the Database, False if it is not.
     */
    public boolean contains(Object o) {
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

    /**
     * Removes a object from the Linked List and the file storing the information from the 
     * file. Removes from the file by rewriting the file line for line and removing the line that 
     * holds the information for the object o.
     * @param o The object you want to remove.
     * @return True if successful. False if not successful.
     * @throws IOException If the File is not found.
     */
    public boolean remove(Object o) throws IOException{
        if(o.equals(null))
            return false;

        if(!this.listObject.contains(o))
            return false;
        else {
            File originalFile = new File("Temp.txt");
            BufferedWriter originalFileWriter = new BufferedWriter(new FileWriter(originalFile));
            String tempLine;
            while((tempLine = reader.readLine()) != null){
                String line = tempLine.trim();
                originalFileWriter.write(line);
                originalFileWriter.write(System.lineSeparator());
                originalFileWriter.flush();
            }
            originalFileWriter.close();
            
            BufferedReader originalFileReader = new BufferedReader(new FileReader(originalFile));
            File tempFile = new File(storage.getName());
            BufferedWriter tempWritter = new BufferedWriter(new FileWriter(tempFile));
            String lineToRemove = o.toString();
            String currentLine;

            while((currentLine = originalFileReader.readLine()) != null){
                String line = currentLine.trim();
                if(!line.equals(lineToRemove)) {
                    tempWritter.write(currentLine);
                    tempWritter.write(System.lineSeparator());
                }
            }
            originalFileReader.close();
            tempWritter.close();
            tempFile.renameTo(this.storage);
            this.storage = tempFile;
            this.listObject.remove(o);
            this.spaceOccupied--;
            return true;
        }
        
    }

    /**
     * Gets the number of objects in the Linked List.
     * @return The number of objects in the Linked List.
     */
    public int getSpaceOccupied(){
        return this.spaceOccupied;
    }
    
    /**
     * Gets the object at a certain position in the Linked List.
     * @param position The position where the object you want to retrieve is located.
     * @return The object from the Linked List at the position. 
     */
    public Object get(int position){
        return this.listObject.get(position);
    }

    /**
     * Reads a file and fills the Database with objects created from the information stored in the 
     * file. 
     * @param FileName The name of the file that will be read.
     * @return The number of objects added from the file.
     * @throws IOException If the File is not found.
     */
    public int populateFromFile(String FileName) throws IOException{
        try{
            File inputFile = new File(FileName);
            BufferedReader tempReader = new BufferedReader(new FileReader(inputFile));
            int counter = 0;

            String currentLine;
            while((currentLine = tempReader.readLine()) != null){
                String[] values = currentLine.split(",");
                if(values.length > 1){
                    EBook temp = new EBook(values, counter);
                    this.listObject.add(temp);
                    this.spaceOccupied++;
                    counter++;
                }
                else {
                    this.listObject.add(currentLine);
                    this.spaceOccupied++;
                }
            }
            tempReader.close();
            return counter;
        }catch(FileNotFoundException e){
            return 0;
        }
    }


}