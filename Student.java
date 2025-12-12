import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;


public class Student {
    private String studentId;
    private String password;
    private String email;

    public Student(String studentId, String password, String email){
        this.studentId = studentId;
        this.password = password;
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}

// -------------------------------------------------------------------------------


//StudentGui
class StudentGui extends JFrame implements ActionListener {
    private Student student;
    private SubjectManagement subjectService; 
    private JButton examsButton;
    private JButton resultButton;
    private JButton recorrectButton;
    private JButton feedbackButton;
    private JButton updateInfoButton;
    private JButton logoutButton;

    public StudentGui(Student student, SubjectManagement subjectService) {
        this.student = student;
        this.subjectService = subjectService;
        initGUI();
    }

    public StudentGui(Student student) {
        this.student = student;
        initGUI();
    }

    private void initGUI() {
        setTitle("Student");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Student", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 17, 17));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(17, 17, 17, 17));

        examsButton = new JButton("Available Exams");
        resultButton = new JButton("View Results");
        recorrectButton = new JButton("Recorrection Request");
        feedbackButton = new JButton("Feedbacks");
        updateInfoButton = new JButton("Update Info");
        logoutButton = new JButton("Logout");

        examsButton.addActionListener(this);
        resultButton.addActionListener(this);
        recorrectButton.addActionListener(this);
        feedbackButton.addActionListener(this);
        updateInfoButton.addActionListener(this);
        logoutButton.addActionListener(this);

        buttonsPanel.add(examsButton);
        buttonsPanel.add(resultButton);
        buttonsPanel.add(recorrectButton);
        buttonsPanel.add(feedbackButton);
        buttonsPanel.add(updateInfoButton);
        buttonsPanel.add(logoutButton);

        add(buttonsPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        this.setVisible(false);
        if (actionEvent.getSource() == examsButton) {
            if (subjectService != null)
                new AccessToExamGui(student, subjectService).setVisible(true);
            else
                JOptionPane.showMessageDialog(this, "Subject service not available", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (actionEvent.getSource() == resultButton) {
            if (subjectService != null)
                new ResultViewingGui(student, subjectService).setVisible(true);
            else
                JOptionPane.showMessageDialog(this, "Subject service not available", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (actionEvent.getSource() == recorrectButton) {
            new ReCorrectionRequestGui(student).setVisible(true);
        } else if (actionEvent.getSource() == feedbackButton) {
            new FeedbackGui(student).setVisible(true);
        } else if (actionEvent.getSource() == updateInfoButton) {
            new UpdateInfogGui(student).setVisible(true);
        } else if (actionEvent.getSource() == logoutButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "؟", "Yes", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
            } else {
                this.setVisible(true);
            }
        }
    }
}

 // ------------------------------------------------------------------------------

//ReCorrectionRequestGui
class ReCorrectionRequestGui extends JFrame {

    private Student student;
    private JTextField examIdField;
    private StudentServices studentService = new StudentServices();

    public ReCorrectionRequestGui(Student student) {
        this.student = student;

        setTitle("ReCorrection Request" + student.getStudentId());
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("ReCorrection Request", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 15, 15, 15);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        formPanel.add(new JLabel("Exam ID"), c);
        c.gridx = 1;
        examIdField = new JTextField(15);
        formPanel.add(examIdField, c);

        JPanel btnPanel = new JPanel();
        
        JButton submitBtn = styledButton("Send Request");
        JButton backBtn = styledButton("back");

        btnPanel.add(submitBtn);
        btnPanel.add(backBtn);
        
        add(btnPanel, BorderLayout.SOUTH);

        submitBtn.addActionListener(actionEvente -> submitRequest());
        
        backBtn.addActionListener(actionEvent -> {
            this.dispose(); 
            new StudentGui(student);
        });
        setVisible(true);
    }
    
    private void submitRequest() {
        String examId = examIdField.getText().trim();        
        if (examId.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "please write your exam ID","wrong", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!studentService.file.takenExam(student.getStudentId(), examId)) {
            JOptionPane.showMessageDialog(this, "Not available,you are not registered this exam", "wrong", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        StudentServices.requestRecorrection(student.getStudentId(), examId);
        JOptionPane.showMessageDialog(this, "send successfully", "done", JOptionPane.INFORMATION_MESSAGE);
        
        examIdField.setText("");
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(170, 40));
        btn.setFocusPainted(false);
        return btn;
    }
}
// -------------------------------------------------------------------------------
//FeedbackGui 
class FeedbackGui extends JFrame {

    private Student student;
    private JTextField examIdField;
    private JTextArea feedbackArea;
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(170, 40)); 
        btn.setFocusPainted(false);
        return btn;
    }

    public FeedbackGui(Student student) {
        this.student = student;

        setTitle("Send feedback" + student.getStudentId());
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Send feedback", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 15, 15, 15);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        formPanel.add(new JLabel("Exam ID:"), c);
        c.gridx = 1;
        examIdField = new JTextField(15);
        formPanel.add(examIdField, c);
        c.gridx = 0; c.gridy = 1;
        formPanel.add(new JLabel(" Feedback:"), c);
        c.gridx = 1;
        feedbackArea = new JTextArea(5, 15);
       feedbackArea.setLineWrap(true);
       JScrollPane scrollPane = new JScrollPane(feedbackArea);
       formPanel.add(scrollPane, c);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        
        JButton submitBtn = styledButton("Send feedback");
        JButton backBtn = styledButton("back");

        btnPanel.add(submitBtn);
        btnPanel.add(backBtn);
        
        add(btnPanel, BorderLayout.SOUTH);

        submitBtn.addActionListener(actionEvent -> submitFeedback());
        
        ActionListener returnAction = actionEvente -> {
            this.dispose(); 
            new StudentGui(student).setVisible(true);
        };
        backBtn.addActionListener(returnAction);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new StudentGui(student).setVisible(true);

            }
        });
        setVisible(true);
    }

    private void submitFeedback() {
        String examId = examIdField.getText().trim();
         String feedback = feedbackArea.getText().trim(); 
        if (examId.isEmpty()|| feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "please write exam ID and feed back", "wrong", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!StudentServices.file.takenExam(student.getStudentId(), examId)) {
            JOptionPane.showMessageDialog(this, "You did not take this exam ", "wrong", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StudentServices.examFeedback(student.getStudentId(), examId,feedback); 
        JOptionPane.showMessageDialog(this, "feedback sent successfuly", "done", JOptionPane.INFORMATION_MESSAGE);
        
        examIdField.setText("");
        feedbackArea.setText("");
    }
}




// -------------------------------------------------------------------------------
//updateInfogGui
class   UpdateInfogGui extends JFrame
 {
    private Student currentStudent;
    private JTextField emailField;
    private JPasswordField passwordField;
    private FileManager fileManager = new FileManager(); 

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(170, 40)); 
        btn.setFocusPainted(false);
        return btn;
    }

public UpdateInfogGui(Student student) {
        this.currentStudent = student;

        setTitle("updateInfo" + student.getStudentId());
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("updateInfo", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        formPanel.add(new JLabel("ID:"), c);
        c.gridx = 1;
        JTextField idField = new JTextField(student.getStudentId(), 15);
        idField.setEditable(false); 
        formPanel.add(idField, c);

  
        c.gridx = 0; c.gridy = 2;
        formPanel.add(new JLabel("Email:"), c);
        c.gridx = 1;
        emailField = new JTextField(student.getEmail(), 15);
        formPanel.add(emailField, c);

        c.gridx = 0; c.gridy = 3;
        formPanel.add(new JLabel("password:"), c);
        c.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, c);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        
        JButton saveBtn = styledButton("save");
        JButton backBtn = styledButton("cancel");
        saveBtn.setPreferredSize(new Dimension(180, 40));
        backBtn.setPreferredSize(new Dimension(180, 40));

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);
        
        add(btnPanel, BorderLayout.SOUTH);

    
        saveBtn.addActionListener(e -> saveUpdates());
        
        ActionListener returnAction = e -> returnToDashboard();
        backBtn.addActionListener(returnAction);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                returnToDashboard();
            }
        });
        setVisible(true);
    }
    
    private void saveUpdates() {
        String newEmail = emailField.getText().trim();
        String newPassword = new String(passwordField.getPassword());
        
        String passwordToSave = newPassword.isEmpty() ? currentStudent.getPassword() : newPassword;

        if ( newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "please write name or email or password", "wrong", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if (fileManager.updateStudentInfo(currentStudent.getStudentId(), newEmail, passwordToSave)) {
            
            currentStudent.setEmail(newEmail);
            currentStudent.setPassword(passwordToSave);

            JOptionPane.showMessageDialog(this, "updated successfully", "successful", JOptionPane.INFORMATION_MESSAGE);
            
            returnToDashboard();
            
        } else {
            JOptionPane.showMessageDialog(this, "something wrong", "failed", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    private void returnToDashboard() {
        this.dispose();
        new StudentGui(currentStudent).setVisible(true);
    }
}



// -------------------------------------------------------------------------------
//AccessToExamGui
class AccessToExamGui extends JFrame {

    private Student student;
    private JTable examsTable;
    private DefaultTableModel tableModel;

    private SubjectManagement subjectService; 

    public AccessToExamGui(Student student, SubjectManagement subjectService){
        this.student = student;
        this.subjectService = subjectService;

        setTitle("Available Exams  " + student.getStudentId());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Available Exams", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        String[] columnNames = {"Subject ID", "Subject Name", "Lecturer", "Status"};

        ArrayList<Subject> subjects = getSubjectsForStudent(student);

        Object[][] data = new Object[subjects.size()][columnNames.length];

        for (int i = 0; i < subjects.size(); i++) {

            Subject s = subjects.get(i);

            boolean taken = StudentServices.file.takenExam(
                    String.valueOf(student.getStudentId()),
                    String.valueOf(s.getId())
            );

            String status = taken ? "Finished" : "Available";

            data[i][0] = s.getId();
            data[i][1] = s.getName();
            data[i][2] = (s.lecturer == null ? "Not assigned" : s.lecturer.getName());
            data[i][3] = status;
        }

        tableModel = new DefaultTableModel(data, columnNames);
        examsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(examsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton startExamBtn = styledButton("Start Exam");
        JButton backBtn = styledButton("Back");

        btnPanel.add(startExamBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        startExamBtn.addActionListener(e -> startSelectedExam());

        backBtn.addActionListener(e -> {
            this.dispose();
            new StudentGui(student, subjectService).setVisible(true);
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new StudentGui(student, subjectService).setVisible(true);
            }
        });

        setVisible(true);
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setFocusPainted(false);
        return btn;
    }

    private ArrayList<Subject> getSubjectsForStudent(Student s) {

        ArrayList<Subject> list = new ArrayList<>();

        for (Subject sub : subjectService.getSubjects()) {
            for (Student st : sub.students) {
                if (st.getStudentId().equals(s.getStudentId())) 
                    {
                      list.add(sub);
                    }
            }
        }

        return list;
    }

    private void startSelectedExam() {

        int selectedRow = examsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a subject first");
            return;
        }

       String subjectId = tableModel.getValueAt(selectedRow, 0).toString();
        String status = (String) tableModel.getValueAt(selectedRow, 3);

        if (status.equals("Finished")) {
            JOptionPane.showMessageDialog(this, "You already finished this exam");
            return;
        }

        StudentServices.examEntry(String.valueOf(student.getStudentId()), String.valueOf(subjectId));

        JOptionPane.showMessageDialog(this, 
            "Exam is now starting for subject: " + subjectId, 
            "Start Exam", 
            JOptionPane.INFORMATION_MESSAGE
        );

        tableModel.setValueAt("Finished", selectedRow, 3);
    }
}




 class ResultViewingGui extends JFrame {

    private Student student;

    public ResultViewingGui(Student student, SubjectManagement subjectService) {
        this.student = student;

        setTitle("Exam Results " + student.getStudentId());
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Published Exam Results", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        String[] columnNames = {"Subject ID", "Course Name", "Grade", "Status"};

        ArrayList<Object[]> resultsList = new ArrayList<>();
       for (Subject sub : subjectService.getSubjects()) {
         for (Student s : sub.getStudents()) {
          if (String.valueOf(s.getId()).equals(String.valueOf(student.getId()))) {
            String grade = StudentServices.file.getGrade(student.getId(), String.valueOf(sub.getId()));
            String status = (grade != null) ? "Published" : "Awaiting";
            resultsList.add(new Object[]{ sub.getId(), sub.getName(), (grade != null ? grade : "N/A"), status });
        }
    }
}

        Object[][] data = resultsList.toArray(new Object[0][]);
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable resultsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton viewCorrectedExamBtn = styledButton("View Corrected Exam");
        JButton backBtn = styledButton("Back");

        viewCorrectedExamBtn.setPreferredSize(new Dimension(220, 45));
        backBtn.setPreferredSize(new Dimension(220, 45));

        btnPanel.add(viewCorrectedExamBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        viewCorrectedExamBtn.addActionListener(e -> viewCorrectedExam(resultsTable));
        backBtn.addActionListener(e -> {
            this.dispose();
            new StudentGui(student, subjectService).setVisible(true);
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new StudentGui(student, subjectService).setVisible(true);
            }
        });

        setVisible(true);
    }

    private void viewCorrectedExam(JTable resultsTable) {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exam result to display.", "Warning", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        String subjectId = resultsTable.getValueAt(selectedRow, 0).toString();
        String status = resultsTable.getValueAt(selectedRow, 3).toString();

        if (status.equals("Published")) {
            JOptionPane.showMessageDialog(this,
                    "Displaying corrected exam for subject ID: " + subjectId,
                    "View",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "The corrected exam cannot be viewed. Result not yet published.",
                    "Unavailable",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        return btn;
    }
}
class Main {
    public static void main(String[] args) {
        SubjectManagement subjectService = new SubjectManagement();
        
        subjectService.addSubject(new Subject("101", "CS101"));
        subjectService.addSubject(new Subject("102", "CS201"));

        Student student = new Student("20241110","123","yasmynmhmd900@gmail.com");
        new StudentGui(student, subjectService).setVisible(true);
    }
}