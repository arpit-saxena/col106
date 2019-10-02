package ProjectManagement;

import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Scheduler_Driver extends Thread implements SchedulerInterface {

    public static void main(String[] args) throws IOException {
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

    public void execute(File file) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

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
                case "":
                    handle_empty_line();
                    break;
                case "ADD":
                    handle_add(cmd);
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }


        run_to_completion();

        print_stats();

    }

    Trie<User> usersTrie = new Trie<>();
    Trie<Project> projectsTrie = new Trie<>();
    Trie<Job> jobsTrie = new Trie<>();
    MaxHeap<Job> jobQueue = new MaxHeap<>();
    int globalTime = 0;
    List<Job> jobsDone = new LinkedList<Job>();

    // For jobs that weren't able to be executed due to budget constraints
    // Project name is key along with a list of the jobs
    HashMap<String, List<MaxHeap<Job>.Node>>  projectsWithNotReadyJobs = new HashMap<>();
    
    @Override
    public void run() {
        // till there are JOBS
        
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
    public void handle_project(String[] cmd) {
        String projectName = cmd[1];
        int projectPriority = Integer.parseInt(cmd[2]);
        int projectBudget = Integer.parseInt(cmd[3]);

        System.out.println("Creating project");
        Project project = new Project(projectName, projectPriority, projectBudget);
        projectsTrie.insert(projectName, project);
    }

    @Override
    public void handle_job(String[] cmd) {
        System.out.println("Creating job");

        String jobName = cmd[1];
        
        String projectName = cmd[2];
        TrieNode node = projectsTrie.search(projectName);
        if (node == null) {
            System.out.println("No such project exists. " + projectName);
            return;
        }
        Project project = (Project) node.getValue();

        String userName = cmd[3];
        node = usersTrie.search(userName);
        if (node == null) {
            System.out.println("No such user exists: " + userName);
            return;
        }
        User user = (User) node.getValue();

        int jobExecutionTime = Integer.parseInt(cmd[4]);

        Job job = new Job(jobName, project, user, jobExecutionTime);
        jobsTrie.insert(jobName, job);
        jobQueue.insert(job);
    }

    @Override
    public void handle_user(String name) {
        System.out.println("Creating user");
        User user = new User(name);
        usersTrie.insert(name, user);
    }

    @Override
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
    public void handle_empty_line() {
        System.out.println("Running code");
        schedule();
        System.out.println("Execution cycle completed");
    }

    @Override
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

        for (MaxHeap<Job>.Node jobNode : project.notReadyJobs) {
            jobQueue.insert(jobNode);
        }
        projectsWithNotReadyJobs.remove(project.name);
        project.notReadyJobs = new LinkedList<>();
    }

    @Override
    public void print_stats() {
        System.out.println("--------------STATS---------------");
        System.out.println("Total jobs done: " + jobsDone.size());
        for (Job job : jobsDone) {
            System.out.println(job);
        }
        System.out.println("------------------------");
        
        MaxHeap<Job> jobsNotDone = new MaxHeap<>();
        projectsWithNotReadyJobs.forEach((projectName, jobNodes) -> {
            jobNodes.forEach((jobNode) -> {
                jobsNotDone.insert(jobNode);
            });
        });
        
        System.out.println("Unfinished jobs: ");
        Job job;
        int numUnfinishedJobs = 0;
        while ((job = jobsNotDone.extractMax()) != null) {
            System.out.println(job);
            numUnfinishedJobs++;
        }
        System.out.println("Total unfinished jobs: " + numUnfinishedJobs);
        System.out.println("--------------STATS DONE---------------");
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
                return;
            }
            job.project().notReadyJobs.add(node);
            
            // If the project has only 1 non ready job now, it must have had no non ready
            // jobs before, so has to be added to the list of all projects with non ready
            // jobs
            if (job.project().notReadyJobs.size() == 1) {
                projectsWithNotReadyJobs.put(job.project().name, job.project().notReadyJobs);
            }
            System.out.println("Un-sufficient budget.");
        }
    }
}
