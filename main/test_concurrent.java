package main;

import java.util.concurrent.*;

import Functions.PostTree;
import Functions.Post;

public class test_concurrent {
    public static ConcurrentLinkedQueue<Post> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        PostTree tree = new PostTree();
        Post answerToEverything = new Post(42, "answer to everything");
        Post an43 = new Post(43, answerToEverything, "really?");
        queue.add(answerToEverything);
        queue.add(an43);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        for(int i=0; i<5; i++){
            executor.execute(new MyRunnable(tree, queue)); //each runnable take time
            System.out.println("submitted one task");
        }

        try {
            for(int i=1; i<=8; i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Integer.toString(i));
            }
            System.out.println("countdown done");
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            
            System.out.println("shutdown finished");
        }
        System.out.println(queue.size());
        for(Post p: queue)
            tree.addPost(queue.poll());
        System.out.println(queue.size());

        for(Post p: tree.getAllPosts())
            System.out.println(p);
    }
    
}

class MyRunnable implements Runnable {
    private PostTree postTree;
    public ConcurrentLinkedQueue<Post> queue;

    public MyRunnable(PostTree postTree, ConcurrentLinkedQueue<Post> queue) {
        // store parameter for later user
        this.postTree = postTree;
        this.queue = queue;
    }
 
    public void run() {
        try {
            Thread.sleep(10000);
            Post p = queue.poll();
            this.postTree.addPost(p);
            System.out.println("post " + Integer.toString(p.getPostID()) + " is added to tree");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("post is not popped");
        }
        System.out.println("run finished");
    }
 }
