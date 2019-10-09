package Trie;

public class Person {
    String name;
    String phoneNumber;

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[Name: " + name + ", Phone=" + phoneNumber + "]";
    }
}
