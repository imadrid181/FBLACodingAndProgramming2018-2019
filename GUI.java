import java.awt.*;
import java.awt.event.*;

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


    private Database books;

    public GUI() {
        books = new Database(10);

        setTitle("Database");
        setSize(1920,1080);
        setLayout(new BorderLayout());
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
               System.exit(0);
            }    
        });

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

                if(redemptionStatusCheckBox.getState() == false)  
                    books.put(new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText()));
                else {
                    EBook inputedEBook = new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText());
                    new addStudent(inputedEBook);
                    books.put(inputedEBook);
                }
            }  
        }); 

        inputPanel.add(addEBook);

        add(inputPanel, BorderLayout.LINE_START);
        inputPanel.setVisible(true);

        
    }

    private class addStudent extends Frame {
        private Panel inputPanel;
        private Label studentName;
        private TextField studentNameField;
        private Label gradeLevel;
        private TextField gradeLevelField;
        private Button addOwner;

        private addStudent(EBook inputedEBook) {
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
    
    
            gradeLevel = new Label("Class For:");
            inputPanel.add(gradeLevel);
    
            gradeLevelField = new TextField(10);
            gradeLevelField.setEditable(true);
            inputPanel.add(gradeLevelField);

            addOwner = new Button("Add Owner");
            addOwner.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    Boolean sentinel = false;
                    if(studentNameField.getText().equals("")){
                        new errorWindow("Insert a name!");
                        sentinel = true;
                    }
                    if(gradeLevelField.getText().equals("")){
                        new errorWindow("Insert a grade level!");
                        sentinel = true;
                    }
                    
                    try {
                        int gradeLevel = Integer.parseInt(gradeLevelField.getText());
                        if(gradeLevel < 1 || gradeLevel > 12){
                            new errorWindow("Grade Level should be between 1 and 12th");
                            sentinel = true;
                        }

                        if(sentinel = true){
                            return;
                        }

                        Student owner = new Student(studentNameField.getText(), gradeLevel);
                        inputedEBook.setOwner(owner);
                    } catch(Exception exception){
                        new errorWindow("Insert a number for grade level!");
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