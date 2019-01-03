import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javafx.scene.layout.Border;

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
    
    static EBook inputedEBook;

    static int counter;
    static Database books;
    static LinkedList<String> redemptionCodes;
    static Panel DatabaseDisplay;
    static JTable database;



    public GUI() {
        books = new Database(10);
        redemptionCodes = new LinkedList<String>();
        counter = 1;

        setTitle("Database");
        setSize(1920,1080);
        setLayout(new BorderLayout());
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
               System.exit(0);
            }    
        });

        database = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column){
                return false;
            };
        };
        database.getTableHeader().setReorderingAllowed(false);

        inputPanel = new Panel(new GridLayout(5,2));

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


        addEBook = new Button("Add");
        addEBook.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
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
                    if(redemptionCodes.contains(redemptionCodeField.getText())){
                        new errorWindow("Redemption Code is already asigned to a book");
                        return;
                    }
                    else {
                        inputedEBook = new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText());
                        books.put(inputedEBook);
                        redemptionCodes.add(redemptionCodeField.getText());
                        Object rowData[] = {inputedEBook.getBookName(), inputedEBook.getClassFor(), inputedEBook.getRedemptionCode(), "False", "No Owner"};
                        DefaultTableModel table = (DefaultTableModel)database.getModel();
                        table.addRow(rowData);
                    }
                }
                else {
                    if(redemptionCodes.contains(redemptionCodeField.getText())){
                        new errorWindow("Redemption Code is already asigned to a book");
                        return;
                    }
                    else{
                        inputedEBook = new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText());
                        redemptionCodes.add(redemptionCode.getText());
                        new addStudent();
                    }
                }
            }  
        });

        addOwner = new Button("Add Owner to EBook");

        inputPanel.add(addEBook);
        inputPanel.add(addOwner);

        add(inputPanel, BorderLayout.NORTH);
        inputPanel.setVisible(true);

        String[] columnNames = {"Name", "Class Book is For", "Redemption Code", "Redemption Status", "Owner"};
        Object[][] data = {};
        database.setModel(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(database);
        scrollPane.setViewportView(database);
        database.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);
    }

    private class addStudent extends Frame {
        private Panel inputPanel;
        private Label studentName;
        private TextField studentNameField;
        private Label gradeLevel;
        private TextField gradeLevelField;
        private Button addOwner;
        private Student owner;


        private addStudent() {
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
                            new errorWindow("Insert a name!");
                            sentinel = true;
                        }
                        if(gradeLevelField.getText().equals("")){
                            new errorWindow("Insert a grade level");
                            sentinel = true;
                        }
                    
                    
                        int gradeLevel = Integer.parseInt(gradeLevelField.getText());
                        if(gradeLevel < 1 || gradeLevel > 12){
                            new errorWindow("Grade Level should be between 1 and 12th");
                            sentinel = true;
                        }

                        if(sentinel == true){
                            return;
                        }

                        owner = new Student(studentNameField.getText(), gradeLevel);
                        inputedEBook.setOwner(owner);
                        books.put(inputedEBook);
                        Object rowData[] = {inputedEBook.getBookName(), inputedEBook.getClassFor(), inputedEBook.getRedemptionCode(), "False", inputedEBook.getOwner().getName()};
                        DefaultTableModel table = (DefaultTableModel)database.getModel();
                        table.addRow(rowData);     
                        dispose();                   
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

    public static void main(String args[]){
        GUI app = new GUI();
    }
}