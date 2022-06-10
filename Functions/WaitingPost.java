package Functions;

import java.util.LinkedList;

public class WaitingPost {
    private LinkedList<Post> queue = new LinkedList<>();

    public boolean addPost(PostTree tree, Post p){
        this.queue.offer(p);
        // waiting time (5-15min)
        Post post_remove = this.queue.poll();
        return tree.addPost(post_remove);
    }
    public Post peekPost(){
        return this.queue.peek();
    }
    public boolean isEmpty(){
        return this.queue.isEmpty();
    }
}
