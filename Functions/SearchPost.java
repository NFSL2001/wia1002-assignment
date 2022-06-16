package Functions;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.*;

public class SearchPost {
    final static Pattern id_pattern = Pattern.compile("#UM(\\d+)", Pattern.CASE_INSENSITIVE);
    final static Pattern date_pattern = Pattern.compile("(\\d{4}-\\d{1,2}-\\d{1,2})");
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
    
    public SearchPost(PostTree postTree, PostQueue postQueue){
        Scanner search = new Scanner(System.in);
        boolean continueSearch = false;
        do{
            continueSearch = false;
            System.out.println("\n\n");
            System.out.println("============== Searching ==============");
            System.out.println(">>> Searching formats and options:");
            System.out.println(">>> YYYY-MM-DD: search by date");
            System.out.println(">>> #UMXXXXXXXX: search by post ID");
            System.out.println(">>> <any>: search by keywords");
            System.out.println("=======================================");
            System.out.println("Please type the key word to search: ");

            String input = search.nextLine().trim();
            Matcher m = id_pattern.matcher(input);
            if(m.find()){
                String id = m.group(1);
                if(searchID(id, postTree).size() != 0){
                    new viewPost(postTree, postQueue, searchID(id, postTree));
                    return;
                } else 
                continueSearch = true;
            }
            Matcher n = date_pattern.matcher(input);
            if(n.find()){
                String date = n.group(1);
                if(searchDate(date, postTree).size() != 0){
                    new viewPost(postTree, postQueue, searchDate(date, postTree));
                    return;
                } else 
                continueSearch = true;
            }

            if(searchWord(input, postTree).size() != 0){
                new viewPost(postTree, postQueue, searchWord(input, postTree));
                return;
            } else {
                System.out.println("No posts match criteria.");
                continueSearch = true;
            }
        } while(continueSearch);
    }


    public LinkedList<Post> searchWord(String keyWord, PostTree postTree){
        //search all post which including the keyWord
        if(keyWord == null) return null;

        LinkedList<Post> matches = new LinkedList<>();

        for(Post p: postTree.getAllPosts()){
            if (p.getContent().toLowerCase().contains(keyWord.toLowerCase()))
                matches.add(p);
        }
        
        return matches;
    }

    public LinkedList<Post> searchID(String postID, PostTree postTree){
        //search all post which has similar postID
        if(postID == null) return null;

        LinkedList<Post> matches = new LinkedList<>();

        for(Post p: postTree.getAllPosts()){
            if(Integer.toString(p.getPostID()).contains(postID)){
                matches.add(p);
            }
        }

        return matches;
    }

    public LinkedList<Post> searchDate(String date, PostTree postTree){
        //search all post which has same date
        if(date == null) return null;

        LinkedList<Post> matches = new LinkedList<>();

        for(Post p: postTree.getAllPosts()){
            if(p.getDate().format(formatter).equals(date)){
                matches.add(p);
            }
        }

        return matches;
    }

}
