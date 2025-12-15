package lecturermodule;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LecturerGUI extends JFrame {

    private FileHandler fh = new FileHandler();

    public LecturerGUI() {
        fh.ensureExamFileExists();
        setTitle("Lecturer Module");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));

        JLabel title = new JLabel("Lecturer", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 25, 25));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        String[] btnNames = {"Create Exam", "Add Question", "Show Exams", "Grade Exam", "Class Report", "Exit"};
        for(String name : btnNames) {
            JButton btn = new JButton(name);
            btn.setFont(new Font("Arial", Font.BOLD, 27));
            btn.addActionListener(e -> handleButton(name));
            buttonsPanel.add(btn);
        }

        add(buttonsPanel, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(245, 245, 245));
        setVisible(true);
    }

    private void handleButton(String name) {
        switch (name) {
            case "Create Exam" -> createExam();
            case "Add Question" -> addQuestion();
            case "Show Exams" -> showExams();
            case "Grade Exam" -> gradeExam();
            case "Class Report" -> classReport();
            case "Exit" -> System.exit(0);
        }
    }

    private void createExam() {
        String id = JOptionPane.showInputDialog(this, "Exam ID:");
        if(id == null || id.isEmpty()) return;
        String subject = JOptionPane.showInputDialog(this, "Subject:");
        if(subject == null) return;
        String duration = JOptionPane.showInputDialog(this, "Duration:");
        if(duration == null) return;
        fh.addExam(id, subject, duration);
        JOptionPane.showMessageDialog(this, "Exam Created Successfully");
    }

    private void addQuestion() {
        String id = JOptionPane.showInputDialog(this, "Enter Exam ID:");
        if(id == null || id.isEmpty()) return;
        String[] types = {"mcq", "tf", "short"};
        String type = (String) JOptionPane.showInputDialog(this, "Select Question Type:", "Question Type",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
        if(type == null) return;
        String text = JOptionPane.showInputDialog(this, "Question Text:");
        if(text == null) return;
        String answer = JOptionPane.showInputDialog(this, "Correct Answer:");
        if(answer == null) return;
        fh.addQuestion(id, type, text, answer);
        JOptionPane.showMessageDialog(this, "Question Added");
    }

    private void showExams() {
        List<String> exams = fh.getExams();
        if(exams.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Exams Found");
            return;
        }
        JOptionPane.showMessageDialog(this, String.join("\n", exams), "Exams", JOptionPane.INFORMATION_MESSAGE);
    }

    private void gradeExam() {
        String id = JOptionPane.showInputDialog(this, "Enter Exam ID:");
        if(id == null || id.isEmpty()) return;
        List<String> qs = fh.getQuestions(id);
        if(qs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Questions Found For This Exam");
            return;
        }
        String student = JOptionPane.showInputDialog(this, "Student Name:");
        if(student == null) return;
        int score = 0;
        for(String q : qs) {
            String[] a = q.split("\\|");
            String ans = JOptionPane.showInputDialog(this, a[1]);
            if(ans != null && ans.equalsIgnoreCase(a[2])) score++;
        }
        fh.saveScore(id, student, score);
        JOptionPane.showMessageDialog(this, "Final Score: " + score);
    }

    private void classReport() {
        String id = JOptionPane.showInputDialog(this, "Enter Exam ID:");
        if(id == null || id.isEmpty()) return;
        List<Integer> scores = fh.getScores(id);
        if(scores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Data Found");
            return;
        }
        int sum = scores.stream().mapToInt(Integer::intValue).sum();
        int min = scores.stream().mapToInt(Integer::intValue).min().orElse(0);
        int max = scores.stream().mapToInt(Integer::intValue).max().orElse(0);
        JOptionPane.showMessageDialog(this, "Average: " + (sum / scores.size()) + "\nMin: " + min + "\nMax: " + max,
                "Class Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LecturerGUI::new);
    }
}
