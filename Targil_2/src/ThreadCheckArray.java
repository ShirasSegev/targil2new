// The class implements Runnable, which allows it to be executed by a thread.
public class ThreadCheckArray implements Runnable {

    // Flag to indicate if a solution has been found.
    private boolean flag;
    
    // Array to store the result of which elements form the sum.
    private boolean[] winArray;
    
    // Shared data object, which holds the array and other necessary information.
    SharedData sd;
    
    // The array of integers we are working with.
    int[] array;
    
    // The target sum we are trying to reach.
    int b;

    // Constructor initializes shared data, array, and target sum.
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        
        // Synchronize to safely access shared data
        synchronized (sd) {
            array = sd.getArray();  // Get the array of integers
            b = sd.getB();          // Get the target sum
        }
        
        // Initialize the winArray to store whether each element contributes to the sum
        winArray = new boolean[array.length];
    }

    // Recursive method to check if we can form the target sum 'b' using elements of the array.
    void rec(int n, int b) {
        // Synchronize to check if the flag has been set by another thread (to stop further processing)
        synchronized (sd) {
            if (sd.getFlag()) {
                return;  // If flag is set, stop recursion
            }
        }

        // Base case: when only one element is left to check
        if (n == 1) {
            // Check if the remaining sum 'b' is achievable with the current element
            if (b == 0 || b == array[n - 1]) {
                flag = true;  // A solution is found
                synchronized (sd) {
                    sd.setFlag(true);  // Set the flag to stop other threads
                }
            }
            // If the current element contributes to the sum, mark it in winArray
            if (b == array[n - 1]) {
                winArray[n - 1] = true;
            }
            return;  // Return to the previous recursive call
        }
        
        // Recursive step: try including the current element in the sum
        rec(n - 1, b - array[n - 1]);
        
        // If a solution is found, mark the current element as part of the sum
        if (flag) {
            winArray[n - 1] = true;
        }

        // Synchronize to check if the flag has been set by another thread (to stop further processing)
        synchronized (sd) {
            if (sd.getFlag()) {
                return;  // If flag is set, stop recursion
            }
        }

        // Recursive step: try excluding the current element from the sum
        rec(n - 1, b);
    }

    // The method that is executed when the thread starts
    public void run() {
        // If the array has more than one element, check for a solution in two threads
        if (array.length != 1) {
            if (Thread.currentThread().getName().equals("thread1")) {
                // First thread starts with the reduced target sum (b - array[n-1])
                rec(array.length - 1, b - array[array.length - 1]);
            } else {
                // Second thread starts with the original target sum (b)
                rec(array.length - 1, b);
            }
        }

        // Base case: if the array has only one element, check if it matches the target sum
        if (array.length == 1) {
            if (b == array[0] && !flag) {
                winArray[0] = true;  // Mark this element as part of the sum
                flag = true;          // A solution is found
                synchronized (sd) {
                    sd.setFlag(true);  // Set the flag to stop other threads
                }
            }
        }

        // If a solution is found, update the shared data with the result
        if (flag) {
            if (Thread.currentThread().getName().equals("thread1")) {
                // Mark the last element as part of the sum for thread1
                winArray[array.length - 1] = true;
            }
            // Synchronize to safely update the shared winArray
            synchronized (sd) {
                sd.setWinArray(winArray);  // Update the winArray in shared data
            }
        }
    }
}
