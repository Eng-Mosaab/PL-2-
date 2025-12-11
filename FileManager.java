import java.util.*;
import java.io.*;

public class FileManager  {
     static final String RESULTS_FILE = "data/results.txt";
     static final String ATTEMPTS_FILE = "data/exam_attempts.txt";
     static final String RECHECK_FILE = "data/recorrection_requests.txt";
     static final String FEEDBACK = "data/exam_feedback.txt";
     static final String SUBJECTS = "data/student_subjects.txt";
    static final String STUDENT_DATA = "data/student_data.txt";

//     3lshan a2asem el line el bakhdo men el file l parts a3rf astakhdemha.
     private String line;
     private String[] parts;

     public void saveStudent(Student student){
         try {
             BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_DATA,true));
             String line = student.getStudentId() + "," + student.getEmail() + "," + student.getPassword();
             writer.write(line);
             writer.newLine();
             writer.close();
         } catch (IOException e) {
             System.out.println("error");
         }
     }
    public boolean takenExam(String studentId, String examId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ATTEMPTS_FILE));
            while((line = reader.readLine()) != null){
                parts = line.split(",");
                if(parts[0].equals(studentId) && parts[1].equals(examId)){
                    return true;
                }
            }
        } catch (IOException e){
            System.out.println("error");
        }
        return false;
    }

    public void addExamAttempts(String studentId, String examId){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ATTEMPTS_FILE,true));
            if(!takenExam(studentId, examId)){
                writer.write(studentId +","+examId);
                writer.newLine();
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("error.");
        }
    }

    public void recorrectionRequest(String studentId, String examId, String request){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(RECHECK_FILE,true));
            writer.write(studentId+","+examId+","+request);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("can't handle your file");
        }
    }

    public boolean RegisteredForSubject(String studentId, String subjectId){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(SUBJECTS));
            while ((line = reader.readLine()) != null){
                parts = line.split(",");
                if(parts[0].equals(studentId) && parts[1].equals(subjectId)){
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("can't find your file");
        }
        return false;
    }

    public String getGrade(String studentId, String examId){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(RESULTS_FILE));
            while ((line = reader.readLine()) != null){
                parts = line.split(",");
                if(parts[0].equals(studentId) && parts[1].equals(examId)){
                    return parts[2];
                }
            }
        } catch (IOException e) {
            System.out.println("can't find your file");
        }
        return null;
    }

    public void examFeedback(String studentId, String examId, String feedback){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK,true));
            writer.write(studentId+","+examId+","+feedback);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("can't handle your file");
        }
    }
}
