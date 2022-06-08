package main;

import java.util.Scanner;

import Functions.viewPost;

public class homepage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1: run test code to make a new tree");
        System.out.println("2: read the tree from csv file");
        System.out.print("Input option: ");
        int option = sc.nextInt();
        switch(option){
            case 1: new viewPost().display(); break;
            case 2: new viewPost().readFiles(); break;
            default: System.exit(0);
        }
        sc.close();
    }
}
