import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInput {
    BufferedReader bufferedReader;

    public FileInput(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException fe) {
            System.out.println("File " + fileName + " not found.");
            System.exit(1);
        }
    }

    public char getChar() {
        try {
            return (char) bufferedReader.read();
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

        while (ch != '\n' && moreAvailable()) {
            ret += ch;
            ch = getChar();
        }
        
        return ret;
    }

    public boolean moreAvailable() {
        try {
            return bufferedReader.ready();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.exit(1);
        }
        return false;
    }

    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            System.err.println("Exception: " + ex.getMessage());
            System.exit(1);
        }
    }
}