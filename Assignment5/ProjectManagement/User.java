package ProjectManagement;

import java.util.ArrayList;
import java.util.List;

import PriorityQueue.MaxHeap;

public class User implements Comparable<User>, UserReport_ {
    String name;
    int budgetConsumed = 0;
    ArrayList<JobReport_> allJobs = new ArrayList<>();
    int latestCompletionTime = -1;

    // node of this user in the max heap of all users
    MaxHeap<User>.Node nodeHeap;

    public User(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(User user) {
        if (this.budgetConsumed != user.budgetConsumed) {
            return this.budgetConsumed - user.budgetConsumed;
        }
        // Completed later => Earlier
        return -(this.latestCompletionTime - user.latestCompletionTime);
    }

    @Override
    public String user() {
        return name;
    }

    @Override
    public int consumed() {
        return budgetConsumed;
    }

    @Override
    public String toString() {
        return "name: " + name + ", "
        + "consumption: " + budgetConsumed + ", "
        + "latestCompletion: " + latestCompletionTime;
    }
}