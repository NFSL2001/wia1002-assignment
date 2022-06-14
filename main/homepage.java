package main;

import java.io.*;
import java.util.Scanner;

import ExtraFeatures.LoginPage;
import Functions.*;

public class homepage {
    public static PostTree postTree;
    public static PostQueue postQueue;
    public static String filename = "file.csv";
    public static boolean isOnVacation = false;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        // read tree from stored file
        try {
            postTree = readFiles.readTree(filename);
        } catch (IOException e) {
            // throw error
            e.printStackTrace();
            System.out.println("Could not read stored file. Exiting.");
            // exit program
            System.exit(1);
        }

        // new queue for posts
        // will also init executor
        postQueue = new PostQueue();

        System.out.println("======== Welcome to Confession Time ========");
        System.out.println("-----------------------------------");

        // this is a login page belongs to Extra Features.
        // username is [admin], password is [letmein]
        boolean isAdmin = false;
        boolean continueProgram = true;

        // start running program
        do {
            System.out.println();
            System.out.println();
            System.out.println("=== Main Menu ===");
            System.out.println("1: Submit a new post");
            System.out.println("2: Reply to a post");
            System.out.println("3: View posts");
            System.out.println("4: Search a post");

            // display whether admin login or not
            if (!isAdmin) System.out.println("5: Admin login");
            else System.out.println("5: Admin logout");
            // functions only for admin
            if (isAdmin) {
                System.out.println("11: ADMIN ONLY: Remove a post and subsequent posts");
                System.out.println("12: ADMIN ONLY: View queuing posts");
                System.out.print("13: ADMIN ONLY: Vacation mode: Turn ");
                if(isOnVacation)
                    System.out.println("off");
                else
                    System.out.println("on");
            }

            System.out.println("-1: Exit program");
            System.out.print("Input option: ");
            int option = sc.nextInt();

            System.out.println();
            switch (option) {
                case 1:
                    new SubmitPost(postTree, postQueue);
                    break;
                case 2:
                    new ReplyPost().reply(postTree, postQueue);
                    break;
                case 3:
                    new viewPost(postTree, postQueue);
                    break;
                case 4:
                    new searchPost();
                    break;

                // if admin, then set to false to logout
                // if not, login to admin
                case 5:
                    if (isAdmin) {
                        isAdmin = false;
                        System.out.println("You have been logged out.");
                    } else
                        isAdmin = new LoginPage().login();
                    break; // by Criss

                // ADMIN ONLY function
                // break if not admin, popup input error
                case 11:
                    if (!isAdmin) {
                        System.out.println("Please input again!");
                        break;
                    }
                    new BatchRemoval(postTree, postQueue);
                    break;
                case 12:
                    if (!isAdmin) {
                        System.out.println("Please input again!");
                        break;
                    }
                    postQueue.view(postTree);
                    break;
                case 13:
                    if (!isAdmin) {
                        System.out.println("Please input again!");
                        break;
                    }
                    isOnVacation = !isOnVacation;
                    break;
                
                // need more options



                // break while loop
                case -1:
                    continueProgram = false;
                    break;

                // not match any above
                default:
                    System.out.println("Please input again!");
                    break;
            }
            // check if should end program
        } while (continueProgram);

        // free up reader
        sc.close();

        // check if there are still posts in queue and resolve them
        // stop executor
        postQueue.stopTasks();
        int remainingCount = postQueue.queueSize();
        if(remainingCount > 0){
            System.out.printf("Remaining %d post%s left; all are pushed into structure.", remainingCount, remainingCount>1?"s":"");
            // move task into tree
            postQueue.moveTask(postTree);
        } else {
            System.out.println("No tasks left.");
        }

        // save the tree
        try {
            saveFiles.saveTree(postTree, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n======== Thank you for using Confession Time ========");
        // exit program
        System.exit(0);
    }
}
