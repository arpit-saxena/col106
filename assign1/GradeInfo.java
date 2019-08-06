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
        GradeInfo grade2 = (GradeInfo) grade;
        return GradeInfo_.gradepoint(grade2.grade());
    }

    public String toString() {
        return grade.name();
    }

    public LetterGrade grade() {
        return grade;
    }
}