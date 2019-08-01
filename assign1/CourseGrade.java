import interfaces.CourseGrade_;

public class CourseGrade implements CourseGrade_ {
    Course course;
    GradeInfo grade;

    public CourseGrade(Course course, GradeInfo grade) {
        this.course = course;
        this.grade = grade;
    }

    public String coursetitle() {
        return course.name();
    }

    public String coursenum() {
        return course.courseNum;
    }

    public GradeInfo grade() {
        return grade;
    }

    public String courseShare(String entryNumber) {
        return course.sharedWith(entryNumber);
    }
}
