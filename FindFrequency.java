public class FindFrequency implements Runnable {
    private final int[] arr;
    private final int[] fr;
    private final int start;
    private final int end;

    public FindFrequency(int[] arr, int[] fr, int start, int end) {
        this.arr = arr;
        this.fr = fr;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for(int i = start; i < end; i++){
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
}
