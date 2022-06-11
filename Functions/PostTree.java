package Functions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class PostTree {
    private LinkedList<Post> list = new LinkedList<>();
    private int nextPostID = -1;
    public PostTree(){}
    
    //change to public for testing code only
    // DO NOT use this function for user input code, use PostQueue
    public boolean addPost(Post p){
        ArrayList<Integer> pastID = this.getIDs(); //get list of post ID
        if(pastID.contains(p.getPostID())){ //check if ID is already in tree, do not add
            return false;
        }
        Post parent = this.findPost(p.getParentID());
        if(parent != null)
            return parent.addChild(p) && this.list.add(p);
        else
            return this.list.add(p);
    }
    public Post findPost(int postID){
        if(postID == -1) return null;
        for(Post p: this.list){
            if(p.getPostID() == postID){
                return p;
            }
        }
        return null;
    }
    public boolean removePost(int postID){
        if(this.findPost(postID) == null)
            return false;
        
        Post currentPost = this.findPost(postID);
        LinkedList<Post> childrens = currentPost.getChildren();
        for(Iterator<Post> iterator = childrens.iterator(); iterator.hasNext(); ){ 
            //use iterator to loop through list to prevent concurrent removal
            Post post = iterator.next();
            removePost(post); //remove children from tree
            iterator.remove(); //remove link to children
        }
        //delete itself if no more child
        if(currentPost.getChildrenSize() == 0){
            if(currentPost.getParentID() != -1 && this.findPost(currentPost.getParentID()).getChildrenSize() > 0)
                this.findPost(currentPost.getParentID()).removeChild(currentPost); //remove link to this post in parent
            this.list.remove(currentPost);
            return true;
        }
        return false;
    }
    public boolean removePost(Post currentPost){
        LinkedList<Post> childrens = currentPost.getChildren();
        for(Iterator<Post> iterator = childrens.iterator(); iterator.hasNext(); ){ 
            //use iterator to loop through list to prevent concurrent removal
            Post post = iterator.next();
            removePost(post); //remove children from tree
            iterator.remove(); //remove link to children
        }
        //delete itself if no more child
        if(currentPost.getChildrenSize() == 0){
            this.list.remove(currentPost);
            return true;
        }
        return false;
    }
    public LinkedList<Post> getAllPost(){
        return this.list;
    }
    ArrayList<Integer> getIDs(){
        ArrayList<Integer> ret = new ArrayList<>();
        for(Post p: this.list){
            ret.add(p.getPostID());
        }
        return ret;
    }
    private int getmaxID(){
        int ret = -1;
        for(Post p: this.list)
            if(p.getPostID() > ret)
                ret = p.getPostID();
        return ret;
    }
    public int getNextPostID(){ //for creating a new post
        if(this.nextPostID == -1){ //if had not been initialize
            this.nextPostID = this.getmaxID() + 1; //get the max id and + 1 to prevent same index
        }
        return this.nextPostID++; //return the integer, then increment
    }
}
