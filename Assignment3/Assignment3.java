import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class Assignment3 {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Format: java assignment3 hashTableSize hashingApproach inputFileName");
            System.exit(-1);
        }

        int hashTableSize = Integer.parseInt(args[0]);
        String hashingApproach = args[1];
        String inputFileName = args[2];

        MyHashTable_<ComparablePair<String, String>, Student> hashTable = null;
        if (hashingApproach.equals("DH")) {
            hashTable = new HashTableDH<>(hashTableSize);
        } else if (hashingApproach.equals("SCBST")) {
            hashTable = new HashTableSCBST<>(hashTableSize);
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFileName));
        } catch (Exception e) {
            System.err.print("File not found: " + inputFileName);
            System.exit(1);
        }

        String line = bufferedReader.readLine();
        while (line != null) {
            String[] params = line.split(" ");
            String queryType = params[0];
            String firstName = params[1];
            String lastName = params[2];
            ComparablePair<String, String> key = new ComparablePair<>(firstName, lastName);
            switch (queryType) {
                case "insert": {
                    String hostel = params[3];
                    String department = params[4];
                    String cgpa = params[5];
                    Student student = new Student(
                        firstName,
                        lastName,
                        hostel,
                        department,
                        cgpa
                    );

                    int res = hashTable.insert(key, student);
                    if (res == -1) {
                        System.out.println("E");
                    } else {
                        System.out.println(res);
                    }
                    break;
                }
                case "update": {
                    String hostel = params[3];
                    String department = params[4];
                    String cgpa = params[5];
                    Student student = new Student(
                        firstName,
                        lastName,
                        hostel,
                        department,
                        cgpa
                    );
                    int res = hashTable.update(key, student);
                    if (res == -1) {
                        System.out.println("E");
                    } else {
                        System.out.println(res);
                    }
                    break;
                }
                case "delete": {
                    int res = hashTable.delete(key);
                    if (res == -1) {
                        System.out.println("E");
                    } else {
                        System.out.println(res);
                    }
                    break;
                }
                case "contains": {
                    boolean res = hashTable.contains(key);
                    String ans = res ? "T" : "F";
                    System.out.println(ans);
                    break;
                }
                case "get": {
                    try {
                        Student queryStudent = hashTable.get(key);
                        System.out.println(
                            queryStudent.fname() + " " +
                            queryStudent.lname() + " " +
                            queryStudent.hostel() + " " +
                            queryStudent.department() + " " +
                            queryStudent.cgpa()
                        );
                    } catch (Exception e) {
                        System.out.println("E");
                    }
                    break;
                }
                case "address": {
                    try {
                        String address = hashTable.address(key);
                        System.out.println(address);
                    } catch (Exception e) {
                        System.out.println("E");
                    }
                }
            }
            
            line = bufferedReader.readLine();
        }
    }
}