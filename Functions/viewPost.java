package Functions;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.*;

public class viewPost {
    private final double defaultPageSize = 10.0;
    private final int maxCommentLength = 30;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
    private final Pattern pattern = Pattern.compile("#UM(\\d+)", Pattern.CASE_INSENSITIVE);

    public viewPost(PostTree postTree, PostQueue postQueue){
        this.viewMenu(postTree, postQueue);
    }

    public void viewMenu(PostTree postTree, PostQueue postQueue){
        Scanner option_sc = new Scanner(System.in);
        //get a list of post
        LinkedList<Post> tree_full = postTree.getAllPosts();
        int treesize = tree_full.size();
        //current page number
        int pageNum = 0;
        int totalPages = (int) Math.ceil(treesize / defaultPageSize);
        boolean continueViewing = true;
        do{
            System.out.println("\n\n");
            System.out.println("===== Select a post to view=====");
            System.out.println();
            for(int i = treesize - 1; i < treesize - (pageNum * defaultPageSize) && i >= 0; i--){
                Post thispost = tree_full.get(i);
                System.out.printf("#UM%08d: \"%s\"\n", thispost.getPostID(), cutoffComment(thispost.getContent()));
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
            System.out.print(">>> Option: ");
            //read input
            String userOption = option_sc.nextLine().trim();
            if(getInteger(userOption) != null || pattern.matcher(userOption).find()){ //if input a digit
                Integer nextviewID = getInteger(userOption);
                if(nextviewID == null){
                    Matcher m = pattern.matcher(userOption);
                    nextviewID = Integer.parseInt(m.group(1));
                }
                //check if postID is valid
                if(postTree.getIDs().indexOf(nextviewID) != -1){
                    this.viewSinglePost(postTree, postQueue, nextviewID); 
                    return;
                } else
                    System.out.println("Invalid option. Please try again.");
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
    
    public void viewMenu(PostTree postTree, PostQueue postQueue, LinkedList<Post> posts){
        System.out.println(posts.getFirst());
        Scanner option_sc = new Scanner(System.in);
        int postCount = posts.size();
        //current page number
        int pageNum = 0;
        int totalPages = (int) Math.ceil(postCount / defaultPageSize);
        boolean continueViewing = true;
        do{
            System.out.println("\n\n");
            System.out.println("===== Select a post to view=====");
            System.out.println();
            for(int i = postCount - 1; i < postCount - (pageNum * defaultPageSize) && i >= 0; i--){
                Post thispost = posts.get(i);
                System.out.printf("#UM%08d: \"%s\"\n", thispost.getPostID(), cutoffComment(thispost.getContent()));
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
            System.out.print(">>> Option: ");
            //read input
            String userOption = option_sc.nextLine().trim();
            if(getInteger(userOption) != null || pattern.matcher(userOption).find()){ //if input a digit
                Integer nextviewID = getInteger(userOption);
                if(nextviewID == null){
                    Matcher m = pattern.matcher(userOption);
                    nextviewID = Integer.parseInt(m.group(1));
                }
                //check if postID is valid
                if(postTree.getIDs().indexOf(nextviewID) != -1){
                    this.viewSinglePost(postTree, postQueue, nextviewID); 
                    return;
                } else
                    System.out.println("Invalid option. Please try again.");
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

    public void viewSinglePost(PostTree postTree, PostQueue postQueue, int postID){
        Scanner option_sc = new Scanner(System.in);
        Post post = postTree.findPost(postID);

        //print post item
        System.out.println("\n\n");
        System.out.println("================================");
        System.out.printf("#UM%08d\n", post.getPostID());
        System.out.printf("[%s]\n", post.getDate().format(formatter));
        System.out.println("");
        if(post.getParentID() != -1)
            System.out.printf("Reply to #UM%08d\n", post.getParentID());
        System.out.println(post.getContent());
        System.out.println("================================");

        //show options
        System.out.println();
        System.out.println(">>> Options:");
        if(post.getParentID() != -1)
            System.out.printf(">>> \"W\" - view post #UM%08d\n", post.getParentID());
        System.out.println(">>> \"A\" - view previous post");
        if(post.getChildrenSize() > 0)
            System.out.println(">>> \"S\" - view posts replying to this post");
        System.out.println(">>> \"D\" - view next post");
        System.out.println(">>> \"X\" - reply to this post");
        System.out.println(">>> \"Q\" - quit viewing post");

        System.out.println(">>> ");
        //read input
        boolean askInputAgain = false;
        do{
            askInputAgain = false;
            System.out.print(">>> Option: ");
            String userOption = option_sc.nextLine().trim();
            if(userOption.length() == 1){
                switch(userOption.toUpperCase()){
                    case "Q": break; //exit code
                    case "W": viewSinglePost(postTree, postQueue, post.getParentID());
                              break;
                    case "A": viewSinglePost(postTree, postQueue, postTree.getPreviousChronologicalPostID(post));
                              break;
                    case "D": viewSinglePost(postTree, postQueue, postTree.getNextChronologicalPostID(post));
                              break;
                    case "S": this.viewMenu(postTree, postQueue, post.getChildren());
                              break;
                    case "X": new ReplyPost().reply(postTree, postQueue, post);
                              break;
                    default: askInputAgain = true; break;
                }
            }
        } while(askInputAgain);
        return;
    }
    
    public String cutoffComment(String str){
        str = str.trim().replaceAll("[\\t\\n\\r]+"," ");
        
        if(str.length() > this.maxCommentLength){
            int correctedMaxWidth = (Character.isLowSurrogate(str.charAt(this.maxCommentLength)))&&this.maxCommentLength>0 ? this.maxCommentLength-1 : this.maxCommentLength;
            return str.substring(0, Math.min(str.length(), correctedMaxWidth)) + "...";
        }
        
        return str;
    }

    //for parsing integer from user input
    public static Integer getInteger(String strNum) {
        if (strNum == null) {
            return null;
        }
        try {
            int d = Integer.parseInt(strNum);
            if(d <= 0)
                return null; //just in case
            return d; //return the parsed value
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
