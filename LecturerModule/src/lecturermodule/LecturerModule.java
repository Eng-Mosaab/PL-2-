import java.util.*;

public class LecturerModule {
    static Scanner sc = new Scanner(System.in);
    static FileHandler fh = new FileHandler();

    public static void main(String[] args) {
        fh.ensureExamFileExists();
        while(true){
            System.out.println("1- Create Exam");
            System.out.println("2- Add Question");
            System.out.println("3- Show Exams");
            System.out.println("4- Grade Exam");
            System.out.println("5- Class Report");
            System.out.println("6- Exit");
            String c = sc.nextLine();
            if(c.equals("1")) createExam();
            else if(c.equals("2")) addQuestion();
            else if(c.equals("3")) showExams();
            else if(c.equals("4")) gradeExam();
            else if(c.equals("5")) classReport();
            else if(c.equals("6")) break;
        }
    }

    static void createExam(){
        System.out.print("Exam ID: "); String id = sc.nextLine();
        System.out.print("Subject: "); String s = sc.nextLine();
        System.out.print("Duration: "); String d = sc.nextLine();
        fh.addExam(id, s, d);
    }

    static void addQuestion(){
        System.out.print("Exam ID: "); String id = sc.nextLine();
        System.out.print("Type (mcq/tf/short): "); String t = sc.nextLine();
        System.out.print("Text: "); String text = sc.nextLine();
        System.out.print("Answer: "); String ans = sc.nextLine();
        fh.addQuestion(id, t, text, ans);
    }

    static void showExams(){
        List<String> exams = fh.getExams();
        for(String e: exams) System.out.println(e);
    }

    static void gradeExam(){
        System.out.print("Exam ID: "); String id = sc.nextLine();
        List<String> qs = fh.getQuestions(id);
        System.out.print("Student Name: "); String st = sc.nextLine();
        int score = 0;
        for(String q: qs){
            String[] a = q.split("\\|");
            System.out.println(a[1]);
            String ans = sc.nextLine();
            if(ans.equalsIgnoreCase(a[2])) score++;
        }
        fh.saveScore(id, st, score);
        System.out.println("Final Score: " + score);
    }

    static void classReport(){
        System.out.print("Exam ID: "); String id = sc.nextLine();
        List<Integer> scores = fh.getScores(id);
        if(scores.isEmpty()) { System.out.println("No Data"); return; }
        int sum=0, min=999, max=0;
        for(int s: scores){
            sum+=s;
            if(s<min) min=s;
            if(s>max) max=s;
        }
        System.out.println("Average: "+(sum/scores.size()));
        System.out.println("Min: "+min);
        System.out.println("Max: "+max);
    }
}
