package Functions;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
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
public class PostQueue implements Lock {
    private LinkedList<Post> queue = new LinkedList<>();

    public PostQueue(){}
    
    public Post peekPost(){
        return this.queue.peek();
    }
    public Post popPost(){
        return this.queue.pop();
    }
    public boolean isEmpty(){
        return this.queue.isEmpty();
    }
    public int queueSize(){
        return this.queue.size();
    }

    public boolean addPost(PostTree tree, Post p) throws InterruptedException {
        //push post into end of queue
        this.queue.offer(p);
        // waiting time (5-15min)
        //TODO Runnable sleep

        if(this.queueSize() <= 5){
            // this is just wait for 5 second only for presentation
            TimeUnit.SECONDS.sleep(5);
            //TimeUnit.MINUTES.sleep(15);// wait for 15minutes
        } else if(this.queueSize() <= 10){
            // this is just wait for 3 second only for presentation
            TimeUnit.SECONDS.sleep(3);
            //TimeUnit.MINUTES.sleep(10);// wait for 10minutes
        } else {
            // this is just wait for 3 second only for presentation
            TimeUnit.SECONDS.sleep(1);
            //TimeUnit.MINUTES.sleep(5);// wait for 10minutes
        }

        //remove post from end of queue
        Post post_remove = this.queue.poll();
        //add the post into tree
        return tree.addPost(post_remove);
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
