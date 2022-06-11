package Functions;


/* By Criss
    1 TimeUnit.DAYS.sleep(1);//day
    2 TimeUnit.HOURS.sleep(1);//hour
    3 TimeUnit.MINUTES.sleep(1);//minute
    4 TimeUnit.SECONDS.sleep(1);//second
    5 TimeUnit.MILLISECONDS.sleep(1000);//milli second
    6 TimeUnit.MICROSECONDS.sleep(1000);//micro second
    7 TimeUnit.NANOSECONDS.sleep(1000);//nano second
* */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// to submit the post
public class SubmitPost {
    private ArrayList<String> strings;
    //private ArrayList<PostTree> arrPost;
    //private Post stringPost;

    public void post() throws InterruptedException {
        // ArrayList<String> strings = new ArrayList<>();
        strings = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Please write your post below: ");
        while (in.hasNext()) {
            String str = in.nextLine();
            strings.add(str);

        /*    PostTree pt1 = new PostTree();
            PostTree pt2 = new PostTree();
            arrPost.add(pt1);
            arrPost.add(pt2);*/

            //new PostTree().addPost(stringPost);
            //new PostQueue().addPost()

            // method below
            //toSleep(str);
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                // if the last word is #, then end user's input and post it.
                if (chars[chars.length - 1] == '#') { //我估计#占2个bytes
                    strings.remove("#");
                    //strings.remove(chars.length-1); //remove the last word #

                    // this is just wait for 5 second
                    TimeUnit.SECONDS.sleep(5);
                    //TimeUnit.MINUTES.sleep(15);// wait for 15minutes

                    System.out.println(strings);
                    strings.clear();//clear all input before
                    // put return here can back to the main menu, but can delete #
                    return;//can't use break because it can't back to main menu
                }

            }

   /* public void toSleep(String str) throws InterruptedException {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            // if the last word is #, then end user's input and post it.

            if (chars[chars.length - 1] == '#') { //我估计#占2个bytes
                // toPost();
                strings.remove("#");
                //strings.remove(chars.length-1); //remove the last word #

                // this is just wait for 5 second
                TimeUnit.SECONDS.sleep(5);
                //TimeUnit.MINUTES.sleep(15);// wait for 15minutes

                System.out.println(strings);
                strings.clear();//remove all input before
                return; // put return here can delete the # and back to the menu
            }
        }
    }*/
        }
    }
}

