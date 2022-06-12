package Functions;


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

import static Functions.viewPost.getInteger;

// to submit the post
public class SubmitPost {
    public void post(PostTree postTree, PostQueue postQueue){
        Scanner in = new Scanner(System.in);
        System.out.println("Input the post ID to reply to, leave blank if not replying: #UM");
        Integer parentPostID = getInteger(in.nextLine());
        if(parentPostID == null){
            System.out.println("Provided ID is not found/invalid, default to not replying.");
        }

        System.out.println("Please write your post below and end with '#' : ");
        String user_comment = "";
        boolean continueInputText = true;
        //loop to get text
        while (continueInputText) {
            String str = in.nextLine().trim();
            //append this line to the user comment
            user_comment += str+"\n";

            if(user_comment.indexOf('#') > 0) { //if last character of input is #
                user_comment = user_comment.substring(0, user_comment.indexOf('#')).trim(); //remove last character
                continueInputText = false; //stop looping
                break;
            }
        }

        //make a new post pobject
        Post newPost = new Post(postTree.getNextPostID(), postTree.findPost(parentPostID), user_comment);
        
        if(spamChecking.isSpam(postQueue, newPost)){
            //comment is repeated and flagged as spam
            System.out.println("Post is flagged as spam and not submitted.");
            return;
        }

        try {
            //add the post to the postTree , need wait few time
            postQueue.addPostQueue(postTree, newPost);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Post submitted, please wait while we approve it.");
        return;
    }
}

