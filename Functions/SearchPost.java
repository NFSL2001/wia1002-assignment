package Functions;

import java.util.Scanner;

public class SearchPost {


    public boolean isEmpty(){
        return new Post().isEmpty();
    }

    public Post searchPost(String keyWord){
        //search all post which including the keyWord
        Scanner in = new Scanner(System.in);
        if(keyWord == null) return null;

        //if don't have the key word ,return null
        if (!(keyWord.contains(new Post().getContent()))) return null;// need fix null
        //System.out.println("Please in put the key word to search: ");
        keyWord = in.nextLine();
        return new PostTree().findPost(keyWord);//hard to do with search method
    }

    public Post searchPost(Integer postID){
        //search all post which including the keyWord
        if(postID == null) return null;

        Scanner in = new Scanner(System.in);
        //if don't have the post ID ,return null
        if ( new Post().getPostID()!=postID ) return null;

        //System.out.println("Please in put the post ID to search: ");
        postID = in.nextInt();
        return new PostTree().findPost(postID);
    }


}
