import java.util.Iterator;

public class Assignment1 {
    private static void getData(String studentRecordFileName, String courseFileName) {
        FileInput fileInput = new FileInput(studentRecordFileName);
        while (fileInput.moreAvailable()) {
            String entryNumber = fileInput.getString();
            String name = fileInput.getString();
            String department = fileInput.getString();
            String hostelName = fileInput.getString();

            new Student(name, entryNumber, hostelName, department);
        }
    
        fileInput.close();
        fileInput = new FileInput(courseFileName);
        while (fileInput.moreAvailable()) {
            String entryNumber = fileInput.getString();
            String courseCode = fileInput.getString();
            String grade = fileInput.getString();
            String courseName = fileInput.getLine();

            Student student = Student.get(entryNumber);
            if (student == null) {
                System.err.println("Student with entry number " + entryNumber + " not found");
                System.exit(1);
            }

            student.addCourse(courseName, courseCode, grade);
        }
    }

    private static void printReverse(Iterator<String> iter) {
        String next;
        if (iter.hasNext()) {
            next = iter.next();
            printReverse(iter);
            System.out.println(next);
        }
    }

    private static String answerShareQuery(String entryNumber, String entityName) {
        Student student = Student.get(entryNumber);
        if (student == null) {
            System.err.println("Student with entry number " + entryNumber + " not found");
            System.exit(1);
        }
        if (student.hostel().equals(entityName)) {
            return student.hostelShare();
        }
        
        if (student.department().equals(entityName)) {
            return student.departmentShare();
        }

        Iterator<CourseGrade_> iter = student.courseList();
        while (iter.hasNext()) {
            CourseGrade courseGrade = (CourseGrade) iter.next();
            if (courseGrade.coursenum().equals(entityName)) {
                return courseGrade.courseShare(entryNumber);
            }
        }

        System.err.println("Entity " + entityName + " not found");
        System.exit(1);
        return "";
    }

    public static String answerCourseTitleQuery(String courseNum) {
        Iterator<Course> iter = Entity.coursesList.elements();
        while(iter.hasNext()) {
            Course course = iter.next();
            if (course.courseNum.equals(courseNum)) {
                return String.format("%s", course.name());
            }
        }
        System.err.println("Course " + courseNum + " not found");
        System.exit(1);
        return "";
    }

    public static String answerInfoQuery(String entryNumber) {
        Student student = Student.get(entryNumber);
        if (student == null) {
            System.err.println("Student with entry number " + entryNumber + " not found");
            System.exit(1);
        }
        String ret = String.format("%s %s %s %s %s ", 
            student.entryNo(),
            student.name(),
            student.department(),
            student.hostel(),
            student.cgpa()
        );

        Iterator<CourseGrade_> iter = student.courseList();
        while(iter.hasNext()) {
            CourseGrade courseGrade = (CourseGrade) iter.next();
            ret += String.format("%s %s ", 
                courseGrade.coursenum(),
                courseGrade.grade().toString()
            );
        }

        return ret.trim();
    }

    private static void answerQueries(String studentQueryFileName) {
        FileInput fileInput = new FileInput(studentQueryFileName);
        LinkedList<String> answers = new LinkedList<>();
        while (fileInput.moreAvailable()) {
            String queryType = fileInput.getString();
            switch (queryType) {
                case "SHARE": {
                    String entryNumber = fileInput.getString();
                    String entityName = fileInput.getString();
                    String answer = answerShareQuery(entryNumber, entityName);
                    answers.add(answer);
                    break;
                }
                case "COURSETITLE": {
                    String courseNum = fileInput.getString();
                    String answer = answerCourseTitleQuery(courseNum);
                    answers.add(answer);
                    break;
                }
                case "INFO": {
                    String entryNumber = fileInput.getString();
                    String answer = answerInfoQuery(entryNumber);
                    answers.add(answer);
                    break;
                }
            }
        }

        printReverse(answers.elements());
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Format: java Assignment1 StudentRecordFile CourseFile StudentQueryFile");
            System.exit(1);
        }

        getData(args[0], args[1]);
        answerQueries(args[2]);
    }
}