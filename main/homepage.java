package main;

import java.util.Scanner;

import ExtraFeatures$6marks.LoginPage;
import Functions$10marks.BatchRemoval;
import Functions$10marks.Reply;
import Functions$10marks.SubmitPost;
import Functions$10marks.viewPost;

public class homepage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //this is a login page belongs to Extra Features.
        //username is [admin], password is [letmein]
        new LoginPage().login();  // by Criss

        while (true) {
            System.out.println("1: run test code to make a new tree");
            System.out.println("2: read the tree from csv file");
            System.out.println("3: ");
            System.out.println("4: input the ID which you want to reply the post");
            System.out.println("5: input the ID which you want to remove the post");
            System.out.println("6: input the name which you want to remove all his/her Post");
            System.out.print("Input option: ");
            int option = sc.nextInt();
            switch (option) {
                case 1 -> new viewPost().display();
                case 2 -> new viewPost().readFiles();
                case 3 -> new SubmitPost().post();
                case 4 -> new Reply().replyID("#UM");
                case 5 -> new BatchRemoval().removeIDPost("#input an ID");
                case 6 -> new BatchRemoval().removeAll("name");
                // need more options

                default -> System.out.println("Wrong! Please input again!");
                //System.exit(0);

            }
            //sc.close();
        }
    }
}
