public class Student {
    private String studentId;
    private String password;
    private String email;

    public Student(String studentId, String password, String email){
        this.studentId = studentId;
        this.password = password;
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
