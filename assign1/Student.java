import java.util.Iterator;

class Student implements Student_ {
    private String name;
    private String entryNumber;
    private Entity hostel;
    private Entity department;
    private SortedLinkedList<CourseGrade_> courses = new SortedLinkedList<>((a, b) -> {
        CourseGrade a1 = (CourseGrade) a;
        CourseGrade b1 = (CourseGrade) b;
        return a1.course.courseNum.compareTo(b1.course.courseNum) < 0;
    });

    public static LinkedList<Student> studentList = new LinkedList<>();

    // Returns a Student object with given entryNumber, or null if it's not found
    public static Student getFromEntryNumber(String entryNumberOrName) {
        Iterator<Student> iter = studentList.elements();
        while (iter.hasNext()) {
            Student student = iter.next();
            if (student.entryNo().equals(entryNumberOrName)) {
                return student;
            }
        }
        return null;
    }

    // Returns a Student object with given entryNumber, or null if it's not found
    public static Student getFromName(String name) {
        Iterator<Student> iter = studentList.elements();
        while (iter.hasNext()) {
            Student student = iter.next();
            if (student.name().equals(name)) {
                return student;
            }
        }
        return null;
    }

    public Student(String name, String entryNumber, String hostelName, String department) {
        this.name = name;
        this.entryNumber = entryNumber;
        this.hostel = Entity.addHostel(hostelName, this);
        this.department = Entity.addDepartment(department, this);
        studentList.add(this);
    }

    public String name() {
        return name;
    }

    public String entryNo() {
        return entryNumber;
    }

    public String hostel() {
        return hostel.name();
    }

    public String department() {
        return department.name();
    }

    public String hostelShare() {
        return this.hostel.sharedWith(entryNumber);
    }

    public String departmentShare() {
        return this.department.sharedWith(entryNumber);
    }

    public String completedCredits() {
        Iterator<CourseGrade_> iter = courses.elements();
        int credits = 0;
        while (iter.hasNext()) {
            CourseGrade_ courseGrade = iter.next();
            String grade = courseGrade.grade().toString();
            if (grade.equals("E") || grade.equals("F") || grade.equals("I")) {
                continue;
            }

            credits += 3;
        }
        String creditsString = "" + credits;
        return creditsString;
    }

    public String cgpa() {
        int credits = 0;
        int gp = 0;
        Iterator<CourseGrade_> iter = courses.elements();
        while (iter.hasNext()) {
            CourseGrade_ courseGrade = iter.next();
            String grade = courseGrade.grade().toString();
            if (grade.equals("I")) {
                continue;
            }

            credits += 3;
            gp += 3 * GradeInfo.gradepoint(courseGrade.grade());
        }
        float cg;
        if (credits == 0) {
            cg = 0.0f;
        } else {
            cg = ((float) gp) / credits;
        }

        return String.format("%.2f", cg);
    }

    public void addCourse(String courseName, String courseNum, String grade) {
        Course course = Entity.addCourse(courseName, courseNum, this);
        CourseGrade courseGrade = new CourseGrade(
            course,
            new GradeInfo(grade)
        );
        courses.add(courseGrade);
    }

    public Iterator<CourseGrade_> courseList() {
        return courses.elements();
    }
}