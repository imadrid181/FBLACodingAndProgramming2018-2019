import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

import javafx.scene.control.ComboBox;
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
    private Button removeEBook;
    private Button updateEBook;
    
    static EBook inputedEBook;

    static int counter;
    static int numberOfRowsRemoved;
    static Database books;
    static Database redeemedEBooks;
    static Database unredeemedEBooks;
    static Database redemptionCodes;
    static Panel DatabaseDisplay;
    static JTable database;



    public GUI() {
        books = new Database();
        redeemedEBooks = new Database();
        unredeemedEBooks = new Database();
        redemptionCodes = new Database();
        counter = 0;
        numberOfRowsRemoved = 0;

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

        inputPanel = new Panel(new GridLayout(6,2));

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
                        inputedEBook = new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText(), counter);
                        books.put(inputedEBook);
                        unredeemedEBooks.put(inputedEBook);
                        redemptionCodes.put(redemptionCodeField.getText());
                        Object rowData[] = {inputedEBook.getBookName(), inputedEBook.getClassFor(), inputedEBook.getRedemptionCode(), "False", "No Owner"};
                        DefaultTableModel table = (DefaultTableModel)database.getModel();
                        table.addRow(rowData);
                        counter++;
                    }
                }
                else {
                    if(redemptionCodes.contains(redemptionCodeField.getText())){
                        new errorWindow("Redemption Code is already asigned to a book");
                        return;
                    }
                    else{
                        inputedEBook = new EBook(nameField.getText(), classForField.getText(), redemptionCodeField.getText(), counter);
                        redemptionCodes.put(redemptionCode.getText());
                        new addStudent(false);
                        counter++;
                    }
                }
            }  
        });

        addOwner = new Button("Add Owner to EBook");
        addOwner.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                new addOwner();
            }
        });

        removeEBook = new Button("Remove E-Book");
        removeEBook.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                new removeRow();
            }
        });

        updateEBook = new Button("Update E-Book");
        updateEBook.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new updateEBook();
            }
        });


        inputPanel.add(addEBook);
        inputPanel.add(addOwner);
        inputPanel.add(removeEBook);
        inputPanel.add(updateEBook);

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
                        if(bookAlreadyExisted == false){
                            inputedEBook.setRedemptionStatus(true);
                            inputedEBook.setOwner(owner);
                            redeemedEBooks.put(inputedEBook);
                            books.put(inputedEBook);
                            Object rowData[] = {inputedEBook.getBookName(), inputedEBook.getClassFor(), inputedEBook.getRedemptionCode(), "True", inputedEBook.getOwner().getName()};
                            DefaultTableModel table = (DefaultTableModel)database.getModel();
                            table.addRow(rowData);     
                        }
                        else {
                            books.remove(inputedEBook);
                            unredeemedEBooks.remove(inputedEBook);
                            inputedEBook.setRedemptionStatus(true);
                            inputedEBook.setOwner(owner);
                            books.put(inputedEBook);
                            redeemedEBooks.put(inputedEBook);
                            DefaultTableModel table = (DefaultTableModel)database.getModel();
                            table.setValueAt("True", inputedEBook.getRowLocation() - numberOfRowsRemoved, 3);   
                            table.setValueAt(owner.getName(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 4);                          
                        }
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
                    }
                }
            });

            display.add(addOwnerOfSelectedEBook);
            display.add(Ebook);
            display.add(listOfEBooks);
            add(display);
            display.setVisible(true);
        }
    }

    private class removeRow extends Frame {
        private Panel display;
        private Label Ebook;
        private JComboBox listOfEBooks;
        private Button removeEBook;

        private removeRow() {
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
            Ebook = new Label("Pick a E-Book to remove:");
            EBook[] listOfUnredeemedEBooks = new EBook[books.getSpaceOccupied()];
            for(int i = 0; i < books.getSpaceOccupied(); i++) {
                listOfUnredeemedEBooks[i] = (EBook)books.get(i);
            }
            listOfEBooks = new JComboBox<EBook>(listOfUnredeemedEBooks);
            
            removeEBook = new Button("Remove");
            removeEBook.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    if(listOfEBooks.getSelectedItem() == null)
                        new errorWindow("No E-Book was selected");
                    else {
                        inputedEBook = (EBook)listOfEBooks.getSelectedItem();
                        books.remove(inputedEBook);
                        redemptionCodes.remove(inputedEBook.getRedemptionCode());
                        DefaultTableModel table = (DefaultTableModel)database.getModel();
                        table.removeRow(inputedEBook.getRowLocation() - numberOfRowsRemoved);
                        numberOfRowsRemoved++;
                    }
                }
            });

            display.add(removeEBook);
            display.add(Ebook);
            display.add(listOfEBooks);
            add(display);
            display.setVisible(true);
        }
    }

    private class updateEBook extends Frame {
        private Panel display;
        private Label Ebook;
        private JComboBox listOfEBooks;
        private Button update;

        private Panel updatePanel;
        private Label name;
        private TextField nameField;
        private Label classFor;
        private TextField classForField;
        private Label redemptionCode;
        private TextField redemptionCodeField;
        private Label redempitonStatus;
        private TextField redemptionStatusField;

        private updateEBook() {
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
            Ebook = new Label("Pick a E-Book to update:");
            EBook[] arrayOfEBooks = new EBook[books.getSpaceOccupied()];
            for(int i = 0; i < books.getSpaceOccupied(); i++) {
                arrayOfEBooks[i] = (EBook)books.get(i);
            }
            listOfEBooks = new JComboBox<EBook>(arrayOfEBooks);
            listOfEBooks.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    EBook temp = (EBook)listOfEBooks.getSelectedItem();
                    nameField.setText(temp.getBookName());
                    classForField.setText(temp.getClassFor());
                    redemptionCodeField.setText(temp.getRedemptionCode());
                    redemptionStatusField.setText(Boolean.toString(temp.getRedemptionStatus()));
                    updatePanel.revalidate();
                }
            });

            updatePanel = new Panel(new GridLayout(4, 2));
            EBook temp = (EBook)listOfEBooks.getSelectedItem();
            name = new Label("Name: ");
            nameField = new TextField(temp.getBookName());
            classFor = new Label("Book is used for: ");
            classForField = new TextField(temp.getClassFor());
            redemptionCode = new Label("Redemption Code: ");
            redemptionCodeField = new TextField(temp.getRedemptionCode());
            redemptionStatus = new Label("Redemption Status: ");
            redemptionStatusField = new TextField(Boolean.toString(temp.getRedemptionStatus()));
            updatePanel.add(name);
            updatePanel.add(nameField);
            updatePanel.add(classFor);
            updatePanel.add(classForField);
            updatePanel.add(redemptionCode);
            updatePanel.add(redemptionCodeField);
            updatePanel.add(redemptionStatus);
            updatePanel.add(redemptionStatusField);

            update = new Button("Update");
            update.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    if(listOfEBooks.getSelectedItem() == null)
                        new errorWindow("No E-Book was selected");
                    else {
                        EBook temp = (EBook)listOfEBooks.getSelectedItem();
                        temp.setBookName(nameField.getText());
                        temp.setClassFor(classForField.getText());
                        temp.setRedemptionCode(redemptionCodeField.getText());
                        temp.setRedemptionStatus(Boolean.parseBoolean(redemptionStatusField.getText()));
                        DefaultTableModel table = (DefaultTableModel)database.getModel();
                        table.setValueAt(nameField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 0);
                        table.setValueAt(classForField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 1);
                        table.setValueAt(redemptionCodeField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 2);
                        table.setValueAt(redemptionStatusField.getText(), inputedEBook.getRowLocation() - numberOfRowsRemoved, 3);
                        dispose();
                    }
                }
            });

            display.add(update);
            display.add(Ebook);
            display.add(listOfEBooks);
            add(display);
            add(updatePanel);
            display.setVisible(true);
            updatePanel.setVisible(true);
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