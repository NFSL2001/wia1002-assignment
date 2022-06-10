package ExtraFeatures$6marks;

import java.util.Scanner;

public class LoginPage {
    public void login(){
        int i =3;
      while (i>-1) {
          Scanner sc = new Scanner(System.in);
          System.out.println();
          System.out.println();
          System.out.println("======== Welcome to Confession Time ========");
          System.out.println("-----------------------------------");
          //  Login page is Extra Features.
          System.out.println("======== Login page ========");
          System.out.println("Please input your username");
          String username = sc.nextLine();
          System.out.println("Please input your password");
          String password = sc.nextLine();
          if (username.equals("admin") && password.equals("letmein")) {
              System.out.println("======== Welcome! ========");
              System.out.println("--------------------------");
              return;
          } else {
              System.out.println("Wrong! You have "+i+" times left");
              i--;
          }

      }
        if (i==-1){
            System.out.println("====You are locked!!!====");
            System.out.println("Please try later!");
            System.exit(0);
        }
    }
}
