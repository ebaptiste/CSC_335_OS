package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for
 * synchronization.
 *
 * <p>
 * You must implement this.
 *
 * @see	nachos.threads.Condition
 */
public class Condition2 {
    /**
     * Allocate a new condition variable.
     *
     * @param	conditionLock	the lock associated with this condition
     *				variable. The current thread must hold this
     *				lock whenever it uses <tt>sleep()</tt>,
     *				<tt>wake()</tt>, or <tt>wakeAll()</tt>.
     */
    public Condition2(Lock conditionLock) {
        this.conditionLock = conditionLock;
        this.urgentQueue = new SynchList();
        this.waiting = 0;
    }

    /**
     * Atomically release the associated lock and go to sleep on this condition
     * variable until another thread wakes it using <tt>wake()</tt>. The
     * current thread must hold the associated lock. The thread will
     * automatically reacquire the lock before <tt>sleep()</tt> returns.
     */
    public void sleep() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        this.waiting++;
        conditionLock.release(); // release lock
        Machine.interrupt().disable(); // disable interrupts
        this.urgentQueue.add(KThread.currentThread()); // add current thread to queue
        KThread.currentThread().sleep(); // block current thread
        Machine.interrupt().enable();// enable interrupts (when thread gets woken up)

        conditionLock.acquire(); // reacquire the lock
    }

    /**
     * Wake up at most one thread sleeping on this condition variable. The
     * current thread must hold the associated lock.
     */
    public void wake() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        Machine.interrupt().disable();
        if (this.waiting > 0) {
            this.waiting --;
            Object waitingThread = urgentQueue.removeFirst();
            ((KThread) waitingThread).ready();
        } 
        Machine.interrupt().enable();
    }

    /**
     * Wake up all threads sleeping on this condition variable. The current
     * thread must hold the associated lock.
     */
    public void wakeAll() {
	    Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        Machine.interrupt().disable();
        while (this.waiting > 0) {
            this.waiting --;
            Object waitingThread = urgentQueue.removeFirst();
            ((KThread) waitingThread).ready();
        } 
        Machine.interrupt().enable();
    }

    private Lock conditionLock;
    private SynchList urgentQueue; // queue for waiting threads
    private int waiting; // number of threads waiting on urgent queue
}
