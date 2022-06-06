package Functions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Post {
    private int postID;
    private Post parentPost;
    private LocalDateTime date;
    private String content;
    private LinkedList<Post> children = new LinkedList<>();

    public Post(int postID, String content){
        this.postID = postID;
        this.content = content;
        this.date = LocalDateTime.now();
    }
    public Post(int postID, Post parentPost, String content){
        this.postID = postID;
        this.parentPost = parentPost;
        this.content = content;
        this.date = LocalDateTime.now();
    }
    
    //getter
    public int getPostID() {
        return postID;
    }
    public int getParentID() {
        if(parentPost == null)
            return -1;
        return parentPost.getPostID();
    }
    public LocalDateTime getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }

    
    //add/get new child
    public boolean addChild(Post t){
        return this.children.add(t);
    }
    public boolean removeChild(Post t){
        return this.children.remove(t);
    }
    /*
    public boolean removeChildAtPos(int i){
        for(Functions.Post p: this.children){
            if(p.getPostID() == i){
                return this.children.remove(p);
            }
        }
        return false;
    }
    public int getChildPosition(Functions.Post t){
        for(int i=0; i<this.getChildrenSize(); i++){
            if(this.getChild(i).equals(t)){
                return i;
            }
        }
        return -1;
    }
    */
    public Post getChildByID(int postID){
        for(Post p: this.children){
            if(p.getPostID() == postID){
                return p;
            }
        }
        return null;
    }
    public Post getChild(int i){
        if(i >= this.children.size()){
            return null;
        }
        return this.children.get(i);
    }
    public Post getFirstChild(){
        return this.getChild(0);
    }
    public Post getLastChild(){
        return this.getChild(this.children.size() - 1);
    }
    public int getChildrenSize(){
        return this.children.size();
    }
    public LinkedList<Post> getChildren(){
        return this.children;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = "";
        str+="Functions.Post #" + Integer.toString(this.getPostID());
        if(this.getParentID() != -1)
            str+="\nReplying to post #" + Integer.toString(this.getParentID());
        str+=": " + this.content;
        str+="\nCommented at " + this.date.format(formatter);
        str+="\n";
        return str;
    }
}
