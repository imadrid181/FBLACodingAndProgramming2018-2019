import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class Database {
    private LinkedList<Object> listObject;
    private int spaceOccupied;
    private Boolean isEmpty;
    private File storage;
    private FileWriter fw;
    private BufferedWriter output;
    private FileReader fr;
    private BufferedReader reader;

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


    public boolean contains(Object o) throws IOException{
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
            tempWritter.close();
            tempFile.renameTo(this.storage);
            this.storage = tempFile;
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