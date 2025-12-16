import java.io.*;
import java.util.ArrayList;

public class file_manger {
    public static final String userfile = "data/user.txt";
    public static final String studentfile = "data/student.txt";
    public static final String lecfile = "data/lec.txt";
    public static final String subjectfile = "data/subject.txt";
    public static final String exams = "data/exams.txt"; 



    // User file handling-----------------------------------------------------------

    public static void saveuser(user u) {
        folderExist();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(userfile, true));
            bw.write(u.getId() + "," + u.getName() + "," + u.getEmail() + "," + u.getPassword());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving user !!!");
        }       
    }

    public static ArrayList<user> loadusers() {
        ArrayList<user> users = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(userfile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    user u = new user(parts[0], parts[1], parts[2], parts[3]);
                    users.add(u);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading users !!!");
        }       
        return users;
    }

    public static void updateuser(String id, String newname, String newemail, String newpassword) {
        folderExist();
        ArrayList<user> users = loadusers();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(userfile));
            for (user u : users) {
                if (u.getId().equals(id)) {
                    u.setName(newname);
                    u.setEmail(newemail);
                    u.setPassword(newpassword);
                }
                bw.write(u.getId() + "," + u.getName() + "," + u.getEmail() + "," + u.getPassword());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error updating user !!!");
        }       
    }

    public static void deleteuser(String id) {
        folderExist();
        ArrayList<user> users = loadusers();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(userfile));
            for (user u : users) {
                if (!u.getId().equals(id)) {
                    bw.write(u.getId() + "," + u.getName() + "," + u.getEmail() + "," + u.getPassword());
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error deleting user !!!");
        }  
    }

    // Student file handling--------------------------------------------------------


    public static void savestudent(student s) {
        folderExist();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(studentfile, true));
            bw.write(s.getId() + "," + s.getName() + "," + s.getEmail() + "," + s.getPassword() + "," + s.getLevel());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving student !!!");
        }       
    }

    public static ArrayList<student> loadstudents() {
        ArrayList<student> students = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(studentfile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    student s = new student(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    students.add(s);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading students !!!");
        }       
        return students;
    }

    // Lecture file handling--------------------------------------------------------


    public static void savelec(lecture l) {
        folderExist();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(lecfile, true));
            bw.write(l.getId() + "," + l.getName() + "," + l.getEmail() + "," + l.getPassword() + "," + l.getDepartment());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving lecture !!!");
        }       
    }

    public static ArrayList<lecture> loadlecs() {
        ArrayList<lecture> lecs = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(lecfile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    lecture l = new lecture(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    lecs.add(l);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading lectures !!!");
        }       
        return lecs;
    }   

    // Subject file handling--------------------------------------------------------

    public static void savesubject(subject s) {
        folderExist();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(subjectfile, true));
            bw.write(s.getCode() + "," + s.getName() + "," + s.getLevel() + "," + s.getCreditHours());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving subject !!!");
        }
    }

    public static ArrayList<subject> loadsubjects() {
        ArrayList<subject> subjects = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(subjectfile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    subject s = new subject(parts[0], parts[1], parts[2], parts[3]);
                    subjects.add(s);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading subjects !!!");
        }  
        return subjects;
    }

    // Exam file handling--------------------------------------------------------
    
    public static void saveexam(exam e) {
        folderExist();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(exams, true));
            bw.write(e.getExamId() + "," + e.getSubjectCode() + "," + e.getDate() + "," + e.getDuration());
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            System.out.println("Error saving exam !!!");
        }       
    }

    public static ArrayList<exam> loadexams() {
        ArrayList<exam> examlist = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(exams));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    exam e = new exam(parts[0], parts[1], parts[2], parts[3]);
                    examlist.add(e);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading exams !!!");
        }       
        return examlist;
    }       
}


