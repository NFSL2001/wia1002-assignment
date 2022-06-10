package main;

import java.util.Scanner;

import ExtraFeatures.LoginPage;
import Functions.BatchRemoval;
import Functions.Reply;
import Functions.SubmitPost;
import Functions.WaitingPost;
import Functions.searchPost;
import Functions.viewPost;

public class homepage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //this is a login page belongs to Extra Features.
        //username is [admin], password is [letmein]
        boolean isAdmin = new LoginPage().login();  // by Criss

        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1: Submit a new post");
            System.out.println("2: View posts");
            System.out.println("3: Search a post");
            System.out.println("4: ");
            if(isAdmin){
                System.out.println("5: ADMIN ONLY: Remove a post and subsequent posts");
                System.out.println("6: ADMIN ONLY: View queuing posts");
            }

            //TODO: Temporary demonstration code; remove before submit
            System.out.println("98: TEMPORARY: run test code to make a new tree");
            System.out.println("99: TEMPORARY: read the tree from csv file");

            System.out.println("-1: Exit program");
            System.out.print("Input option: ");
            int option = sc.nextInt();
            switch (option) {
                case 1: new SubmitPost().post(); break;
                case 2: new viewPost().display(); break;
                case 3: new searchPost(); break;
                case 4: break;
                //break if not admin
                case 5: if(!isAdmin) {System.out.println("Please input again!"); break;}
                        new BatchRemoval().removeIDPost("#input an ID"); break;
                case 6: if(!isAdmin) {System.out.println("Please input again!"); break;}
                        new WaitingPost(); break;
                // need more options


                case 98: new viewPost().display(); break;
                case 99: new viewPost().readFiles(); break;
                case -1: System.exit(0);
                default: System.out.println("Please input again!"); break;
            }
        }
    }
}
