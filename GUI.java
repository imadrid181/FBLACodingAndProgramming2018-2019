import java.awt.*;
import java.awt.event.*;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/*
 * The main Gui is where the user will be able to pick what they want to do such as adding a new E-book, and it allows the user to view a table 
 * containing all the information of an E-Book.
 */ 


public class GUI extends Frame  {
    private Panel inputPanel;
    private Label name;
    private TextField nameField;
    private Label classFor;
    private TextField classForField;
    private Label redemptionCode;
    private TextField redemptionCodeField;
    private Label redemptionStatus;
    private Checkbox redemptionStatusCheckBox;
    private Button addEBook;
    private Button addOwner;
    private Button removeEBook;
    private Button updateEBook;
    private Button generateReport;
    
    static EBook inputedEBook;

    static int counter;
    static int numberOfRowsRemoved;
    static Database books;
    static Database unredeemedEBooks;
    static Database redemptionCodes;
    static Panel DatabaseDisplay;
    static JTable database;



    public GUI() throws IOException{
        //Creation of the Databases that will hold the E-Books that will be used throughout the Gui. Each database is filled from the file where it is stored.
        books = new Database("E-Books");
        counter = books.populateFromFile("E-Books.txt");
        unredeemedEBooks = new Database("UnredeemedE-Books");
        unredeemedEBooks.populateFromFile("UnredeemedE-Books.txt");
        redemptionCodes = new Database("RedemptionCodes");
        redemptionCodes.populateFromFile("RedemptionCodes.txt");
        numberOfRowsRemoved = 0;

        //Creation of Frame and allows for Frame to be exited.
        setTitle("Database");
        setSize(1920,1080);
        setLayout(new BorderLayout());
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
               System.exit(0);
            }    
        });

        //Creation of table that will allow user to view information.
        database = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column){
                return false;
            };
        };
        database.getTableHeader().setReorderingAllowed(false);

        //The Panel will allow for the user to input information for an E-Book and 
        inputPanel = new Panel(new GridLayout(7,2));

        //Labels and TextFields that will guide the user with adding a new E-Book.
        name = new Label("Name:");
        inputPanel.add(name);

        nameField = new TextField(10);
        nameField.setEditable(true);
        inputPanel.add(nameField);


        classFor = new Label("Class For:");
        inputPanel.add(classFor);

        classForField = new TextField(10);
        classForField.setEditable(true);
        inputPanel.add(classForField);


        redemptionCode = new Label("Redemption Code:");
        inputPanel.add(redemptionCode);

        redemptionCodeField = new TextField(10);
        redemptionCodeField.setEditable(true);
        inputPanel.add(redemptionCodeField);


        redemptionStatus = new Label("Redemption Status:");
        inputPanel.add(redemptionStatus);

        redemptionStatusCheckBox = new Checkbox("Redeemed", false);  
        inputPanel.add(redemptionStatusCheckBox);

        //Button that takes the information from textFields and adds a new E-Book to the database of E-Books and E-Books that are not redeemed.
        addEBook = new Button("Add");
        addEBook.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    //Check to make sure that all the information is provided. If textfield is left blank a errorWindow will appear telling the user what they need input.
                    Boolean sentinel = false;
                    if(nameField.getText().equals("")) {
                        new errorWindow("Enter a name for the E-Book!");
                        sentinel = true;
                    }
                    if(classForField.getText().equals("")){
                        new errorWindow("Enter the name of the class for the E-Book");
                        sentinel = true;
                    }
                    if(redemptionCodeField.getText().equals("")){
                        new errorWindow("Enter the redemption code of the E-Book");
                        sentinel = true;
                    }
                    if(sentinel == true)
                        return;
                    
                    if(redemptionStatusCheckBox.getState() == false) {
                        //Checks if redemption code is assigned to another E-Book since there cannot be any repeat redemption code.
                        if(redemptionCodes.contains(redemptionCodeField.getText()+",")){
                            new errorWindow("Redemption Code is already asigned to a book");
                            return;
                        }
                        else {
                            //Adds E-Book with no owner. Adds new E-Book to the database of Books and unredeemedBooks and then adds the information to the table.
                            inputedEBook = new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText(), counter);
                            books.put(inputedEBook);
                            unredeemedEBooks.put(inputedEBook);
                            redemptionCodes.put(redemptionCodeField.getText()+",");
                            Object rowData[] = {inputedEBook.getBookName(), inputedEBook.getClassFor(), inputedEBook.getRedemptionCode(), "false", "No Owner", "0"};
                            DefaultTableModel table = (DefaultTableModel)database.getModel();
                            table.addRow(rowData);
                            counter++;
                        }
                    }
                    else {
                        //Checks if redemption code has already been assigned to another book.
                        if(redemptionCodes.contains(redemptionCodeField.getText())){
                            new errorWindow("Redemption Code is already asigned to a book");
                            return;
                        }
                        else{
                            //Creates instance of the frame that will allow the user to input the information for a student.
                            inputedEBook = new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText(), counter);
                            redemptionCodes.put(redemptionCode.getText()+",");
                            new addStudent(false);
                            counter++;
                        }
                    }
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }  
        });

        //Button that will create instance of the addOwner GUI.
        addOwner = new Button("Add Owner to E-Book");
        addOwner.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                new addOwner();
            }
        });

        //Button that will create instance of the removeEBook GUI.
        removeEBook = new Button("Remove E-Book");
        removeEBook.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                new removeRow();
            }
        });

        //Button that will create instance of the updateEBook GUI.
        updateEBook = new Button("Update E-Book");
        updateEBook.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new updateEBook();
            }
        });
        /* The generate report button creates the report that reads the information from E-Books.txt
         *
         */
        generateReport = new Button("Generate Report");
        generateReport.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){
                try {
                    this.makeReport();
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            public void makeReport() throws IOException{
                File report = new File("Report.txt");
                BufferedWriter reportWriter = new BufferedWriter(new FileWriter(report));
                BufferedReader bookReader = new BufferedReader(new FileReader("E-Books.txt"));
                String current;
                while((current = bookReader.readLine()) != null ){
                    String[] tokens = current.split(",");
                    reportWriter.write(tokens[0]+" with code "+tokens[2]+"is assigned to "+tokens[4]+" who is in "+tokens[5]+" grade.");
                    reportWriter.write(System.lineSeparator());
                    reportWriter.flush();
                }
                reportWriter.close();
                bookReader.close();
                new reportWindow();
            }
        
        });

        //Adds buttons to the panel and adsd the panel to the frame.
        inputPanel.add(addEBook);
        inputPanel.add(addOwner);
        inputPanel.add(removeEBook);
        inputPanel.add(updateEBook);
        inputPanel.add(generateReport);

        add(inputPanel, BorderLayout.NORTH);
        inputPanel.setVisible(true);

        //Creation of the columns for the table.
        String[] columnNames = {"Name", "Class Book is For", "Redemption Code", "Redemption Status", "Owner", "Grade Level"};
        Object[][] data = {};
        database.setModel(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(database);
        scrollPane.setViewportView(database);
        database.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);

        //Puts the data from E-Books.txt into the table.
        BufferedReader tempReader =  new BufferedReader(new FileReader(new File("E-Books.txt")));
        String currentLine;
        while((currentLine = tempReader.readLine()) != null){
            String[] values = currentLine.split(",");
            for(int i = 0; i < values.length; i++)
                values[i] = values[i].trim();
            DefaultTableModel table = (DefaultTableModel)database.getModel();
            table.addRow(values);  
        }
    }

    //Frame that allows user to input information for a student
    private class addStudent extends Frame {
        private Panel inputPanel;
        private Label studentName;
        private TextField studentNameField;
        private Label gradeLevel;
        private TextField gradeLevelField;
        private Button addOwner;
        private Student owner;


        private addStudent(Boolean bookAlreadyExisted) {
            setTitle("Student Input");
            setSize(1920,1080);
            setLayout(new FlowLayout());
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    dispose();
                }    
            });

            inputPanel = new Panel(new GridLayout(3,2));

            studentName = new Label("Student Name:");
            inputPanel.add(studentName);
    
            studentNameField = new TextField(10);
            nameField.setEditable(true);
            inputPanel.add(studentNameField);
    
    
            gradeLevel = new Label("Grade Level:");
            inputPanel.add(gradeLevel);
    
            gradeLevelField = new TextField(10);
            gradeLevelField.setEditable(true);
            inputPanel.add(gradeLevelField);

            addOwner = new Button("Add Owner");
            addOwner.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    Boolean sentinel = false;
                    try{
                        if(studentNameField.getText().equals("")){
                            new errorWindow("Insert a name");
                            sentinel = true;
                        }
                        if(gradeLevelField.getText().equals("")){
                            new errorWindow("Insert a grade level");
                            sentinel = true;
                        }
                    
                    
                        int gradeLevel = Integer.parseInt(gradeLevelField.getText().trim());
                        if(gradeLevel < 1 || gradeLevel > 12){
                            new errorWindow("Grade Level should be between 1 and 12th");
                            sentinel = true;
                        }

                        if(sentinel == true){
                            return;
                        }

                        owner = new Student(studentNameField.getText(), gradeLevel);
                        if(bookAlreadyExisted == false){
                            inputedEBook.setRedemptionStatus(true);
                            inputedEBook.setOwner(owner);
                            books.put(inputedEBook);
                            Object rowData[] = {inputedEBook.getBookName(), inputedEBook.getClassFor(), inputedEBook.getRedemptionCode(), "true", inputedEBook.getOwner().getName(), 
                                                Integer.toString(inputedEBook.getOwner().getGradeLevel())};
                            DefaultTableModel table = (DefaultTableModel)database.getModel();
                            table.addRow(rowData);     
                            dispose();  
                        }
                        else {
                            books.remove(inputedEBook);
                            unredeemedEBooks.remove(inputedEBook);
                            inputedEBook.setRedemptionStatus(true);
                            inputedEBook.setOwner(owner);
                            books.put(inputedEBook);
                            DefaultTableModel table = (DefaultTableModel)database.getModel();
                            table.setValueAt("true", inputedEBook.getRowLocation() - numberOfRowsRemoved, 3);   
                            table.setValueAt(owner.getName(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 4);   
                            table.setValueAt(Integer.toString(inputedEBook.getOwner().getGradeLevel()), inputedEBook.getRowLocation() - numberOfRowsRemoved, 5);    
                            dispose();                     
                        }
                   
                    }catch(Exception exception){
                        new errorWindow("Grade Level must be a number");
                    }
                }  
            });
            inputPanel.add(addOwner);

            add(inputPanel);
            inputPanel.setVisible(true);
        }
    }

    private class addOwner extends Frame {
        private Panel display;
        private Label Ebook;
        private JComboBox listOfEBooks;
        private Button addOwnerOfSelectedEBook;

        private addOwner() {
            setTitle("Add Owner");
            setSize(1920,1080);
            setLayout(new FlowLayout());
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    dispose();
                }    
            });

            display = new Panel();
            Ebook = new Label("Pick a E-Book to assign an Owner:");
            EBook[] listOfUnredeemedEBooks = new EBook[unredeemedEBooks.getSpaceOccupied()];
            for(int i = 0; i < unredeemedEBooks.getSpaceOccupied(); i++) {
                listOfUnredeemedEBooks[i] = (EBook)unredeemedEBooks.get(i);
            }
            listOfEBooks = new JComboBox<EBook>(listOfUnredeemedEBooks);
            
            addOwnerOfSelectedEBook = new Button("Add Owner");
            addOwnerOfSelectedEBook.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    if(listOfEBooks.getSelectedItem() == null)
                        new errorWindow("No E-Book was selected");
                    else {
                        inputedEBook = (EBook)listOfEBooks.getSelectedItem();
                        new addStudent(true);
                        dispose();
                    }
                }
            });

            display.add(Ebook);
            display.add(listOfEBooks);
            display.add(addOwnerOfSelectedEBook);
            add(display);
            display.setVisible(true);
        }
    }

    private class removeRow extends Frame {
        private Panel display;
        private Label Ebook;
        private JComboBox listOfEBooks;
        private Button removeEBook;

        private removeRow(){
            setTitle("Remove E-Book");
            setSize(1920,1080);
            setLayout(new FlowLayout());
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    dispose();
                }    
            });

            display = new Panel();
            Ebook = new Label("Pick a E-Book to remove:");
            EBook[] listOfEBook = new EBook[books.getSpaceOccupied()];
            for(int i = 0; i < books.getSpaceOccupied(); i++) {
                listOfEBook[i] = (EBook)books.get(i);
            }
            listOfEBooks = new JComboBox<EBook>(listOfEBook);
            
            removeEBook = new Button("Remove");
            removeEBook.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    try{
                        if(listOfEBooks.getSelectedItem() == null)
                            new errorWindow("No E-Book was selected");
                        else {
                            inputedEBook = (EBook)listOfEBooks.getSelectedItem();
                            books.remove(inputedEBook);
                            redemptionCodes.remove(inputedEBook.getRedemptionCode()+",");
                            if(inputedEBook.getRedemptionStatus() == false)
                                unredeemedEBooks.remove(inputedEBook);
                            DefaultTableModel table = (DefaultTableModel)database.getModel();
                            table.removeRow(inputedEBook.getRowLocation() - numberOfRowsRemoved);
                            numberOfRowsRemoved++;
                        }
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            });

            display.add(Ebook);
            display.add(listOfEBooks);
            display.add(removeEBook);
            add(display);
            display.setVisible(true);
        }
    }

    private class updateEBook extends Frame {
        private Panel display;
        private Label Ebook;
        private JComboBox<EBook> listOfEBooks;
        private Button update;

        private Panel updatePanel;
        private Label name;
        private TextField nameField;
        private Label classFor;
        private TextField classForField;
        private Label redemptionCode;
        private TextField redemptionCodeField;
        private Label redempitonStatus;
        private JComboBox<String> redemptionStatusComboBox;

        private Panel updateStudentPanel;
        private Label studentName;
        private TextField studentNameField;
        private Label gradeLevel;
        private TextField gradeLevelField;

        private updateEBook() {
            setTitle("Update E-Book");
            setSize(1920,1080);
            setLayout(new FlowLayout());
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    dispose();
                }    
            });

            display = new Panel();
            Ebook = new Label("Pick a E-Book to update:");
            EBook[] arrayOfEBooks = new EBook[books.getSpaceOccupied()];
            for(int i = 0; i < books.getSpaceOccupied(); i++) {
                if(books.get(i) != null)
                    arrayOfEBooks[i] = (EBook)books.get(i);
            }

            listOfEBooks = new JComboBox<EBook>(arrayOfEBooks);
            listOfEBooks.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    EBook temp = (EBook)listOfEBooks.getSelectedItem();
                    nameField.setText(temp.getBookName());
                    classForField.setText(temp.getClassFor());
                    redemptionCodeField.setText(temp.getRedemptionCode());
                    if(temp.getRedemptionStatus()){
                        redemptionStatusComboBox.setSelectedIndex(0);
                    }
                    else
                        redemptionStatusComboBox.setSelectedIndex(1);
                    updatePanel.revalidate();
                }
            });

            updateStudentPanel = new Panel(new GridLayout(2,2));
            studentName = new Label("Student Name: ");
            studentNameField = new TextField();
            gradeLevel = new Label("Grade Level: ");
            gradeLevelField = new TextField();

            updatePanel = new Panel(new GridLayout(4, 2));
            EBook temp = (EBook)listOfEBooks.getSelectedItem();
            if(temp != null){
                name = new Label("Name: ");
                nameField = new TextField(temp.getBookName());
                classFor = new Label("Book is used for: ");
                classForField = new TextField(temp.getClassFor());
                redemptionCode = new Label("Redemption Code: ");
                redemptionCodeField = new TextField(temp.getRedemptionCode());
                redemptionStatus = new Label("Redemption Status: ");
                redemptionStatusComboBox = new JComboBox<String>(new String[] {"true", "false"});
                redemptionStatusComboBox.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(redemptionStatusComboBox.getSelectedItem().equals("true")){
                            studentNameField.setText("");
                            studentNameField.setEditable(true);
                            gradeLevelField.setText("");
                            gradeLevelField.setEditable(true);
                            updateStudentPanel.revalidate();
                        }
                    }
                });
                if(temp.getRedemptionStatus()){
                    redemptionStatusComboBox.setSelectedIndex(0);
                }
                else
                    redemptionStatusComboBox.setSelectedIndex(1);

                if(temp.getRedemptionStatus() == true){
                    studentNameField.setText(temp.getOwner().getName());
                    studentNameField.setEditable(true);
                    gradeLevelField.setText(Integer.toString(temp.getOwner().getGradeLevel()));
                    gradeLevelField.setEditable(true);
                    updateStudentPanel.revalidate();
                }
                else {
                    studentNameField.setText("");
                    studentNameField.setEditable(false);
                    gradeLevelField.setText("");
                    gradeLevelField.setEditable(false);
                    updateStudentPanel.revalidate();
                }
            }
            else{
                name = new Label("Name: ");
                nameField = new TextField("");
                classFor = new Label("Book is used for: ");
                classForField = new TextField("");
                redemptionCode = new Label("Redemption Code: ");
                redemptionCodeField = new TextField("");
                redemptionStatus = new Label("Redemption Status: ");
                redemptionStatusComboBox = new JComboBox<String>(new String[] {"true", "false"});
                redemptionStatusComboBox.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(redemptionStatusComboBox.getSelectedItem().equals("true")){
                            studentNameField.setText("");
                            studentNameField.setEditable(true);
                            gradeLevelField.setText("");
                            gradeLevelField.setEditable(true);
                            updateStudentPanel.revalidate();
                        }
                        else {
                            studentNameField.setText("");
                            studentNameField.setEditable(false);
                            gradeLevelField.setText("");
                            gradeLevelField.setEditable(false);
                            updateStudentPanel.revalidate();
                        }
                    }
                });  
            }

            updatePanel.add(name);
            updatePanel.add(nameField);
            updatePanel.add(classFor);
            updatePanel.add(classForField);
            updatePanel.add(redemptionCode);
            updatePanel.add(redemptionCodeField);
            updatePanel.add(redemptionStatus);
            updatePanel.add(redemptionStatusComboBox);

            updateStudentPanel.add(studentName);
            updateStudentPanel.add(studentNameField);
            updateStudentPanel.add(gradeLevel);
            updateStudentPanel.add(gradeLevelField);


            update = new Button("Update");
            update.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    boolean sentinel = false;
                    try{
                        if(listOfEBooks.getSelectedItem() == null) {
                            new errorWindow("No E-Book was selected");
                            return;
                        }

                        if(nameField.getText().equals("")) {
                            new errorWindow("Enter a name for the E-Book!");
                            sentinel = true;
                        }
                        if(classForField.getText().equals("")){
                            new errorWindow("Enter the name of the class for the E-Book");
                            sentinel = true;
                        }
                        if(redemptionCodeField.getText().equals("")){
                            new errorWindow("Enter the redemption code of the E-Book");
                            sentinel = true;
                        }

                        if(sentinel == true){
                            return;
                        }
                        else {
                            if(redemptionStatusComboBox.getSelectedItem().equals("false")){
                                EBook temp = (EBook)listOfEBooks.getSelectedItem();
                                books.remove(temp);
                                redemptionCodes.remove(temp.getRedemptionCode()+",");
                                temp.setBookName(nameField.getText());
                                temp.setClassFor(classForField.getText());
                                temp.setRedemptionCode(redemptionCodeField.getText());
                                temp.setRedemptionStatus(Boolean.parseBoolean(redemptionStatusComboBox.getSelectedItem().toString()));
                                books.put(temp);
                                redemptionCodes.put(redemptionCodeField.getText()+",");
                                DefaultTableModel table = (DefaultTableModel)database.getModel();
                                table.setValueAt(nameField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 0);
                                table.setValueAt(classForField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 1);
                                table.setValueAt(redemptionCodeField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 2);
                                table.setValueAt(redemptionStatusComboBox.getSelectedItem(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 3);
                                dispose();
                            }
                            else {
                                EBook temp = (EBook)listOfEBooks.getSelectedItem();
                                books.remove(temp);
                                if(studentNameField.getText().equals("")){
                                    new errorWindow("Insert a name");
                                    sentinel = true;
                                }
                                if(gradeLevelField.getText().equals("")){
                                    new errorWindow("Insert a grade level");
                                    sentinel = true;
                                }
                                
                                try{
                                    int gradeLevel = Integer.parseInt(gradeLevelField.getText().trim());
                                    if(Integer.parseInt(gradeLevelField.getText().trim()) < 1 || Integer.parseInt(gradeLevelField.getText().trim()) > 12){
                                        new errorWindow("Grade Level should be between 1 and 12th");
                                        sentinel = true;
                                    }
                                }catch(NumberFormatException nfe){
                                    new errorWindow("Grade Level must be a number");
                                }

            
                                if(sentinel == true){
                                    return;
                                }
                                if(temp.getRedemptionStatus() == false){
                                    redemptionCodes.remove(temp.getRedemptionCode()+",");
                                    unredeemedEBooks.remove(temp);
                                    temp.setBookName(nameField.getText());
                                    temp.setClassFor(classForField.getText());
                                    temp.setRedemptionCode(redemptionCodeField.getText());
                                    temp.setRedemptionStatus(Boolean.parseBoolean(redemptionStatusComboBox.getSelectedItem().toString()));
                                    temp.setOwner(new Student(studentNameField.getText(), Integer.parseInt(gradeLevelField.getText())));
                                    books.put(temp);
                                    redemptionCodes.put(redemptionCodeField.getText()+",");
                                    DefaultTableModel table = (DefaultTableModel)database.getModel();
                                    table.setValueAt(nameField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 0);
                                    table.setValueAt(classForField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 1);
                                    table.setValueAt(redemptionCodeField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 2);
                                    table.setValueAt(redemptionStatusComboBox.getSelectedItem(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 3);
                                    table.setValueAt(studentNameField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 4);
                                    table.setValueAt(gradeLevelField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 5);
                                    dispose();
                                }
                                else {
                                    redemptionCodes.remove(temp.getRedemptionCode()+",");
                                    temp.setBookName(nameField.getText());
                                    temp.setClassFor(classForField.getText());
                                    temp.setRedemptionCode(redemptionCodeField.getText());
                                    temp.setRedemptionStatus(Boolean.parseBoolean(redemptionStatusComboBox.getSelectedItem().toString()));
                                    temp.setRedemptionStatus(Boolean.parseBoolean(redemptionStatusComboBox.getSelectedItem().toString()));
                                    temp.setOwner(new Student(studentNameField.getText(), Integer.parseInt(gradeLevelField.getText())));
                                    books.put(temp);
                                    redemptionCodes.put(redemptionCodeField.getText()+",");
                                    DefaultTableModel table = (DefaultTableModel)database.getModel();
                                    table.setValueAt(nameField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 0);
                                    table.setValueAt(classForField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 1);
                                    table.setValueAt(redemptionCodeField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 2);
                                    table.setValueAt(redemptionStatusComboBox.getSelectedItem(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 3);
                                    table.setValueAt(studentNameField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 4);
                                    table.setValueAt(gradeLevelField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 5);
                                    dispose();
                                }
                            }
                        }
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            });

            display.add(Ebook);
            display.add(listOfEBooks);
            add(display);
            add(updatePanel);
            add(updateStudentPanel);
            add(update);
            display.setVisible(true);
            updatePanel.setVisible(true);
            updateStudentPanel.setVisible(true);
        }
    }



    private class errorWindow extends Frame{
        private Panel errorPanel;
        private Label errorMessage;
        private Button close;

        private errorWindow(String errorMessage){
            setTitle("Error");
            setSize(300,100);
            setLayout(new FlowLayout());
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    dispose();
                }    
            });

            errorPanel = new Panel(new GridLayout(2,1));

            this.errorMessage = new Label(errorMessage);
            errorPanel.add(this.errorMessage);

            close = new Button("Close");
            close.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    dispose();
                }  
            });
            errorPanel.add(close);

            add(errorPanel);
            errorPanel.setVisible(true);
            
        }
    }

    private class reportWindow extends Frame{
        private Panel reportPanel;
        private Label reportMessage;
        private Button close;

        private reportWindow(){
            setTitle("Report");
            setSize(300,100);
            setLayout(new FlowLayout());
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    dispose();
                }    
            });

            reportPanel = new Panel(new GridLayout(2,1));

            this.reportMessage = new Label("Report Generated");
            reportPanel.add(this.reportMessage);

            close = new Button("Close");
            close.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    dispose();
                }  
            });
            reportPanel.add(close);

            add(reportPanel);
            reportPanel.setVisible(true);
            
        }
    }

    public static void main(String args[]) throws IOException {
        GUI app = new GUI();
    }
}