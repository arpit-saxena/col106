public class Student implements Student_ {
    String fname;
    String lname;
    String hostel;
    String department;
    String cgpa;

    public Student(
        String fname,
        String lname,
        String hostel,
        String department,
        String cgpa
    ) {
        this.fname = fname;
        this.lname = lname;
        this.hostel = hostel;
        this.department = department;
        this.cgpa = cgpa;
    }

    public String fname() {
        return this.fname;
    }

    public String lname() {
        return this.lname;
    }

    public String hostel() {
        return this.hostel;
    }

    public String department() {
        return this.department;
    }

    public String cgpa() {
        return this.cgpa;
    }
}