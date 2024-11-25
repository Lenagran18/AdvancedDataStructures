package SplayTrees;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SplayTreeProgram {
    private SplayTree tree = new SplayTree();

    public void readData(String fileName) {

        try (Scanner inputStream = new Scanner(new File(fileName))) {
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();

                int number = Integer.parseInt(line.trim());
                tree.insertOrdinary(number);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }
        tree.displayTree(tree.root, 0);
    }

    public void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Interactive mode started. Enter commands: ");
        while (true) {
            System.out.println("Command: ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ");

            if (parts.length < 2) {
                continue;
            }

            char command = parts[0].charAt(0);
            int key = Integer.parseInt(parts[1]);

            switch (command) {
                case 'S':
                    tree.root = tree.splay(tree.root, key);
                    System.out.println("Splay is done");
                    System.out.println();
                    tree.displayTree(tree.root, 0);
                    break;
                case 'F':
                    tree.find(key);
                    tree.displayTree(tree.root, 0);
                    break;
                case 'I':
                    tree.insert(key);
                    tree.displayTree(tree.root, 0);
                    break;
                case 'D':
                    tree.delete(key);
                    tree.displayTree(tree.root, 0);
                    break;
                default:
                    System.out.println("Unknown command. Use S, F, I, or D.");
                    continue;
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        SplayTreeProgram program = new SplayTreeProgram();
        String fileName = "/Users/lenagran/Desktop/AdvancedDataStructures/SplayTrees/in.dat";
        program.readData(fileName);
        program.interactiveMode();
    }
}