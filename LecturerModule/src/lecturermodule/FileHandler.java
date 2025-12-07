import java.io.*;
import java.util.*;

public class FileHandler {
    private String path = System.getProperty("user.dir") + File.separator;

    public void ensureExamFileExists() {
        try {
            File f = new File(path + "exams.txt");
            if(!f.exists()) f.createNewFile();
        } catch(Exception e){}
    }

    public void addExam(String id, String subject, String duration) {
        try {
            FileWriter w = new FileWriter(path + "exams.txt", true);
            w.write(id + "," + subject + "," + duration + "\n");
            w.close();
            new File(path + "q_" + id + ".txt").createNewFile();
            new File(path + "sub_" + id + ".txt").createNewFile();
        } catch(Exception e){}
    }

    public void addQuestion(String id, String type, String text, String answer) {
        try {
            FileWriter w = new FileWriter(path + "q_" + id + ".txt", true);
            w.write(type + "|" + text + "|" + answer + "\n");
            w.close();
        } catch(Exception e){}
    }

    public List<String> getExams() {
        List<String> exams = new ArrayList<>();
        try {
            BufferedReader r = new BufferedReader(new FileReader(path + "exams.txt"));
            String line;
            while((line=r.readLine()) != null) exams.add(line);
        } catch(Exception e){}
        return exams;
    }

    public List<String> getQuestions(String id) {
        List<String> qs = new ArrayList<>();
        try {
            BufferedReader r = new BufferedReader(new FileReader(path + "q_" + id + ".txt"));
            String line;
            while((line=r.readLine()) != null) qs.add(line);
        } catch(Exception e){}
        return qs;
    }

    public void saveScore(String id, String student, int score) {
        try {
            FileWriter w = new FileWriter(path + "sub_" + id + ".txt", true);
            w.write(student + "," + score + "\n");
            w.close();
        } catch(Exception e){}
    }

    public List<Integer> getScores(String id) {
        List<Integer> scores = new ArrayList<>();
        try {
            BufferedReader r = new BufferedReader(new FileReader(path + "sub_" + id + ".txt"));
            String line;
            while((line=r.readLine()) != null) {
                scores.add(Integer.parseInt(line.split(",")[1]));
            }
        } catch(Exception e){}
        return scores;
    }
}
