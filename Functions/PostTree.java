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
    public Post findPost(Integer postID){
        if(postID == null) return null;
        for(Post p: this.list){
            if(p.getPostID() == postID){
                return p;
            }
        }
        return null;
    }

    //iteratively remove post
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
    //end iteratively remove post

    public LinkedList<Post> getAllPosts(){
        return this.list;
    }
    public int size(){
        return this.list.size();
    }
    ArrayList<Integer> getIDs(){
        ArrayList<Integer> ret = new ArrayList<>();
        for(Post p: this.list){
            ret.add(p.getPostID());
        }
        return ret;
    }

    //for creating a new post
    private int getMaxID(){
        int ret = -1;
        for(Post p: this.list)
            if(p.getPostID() > ret)
                ret = p.getPostID();
        return ret;
    }
    public int getNextPostID(){ //for creating a new post
        if(this.nextPostID == -1){ //if had not been initialize
            this.nextPostID = this.getMaxID() + 1; //get the max id and + 1 to prevent same index
        }
        return this.nextPostID++; //return the integer, then increment
    }

    //for viewPost
    public int getNextChronologicalPostID(Post post){ //get the next post *according to time*
        if(this.list.indexOf(post) < 0){ //if post not in tree
            return this.list.getFirst().getPostID(); //return first post
        }
        int list_index = this.list.indexOf(post); //get post index by time
        if(list_index == this.list.size() - 1){ //if last post in tree
            return this.list.getLast().getPostID(); //return first post
        }
        return this.list.get(list_index + 1).getPostID();
    }
    public int getPreviousChronologicalPostID(Post post){ //get the previous post *according to time*
        if(this.list.indexOf(post) <= 0){ //if post not in tree or post is first post
            return this.list.getFirst().getPostID(); //return first post
        }
        int list_index = this.list.indexOf(post); //get post index by time
        if(list_index == this.list.size() - 1){ //if last post in tree
            return this.list.getLast().getPostID(); //return last post
        }
        return this.list.get(list_index - 1).getPostID();
    }
}
