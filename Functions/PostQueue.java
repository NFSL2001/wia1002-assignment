package Functions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

//lock it for few minus
public class PostQueue {
    //thread for concurrent
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    //store posts to be added
    private static ConcurrentLinkedQueue<Post> queue = new ConcurrentLinkedQueue<>();

    public PostQueue(){}
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
    private ConcurrentLinkedQueue<Post> queue;
    private boolean isDemo = true;

    public MyRunnable(PostTree postTree, ConcurrentLinkedQueue<Post> queue) {
        // store parameter for later user
        this.postTree = postTree;
        this.queue = queue;
    }
    
    /*
     * Waits according to the size of queue.
     * Then, try to pop a post from given queue and add it into the tree.
     * Post should be pushed into queue before adding this task.
     */
    public void run() {
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