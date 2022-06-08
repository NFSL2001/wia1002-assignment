package Functions;

import java.io.*;


public class saveFiles {
    public static void saveTree(PostTree tree, String filename) throws IOException {
        // FileWriter writer = new FileWriter(String.valueOf(new FileOutputStream("mainPosts.txt")));
        PrintWriter writer = new PrintWriter(new FileOutputStream(filename));
        for(Post post: tree.getAllPost()){
            writePost(writer, post);
        }
        writer.close();
    }
    static void writePost(PrintWriter writer, Post post) throws IOException {
        writer.print(post.getPostID());
        writer.print(",");
        writer.print(post.getDate());
        writer.print(",");
        writer.print(escape(post.getContent()));
        writer.print(",");
        writer.print(post.getParentID());
        writer.println();
    }

    static String escape(String str){
        return '"' + str.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n").replaceAll("\"", "\\\\\"") + '"';
    }
}
