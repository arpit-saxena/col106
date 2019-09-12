import java.io.BufferedReader;
import java.io.FileReader;

public class Tester {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("first-last-name.txt"));
        } catch (Exception e) {
            System.err.print("File not found: first-last-name.txt");
            System.exit(1);
        }

        String line = bufferedReader.readLine();
        int a = 1, b = 1;
        int size = 53;
        String[] arr = new String[1653];
        while (line != null) {
            String[] name = line.split(" ");
            String nameConcat = name[0] + name[1];
            int h1 = (int) HashFunctions.djb2(nameConcat, size);
            int h2 = (int) HashFunctions.sdbm(nameConcat, size);
            /* System.out.println(h1 + " " + h2); */
            if (h1 == a) {
                System.out.println(name[0] + " " + name[1]);
                a++;
            }
            line = bufferedReader.readLine();
        }
    }
}