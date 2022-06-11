package Functions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

//remove the post according to the ID
public class BatchRemoval {

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
