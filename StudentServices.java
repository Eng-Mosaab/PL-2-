import java.util.Scanner;

public class StudentServices {
    static FileManager file = new FileManager();
    static Scanner input = new Scanner(System.in);

    public static void examEntry(String studentId, String examId){
        if(file.takenExam(studentId,examId)){
            System.out.println("You've already taken this exam.");
        }
        else {
            System.out.println("Taking the exam...");
            file.addExamAttempts(studentId,examId);
            System.out.println("Your exam attempt was added.");
        }

    }

    public static void requestRecorrection(String studentId, String examId){
        System.out.print("Enter your reason for re-correction: ");
        String reason = input.nextLine();
        file.recorrectionRequest(studentId,examId,reason);

        System.out.println("Your request was submitted.");
    }
    public static void accessToExam(String studentId, String examId) {
        if (file.RegisteredForSubject(studentId, examId)) {
            System.out.println("you are registered this subject,you can take the exam");
        } else {
            System.out.println("Not available,you are not registered this subject");
        }
    }

    public static void veiwResult(String studentId, String examId) {
        if (file.takenExam(studentId, examId)) {
            String grade = file.getGrade(studentId, examId);
            if (grade == null) {
                System.out.println("Not available");
            } else {
                System.out.println("Student " + studentId + "your result for exam " + examId + "is:" + grade);
            }

        } else {
            System.out.println("You did not take this exam");
            return;
        }

    }

    public static void examFeedback(String studentId, String examId , String feedback) {
        if (file.takenExam(studentId, examId)) {
            System.out.println("Send your feedback on exam " + examId);
            feedback = input.nextLine();
            file.examFeedback(studentId, examId, feedback);
        } else {
            System.out.println("You did not take this exam");
            return;
        }

    }
}
