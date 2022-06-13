package main;

import Functions.Post;
import Functions.PostQueue;
import Functions.PostTree;
import Functions.SubmitPost;

public class test {
    public static void main(String[] args){
        PostTree tree = new PostTree();
        PostQueue queue = new PostQueue();
        tree.addPost(new Post(1234, "hi world"));
        
        //for test , this need more edit
        new SubmitPost(tree, queue);
        for(Post p: tree.getAllPosts()){
            System.out.println(p);
        }
        System.exit(0);
    }
}
