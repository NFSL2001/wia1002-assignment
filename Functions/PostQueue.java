package Functions;

import java.util.LinkedList;

public class PostQueue {
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
        Post post_remove = this.queue.poll();
        return tree.addPost(post_remove);
    }
}
