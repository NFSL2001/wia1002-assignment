package Functions$10marks;
// by Wei Zhang
import java.util.ArrayList;
import java.util.Scanner;

public class submitPost {
    private ArrayList<String> strings;

    //name ID


    public static void main(String[] args) {
        new submitPost().post();
    }
    public void post(){
        //ArrayList<String> strings = new ArrayList<>();
        strings = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()){
           String str = in.nextLine();
           strings.add(str);

           //method below
           toPost(str);
          /*  char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                //if the last word is #, then end user's input and post it.
                if (chars[chars.length-1]=='#'){
//                    //this method is below
//                    toPost();
                    strings.remove("#");
                    System.out.println(strings);
                    break;
                }
            }*/
        }

    }
    public void toPost(String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            //if the last word is #, then end user's input and post it.
            if (chars[chars.length-1]=='#'){
//                    //this method is below
//                    toPost();
                strings.remove("#");
                System.out.println(strings);
                break;
            }
        }
    }
}
