package Functions;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.*;
/* By Criss
    1 TimeUnit.DAYS.sleep(1);//day
    2 TimeUnit.HOURS.sleep(1);//hour
    3 TimeUnit.MINUTES.sleep(1);//minute
    4 TimeUnit.SECONDS.sleep(1);//second
    5 TimeUnit.MILLISECONDS.sleep(1000);//milli second
    6 TimeUnit.MICROSECONDS.sleep(1000);//micro second
    7 TimeUnit.NANOSECONDS.sleep(1000);//nano second
* */
import java.util.regex.Matcher;

//lock it for few minus
public class PostQueue {
    //thread for concurrent
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    //store posts to be added
    private static ConcurrentLinkedDeque<Post> queue = new ConcurrentLinkedDeque<>();
    private final double defaultPageSize = 10.0;

    public PostQueue(){}

    public void view(PostTree postTree){
        Scanner option_sc = new Scanner(System.in);
        LinkedList<Post> posts = this.getAllPosts();
        int postCount = posts.size();
        //current page number
        int pageNum = 0;
        int totalPages = (int) Math.ceil(postCount / defaultPageSize);
        boolean continueViewing = true;
        do{
            System.out.println("\n\n");
            System.out.println("===== Queueing posts =====");
            System.out.println();
            for(int i = postCount - 1; i < postCount - (pageNum * defaultPageSize) && i >= 0; i--){
                Post thispost = posts.get(i);
                System.out.printf("#UM%08d: \"%s\"\n", thispost.getPostID(), viewPost.cutoffComment(thispost.getContent()));
            }
            System.out.println("Page " + Integer.toString(pageNum + 1) + " of "+ Integer.toString(totalPages));
            System.out.println("===========================");

            //show options
            System.out.println();
            System.out.println(">>> Options:");
            if(pageNum > 0)
                System.out.println(">>> \"A\" - view previous page");
            if(pageNum < totalPages && totalPages > 1)
                System.out.println(">>> \"D\" - view next page");
            System.out.println(">>> <number> or \"#UM??????\" - view confession with selected ID");
            System.out.println(">>> \"Q\" - quit viewing posts");

            System.out.println(">>> ");
            System.out.print(">>> Option: ");
            //read input
            String userOption = option_sc.nextLine().trim();
            if(viewPost.getInteger(userOption) != null || viewPost.pattern.matcher(userOption).find()){ //if input a digit
                Integer nextviewID = viewPost.getInteger(userOption);
                if(nextviewID == null){
                    Matcher m = viewPost.pattern.matcher(userOption);
                    nextviewID = Integer.parseInt(m.group(1));
                }
                System.out.println("a");
                //check if postID is valid
                if(this.getIDs().indexOf(nextviewID) != -1){
                    this.viewSinglePost(findPost(nextviewID)); 
                    return;
                } else
                    System.out.println("Invalid ID given. Please try again.");
            }
            if(userOption.length() == 1){
                switch(userOption.toUpperCase()){
                    case "Q": continueViewing = false; return; //exit code
                    case "A": if(pageNum > 0) pageNum--; 
                              else System.out.println("Invalid option!"); 
                              break;
                    case "D": if(pageNum < totalPages && totalPages > 1) pageNum++;
                              else System.out.println("Invalid option!"); 
                              break;
                }
            }
        } while(continueViewing);
        return;
    }

    public void viewSinglePost(Post post){
        Scanner option_sc = new Scanner(System.in);

        //print post item
        System.out.println("\n\n");
        System.out.println("================================");
        System.out.printf("#UM%08d\n", post.getPostID());
        System.out.printf("[%s]\n", post.getDate().format(viewPost.formatter));
        System.out.println("");
        if(post.getParentID() != -1)
            System.out.printf("Reply to #UM%08d\n", post.getParentID());
        System.out.println(post.getContent());
        System.out.println("================================");

        //show options
        System.out.println();
        System.out.println(">>> Options:");
        System.out.println(">>> \"X\" - remove this post");
        System.out.println(">>> \"Q\" - quit viewing post");

        System.out.println(">>> ");
        //read input
        boolean askInputAgain = false;
        do{
            askInputAgain = false;
            System.out.print(">>> Option: ");
            String userOption = option_sc.nextLine().trim();
            if(userOption.length() == 1){
                switch(userOption.toUpperCase()){
                    case "Q": break; //exit code
                    case "X": boolean removed = queue.remove(post);
                              if(removed)
                                  System.out.printf("Post #UM%08d removed from queue.\n", post.getPostID());
                              else
                                  System.out.println("Post is added into tree.");
                              break;
                    default: askInputAgain = true; break;
                }
            }
        } while(askInputAgain);
        return;
    }


    public Post peekPost(){
        return queue.peek();
    }
    public boolean isEmpty(){
        return queue.isEmpty();
    }
    public int queueSize(){
        return queue.size();
    }
    public LinkedList<Post> getAllPosts(){
        LinkedList<Post> list = new LinkedList<Post>();
        for(Post p: queue){
            list.add(p);
        }
        return list;
    }
    public LinkedList<Integer> getIDs(){
        LinkedList<Integer> list = new LinkedList<Integer>();
        for(Post p: queue){
            list.add(p.getPostID());
        }
        return list;
    }
    public Post findPost(Integer postID){
        if(postID == null) return null;
        for(Post p: queue){
            if(p.getPostID() == postID){
                return p;
            }
        }
        return null;
    }

    public void addTask(PostTree tree, Post p){
        //add post into queue
        queue.offer(p);
        //submit a runnable task to executor
        executor.execute(new MyRunnable(tree, queue));
        return;
    }
    public void stopTasks(){ //stop executor
        try {
            System.out.println("Attempting to end pending tasks…");
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {}
        finally {
            /*if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }*/
            executor.shutdownNow();
        }
    }
    public boolean moveTask(PostTree tree){ //move all the remaining task into given tree
        boolean isDone = true;
        for(Post p: queue){
            boolean test = tree.addPost(p);
            isDone = isDone && test;
        }
        return isDone;
    }
}

//runnable class for thread
class MyRunnable implements Runnable {
    private PostTree postTree;
    private ConcurrentLinkedDeque<Post> queue;
    private boolean isDemo = true;

    public MyRunnable(PostTree postTree, ConcurrentLinkedDeque<Post> queue2) {
        // store parameter for later user
        this.postTree = postTree;
        this.queue = queue2;
    }
    
    /*
     * Waits according to the size of queue.
     * Then, try to pop a post from given queue and add it into the tree.
     * Post should be pushed into queue before adding this task.
     */
    public void run() {
        if(this.queue.isEmpty())
            return; //skip if queue empty
        try {
            int i = queue.size();
            if(i <= 5){ //less than 5, wait longest
                if(isDemo)
                    TimeUnit.SECONDS.sleep(10);
                else
                    TimeUnit.MINUTES.sleep(15);
            } else if(i <= 10){ // 5 <= x <= 10
                if(isDemo)
                    TimeUnit.SECONDS.sleep(8);
                else
                    TimeUnit.MINUTES.sleep(10);
            } else { //more than 10, wait short
                if(isDemo)
                    TimeUnit.SECONDS.sleep(5);
                else
                    TimeUnit.MINUTES.sleep(5);
            }
            if(this.queue.isEmpty())
                return; //skip if queue empty
            //pop a post from queue
            Post p = queue.poll();
            //add the post into tree
            this.postTree.addPost(p);
            //print when post is added
            //System.out.println("Post " + Integer.toString(p.getPostID()) + " is added to tree");
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("Task ended.");
        }
    }
}