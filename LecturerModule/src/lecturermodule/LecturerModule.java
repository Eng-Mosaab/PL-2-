import java.io.*;
import java.util.*;

public class LecturerModule {
    static Scanner sc = new Scanner(System.in);
    static String path = System.getProperty("user.dir") + File.separator;

    public static void main(String[] args) {
        File f = new File(path + "exams.txt");
        if(!f.exists()){
            try{f.createNewFile();}catch(Exception e){}
        }
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
        System.out.print("Exam ID: ");
        String id = sc.nextLine();
        System.out.print("Subject: ");
        String s = sc.nextLine();
        System.out.print("Duration: ");
        String d = sc.nextLine();
        try{
            FileWriter w = new FileWriter(path + "exams.txt", true);
            w.write(id+","+s+","+d+"\n");
            w.close();
            new File(path + "q_"+id+".txt").createNewFile();
            new File(path + "sub_"+id+".txt").createNewFile();
        }catch(Exception e){}
    }

    static void addQuestion(){
        System.out.print("Exam ID: ");
        String id = sc.nextLine();
        System.out.print("Type (mcq/tf/short): ");
        String t = sc.nextLine();
        System.out.print("Text: ");
        String text = sc.nextLine();
        System.out.print("Answer: ");
        String ans = sc.nextLine();
        try{
            FileWriter w = new FileWriter(path + "q_"+id+".txt", true);
            w.write(t+"|"+text+"|"+ans+"\n");
            w.close();
        }catch(Exception e){}
    }

    static void showExams(){
        try{
            BufferedReader r = new BufferedReader(new FileReader(path + "exams.txt"));
            String line;
            while((line=r.readLine())!=null){
                System.out.println(line);
            }
        }catch(Exception e){}
    }

    static void gradeExam(){
        System.out.print("Exam ID: ");
        String id = sc.nextLine();
        List<String> qs = new ArrayList<>();
        try{
            BufferedReader r = new BufferedReader(new FileReader(path + "q_"+id+".txt"));
            String l;
            while((l=r.readLine())!=null) qs.add(l);
        }catch(Exception e){}
        System.out.print("Student Name: ");
        String st = sc.nextLine();
        int score = 0;
        for(String q : qs){
            String[] a = q.split("\\|");
            System.out.println(a[1]);
            String ans = sc.nextLine();
            if(ans.equalsIgnoreCase(a[2])) score++;
        }
        try{
            FileWriter w = new FileWriter(path + "sub_"+id+".txt", true);
            w.write(st+","+score+"\n");
            w.close();
        }catch(Exception e){}
        System.out.println("Final Score: "+score);
    }

    static void classReport(){
        System.out.print("Exam ID: ");
        String id = sc.nextLine();
        List<Integer> scores = new ArrayList<>();
        try{
            BufferedReader r = new BufferedReader(new FileReader(path + "sub_"+id+".txt"));
            String l;
            while((l=r.readLine())!=null){
                String[] a = l.split(",");
                scores.add(Integer.parseInt(a[1]));
            }
        }catch(Exception e){}
        if(scores.size()==0){
            System.out.println("No Data");
            return;
        }
        int sum=0,min=999,max=0;
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
