package Functions;

import java.io.*;
import java.time.LocalDateTime;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class readFiles {
    public static PostTree readTree(String filename) throws IOException {
        //read with utf-8 to support non-ASCII character (eg emoji)
        Scanner sc = new Scanner(new FileInputStream(filename),"utf-8");
        PostTree tree = new PostTree();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            List<String> items;
            try {
                items = parseCSV(line);
                int postID = Integer.parseInt(items.get(0));
                LocalDateTime date = LocalDateTime.parse(items.get(1));
                //read comment, just in case comment has comma
                String content = new String();
                for(int i=2; i < items.size()-1; i++){
                    content += items.get(i);
                    if(i != items.size()-2)
                        content+=",";
                }
                content = unescape(content);
                int parentPostID = Integer.parseInt(items.get(items.size()-1));
                if(parentPostID == -1){
                    Post post = new Post(postID, content, date);
                    tree.addPost(post);
                } else {
                    Post parentPost = tree.findPost(parentPostID);
                    Post post = new Post(postID, parentPost, content, date);
                    tree.addPost(post);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tree;
    }
    
    static String unescape(String str){
        //remove first and last quote from escape
        str = str.substring(1, str.length() - 1);
        //unescape the rest of quotes and \n
        return str.replaceAll("\\\\n", "\\\n").replaceAll("\\\\\"", "\"") ;
    }

    final static String regex_str = "(?:,|\\n|^)(\"(?:(?:\\\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|$))";
    final static Pattern quote = Pattern.compile(regex_str);

    final static List<String> parseCSV(String line) throws Exception{
        List<String> list = new ArrayList<String>();
        line += ",";

        for (int x = 0; x < line.length(); x++)
        {
            String s = line.substring(x);
            if (s.trim().startsWith("\\\\\""))
            {
                Matcher m = quote.matcher(s);
                if (!m.find())
                    throw new Exception("CSV is malformed");
                list.add(m.group(1).replace("\"\"", "\""));
                x += m.end() - 1;
            }
            else
            {
                int y = s.indexOf(",");
                if (y == -1)
                    throw new Exception("CSV is malformed");
                list.add(s.substring(0, y));
                x += y;
            }
        }
        return list;
    }
}
