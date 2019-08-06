import java.util.Iterator;

public class Entity implements Entity_ {
    private String name;
    private SortedLinkedList<Student_> studentList;

    public Entity(String name) {
        this.name = name;
        studentList = new SortedLinkedList<Student_>((a, b) -> {
            return a.entryNo().compareTo(b.entryNo()) <= 0;
        });
    }

    public String name() {
        return name;
    }

    public void addStudent(Student_ student) {
        studentList.add(student);
    }

    public Iterator<Student_> studentList() {
        return studentList.elements();
    }

    // Returns entryNumbers of students sharing this entity with the given
    // entry number students, arranged lexicographically, in a string separated by a space
    public String sharedWith(String entryNumber) {
        Iterator<Student_> iter = studentList.elements();
        String ret = "";
        while (iter.hasNext()) {
            String entryNum = iter.next().entryNo();
            if (!entryNum.equals(entryNumber)) {
                ret += entryNum + " ";
            }
        }
        return ret.trim();
    }

    public static LinkedList<Entity> hostelList = new LinkedList<Entity>(); // List of all hostels
    public static LinkedList<Course> coursesList = new LinkedList<Course>();
    public static LinkedList<Entity> departmentList = new LinkedList<Entity>();

    // Find in list an entity of name name, if it doesn't exist, create it
    // To that Entity add student and return the Entity.
    public static Entity addTo(LinkedList<Entity> list, String entityName, Student student) {
        Iterator<Entity> iter = list.elements();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            if (entity.name().equals(entityName)) {
                entity.addStudent(student);
                return entity;
            }
        }

        Entity newEntity = new Entity(entityName);
        list.add(newEntity);
        newEntity.addStudent(student);
        return newEntity;
    }

    public static Course addTo(LinkedList<Course> list, String courseName, String courseNum, Student student) {
        Iterator<Course> iter = list.elements();
        while (iter.hasNext()) {
            Course course = iter.next();
            if (course.name().equals(courseName)) {
                course.addStudent(student);
                return course;
            }
        }

        Course newCourse = new Course(courseNum, courseName);
        list.add(newCourse);
        newCourse.addStudent(student);
        return newCourse;
    }

    public static Entity addHostel(String hostelName, Student student) {
        return addTo(hostelList, hostelName, student);
    }

    public static Course addCourse(String courseName, String courseNum, Student student) {
        return addTo(coursesList, courseName, courseNum, student);
    }

    public static Entity addDepartment(String departmentName, Student student) {
        return addTo(departmentList, departmentName, student);
    }
}

class Course extends Entity {
    String courseNum;

    public Course(String courseNum, String courseName) {
        super(courseName);
        this.courseNum = courseNum;
    }
}