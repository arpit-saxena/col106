package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    /**
     * returns < 0 if this student has lesser marks
     *         = 0 if both students have same marks
     *         > 0 if this student has more marks
     */
    @Override
    public int compareTo(Student student) {
        return this.marks - student.marks;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{name=\'" + name + "\', marks=" + marks + "}";
    }
}
