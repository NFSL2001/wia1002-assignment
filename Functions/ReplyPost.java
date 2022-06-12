package Functions;

import java.util.Scanner;

import static Functions.viewPost.getInteger;

public class ReplyPost {
    public void reply(PostTree postTree, PostQueue postQueue){
        new SubmitPost().post(postTree,postQueue);
    }
}
