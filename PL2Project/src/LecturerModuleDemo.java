import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;


public class LecturerModuleDemo {

 
    public static class Exam implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public long subjectId;
        public String title;
        public LocalDateTime startTime, endTime;
        public int durationMinutes;
        public String status = "DRAFT"; 
        public long createdByLecturerId;
        public List<Question> questions = new ArrayList<>();
        public Exam() {}
        @Override public String toString() {
            return String.format("Exam[id=%d,title=%s,subjectId=%d,status=%s,questions=%d]", id, title, subjectId, status, questions.size());
        }
    }

    public static class Question implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public String qtype; 
        public String text;
        public double points = 1.0;
        public List<Choice> choices = new ArrayList<>(); 
        public Question() {}
        @Override public String toString() {
            return String.format("Q[id=%d,type=%s,points=%.2f] %s", id, qtype, points, text);
        }
    }

    public static class Choice implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public String text;
        public boolean isCorrect;
        public Choice() {}
        @Override public String toString() { return String.format("Choice[id=%d,correct=%s] %s", id, isCorrect, text); }
    }

    public static class ExamAttempt implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public long examId;
        public long studentId;
        public LocalDateTime startedAt;
        public LocalDateTime submittedAt;
        public String status = "IN_PROGRESS"; 
        public List<StudentAnswer> answers = new ArrayList<>();
        public Double totalScore = null;
        public ExamAttempt() {}
        @Override public String toString() { return String.format("Attempt[id=%d,examId=%d,studentId=%d,status=%s,totalScore=%s]", id, examId, studentId, status, totalScore); }
    }

    public static class StudentAnswer implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public long questionId;
        public Long selectedChoiceId; 
        public String answerText;     
        public Double score;          
        public StudentAnswer() {}
        @Override public String toString() {
            return String.format("Ans[id=%d,q=%d,choice=%s,text=%s,score=%s]", id, questionId, selectedChoiceId, answerText, score);
        }
    }

    public static class Grade implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public long attemptId;
        public double finalScore;
        public long gradedBy; 
        public LocalDateTime gradedAt;
        public boolean published = false;
        public Grade() {}
    }


    public static class Subject implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public String code;
        public String title;
        public Subject() {}
        @Override public String toString() { return String.format("Subject[id=%d,code=%s,title=%s]", id, code, title); }
    }

    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        public long id;
        public String name;
        public Student() {}
        @Override public String toString() { return String.format("Student[id=%d,name=%s]", id, name); }
    }

   
    public static class DataStore {
        public List<Exam> exams = new ArrayList<>();
        public List<ExamAttempt> attempts = new ArrayList<>();
        public List<Grade> grades = new ArrayList<>();
        public List<Subject> subjects = new ArrayList<>();
        public List<Student> students = new ArrayList<>();

        private final String EXAMS_F = "lect_exams.dat";
        private final String ATTEMPTS_F = "lect_attempts.dat";
        private final String GRADES_F = "lect_grades.dat";
        private final String SUBJECTS_F = "lect_subjects.dat";
        private final String STUDENTS_F = "lect_students.dat";

  
        private long examSeq = 1;
        private long questionSeq = 1;
        private long choiceSeq = 1;
        private long attemptSeq = 1;
        private long answerSeq = 1;
        private long gradeSeq = 1;
        private long subjectSeq = 1;
        private long studentSeq = 1;

        public void loadAll() {
            exams = read(EXAMS_F, exams);
            attempts = read(ATTEMPTS_F, attempts);
            grades = read(GRADES_F, grades);
            subjects = read(SUBJECTS_F, subjects);
            students = read(STUDENTS_F, students);
            
            for (Exam e : exams) {
                examSeq = Math.max(examSeq, e.id + 1);
                for (Question q : e.questions) {
                    questionSeq = Math.max(questionSeq, q.id + 1);
                    for (Choice c : q.choices) choiceSeq = Math.max(choiceSeq, c.id + 1);
                }
            }
            for (ExamAttempt at : attempts) {
                attemptSeq = Math.max(attemptSeq, at.id + 1);
                for (StudentAnswer sa : at.answers) answerSeq = Math.max(answerSeq, sa.id + 1);
            }
            for (Grade g : grades) gradeSeq = Math.max(gradeSeq, g.id + 1);
            for (Subject s : subjects) subjectSeq = Math.max(subjectSeq, s.id + 1);
            for (Student st : students) studentSeq = Math.max(studentSeq, st.id + 1);
        }

        public void saveAll() {
            write(EXAMS_F, exams);
            write(ATTEMPTS_F, attempts);
            write(GRADES_F, grades);
            write(SUBJECTS_F, subjects);
            write(STUDENTS_F, students);
        }

        @SuppressWarnings("unchecked")
        private <T> List<T> read(String fname, List<T> fallback) {
            File f = new File(fname);
            if (!f.exists()) return fallback;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object o = ois.readObject();
                return (List<T>) o;
            } catch (Exception ex) {
                System.out.println("Warning: couldn't read " + fname + " : " + ex.getMessage());
                return fallback;
            }
        }

        private <T> void write(String fname, List<T> data) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fname))) {
                oos.writeObject(data);
            } catch (Exception ex) {
                System.out.println("Error writing " + fname + " : " + ex.getMessage());
            }
        }

       
        public long nextExamId() { return examSeq++; }
        public long nextQuestionId() { return questionSeq++; }
        public long nextChoiceId() { return choiceSeq++; }
        public long nextAttemptId() { return attemptSeq++; }
        public long nextAnswerId() { return answerSeq++; }
        public long nextGradeId() { return gradeSeq++; }
        public long nextSubjectId() { return subjectSeq++; }
        public long nextStudentId() { return studentSeq++; }

        
        public Optional<Exam> findExam(long id) { return exams.stream().filter(e->e.id==id).findFirst(); }
        public Optional<ExamAttempt> findAttempt(long id) { return attempts.stream().filter(a->a.id==id).findFirst(); }
        public Optional<Grade> findGradeByAttempt(long attemptId) { return grades.stream().filter(g->g.attemptId==attemptId).findFirst(); }
        public Optional<Subject> findSubject(long id) { return subjects.stream().filter(s->s.id==id).findFirst(); }
        public Optional<Student> findStudent(long id) { return students.stream().filter(s->s.id==id).findFirst(); }
    }

   
    public static class LecturerModule {
        private DataStore ds;
        public LecturerModule(DataStore ds) { this.ds = ds; }

       
        public Subject createSubject(String code, String title) {
            Subject s = new Subject(); s.id = ds.nextSubjectId(); s.code = code; s.title = title; ds.subjects.add(s); ds.saveAll(); return s;
        }
        public Student createStudent(String name) {
            Student st = new Student(); st.id = ds.nextStudentId(); st.name = name; ds.students.add(st); ds.saveAll(); return st;
        }

     
        public Exam createExam(long subjectId, String title, LocalDateTime start, LocalDateTime end, int durationMinutes, long lecturerId) {
            if (!ds.findSubject(subjectId).isPresent()) throw new RuntimeException("Subject not found");
            Exam e = new Exam();
            e.id = ds.nextExamId();
            e.subjectId = subjectId;
            e.title = title;
            e.startTime = start;
            e.endTime = end;
            e.durationMinutes = durationMinutes;
            e.status = "DRAFT";
            e.createdByLecturerId = lecturerId;
            ds.exams.add(e);
            ds.saveAll();
            return e;
        }

      
        public Question addQuestion(long examId, String qtype, String text, double points) {
            Exam e = ds.findExam(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
            Question q = new Question();
            q.id = ds.nextQuestionId();
            q.qtype = qtype.toUpperCase();
            q.text = text;
            q.points = points;
            e.questions.add(q);
            ds.saveAll();
            return q;
        }


        public Choice addChoice(long examId, long questionId, String text, boolean isCorrect) {
            Exam e = ds.findExam(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
            Question q = e.questions.stream().filter(x->x.id==questionId).findFirst().orElseThrow(() -> new RuntimeException("Question not found"));
            Choice c = new Choice();
            c.id = ds.nextChoiceId();
            c.text = text;
            c.isCorrect = isCorrect;
            q.choices.add(c);
            ds.saveAll();
            return c;
        }

        public void publishExam(long examId) {
            Exam e = ds.findExam(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
            e.status = "PUBLISHED";
            ds.saveAll();
        }

        
        public ExamAttempt startAttempt(long examId, long studentId) {
           
            boolean exists = ds.attempts.stream().anyMatch(a->a.examId==examId && a.studentId==studentId);
            if (exists) throw new RuntimeException("Attempt already exists for this student and exam");
            ExamAttempt at = new ExamAttempt();
            at.id = ds.nextAttemptId();
            at.examId = examId;
            at.studentId = studentId;
            at.startedAt = LocalDateTime.now();
            at.status = "IN_PROGRESS";
            ds.attempts.add(at);
            ds.saveAll();
            return at;
        }

       
        public void submitAttempt(long attemptId, List<StudentAnswer> answers) {
            ExamAttempt at = ds.findAttempt(attemptId).orElseThrow(() -> new RuntimeException("Attempt not found"));
            if (!"IN_PROGRESS".equals(at.status)) throw new RuntimeException("Attempt not in progress");
            
            for (StudentAnswer sa : answers) {
                sa.id = ds.nextAnswerId();
            }
            at.answers = new ArrayList<>(answers);
            at.submittedAt = LocalDateTime.now();
            at.status = "SUBMITTED";
     
            autoGrade(at);
            ds.saveAll();
        }

        
        private void autoGrade(ExamAttempt at) {
            Exam e = ds.findExam(at.examId).orElseThrow(() -> new RuntimeException("Exam not found"));
            double total = 0.0;
            for (StudentAnswer sa : at.answers) {
                Optional<Question> oq = e.questions.stream().filter(q->q.id==sa.questionId).findFirst();
                if (!oq.isPresent()) continue;
                Question q = oq.get();
                if ("MCQ".equalsIgnoreCase(q.qtype) || "TF".equalsIgnoreCase(q.qtype)) {
                    boolean correct = false;
                    if (sa.selectedChoiceId != null) {
                        for (Choice c : q.choices) {
                            if (c.id == sa.selectedChoiceId && c.isCorrect) { correct = true; break; }
                        }
                    }
                    sa.score = correct ? q.points : 0.0;
                    total += sa.score;
                } else if ("SHORT".equalsIgnoreCase(q.qtype)) {
                    if (sa.answerText != null && !q.choices.isEmpty()) {
                        String given = sa.answerText.trim().toLowerCase();
                        boolean correct = q.choices.stream().anyMatch(c->c.isCorrect && c.text.trim().toLowerCase().equals(given));
                        sa.score = correct ? q.points : 0.0;
                        total += sa.score;
                    } else {
                        sa.score = 0.0;
                    }
                } else {
                    
                    sa.score = null;
                }
            }
            at.totalScore = total;
            at.status = "GRADED";
            
            Optional<Grade> og = ds.findGradeByAttempt(at.id);
            if (og.isPresent()) {
                Grade g = og.get();
                g.finalScore = total;
                g.gradedBy = -1;
                g.gradedAt = LocalDateTime.now();
                g.published = false;
            } else {
                Grade g = new Grade();
                g.id = ds.nextGradeId();
                g.attemptId = at.id;
                g.finalScore = total;
                g.gradedBy = -1;
                g.gradedAt = LocalDateTime.now();
                g.published = false;
                ds.grades.add(g);
            }
        }

       
        public void manualGradeAnswer(long attemptId, long answerId, double score, long graderId) {
            ExamAttempt at = ds.findAttempt(attemptId).orElseThrow(() -> new RuntimeException("Attempt not found"));
            StudentAnswer sa = at.answers.stream().filter(x->x.id==answerId).findFirst().orElseThrow(() -> new RuntimeException("Answer not found"));
            sa.score = score;
           
            double total = 0;
            for (StudentAnswer s : at.answers) if (s.score != null) total += s.score;
            at.totalScore = total;
            
            Optional<Grade> og = ds.findGradeByAttempt(at.id);
            if (og.isPresent()) {
                Grade g = og.get();
                g.finalScore = total;
                g.gradedAt = LocalDateTime.now();
                g.gradedBy = graderId;
            } else {
                Grade g = new Grade();
                g.id = ds.nextGradeId();
                g.attemptId = at.id;
                g.finalScore = total;
                g.gradedAt = LocalDateTime.now();
                g.gradedBy = graderId;
                g.published = false;
                ds.grades.add(g);
            }
            ds.saveAll();
        }

        
        public void publishGrade(long gradeId) {
            Grade g = ds.grades.stream().filter(x->x.id==gradeId).findFirst().orElseThrow(() -> new RuntimeException("Grade not found"));
            g.published = true;
            ds.saveAll();
        }

        public Map<String, Object> examSummary(long examId) {
            Exam e = ds.findExam(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
            List<ExamAttempt> attempts = ds.attempts.stream().filter(a->a.examId==examId).collect(Collectors.toList());
            List<Double> scores = new ArrayList<>();
            for (ExamAttempt at : attempts) {
                Optional<Grade> og = ds.findGradeByAttempt(at.id);
                if (og.isPresent() && og.get().published) scores.add(og.get().finalScore);
            }
            int totalAttempts = attempts.size();
            double avg = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double max = scores.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
            double min = scores.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
            double median = 0;
            if (!scores.isEmpty()) {
                Collections.sort(scores);
                int n = scores.size();
                median = (n % 2 == 1) ? scores.get(n/2) : (scores.get(n/2 - 1) + scores.get(n/2)) / 2.0;
            }
            Map<String,Object> m = new LinkedHashMap<>();
            m.put("examId", examId);
            m.put("title", e.title);
            m.put("totalAttempts", totalAttempts);
            m.put("publishedGradesCount", scores.size());
            m.put("average", avg);
            m.put("max", max);
            m.put("min", min);
            m.put("median", median);
            return m;
        }

  
        public List<Map<String,Object>> questionStats(long examId) {
            Exam e = ds.findExam(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
            List<Map<String,Object>> stats = new ArrayList<>();
            for (Question q : e.questions) {
                Map<String,Object> s = new LinkedHashMap<>();
                s.put("questionId", q.id);
                s.put("text", q.text);
                s.put("points", q.points);
                s.put("answered", 0);
                s.put("correct", 0);
                stats.add(s);
            }
            List<ExamAttempt> attempts = ds.attempts.stream().filter(a->a.examId==examId).collect(Collectors.toList());
            for (ExamAttempt at : attempts) {
                for (StudentAnswer sa : at.answers) {
                    for (Map<String,Object> s : stats) {
                        if (((Long)s.get("questionId")).longValue() == sa.questionId) {
                            s.put("answered", ((Integer)s.get("answered")) + 1);
                     
                            Optional<Question> oq = e.questions.stream().filter(q->q.id==sa.questionId).findFirst();
                            if (oq.isPresent()) {
                                Question q = oq.get();
                                if (sa.score != null && Math.abs(sa.score - q.points) < 1e-6) {
                                    s.put("correct", ((Integer)s.get("correct")) + 1);
                                }
                            }
                        }
                    }
                }
            }
            for (Map<String,Object> s : stats) {
                int ans = (Integer)s.get("answered"); int cor = (Integer)s.get("correct");
                double pct = ans==0 ? 0.0 : (100.0 * cor / ans);
                s.put("percentCorrect", pct);
            }
            return stats;
        }

        public String exportExamReportCsv(long examId, String filename) throws IOException {
            Map<String,Object> sum = examSummary(examId);
            List<Map<String,Object>> qstats = questionStats(examId);
            try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
                pw.println("ExamId,Title,TotalAttempts,PublishedGradesCount,Average,Max,Min,Median");
                pw.printf("%d,%s,%d,%d,%.2f,%.2f,%.2f,%.2f\n",
                        (Long)sum.get("examId"),
                        quote((String)sum.get("title")),
                        (Integer)sum.get("totalAttempts"),
                        (Integer)sum.get("publishedGradesCount"),
                        (Double)sum.get("average"),
                        (Double)sum.get("max"),
                        (Double)sum.get("min"),
                        (Double)sum.get("median"));
                pw.println();
                pw.println("QuestionId,Text,Points,Answered,Correct,PercentCorrect");
                for (Map<String,Object> s : qstats) {
                    pw.printf("%d,%s,%.2f,%d,%d,%.2f\n",
                            (Long)s.get("questionId"),
                            quote((String)s.get("text")),
                            (Double)s.get("points"),
                            (Integer)s.get("answered"),
                            (Integer)s.get("correct"),
                            (Double)s.get("percentCorrect"));
                }
            }
            return filename;
        }

        private String quote(String s) {
            if (s == null) return "";
            if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
                return "\"" + s.replace("\"", "\"\"") + "\"";
            }
            return s;
        }
    }

   
    public static void main(String[] args) {
        DataStore ds = new DataStore();
        ds.loadAll();
        LecturerModule lm = new LecturerModule(ds);
        Scanner sc = new Scanner(System.in);

        System.out.println("Lecturer Module Demo (file-based). Data files are created in working directory.");
        System.out.println("First: create a Subject and some Students to simulate (option 1 & 2).");

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Create Subject");
            System.out.println("2) Create Student (for simulation)");
            System.out.println("3) Create Exam");
            System.out.println("4) Add Question to Exam");
            System.out.println("5) Add Choice to Question (for MCQ/TF/SHORT)");
            System.out.println("6) Publish Exam");
            System.out.println("7) Start Attempt (simulate student)");
            System.out.println("8) Submit Attempt (enter answers & auto-grade)");
            System.out.println("9) Manual grade an answer (essay)");
            System.out.println("10) Print exam report");
            System.out.println("11) Export exam report CSV");
            System.out.println("12) List Exams");
            System.out.println("13) List Attempts");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            String c = sc.nextLine().trim();
            try {
                switch (c) {
                    case "1": {
                        System.out.print("Subject code: "); String code = sc.nextLine().trim();
                        System.out.print("Subject title: "); String title = sc.nextLine().trim();
                        Subject s = lm.createSubject(code, title);
                        System.out.println("Created: " + s);
                        break;
                    }
                    case "2": {
                        System.out.print("Student name: "); String name = sc.nextLine().trim();
                        Student st = lm.createStudent(name);
                        System.out.println("Created: " + st);
                        break;
                    }
                    case "3": {
                        System.out.print("Subject id: "); long sid = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Title: "); String t = sc.nextLine().trim();
                        System.out.print("Start (yyyy-MM-ddTHH:mm) or empty: "); String stime = sc.nextLine().trim();
                        System.out.print("End (yyyy-MM-ddTHH:mm) or empty: "); String etime = sc.nextLine().trim();
                        System.out.print("Duration minutes: "); int dur = Integer.parseInt(sc.nextLine().trim());
                        LocalDateTime sdt = stime.isEmpty()?null:LocalDateTime.parse(stime);
                        LocalDateTime edt = etime.isEmpty()?null:LocalDateTime.parse(etime);
                        System.out.print("Lecturer id (any number, for demo): "); long lid = Long.parseLong(sc.nextLine().trim());
                        Exam ex = lm.createExam(sid, t, sdt, edt, dur, lid);
                        System.out.println("Exam created: " + ex);
                        break;
                    }
                    case "4": {
                        System.out.print("Exam id: "); long eid = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Type (MCQ/TF/SHORT/ESSAY): "); String qt = sc.nextLine().trim().toUpperCase();
                        System.out.print("Question text: "); String qtxt = sc.nextLine().trim();
                        System.out.print("Points: "); double pts = Double.parseDouble(sc.nextLine().trim());
                        Question q = lm.addQuestion(eid, qt, qtxt, pts);
                        System.out.println("Question added: " + q);
                        break;
                    }
                    case "5": {
                        System.out.print("Exam id: "); long eid = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Question id: "); long qid = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Choice text: "); String ctxt = sc.nextLine().trim();
                        System.out.print("Is correct? (true/false): "); boolean cor = Boolean.parseBoolean(sc.nextLine().trim());
                        Choice ch = lm.addChoice(eid, qid, ctxt, cor);
                        System.out.println("Choice added: " + ch);
                        break;
                    }
                    case "6": {
                        System.out.print("Exam id to publish: "); long eid = Long.parseLong(sc.nextLine().trim());
                        lm.publishExam(eid);
                        System.out.println("Published exam " + eid);
                        break;
                    }
                    case "7": {
                        System.out.print("Exam id: "); long eid = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Student id: "); long sid = Long.parseLong(sc.nextLine().trim());
                        ExamAttempt at = lm.startAttempt(eid, sid);
                        System.out.println("Attempt started: " + at);
                        break;
                    }
                    case "8": {
                        System.out.print("Attempt id: "); long aid = Long.parseLong(sc.nextLine().trim());
                        ExamAttempt at = ds.findAttempt(aid).orElseThrow(() -> new RuntimeException("Attempt not found"));
                        Exam ex = ds.findExam(at.examId).orElseThrow(() -> new RuntimeException("Exam not found"));
                        List<StudentAnswer> answers = new ArrayList<>();
                        System.out.println("Answer the following questions:");
                        for (Question q : ex.questions) {
                            System.out.println(q);
                            if ("MCQ".equalsIgnoreCase(q.qtype) || "TF".equalsIgnoreCase(q.qtype)) {
                                for (Choice ch : q.choices) System.out.println("   " + ch);
                                System.out.print("Enter selected choice id (or empty): ");
                                String ssel = sc.nextLine().trim();
                                StudentAnswer sa = new StudentAnswer();
                                sa.questionId = q.id;
                                if (!ssel.isEmpty()) sa.selectedChoiceId = Long.parseLong(ssel);
                                answers.add(sa);
                            } else {
                                System.out.print("Enter answer text: ");
                                String atxt = sc.nextLine();
                                StudentAnswer sa = new StudentAnswer();
                                sa.questionId = q.id;
                                sa.answerText = atxt;
                                answers.add(sa);
                            }
                        }
                        lm.submitAttempt(aid, answers);
                        System.out.println("Submitted and auto-graded (MCQ/TF/SHORT).");
                        break;
                    }
                    case "9": {
                        System.out.print("Attempt id: "); long aid = Long.parseLong(sc.nextLine().trim());
                        ExamAttempt at = ds.findAttempt(aid).orElseThrow(() -> new RuntimeException("Attempt not found"));
                        System.out.println("Answers:");
                        for (StudentAnswer sa : at.answers) System.out.println(sa);
                        System.out.print("Answer id to grade: "); long ansid = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Score to assign: "); double score = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Grader id (your id): "); long gid = Long.parseLong(sc.nextLine().trim());
                        lm.manualGradeAnswer(aid, ansid, score, gid);
                        System.out.println("Manual graded and grade record updated.");
                        break;
                    }
                    case "10": {
                        System.out.print("Exam id: "); long eid = Long.parseLong(sc.nextLine().trim());
                        Map<String,Object> sum = lm.examSummary(eid);
                        System.out.println("Exam Summary: " + sum);
                        List<Map<String,Object>> qstats = lm.questionStats(eid);
                        System.out.println("Question Stats:");
                        for (Map<String,Object> s : qstats) System.out.println(s);
                        break;
                    }
                    case "11": {
                        System.out.print("Exam id: "); long eid = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Output CSV filename: "); String fn = sc.nextLine().trim();
                        String out = lm.exportExamReportCsv(eid, fn);
                        System.out.println("Exported to " + out);
                        break;
                    }
                    case "12": {
                        System.out.println("Exams:");
                        for (Exam e : ds.exams) System.out.println(e);
                        break;
                    }
                    case "13": {
                        System.out.println("Attempts:");
                        for (ExamAttempt a : ds.attempts) System.out.println(a);
                        break;
                    }
                    case "0": {
                        ds.saveAll();
                        System.out.println("Data saved. Exiting.");
                        sc.close();
                        System.exit(0);
                    }
                    default:
                        System.out.println("Unknown option");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
