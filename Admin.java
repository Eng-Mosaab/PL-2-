/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author youssef
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//Admin Class ----------------------------------------------------------------------------------------------------------------------------------
public class Admin {
    private String userName;
    private String password;

    public Admin(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public boolean login(String user, String pass){
        return (this.userName.equals(user) && this.password.equals(pass));
    }

    public void changeuserName(String userName){
        this.userName = userName;
    }

    public void changePassword(String password){
        this.password = password;
    }
}

//Admin Gui ----------------------------------------------------------------------------------------------------------------------------------------------
 class AdminGui extends JFrame {

    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Admin admin;

    public AdminGui(Admin admin) {
        this.admin = admin;

        
        setTitle("Admin Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//نقفل البروجكت لو دوسنا على الاكس اللي فوق عاليمين
        setLocationRelativeTo(null);//نوسطن الويندو اللي هتظهر فالنص 
        setLayout(new BorderLayout());//بيقسم مساحات الويندو يمين شمال جنوب شمال

        // ===== Title Panel ===== الجزء الشمال اللي فوق من الويندو
        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);//هنا عرفنا تايتل كفاريبل من النوع جي ليبل عشان يخلي اسم الجامعة يظهر والمسئول عن انها تكون فالنص سوينج كونستانت دوت سينتر
        title.setFont(new Font("Arial", Font.BOLD, 26));//تظبيط الخط وكده وعشان نشغل المميزات دي بنعمل ايمبورت للمكتبة ايه دبليو تي 
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));//بادينج زي السي اس اس 
        add(title, BorderLayout.NORTH);//بادينج للعنوان  اللي فالشمال اللي فوق

        // ===== Center Form Panel ===== 
        JPanel formPanel = new JPanel(new GridBagLayout());//دي الكونتاينر اللي فالنص  اللس هيكون فيها الفيلدز اللي هنكتب فيها 
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));//مرونة وتحكم فالمسافات 
        GridBagConstraints c = new GridBagConstraints();//بنعرف اوبجكت اسمه سي عشان نتحكم فطرق ادخال التيكست فالفيلد 
        c.insets = new Insets(10, 10, 10, 10);//
        c.fill = GridBagConstraints.HORIZONTAL;//مثلا انها تكون بالعرض مش بالطول

        // Username
        c.gridx = 0; c.gridy = 0;//تحديد موقع الفيلد اللي عندي والليبل
        formPanel.add(new JLabel("Username:"), c);//

        c.gridx = 1;
        usernameField = new JTextField(15);//
        formPanel.add(usernameField, c);//

        // Password
        c.gridx = 0; c.gridy = 1;//زي ما قولنا التسمية عالشمال والفيلد عاليمين
        formPanel.add(new JLabel("Password:"), c);

        c.gridx = 1;
        passwordField = new JPasswordField(15);//من خواص الجي باسوردانك لما تكتب الباسورد بتحط دواير مطرح الكلام عشان ميبانش
        formPanel.add(passwordField, c);

        add(formPanel, BorderLayout.CENTER);//هيخلي الفورم بانل متسنترة كويس

        // ===== Login button ===== حرفيا زي الليبل اللي فالنورث فمش هشرحهالكوا مش ناقصة وجع دماغ
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel btnPanel = new JPanel();
        btnPanel.add(loginBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // ===== Login Action =====
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {//بننده على اكشن بيرفورم عشان تجيت الاسم والباسوردوتتحقق منهم
                if (admin.login(usernameField.getText(), new String(passwordField.getPassword()))) {//
                    JOptionPane.showMessageDialog(null, "welcome to Helwan University");//بيبعت رسالة ترحيب لو الكلام صح واليوزر مش نصاب
                    new AdminMenuGUI().setVisible(true);//روح بقى عالهوم بيدج
                    dispose();//دي بتعمل حاجة زي كراش للوجين جي يو اي
                } else {//
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");//لو فولس اظهرله الرسالة دي 
                }
            }
        });
    }
}
//Admin Menu Gui----------------------------------------------------------------------------------------------------------------------------------
class AdminMenuGUI extends JFrame {

    public AdminMenuGUI() {
        setTitle("Admin Menu");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel title = new JLabel("Helwan University", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // ===== Buttons Panel =====
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 10, 15, 10); // space between buttons
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        JButton userBtn = styledButton("User Management");
        JButton subjectBtn = styledButton("Subject Management");
        JButton gradeBtn = styledButton("Grade Approval");

        c.gridy = 0; panel.add(userBtn, c);
        c.gridy = 1; panel.add(subjectBtn, c);
        c.gridy = 2; panel.add(gradeBtn, c);

        add(panel, BorderLayout.CENTER);

        // ===== Actions =====
        userBtn.addActionListener(e -> new UserManagementGUI().setVisible(true));
        subjectBtn.addActionListener(e -> new SubjectManagementGUI().setVisible(true));
        gradeBtn.addActionListener(e -> new GradeApprovalGUI().setVisible(true));
    }

    // ===== Reusable method for styling buttons =====
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(250, 50));
        return btn;
    }
}

//  User Class ------------------------------------------------------------------------------------------------------------------------------
class User {
    private int id;
    private String name;
    private String email;
    private String password;

    public User(int id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId(){ return id; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getPassword(){ return password; }

    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
    public void setPassword(String password){ this.password = password; }

    @Override
    public String toString(){
        return "ID:" + id + " NAME:" + name + " E-MAIL:" + email + " PASSWORD:" + password;
    }
}

// User Management-------------------------------------------------------------------------------------------------------------------- 
class UserManagement {
    private ArrayList<User> users = new ArrayList<>();

    public void addUsers(User user){
        users.add(user);
    }

    public boolean deleteUsers(int id){
        return users.removeIf(u -> u.getId() == id);
    }

    public boolean updateUsers(int id, String new_name, String new_email, String new_password){
        for(User u : users){
            if(u.getId() == id){
                u.setName(new_name);
                u.setEmail(new_email);
                u.setPassword(new_password);
                return true;
            }
        }
        return false;
    }

    public User searchUsers(int id){
        for(User u : users){
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }

    public void listUsers(){
        for(User u : users){
            System.out.println(u);
        }
    }
}
// User Management Gui------------------------------------------------------------------------------------------------------------
class UserManagementGUI extends JFrame {

    private UserManagement userService = new UserManagement();

    public UserManagementGUI() {
        setTitle("User Management");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel title = new JLabel("User Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 12, 12, 12);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);

        // Row 1: ID
        c.gridx = 0; c.gridy = 0;
        formPanel.add(new JLabel("ID:"), c);
        c.gridx = 1;
        formPanel.add(idField, c);

        // Row 2: Name
        c.gridx = 0; c.gridy = 1;
        formPanel.add(new JLabel("Name:"), c);
        c.gridx = 1;
        formPanel.add(nameField, c);

        // Row 3: Email
        c.gridx = 0; c.gridy = 2;
        formPanel.add(new JLabel("Email:"), c);
        c.gridx = 1;
        formPanel.add(emailField, c);

        add(formPanel, BorderLayout.CENTER);

        // ===== Buttons Panel =====
        JPanel btnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(10, 10, 10, 10);
        b.fill = GridBagConstraints.HORIZONTAL;

        JButton addBtn = styledButton("Add User");
        JButton updateBtn = styledButton("Update User");
        JButton deleteBtn = styledButton("Delete User");
        JButton listBtn = styledButton("List Users");

        b.gridx = 0; b.gridy = 0; btnPanel.add(addBtn, b);
        b.gridy = 1; btnPanel.add(updateBtn, b);
        b.gridy = 2; btnPanel.add(deleteBtn, b);
        b.gridy = 3; btnPanel.add(listBtn, b);

        add(btnPanel, BorderLayout.EAST);

        //Actions---------------------------------------------------------------------------------------------------------------------------------

        // Add User
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                userService.addUsers(new User(id, nameField.getText(), emailField.getText(), ""));
                JOptionPane.showMessageDialog(this, "User Added Successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // Update User
        updateBtn.addActionListener(e -> {
            try {
                if (userService.updateUsers(Integer.parseInt(idField.getText()), nameField.getText(), emailField.getText(), "")) {
                    JOptionPane.showMessageDialog(this, "User Updated");
                } else {
                    JOptionPane.showMessageDialog(this, "User Not Found");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // Delete User
        deleteBtn.addActionListener(e -> {
            try {
                if (userService.deleteUsers(Integer.parseInt(idField.getText()))) {
                    JOptionPane.showMessageDialog(this, "User Deleted");
                } else {
                    JOptionPane.showMessageDialog(this, "User Not Found");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // List Users
        listBtn.addActionListener(e -> userService.listUsers());
    }

    // ===== Styling Buttons =====
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setFocusPainted(false);
        return btn;
    }
}


// Exam Class------------------------------------------------------------------------------------------------------------------------------------
class Exam {
    private String examName;
    private String subject;
    private int duration;
    private boolean approved = false;
    private boolean published = false;

    public Exam(String examName, String subject, int duration){
        this.examName = examName;
        this.subject = subject;
        this.duration = duration;
    }

    public String getExamName(){ return examName; }
    public String getSubject(){ return subject; }
    public int getDuration(){ return duration; }
    public boolean isApproved(){ return approved; }
    public boolean isPublished(){ return published; }

    public void setApproved(boolean approved){ this.approved = approved; }
    public void publish(){ this.published = true; }

    @Override
    public String toString() {
        return "Exam{" +
                "examName=" + examName +
                ", subject=" + subject +
                ", duration=" + duration +
                ", approved=" + approved +
                ", published=" + published +
                '}';
    }
}

// ======= Grade Approval =======
class GradeApproval {
    public void reviewGrades(Exam exam){
        System.out.println("Reviewing grades: " + exam.getExamName());
    }

    public void approvedGrades(Exam exam){
        exam.setApproved(true);
        System.out.println("Grades Approved");
    }

    public void publishGrades(Exam exam){
        if(exam.isApproved()){
            exam.publish();
            System.out.println("Results published successfully.");
        } else {
            System.out.println("Error: Exam not approved yet");
        }
    }
}

// ======= Student Class =======
class Student{
    private int id;
    private String name;
    private String email;

    public Student(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId(){ return id; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }

    @Override
    public String toString(){
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}

// ======= Lecturer Class =======
class Lecturer {
    private int id;
    private String name;
    private String email;

    public Lecturer(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId(){ return id; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }

    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }

    @Override
    public String toString(){
        return "Lecturer{id=" + id + ", name=" + name + ", email=" + email + "}";
    }
}

// ======= Subject Class =======
class Subject {
    private int id;
    private String name;
    private Lecturer lecturer;
    private ArrayList<Student> students = new ArrayList<>();

    public Subject(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){ return id; }
    public void setLecturer(Lecturer l){ this.lecturer = l; }
    public void addStudent(Student s){ students.add(s); }

    @Override
    public String toString(){
        return "Subject{id=" + id + ", name='" + name + "', students=" + students.size() + "}";
    }
}

// ======= Subject Management =======
class SubjectManagement {
    private ArrayList<Subject> subjects = new ArrayList<>();

    public void addSubject(Subject sub){
        subjects.add(sub);
    }

    public void assignSubjectToStudent(int subjectid, Student s){
        for(Subject sub : subjects){
            if(sub.getId() == subjectid){
                sub.addStudent(s);
            }
        }
    }

    public void assignSubjectToLecturer(int subjectid, Lecturer l){
        for(Subject sub : subjects){
            if(sub.getId() == subjectid){
                sub.setLecturer(l);
            }
        }
    }

    public void listSubjects(){
        for(Subject sub : subjects){
            System.out.println(sub);
        }
    }
}

// Subject Management Gui------------------------------------------------------------------------------------------------------------------------------------
class SubjectManagementGUI extends JFrame {

    private SubjectManagement subjectService = new SubjectManagement();

    public SubjectManagementGUI() {
        setTitle("Subject Management");
        setSize(600, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel title = new JLabel("Subject Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 12, 12, 12);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField subjectIdField = new JTextField(15);
        JTextField subjectNameField = new JTextField(15);
        JTextField studentIdField = new JTextField(15);
        JTextField lecturerIdField = new JTextField(15);

        // Subject ID
        c.gridx = 0; c.gridy = 0;
        formPanel.add(new JLabel("Subject ID:"), c);
        c.gridx = 1;
        formPanel.add(subjectIdField, c);

        // Subject Name
        c.gridx = 0; c.gridy = 1;
        formPanel.add(new JLabel("Subject Name:"), c);
        c.gridx = 1;
        formPanel.add(subjectNameField, c);

        // Student ID
        c.gridx = 0; c.gridy = 2;
        formPanel.add(new JLabel("Student ID:"), c);
        c.gridx = 1;
        formPanel.add(studentIdField, c);

        // Lecturer ID
        c.gridx = 0; c.gridy = 3;
        formPanel.add(new JLabel("Lecturer ID:"), c);
        c.gridx = 1;
        formPanel.add(lecturerIdField, c);

        add(formPanel, BorderLayout.CENTER);

        // ===== Buttons Panel =====
        JPanel btnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(10, 10, 10, 10);
        b.fill = GridBagConstraints.HORIZONTAL;

        JButton addBtn = styledButton("Add Subject");
        JButton assignStudentBtn = styledButton("Assign Student");
        JButton assignLecturerBtn = styledButton("Assign Lecturer");
        JButton listBtn = styledButton("List Subjects");

        b.gridx = 0; b.gridy = 0; btnPanel.add(addBtn, b);
        b.gridy = 1; btnPanel.add(assignStudentBtn, b);
        b.gridy = 2; btnPanel.add(assignLecturerBtn, b);
        b.gridy = 3; btnPanel.add(listBtn, b);

        add(btnPanel, BorderLayout.EAST);

        // ===== Actions =====

        // Add Subject
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(subjectIdField.getText());
                subjectService.addSubject(new Subject(id, subjectNameField.getText()));
                JOptionPane.showMessageDialog(this, "Subject Added");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // Assign Student
        assignStudentBtn.addActionListener(e -> {
            try {
                int subjectId = Integer.parseInt(subjectIdField.getText());
                int studentId = Integer.parseInt(studentIdField.getText());

                Student student = new Student(studentId, "Student " + studentId,
                        "student" + studentId + "@mail.com");

                subjectService.assignSubjectToStudent(subjectId, student);
                JOptionPane.showMessageDialog(this, "Student Assigned");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // Assign Lecturer
        assignLecturerBtn.addActionListener(e -> {
            try {
                int subjectId = Integer.parseInt(subjectIdField.getText());
                int lecturerId = Integer.parseInt(lecturerIdField.getText());

                Lecturer lecturer = new Lecturer(lecturerId, "Lecturer " + lecturerId,
                        "lecturer" + lecturerId + "@mail.com");

                subjectService.assignSubjectToLecturer(subjectId, lecturer);
                JOptionPane.showMessageDialog(this, "Lecturer Assigned");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // List Subjects
        listBtn.addActionListener(e -> subjectService.listSubjects());
    }

    // ===== Reusable Button Style =====
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(170, 40));
        btn.setFocusPainted(false);
        return btn;
    }
}

// Grade Aooroval Gui--------------------------------------------------------------------------------------------------------------------------------------
class GradeApprovalGUI extends JFrame {

    private GradeApproval gradeService = new GradeApproval();

    public GradeApprovalGUI() {
        setTitle("Grade Approval System");
        setSize(550, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel title = new JLabel("Grade Approval", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 15, 15, 15);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField examNameField = new JTextField(15);

        // Exam Name row
        c.gridx = 0; c.gridy = 0;
        formPanel.add(new JLabel("Exam Name:"), c);

        c.gridx = 1;
        formPanel.add(examNameField, c);

        add(formPanel, BorderLayout.CENTER);

        // ===== Buttons Panel =====
        JPanel btnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(10, 10, 10, 10);
        b.fill = GridBagConstraints.HORIZONTAL;

        JButton reviewBtn = styledButton("Review Grades");
        JButton approveBtn = styledButton("Approve Grades");
        JButton publishBtn = styledButton("Publish Results");

        b.gridx = 0; b.gridy = 0;
        btnPanel.add(reviewBtn, b);

        b.gridy = 1;
        btnPanel.add(approveBtn, b);

        b.gridy = 2;
        btnPanel.add(publishBtn, b);

        add(btnPanel, BorderLayout.EAST);

        // ===== Actions =====

        // Review Grades
        reviewBtn.addActionListener(e -> {
            try {
                String examName = examNameField.getText();
                Exam exam = new Exam(examName, "DefaultSubject", 60);
                gradeService.reviewGrades(exam);
                JOptionPane.showMessageDialog(this, "Grades Reviewed");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // Approve Grades
        approveBtn.addActionListener(e -> {
            try {
                String examName = examNameField.getText();
                Exam exam = new Exam(examName, "DefaultSubject", 60);
                gradeService.approvedGrades(exam);
                JOptionPane.showMessageDialog(this, "Grades Approved");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        // Publish Grades
        publishBtn.addActionListener(e -> {
            try {
                String examName = examNameField.getText();
                Exam exam = new Exam(examName, "DefaultSubject", 60);
                exam.setApproved(true);
                gradeService.publishGrades(exam);
                JOptionPane.showMessageDialog(this, "Results Published");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });
    }

    // ===== Button Styling =====
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(170, 40));
        btn.setFocusPainted(false);
        return btn;
    }
}
class Main {
    public static void main(String[] args) {
        Admin admin = new Admin("YOUCCEF", "الحمدلله");
        SwingUtilities.invokeLater(() -> {
            AdminGui gui = new AdminGui(admin);
            gui.setVisible(true);
        });
    }
}
