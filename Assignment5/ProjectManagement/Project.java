package ProjectManagement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import PriorityQueue.MaxHeap;

public class Project{
    String name;
    int priority;
    int budget;

    // Jobs of this project that weren't able to be executed due to budget limits
    public List<MaxHeap<Job>.Node> notReadyJobs = new LinkedList<>();

    // All jobs of the project
    public ArrayList<JobReport_> allJobs = new ArrayList<>();

    public Project(String name, int priority, int budget) {
        this.name = name;
        this.priority = priority;
        this.budget = budget;
    }

    public int compareTo(Project project) {
        return name.compareTo(project.name);
    }
}
