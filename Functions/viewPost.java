package Functions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.*;

public class viewPost {
    private final double defaultPageSize = 10.0;
    private final Pattern pattern = Pattern.compile("#UM(\\d+)", Pattern.CASE_INSENSITIVE);

    public viewPost(PostTree postTree){
        this.viewMenu(postTree);
    }

    public void viewMenu(PostTree postTree){
        Scanner option_sc = new Scanner(System.in);
        //get a list of post
        LinkedList<Post> tree_full = postTree.getAllPost();
        int treesize = tree_full.size();
        //current page number
        int pageNum = 0;
        int totalPages = (int) Math.ceil(treesize / defaultPageSize);
        boolean continueViewing = false;
        do{
            System.out.println("===== Select a post to view=====");
            System.out.println();
            for(int i = treesize - 1; i < treesize - (pageNum * defaultPageSize) && i > 0; i--){
                Post thispost = tree_full.get(i);
                System.out.printf("#UM%8d: \"%s\"\n", thispost.getPostID(), cutoffComment(thispost.getContent()));
            }
            System.out.println("Page " + Integer.toString(pageNum + 1) + " of "+ Integer.toString(totalPages));
            System.out.println("================================");

            //show options
            System.out.println();
            System.out.println(">>> Options:");
            if(pageNum > 0)
                System.out.println(">>> \"A\" - view previous page");
            if(pageNum < totalPages && totalPages > 1)
                System.out.println(">>> \"D\" - view next page");
            System.out.println(">>> <number> or \"#UM??????\" - view confession with selected ID");
            System.out.println(">>> \"S\" - search post");
            System.out.println(">>> \"Q\" - quit viewing posts");

            System.out.println(">>> ");
            System.out.print(">>> Option:");
            //read input
            String userOption = option_sc.nextLine();
            if(getInteger(userOption) != null || pattern.matcher(userOption).find()){ //if input a digit
                Integer nextviewID = getInteger(userOption);
                if(nextviewID == null){
                    Matcher m = pattern.matcher(userOption);
                    nextviewID = Integer.parseInt(m.group(1));
                }
                this.viewSinglePost(postTree, nextviewID); 
                return;
            }
            if(userOption.length() == 1){
                switch(userOption){
                    case "Q": continueViewing = false; return; //exit code
                    case "A": if(pageNum > 0) pageNum--; 
                              else System.out.println("Invalid option!"); 
                              break;
                    case "D": if(pageNum < totalPages && totalPages > 1) pageNum++;
                              else System.out.println("Invalid option!"); 
                              break;
                    case "S": new searchPost(); //go to search post
                              continueViewing = false; return; //exit code when done searching
                }
            }
        } while(continueViewing);
        return;
    }
    
    public void viewMenu(PostTree postTree, LinkedList<Post> posts){
        Scanner option_sc = new Scanner(System.in);
        int postCount = posts.size();
        //current page number
        int pageNum = 0;
        int totalPages = (int) Math.ceil(postCount / defaultPageSize);
        boolean continueViewing = false;
        do{
            System.out.println("===== Select a post to view=====");
            System.out.println();
            for(int i = postCount - 1; i < postCount - (pageNum * defaultPageSize) && i > 0; i--){
                Post thispost = posts.get(i);
                System.out.printf("#UM%8d: \"%s\"\n", thispost.getPostID(), cutoffComment(thispost.getContent()));
            }
            System.out.println("Page " + Integer.toString(pageNum + 1) + " of "+ Integer.toString(totalPages));
            System.out.println("================================");

            //show options
            System.out.println();
            System.out.println(">>> Options:");
            if(pageNum > 0)
                System.out.println(">>> \"A\" - view previous page");
            if(pageNum < totalPages && totalPages > 1)
                System.out.println(">>> \"D\" - view next page");
            System.out.println(">>> <number> or \"#UM??????\" - view confession with selected ID");
            System.out.println(">>> \"S\" - search post");
            System.out.println(">>> \"Q\" - quit viewing posts");

            System.out.println(">>> ");
            System.out.print(">>> Option:");
            //read input
            String userOption = option_sc.nextLine();
            if(getInteger(userOption) != null || pattern.matcher(userOption).find()){ //if input a digit
                Integer nextviewID = getInteger(userOption);
                if(nextviewID == null){
                    Matcher m = pattern.matcher(userOption);
                    nextviewID = Integer.parseInt(m.group(1));
                }
                this.viewSinglePost(postTree, nextviewID); 
                return;
            }
            if(userOption.length() == 1){
                switch(userOption){
                    case "Q": continueViewing = false; return; //exit code
                    case "A": if(pageNum > 0) pageNum--; 
                              else System.out.println("Invalid option!"); 
                              break;
                    case "D": if(pageNum < totalPages && totalPages > 1) pageNum++;
                              else System.out.println("Invalid option!"); 
                              break;
                    case "S": new searchPost(); //go to search post
                              continueViewing = false; return; //exit code when done searching
                }
            }
        } while(continueViewing);
        return;
    }

    public void viewSinglePost(PostTree postTree, int postID){
        Scanner option_sc = new Scanner(System.in);
        Post post = postTree.findPost(postID);

        //print post item
        System.out.println("\n\n");
        System.out.println("================================");
        System.out.printf("#UM%8d\n", post.getPostID());
        System.out.printf("[%s]", post.getDate().toString());
        System.out.println();
        if(post.getParentID() != -1)
            System.out.printf("Reply to #UM%8d\n", post.getParentID());
        System.out.println(post.getContent());
        System.out.println("================================");

        //show options
        System.out.println();
        System.out.println(">>> Options:");
        if(post.getParentID() != -1)
            System.out.printf(">>> \"W\" - view post #UM%8d\n", post.getParentID());
        System.out.println(">>> \"A\" - view previous post");
        if(post.getChildrenSize() > 0)
            System.out.println(">>> \"S\" - view posts replying to this post");
        System.out.println(">>> \"D\" - view next post");
        System.out.println(">>> \"Q\" - quit viewing post");

        System.out.println(">>> ");
        //read input
        boolean askInputAgain = false;
        do{
            askInputAgain = false;
            System.out.print(">>> Option:");
            String userOption = option_sc.nextLine();
            if(userOption.length() == 1){
                switch(userOption.toUpperCase()){
                    case "Q": break; //exit code
                    case "W": viewSinglePost(postTree, post.getParentID());
                              break;
                    case "A": viewSinglePost(postTree, postTree.getNextChronologicalPostID(post));
                              break;
                    case "D": viewSinglePost(postTree, postTree.getPreviousChronologicalPostID(post));
                              break;
                    case "S": this.viewMenu(postTree, post.getChildren()); break; //temporary
                    default: askInputAgain = true; break;
                }
            }
        } while(askInputAgain);
        return;
    }
    
    public String cutoffComment(String str){
        str = str.trim().replaceAll("[\\t\\n\\r]+"," ");
        int maxWidth = 20;
        
        if(str.length() > maxWidth){
            int correctedMaxWidth = (Character.isLowSurrogate(str.charAt(maxWidth)))&&maxWidth>0 ? maxWidth-1 : maxWidth;
            return str.substring(0, Math.min(str.length(), correctedMaxWidth))) + "...";
        }
        
        return str;
    }
    
    public void display() {
        PostTree tree = new PostTree();
        PostQueue queue = new PostQueue();
        try {
            queue.addPost(tree, new Post(1234, "hi world"));
            Post parent = tree.findPost(1234);
            queue.addPost(tree, new Post(1235, parent, "hi world 2"));
            queue.addPost(tree, new Post(4, "i am ok"));
            queue.addPost(tree, new Post(5, "i am ok"));
            Post answerToEverything = new Post(42, "answer to everything");
            queue.addPost(tree, answerToEverything);
            queue.addPost(tree, new Post(43, answerToEverything, "really? \"It might just be fake...\""));
            queue.addPost(tree, new Post(44, answerToEverything, "It's fake!\nDon't believe him!"));
            queue.addPost(tree, new Post(45, answerToEverything, "hmmmm...."));
            Post reply1 = tree.findPost(45);
            queue.addPost(tree, new Post(46, reply1, "What you think #45?"));
            reply1 = tree.findPost(46);
            queue.addPost(tree, new Post(47, reply1, "Maybe."));
            queue.addPost(tree, new Post(48, reply1, "Maybe too."));
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("Is queue empty? "+queue.isEmpty());
        for(Post p: tree.getAllPost()){
            System.out.println(p);
        }
        System.out.println("=== Removing post #4...");
        System.out.println(tree.removePost(4));
        System.out.println("=== Removing post #1234...");
        System.out.println(tree.removePost(1234));
        for(Post p: tree.getAllPost()){
            System.out.println(p);
        }
        /*System.out.println("=== Removing post #45...");
        System.out.println(tree.removePost(45));
        for(Post p: tree.getAllPost()){
            System.out.println(p);
        }*/
        
        System.out.println("=== Writing tree at 'file.csv'...");
        try {
            saveFiles.saveTree(tree, "file.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public void readFiles() {
        try {
            PostTree tree = readFiles.readTree("file.csv");
            for(Post p: tree.getAllPost()){
                System.out.println(p);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;
    }

    //for parsing integer from user input
    public static Integer getInteger(String strNum) {
        if (strNum == null) {
            return null;
        }
        try {
            int d = Integer.parseInt(strNum);
            if(d <= 0)
                return -1; //just in case
            return d; //return the parsed value
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
