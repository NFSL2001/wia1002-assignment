package Functions;

import java.io.*;
import java.time.LocalDateTime;


public class saveFiles {
    public static void addMainPost(String postID, LocalDateTime postDate, String postContent) throws IOException {
       // FileWriter writer = new FileWriter(String.valueOf(new FileOutputStream("mainPosts.txt")));
        PrintWriter writer = new PrintWriter(new FileOutputStream("mainPosts.txt", true));
        writer.println(postID);
        writer.println(postDate);
        writer.println(postContent);
        writer.close();
    }
    public static void addReplyPost(String postID, String mainPostID ,LocalDateTime postDate, String postContent) throws IOException {
        // FileWriter writer = new FileWriter(String.valueOf(new FileOutputStream("mainPosts.txt")));
        PrintWriter writer = new PrintWriter(new FileOutputStream("replyPosts.txt", true));
        writer.println(postID);
        writer.println(mainPostID);
        writer.println(postDate);
        writer.println(postContent);
        writer.close();
    }
}
