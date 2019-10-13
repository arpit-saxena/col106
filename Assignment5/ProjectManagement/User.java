package ProjectManagement;

import java.util.ArrayList;
import java.util.List;

public class User implements Comparable<User>, UserReport_ {
    String name;
    int budgetConsumed = 0;
    ArrayList<JobReport_> allJobs = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(User user) {
        return name.compareTo(user.name);
    }

    @Override
    public String user() {
        return name;
    }

    @Override
    public int consumed() {
        return budgetConsumed;
    }
}
