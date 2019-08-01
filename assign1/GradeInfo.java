import interfaces.GradeInfo_;

public class GradeInfo implements GradeInfo_ {
    private GradeInfo_.LetterGrade grade;

    GradeInfo(String gradeStr) {
        try {
            grade = GradeInfo_.LetterGrade.valueOf(gradeStr);
        } catch(IllegalArgumentException ex) {
            System.out.println("Unsupported grade: " + gradeStr);
            System.exit(1);
        }
    }

    public static int gradepoint(GradeInfo_ grade){
        return GradeInfo_.gradepoint(grade.grade());
    }

    // TODO: Make sure this is right
    public boolean isCompleted() {
        switch (grade) {
            case E:
            case F:
            case I:
                return false;
        }
        return true;
    }

    public String toString() {
        return grade.name();
    }

    public LetterGrade grade() {
        return grade;
    }
}