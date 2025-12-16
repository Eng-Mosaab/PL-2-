package lecturermodule;

import java.io.*;
import java.util.*;

public class FileHandler {

    private final String path;

    public FileHandler() {
        path = System.getProperty("user.dir") + File.separator;
        ensureExamFileExists();
    }

  
    public void ensureExamFileExists() {
        try {
            File examsFile = new File(path + "exams.txt");
            if (!examsFile.exists()) examsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addExam(String id, String subject, String duration) {
        try (FileWriter writer = new FileWriter(path + "exams.txt", true)) {
            writer.write(id + "," + subject + "," + duration + "\n");
 
            new File(path + "q_" + id + ".txt").createNewFile();
            new File(path + "sub_" + id + ".txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getExams() {
        List<String> exams = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "exams.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                exams.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exams;
    }


    public void addQuestion(String examId, String type, String text, String answer) {
        try (FileWriter writer = new FileWriter(path + "q_" + examId + ".txt", true)) {
            writer.write(type + "|" + text + "|" + answer + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getQuestions(String examId) {
        List<String> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "q_" + examId + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

  
    public void saveScore(String examId, String student, int score) {
        try (FileWriter writer = new FileWriter(path + "sub_" + examId + ".txt", true)) {
            writer.write(student + "," + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getScores(String examId) {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "sub_" + examId + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    scores.add(Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

   
    public boolean examExists(String examId) {
        List<String> exams = getExams();
        for (String e : exams) {
            if (e.startsWith(examId + ",")) return true;
        }
        return false;
    }
}
