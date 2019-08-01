import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInput {
    FileReader fileReader;

    public FileInput(String fileName) {
        try {
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException fe) {
            System.out.println("File " + fileName + " not found.");
            System.exit(1);
        }
    }

    public char getChar() {
        try {
            return (char) fileReader.read();
        } catch (IOException ex) {
            System.out.println("Encountered exception: " + ex.getMessage());
            System.exit(1);
        }
        return '0';
    }

    // Gets a string from fileReader till a whitespace is encountered
    public String getString() {
        String ret = "";
        char ch = getChar();

        // Discard whitespace in beginning;
        while (Character.isWhitespace(ch)) {
            ch = getChar();
        }

        while (!Character.isWhitespace(ch)) {
            ret += ch;
            ch = getChar();
        }
        
        return ret;
    }

    public String getLine() {
        String ret = "";
        char ch = getChar();

        while (ch != '\n') {
            ret += ch;
            ch = getChar();
        }
        
        return ret;
    }

    public boolean moreAvailable() {
        try {
            return fileReader.ready();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.exit(1);
        }
        return false;
    }

    public void close() {
        try {
            fileReader.close();
        } catch (IOException ex) {
            System.err.println("Exception: " + ex.getMessage());
            System.exit(1);
        }
    }
}