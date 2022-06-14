package Functions;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;

import static Functions.viewPost.getInteger;

//remove the post according to the ID
public class BatchRemoval {

    public BatchRemoval(PostTree postTree, PostQueue postQueue){
        Scanner num_sc = new Scanner(System.in);
        System.out.print("Type post ID to remove (number or #UM00000000): ");
        String userOption = num_sc.next();
        if(getInteger(userOption) != null || viewPost.pattern.matcher(userOption).find()){ //if input a digit
            Integer removeID = getInteger(userOption);
            if(removeID == null){
                Matcher m = viewPost.pattern.matcher(userOption);
                removeID = Integer.parseInt(m.group(1));
            }
            //check if postID is valid
            if(postTree.getIDs().indexOf(removeID) != -1)
                removeIDPost(removeID, postTree);
            else
                System.out.println("Invalid post ID. Please try again.");
        }
    }

    //store All IDs in a HashSet
    private Set<Integer> postsToBeRemoved = new HashSet<>();
    public void removeIDPost(int ID, PostTree posts){
        //Adding the main post ID
        postsToBeRemoved.add(ID);
        helperFunction(ID, posts);
        removed(posts);
    }

    private void helperFunction(int ID, PostTree posts){
        if (posts.findPost(ID) == null)
            return;
        // Iterating through All children of one post
        for (int i=0; i<posts.findPost(ID).getChildrenSize();i++) {
            helperFunction(posts.findPost(ID).getChild(i).getPostID(), posts);
        }
        // adding to HashSet
        for (Post post : posts.findPost(ID).getChildren()){
            postsToBeRemoved.add(post.getPostID());
        }
    }

   private void removed(PostTree posts){
       System.out.println("Posts has been removed: " + postsToBeRemoved);
       // Remove all posts by their ID
       postsToBeRemoved.forEach(posts::removePost);
    }
}
