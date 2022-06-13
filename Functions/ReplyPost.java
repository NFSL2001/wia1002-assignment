package Functions;

import java.util.Scanner;

import static Functions.viewPost.getInteger;

public class ReplyPost {
    public void reply(PostTree postTree, PostQueue postQueue){
        new SubmitPost(postTree,postQueue);
    }

    public void reply(PostTree postTree, PostQueue postQueue, Post parentPost){
        Scanner in = new Scanner(System.in);
        System.out.printf("You are replying to post #UM%08d\n", parentPost.getPostID());

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
        Post newPost = new Post(postTree.getNextPostID(), parentPost, user_comment);
        
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
