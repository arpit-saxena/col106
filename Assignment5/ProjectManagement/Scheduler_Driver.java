package ProjectManagement;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.MaxHeapRB;
import Trie.Trie;
import Trie.TrieNode;

public class Scheduler_Driver extends Thread implements SchedulerInterface {


    public static void main(String[] args) throws IOException {
//

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;
                ArrayList<UserReport_> userReport = null;
                ArrayList<JobReport_> report = null;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        qstart_time = System.nanoTime();
                        report = timed_report(cmd);
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        userReport = timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

                if (report != null)
                for (JobReport_ rep : report) {
                    System.out.println(rep);
                }
            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                res = handle_new_project(cmd);
                break;
            case "NEW_USER":
                res = handle_new_user(cmd);
                break;
            case "NEW_PROJECTUSER":
                res = handle_new_projectuser(cmd);
                break;
            case "NEW_PRIORITY":
                res = handle_new_priority(cmd[1]);
                break;
        }

        return res;
    }

    Trie<User> usersTrie = new Trie<>();
    Trie<Project> projectsTrie = new Trie<>();
    Trie<Job> jobsTrie = new Trie<>();
    MaxHeap<Job> jobQueue = new MaxHeap<>();
    int globalTime = 0;
    List<Job> jobsDone = new ArrayList<Job>();
    MaxHeap<User> allUsers = new MaxHeap<>();
    MaxHeapRB<Integer, Job> allNotReadyJobs = new MaxHeapRB<>();

    // For jobs that weren't able to be executed due to budget constraints
    // Project name is key along with a list of the jobs
    Trie<Project>  projectsWithNotReadyJobs = new Trie<>();

    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
        ArrayList<MaxHeap<User>.Node> nodeList = new ArrayList<>(top);
        ArrayList<UserReport_> list = new ArrayList<>(top);
        for (int i = 0; i < top && allUsers.size() > 0; i++) {
            MaxHeap<User>.Node node = allUsers.extractMaxNode();
            nodeList.add(node);
            list.add(node.key);
        }

        allUsers.insert(nodeList);
        return list;
    }


    @Override
    public void timed_flush(int waitTime) {
        // Execute all jobs with arrivalTime <= beginTime
        int beginTime = globalTime - waitTime;

        // Jobs that have to be tried to be executed
        ArrayList<MaxHeap<Job>.Node> potJobs = new ArrayList<>();

        // Jobs that will form the new job queue
        ArrayList<MaxHeap<Job>.Node> newQueue = new ArrayList<>();
        jobQueue.forEachNode(jobNode -> {
            Job job = jobNode.key;
            if (job.arrivalTime() <= beginTime && job.canExecute()) {
                potJobs.add(jobNode);
            } else {
                newQueue.add(jobNode);
            }
        });

        // Don't have to do anything if no potential job to flush
        if (potJobs.size() > 0) {
            // jobs to flush
            MaxHeap<Job> flushJobs = new MaxHeap<>();
            flushJobs.insert(potJobs);
            MaxHeap<Job>.Node node;
            while (flushJobs.size() > 0) {
                node = flushJobs.extractMaxNode();
                if (!executeIfPossible(node.key)) {
                    newQueue.add(node);
                }
            }

            jobQueue.clear();
            jobQueue.insert(newQueue);
        }
    }
    

    private ArrayList<JobReport_> handle_new_priority(String s) {
        int priority = Integer.parseInt(s);
        ArrayList<MaxHeap<Job>.Node> nodeList = new ArrayList<>();
        ArrayList<JobReport_> list = new ArrayList<>( 
            allNotReadyJobs.elementsGreaterThanEqualTo(currPriority -> {
                return currPriority - priority;
            }));

        list.addAll(jobQueue.elementsGreaterThanEqualTo(job -> {
            return job.project().priority - priority;
        }));
        return list;
    }

    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
        String projectName = cmd[1];
        TrieNode node = projectsTrie.search(projectName);
        if (node == null) return null;
        Project project = (Project) node.getValue();

        String userName = cmd[2];
        node = usersTrie.search(userName);
        if (node == null) return null;
        User user = (User) node.getValue();

        int t1 = Integer.parseInt(cmd[3]);
        int t2 = Integer.parseInt(cmd[4]);

        ArrayList<JobReport_> jobs = new ArrayList<>();
        ArrayList<JobReport_> projectWiseJobs, userWiseJobs;

        projectWiseJobs = findJobsByArrivalTime(project.allJobs, t1, t2);
        userWiseJobs = findJobsByArrivalTime(user.allJobs, t1, t2);
        
        if (projectWiseJobs.size() < userWiseJobs.size()) {
            for (JobReport_ job: projectWiseJobs) {
                if (job.user().equals(userName)) {
                    jobs.add(job);
                }
            }
        } else {
            for (JobReport_ job : userWiseJobs) {
                if (job.project_name().equals(projectName)) {
                    jobs.add(job);
                }
            }
        }
        
        jobs.sort((job1, job2) -> {
            if (job1.completion_time() != -1 && job2.completion_time() != -1) {
                return job1.completion_time() - job2.completion_time();
            }
            return (job1.arrival_time() - job2.arrival_time());
        });

        return jobs;
    }

    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
        String userName = cmd[1];
        TrieNode node = usersTrie.search(userName);
        if (node == null) {
            return null;
        }
        User user = (User) node.getValue();
        int t1 = Integer.parseInt(cmd[2]);
        int t2 = Integer.parseInt(cmd[3]);
        return findJobsByArrivalTime(user.allJobs, t1, t2);
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
        String projectName = cmd[1];
        TrieNode node = projectsTrie.search(projectName);
        if (node == null) {
            return null;
        }
        Project project = (Project) node.getValue();
        int t1 = Integer.parseInt(cmd[2]);
        int t2 = Integer.parseInt(cmd[3]);
        return findJobsByArrivalTime(project.allJobs, t1, t2);
    }

    /**
     * Returns all jobs with arrival time >= t1 and <= t2 from an array of jobs
     */
    private ArrayList<JobReport_> findJobsByArrivalTime(
            ArrayList<JobReport_> jobs, int t1, int t2) {
        int begin = 0;
        int end = jobs.size() - 1;

        int middle = (begin + end) / 2;
        while(begin <= end) {
            if (jobs.get(middle).arrival_time() < t1) {
                begin = middle + 1;
            } else if (jobs.get(middle).arrival_time() > t2) {
                end = middle - 1;
            } else {
                begin = findJobsArrivedLaterThanOrAt(
                    jobs, begin, middle, t1);
                end = findJobsArrivedEarlierThanOrAt(
                    jobs, middle, end, t2);
                break;
            }
            middle = (begin + end) / 2;
        }

        return new ArrayList<>(jobs.subList(begin, end + 1));
    }

    /**
     * Returns index i such that jobs[i..end] is the list of jobs which have
     * arrived later than or at time t. Assumes jobs[end] has arrived later
     * than or equal to t
     */
    private int findJobsArrivedLaterThanOrAt(List<JobReport_> jobs, int begin,
                                             int end, int t) {
        int middle = (begin + end) / 2;

        // INV: jobs[end+1..end0] arrived at time >= t
        //      jobs[begin0..begin-1] arrived at time < t
        //      begin0 <= begin <= end <= end0
        while (begin <= end) {
            if (jobs.get(middle).arrival_time() < t) {
                begin = middle + 1;
            } else {
                end = middle - 1;
            }
            middle = (begin + end) / 2;
        }

        // assert: jobs[begin..end0] arrived at time >= t.
        // jobs[begin - 1] arrived at time < t
        return begin;
    }

    /**
     * Returns index i such that jobs[begin..i] is the list of jobs which have
     * arrived earlier than or at time t. Assumes jobs[begin] has arrived earlier
     * than or equal to t
     */
    private int findJobsArrivedEarlierThanOrAt(List<JobReport_> jobs, int begin,
                                             int end, int t) {
        int middle = (begin + end) / 2;

        //INV: jobs[begin0..begin-1] arrived at time <= t
        //     jobs[end+1..end0] arrived at time > t
        //     begin0 <= begin <= end <= end0
        while (begin <= end) {
            if (jobs.get(middle).arrival_time() > t) {
                end = middle - 1;
            } else {
                begin = middle + 1;
            }
            middle = (begin + end) / 2;
        }

        // assert: jobs[begin0..end] arrived at time <= t.
        // jobs[end+1..end0] arrived at time > t.
        return end;
    }


    @Override
    public void schedule() {
        System.out.println("Remaining jobs: " + jobQueue.size());
        MaxHeap<Job>.Node node;
        while((node = jobQueue.extractMaxNode()) != null){
            Job job = node.key;
            System.out.println(
                "Executing: "
                + job.name()
                + " from: "
                + job.project().name
            );
            if (job.project().budget >= job.executionTime()) {
                globalTime += job.executionTime();
                job.setCompleted(globalTime);
                job.project().budget -= job.executionTime();
                jobsDone.add(job);
                System.out.println(
                    "Project: "
                    + job.project().name
                    + " budget remaining: "
                    + job.project().budget
                );
                job.creator().latestCompletionTime = globalTime;
                job.creator().budgetConsumed += job.executionTime();
                allUsers.bubbleUp(job.creator().nodeHeap.index);
                return;
            }
            job.project().notReadyJobs.insert(job);
            allNotReadyJobs.insert(job.project().priority, job);
            System.out.println("Un-sufficient budget.");
        }
    }

    private void timed_schedule() {
        MaxHeap<Job>.Node node;
        while((node = jobQueue.extractMaxNode()) != null){
            Job job = node.key;
            if (executeIfPossible(job)) {
                return;
            }
            job.project().notReadyJobs.insert(job);
            allNotReadyJobs.insert(job.project().priority, job);
        }
    }

    /**
     * Execute the given job if possible
     * Return true if job was executed, false otherwise
     */
    private boolean executeIfPossible(Job job) {
        if (!job.canExecute()) return false;

        globalTime += job.executionTime();
        job.setCompleted(globalTime);
        job.project().budget -= job.executionTime();
        jobsDone.add(job);

        job.creator().latestCompletionTime = globalTime;
        job.creator().budgetConsumed += job.executionTime();
        allUsers.bubbleUp(job.creator().nodeHeap.index);

        return true;
    }

    @Override
    public void run_to_completion() {
        while (jobQueue.size() != 0) {
            System.out.println("Running code");
            schedule();
            System.out.println("System execution completed");
        }
    }

    @Override
    public void timed_run_to_completion() {
        while(jobQueue.size() != 0) {
            timed_schedule();
        }
    }

    @Override
    public void print_stats() {
        System.out.println("--------------STATS---------------");
        System.out.println("Total jobs done: " + jobsDone.size());
        for (Job job : jobsDone) {
            System.out.println(job);
        }
        System.out.println("------------------------");
        
        MaxHeapRB<Project, Job> jobsNotDone = new MaxHeapRB<>();
        projectsTrie.forEach(project -> {
            MaxHeap<Job>.Node node;
            while ((node = project.notReadyJobs.extractMaxNode()) != null) {
                jobsNotDone.insert(project, node.key);
            }
        });
        
        System.out.println("Unfinished jobs: ");
        ArrayList<Job> jobList = jobsNotDone.topNumElements(-1);
        for (Job job : jobList) {
            System.out.println(job);
        }
        System.out.println("Total unfinished jobs: " + jobList.size());
        System.out.println("--------------STATS DONE---------------");
    }

    public void handle_add(String[] cmd) {
        System.out.println("ADDING Budget");

        String projectName = cmd[1];
        int budgetAddition = Integer.parseInt(cmd[2]);

        TrieNode node = projectsTrie.search(projectName);
        if (node == null) {
            System.out.println("No such project exists. " + projectName);
            return;
        }

        Project project = (Project) node.getValue();
        project.budget += budgetAddition;

        project.notReadyJobs.forEach(jobNode -> {
            jobQueue.insert(jobNode);
        });
        project.notReadyJobs = new MaxHeap<>();

        MaxHeapRB<Integer, Job> newAllNotReadyJobs = new MaxHeapRB<>();
        allNotReadyJobs.forEach(job -> {
            if (!job.project().name.equals(projectName)) {
                newAllNotReadyJobs.insert(job.project().priority, job);
            }
        });
        allNotReadyJobs = newAllNotReadyJobs;
    }

    @Override
    public void handle_empty_line() {
        System.out.println("Running code");
        schedule();
        System.out.println("Execution cycle completed");
    }

    public void timed_handle_empty_line() {
        timed_schedule();
    }


    public void handle_query(String jobName) {
        System.out.println("Querying");
        TrieNode node = jobsTrie.search(jobName);
        if (node == null) {
            System.out.println(jobName + ": NO SUCH JOB");
            return;
        }

        Job job = (Job) node.getValue();
        if (job.isCompleted) {
            System.out.println(jobName + ": COMPLETED");
        } else {
            System.out.println(jobName + ": NOT FINISHED");
        }
    }

    @Override
    public void handle_user(String name) {
        System.out.println("Creating user");
        timed_handle_user(name);
    }

    @Override
    public void timed_handle_user(String name) {
        User user = new User(name);
        usersTrie.insert(name, user);
        user.nodeHeap = allUsers.insertAndGetNode(user);
    }

    @Override
    public void handle_job(String[] cmd) {
        System.out.println("Creating job");

        switch(internal_handle_job(cmd)) {
            case 1:
                System.out.println("No such project exists. " + cmd[2]);
                break;
            case 2:
                System.out.println("No such user exists: " + cmd[3]);
                break;
        }
    }

    @Override
    public void timed_handle_job(String[] cmd) {
        internal_handle_job(cmd);
    }

    /**
     * Returns 1 if No such project exists
     *         2 if No such user exists
     *         0 otherwise
     */
    private int internal_handle_job(String[] cmd) {
        String jobName = cmd[1];
        
        String projectName = cmd[2];
        TrieNode node = projectsTrie.search(projectName);
        if (node == null) {
            return 1;
        }
        Project project = (Project) node.getValue();

        String userName = cmd[3];
        node = usersTrie.search(userName);
        if (node == null) {
            return 2;
        }
        User user = (User) node.getValue();

        int jobExecutionTime = Integer.parseInt(cmd[4]);

        Job job = new Job(jobName, project, user, jobExecutionTime, globalTime);
        jobsTrie.insert(jobName, job);
        jobQueue.insert(job);

        project.allJobs.add(job);
        user.allJobs.add(job);

        return 0;
    }

    @Override
    public void handle_project(String[] cmd) {
        System.out.println("Creating project");
        timed_handle_project(cmd);
    }

    @Override
    public void timed_handle_project(String[] cmd) {
        String projectName = cmd[1];
        int projectPriority = Integer.parseInt(cmd[2]);
        int projectBudget = Integer.parseInt(cmd[3]);

        Project project = new Project(projectName, projectPriority, projectBudget);
        projectsTrie.insert(projectName, project);
    }
}
