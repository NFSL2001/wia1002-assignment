package Functions;

import java.util.Scanner;

public class ReplyPost {
    public void reply(PostTree postTree, PostQueue postQueue){
        new SubmitPost(postTree,postQueue);
    }

    public void reply(PostTree postTree, PostQueue postQueue, Post parentPost){
        Scanner in = new Scanner(System.in);
        System.out.printf("You are replying to post #UM%08d\n", parentPost.getPostID());

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
        Post newPost = new Post(postTree.getNextPostID(), parentPost, user_comment);
        SubmitPost.pushPostToQueue(postTree, postQueue, newPost);
        return;
    }
}
