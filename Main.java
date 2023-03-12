import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Read array randomly(1) or from a file(2)? - ");
        String choice = sc.nextLine();
        if (Objects.equals(choice, "1")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Write result in console(1) or to file(2) - ");
            String choice1 = scanner.nextLine();
            int[] arrR = new int[90000];
            for (int i = 0; i < arrR.length; i++) {
                arrR[i] = (int) (Math.random() * 15) + 1;
            }
            long startTime = System.currentTimeMillis();
            if (Objects.equals(choice1, "1")){
                System.out.print("\nRandomly made array -> ");
                for (int elem : arrR) {
                    System.out.print(elem + " ");
                }
                maxCountElem(arrR);
            } else if (Objects.equals(choice1, "2")) {
                maxCountElemFile(arrR);
            } else {
                System.out.println("Wrong input!");
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("\nTask completion time: " + elapsedTime + " milliseconds");
        } else if (Objects.equals(choice, "2")) {
            int[] arrF = readFile("input");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Write result in console(1) or to file(2) - ");
            String choice2 = scanner.nextLine();
            long startTime = System.currentTimeMillis();
            if (Objects.equals(choice2, "1")){
                System.out.print("\nArray from file -> ");
                for (int elem : Objects.requireNonNull(arrF)) {
                    System.out.print(elem + " ");
                }
                maxCountElem(arrF);
            } else if (Objects.equals(choice2, "2")) {
                maxCountElemFile(Objects.requireNonNull(arrF));
            } else {
                System.out.println("Wrong input!");
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("\nTask completion time: " + elapsedTime + " milliseconds");
        } else {
            System.out.println("Wrong input!");
        }
    }
    public static void findFrequency(int[] arr, int[] fr){
        for(int i = 0; i < arr.length; i++){
            int count = 1;
            for(int j = i+1; j < arr.length; j++){
                if(arr[i] == arr[j]){
                    count++;
                    fr[j] = -1;
                }
            }
            if(fr[i] != -1)
                fr[i] = count;
        }
    }
    public static void maxCountElem(int[] arr){
        int[] fr = new int [arr.length];
        findFrequency(arr, fr);
        int max = fr[0];
        for (int j : fr)
            if (j > max)
                max = j;
        System.out.print("\nElement/elements with the most frequency in array: ");
        for(int i = 0; i < fr.length; i++)
            if(fr[i] != -1 && fr[i] == max)
                System.out.print(arr[i] + " ");
    }
    public static void maxCountElemFile(int[] arr){
        try {
            FileWriter myWriter = new FileWriter("output");
            myWriter.write("Array -> ");
            for (int elem : arr) myWriter.write(elem + " ");
            int[] fr = new int [arr.length];
            findFrequency(arr, fr);
            int max = fr[0];
            for (int j : fr)
                if (j > max)
                    max = j;
            myWriter.write("\nElement/elements with the most frequency in array: ");
            for(int i = 0; i < fr.length; i++)
                if(fr[i] != -1 && fr[i] == max)
                    myWriter.write(arr[i] + " ");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static int[] readFile(String file){
        try{
            File f = new File(file);
            Scanner s = new Scanner(f);
            int ctr = 0;
            while (s.hasNextInt()){
                ctr++;
                s.nextInt();
            }
            int[] array = new int[ctr];
            Scanner s1 = new Scanner(f);
            for (int i = 0; i < array.length; i++)
                array[i] = s1.nextInt();
            return array;
        }
        catch (Exception e){
            return null;
        }
    }
}