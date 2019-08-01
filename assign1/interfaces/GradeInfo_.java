package interfaces;

//TODO: WTF?! Had to change interface definition...

public interface GradeInfo_ {
   public enum LetterGrade {
      A, Aminus, B, Bminus, C, Cminus, D, E, F, I;
   } // I is the place-holder grade for the current semester, where grade has not been earned yet
   public static int gradepoint(LetterGrade grade) {
      switch(grade) {
         case A:
             return 10;
         case Aminus:
             return 9;
         case B:
             return 8;
         case Bminus:
             return 7;
         case C:
             return 6;
         case Cminus:
             return 5;
         case D:
             return 4;
         case E:
             return 2;
         case F:
             return 0;
         case I:
             System.out.println("I grade has no grade point associated.");
             System.exit(1);
     }
     System.out.println("Invalid grade");
     System.exit(1);
     return 0;
   }  // Returns the points earned for each letter grade
   public LetterGrade grade(); // TODO: Added on my own
}
