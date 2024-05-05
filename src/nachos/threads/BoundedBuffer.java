package nachos.threads;
import java.util.Arrays;

public class BoundedBuffer {

    private Lock lock;      // class lock object
    private char[] buffer;  // buffer contents
    private int count;      // number of items in the buffer
    private int n;          // max buffer size
    private int nextIn, nextOut; // indexes denoting where the next char should be input and output
    private Condition2 emptySlot, fullSlot; // full and empty slot Condition2s

    // non-default constructor with a fixed size
    public BoundedBuffer(int maxsize) {
        lock = new Lock();
        buffer = new char[maxsize];
        count = nextIn = nextOut = 0;
        n = maxsize;
        emptySlot = new Condition2(lock);
        fullSlot = new Condition2(lock);

    }

    // Read a character from the buffer, blocking until there is a char 
    // in the buffer to satisfy the request. Return the char read. 
    public char read() {
        lock.acquire();
        while (count <= 0) {
            fullSlot.sleep();
        }

        char c = buffer[nextOut];
        nextOut = (nextOut + 1) % n;
        count--;
        emptySlot.wake();
        lock.release();

        return c;

    }


    // Write the given character c into the buffer, blocking until 
    // enough space is available to satisfy the request.
    public void write(char c) {
        lock.acquire();
        while (count == n) {
            emptySlot.sleep();
        }

        buffer[nextIn] = c;

        KThread.yieldIfShould(0);

        nextIn = (nextIn + 1) % n;
        count++;
        fullSlot.wake();

        lock.release();

    }

    // Prints the contents of the buffer; for debugging only
    public void print() {
        lock.acquire();
        System.out.println(Arrays.toString(buffer));
        lock.release();

    }

}
