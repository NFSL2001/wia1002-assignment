package main;

import java.util.Scanner;

import ExtraFeatures$6marks.LoginPage;
import Functions$10marks.viewPost;

public class homepage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //this is a login page belongs to Extra Features.
        //username is [admin], password is [letmein]
        new LoginPage().login();

        while (true) {
            System.out.println("1: run test code to make a new tree");
            System.out.println("2: read the tree from csv file");
            System.out.print("Input option: ");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    new viewPost().display();
                    break;
                case 2:
                    new viewPost().readFiles();
                    break;
                default:
                    System.out.println("Wrong! Please input again!");
                    //System.exit(0);

            }
            sc.close();
        }


    }
}
