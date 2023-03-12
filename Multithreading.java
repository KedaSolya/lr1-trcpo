import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Multithreading {
    public static void main(String[] args) {
        int numThreads = 5;
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
                maxCountElem(arrR, numThreads);
            } else if (Objects.equals(choice1, "2")) {
                maxCountElemFile(arrR, numThreads);
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
                maxCountElem(arrF, numThreads);
            } else if (Objects.equals(choice2, "2")) {
                maxCountElemFile(Objects.requireNonNull(arrF), numThreads);
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
    public static void findFrequencyParallel(int[] arr, int[] fr, int numThreads){
        int length = arr.length;
        int chunkSize = length / numThreads;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads-1) ? length : (i+1) * chunkSize;
            threads[i] = new Thread(new FindFrequency(arr, fr, start, end));
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
    public static void maxCountElem(int[] arr, int numThreads){
        int[] fr = new int [arr.length];
        findFrequencyParallel(arr, fr, numThreads);
        int max = fr[0];
        for (int j : fr)
            if (j > max)
                max = j;
        System.out.print("\nElement/elements with the most frequency in array: ");
        for(int i = 0; i < fr.length; i++)
            if(fr[i] != -1 && fr[i] == max)
                System.out.print(arr[i] + " ");
    }
    public static void maxCountElemFile(int[] arr, int numThreads){
        try {
            FileWriter myWriter = new FileWriter("output");
            myWriter.write("Array -> ");
            for (int elem : arr) myWriter.write(elem + " ");
            int[] fr = new int [arr.length];
            findFrequencyParallel(arr, fr, numThreads);
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
