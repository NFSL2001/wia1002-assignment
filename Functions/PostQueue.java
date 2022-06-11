package Functions;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

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

    public boolean addPost(PostTree tree, Post p){
        this.queue.offer(p);
        // waiting time (5-15min)
        //TODO Runnable sleep


        Post post_remove = this.queue.poll();
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
