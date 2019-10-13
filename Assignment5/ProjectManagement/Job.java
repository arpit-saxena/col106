package ProjectManagement;

public class Job implements Comparable<Job>, JobReport_ {
    private String name;
    private Project project;
    private User creator;
    boolean isCompleted = false;
    private int executionTime;
    private int endTime = -1;
    private int arrivalTime;

    public Job(String name, Project project, User creator, 
                    int executionTime, int arrivalTime) {
        if (project == null) {
            throw new IllegalArgumentException("Project of a job cannot be null");
        }

        if (creator == null) {
            throw new IllegalArgumentException("Creator of a job cannot be null");
        }

        this.name = name;
        this.project = project;
        this.creator = creator;
        this.executionTime = executionTime;
        this.arrivalTime = arrivalTime;
    }

    public String name() {
        return name;
    }

    public Project project() {
        return project;
    }

    public User creator() {
        return creator;
    }

    public void setCompleted(int endTime) {
        isCompleted = true;
        this.endTime = endTime;
    }

    public int executionTime() {
        return executionTime;
    }

    /**
     * Compares the priority of this job and the job in the argument
     * Returns < 0 if the priority of this job is lesser than the job in argument
     *         = 0 if both have equal priority
     *         > 0 if the priority of this job is greater than the job in argument
     */
    @Override
    public int compareTo(Job job) {
        return project.priority - job.project.priority;
    }

    // TODO: Remove ArrivalTime from here; not required
    @Override
    public String toString() {
        return 
            "Job{user='"
            + creator.name
            + "', project='"
            + project.name
            + "', jobstatus="
            + (isCompleted ? "COMPLETED" : "REQUESTED")
            + ", execution_time="
            + executionTime
            + ", arrival_time="
            + arrivalTime
            + ", end_time="
            + (endTime == -1 ? "null" : endTime)
            + ", name='"
            + name
            + "'}";
    }

    @Override
    public String user() {
        return creator.name;
    }

    @Override
    public String project_name() {
        return project.name;
    }

    // TODO: Confirm if budget() is supposed to return executionTime
    @Override
    public int budget() {
        return executionTime;
    }

    @Override
    public int arrival_time() {
        return arrivalTime;
    }

    @Override
    public int completion_time() {
        return endTime;
    }
}