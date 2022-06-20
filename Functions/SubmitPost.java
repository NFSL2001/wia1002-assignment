package Functions;


import java.io.IOException;
import java.util.HashMap;
/* By Criss
    1 TimeUnit.DAYS.sleep(1);//day
    2 TimeUnit.HOURS.sleep(1);//hour
    3 TimeUnit.MINUTES.sleep(1);//minute
    4 TimeUnit.SECONDS.sleep(1);//second
    5 TimeUnit.MILLISECONDS.sleep(1000);//milli second
    6 TimeUnit.MICROSECONDS.sleep(1000);//micro second
    7 TimeUnit.NANOSECONDS.sleep(1000);//nano second
* */
import java.util.Scanner;

import ExtraFeatures.VacationMode;
import main.homepage;

import static Functions.viewPost.getInteger;

// to submit the post
public class SubmitPost {
    public SubmitPost(PostTree postTree, PostQueue postQueue){
        this.post(postTree, postQueue);
    }

    public void post(PostTree postTree, PostQueue postQueue){
        Scanner in = new Scanner(System.in);
        System.out.print("Input the post ID to reply to, leave blank if not replying: #UM");
        Integer parentPostID = getInteger(in.nextLine());
        if(parentPostID == null || postTree.getIDs().indexOf(parentPostID) == -1){
            System.out.println("Provided ID is not found/invalid, default to not replying.");
            parentPostID = null;
        }

        System.out.println("Please write your post below.\nTo stop the text, type '-1' on a new line : ");
        String user_comment = "";
        boolean continueInputText = true;
        //loop to get text
        while (continueInputText) {
            String str = in.nextLine().trim();
            //append this line to the user comment
            if(str.startsWith("-1")){ //if line stars with -1
                continueInputText = false; //stop looping
                break;
            }
            user_comment += str+"\n";
        }

        //make a new post pobject
        Post newPost = new Post(postTree.getNextPostID(), postTree.findPost(parentPostID), user_comment);
        pushPostToQueue(postTree, postQueue, newPost);
        return;
    }

    public static void pushPostToQueue(PostTree postTree, PostQueue postQueue, Post newPost){
        System.out.println("\n");
        //filtering spam
        if(spamChecking.isSpam(postQueue, newPost)){
            //comment is repeated and flagged as spam
            System.out.println("WARNING: Post is flagged as spam and not submitted.");
            return;
        }
        //filtering content
        if(homepage.isOnVacation){ //if admin activated this option
            HashMap<String, Boolean> dict;
            try {
                dict = VacationMode.checkPost(newPost.getContent());
                if(!dict.get("polite") || !dict.get("noPersonalInfo") || !dict.get("noURLs")){
                    //comment is not 
                    System.out.println("WARNING: Post is rejected and not submitted.");
                    return;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //add the post to the postTree , need wait few time
        postQueue.addTask(postTree, newPost);

        System.out.println("Post submitted, please wait while we approve it.");
        return;
    }
}

