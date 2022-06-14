package ExtraFeatures;

import java.util.Scanner;
import java.io.*;

public class LoginPage {
    /*
     * OUTPUT: return true if admin, false if user
     */
    public boolean login() { 
        int attemptCount = 3;
        Scanner loginsc = new Scanner(System.in);

        while (attemptCount > -1) {
            
            // Login page is Extra Features.
            System.out.println("======== Login admin ========");
            System.out.print("Please input your username: ");
            String username = loginsc.nextLine();
            String password = readPassword("Please input your password: ");

            System.out.println();

            if (username.equals("admin") && password.equals("letmein")) {
                System.out.println("======== Welcome! ========");
                System.out.println("--------------------------");
                return true;
            } else {
                System.out.println("Wrong! You have " + attemptCount + " times left");
                attemptCount--;
            }
        }
        if (attemptCount == -1) {
            System.out.println("====You are locked!!!====");
            System.out.println("Please try later!");
        }
        return false;
    }

    /**
    *@param prompt The prompt to display to the user
    *@return The password as entered by the user
    */
    public static String readPassword(String prompt) {
        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";

        try {
            password = in.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // stop masking
        et.stopMasking();
        // return the password entered by the user
        return password;
    }
}

/*
 * Obsfucate password with *
 */
class EraserThread implements Runnable {
   private boolean stop;

   /**
    *@param The prompt displayed to the user
    */
   public EraserThread(String prompt) {
       System.out.print(prompt);
   }

   /**
    * Begin masking...display asterisks (*)
    */
   public void run () {
      stop = true;
      while (stop) {
         System.out.print("\010*");
     try {
        Thread.currentThread();
        Thread.sleep(1);
         } catch(InterruptedException ie) {
            ie.printStackTrace();
         }
      }
   }

   /**
    * Instruct the thread to stop masking
    */
   public void stopMasking() {
      this.stop = false;
   }
}