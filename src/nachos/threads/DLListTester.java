package nachos.threads;

import org.junit.Test;
import static org.junit.Assert.*;
//import nachos.threads.DLList;

public class DLListTester {

    @Test
    public void testPrepend() {
        DLList testList = new DLList();
        testList.prepend("Object1");
        assertEquals(1, testList.size());

        testList.prepend("Object2");
        assertEquals(2, testList.size());

        assertEquals("Object2", testList.removeHead());
        assertEquals("Object1", testList.removeHead());
    } 

    @Test
    public void testRemoveHead() {
        DLList testList = new DLList();
        testList.prepend("Object1");
        testList.prepend("Object2");

        assertEquals("Object2", testList.removeHead());
        assertEquals(1, testList.size());

        assertEquals("Object1", testList.removeHead());
        assertEquals(0, testList.size());

        assertEquals(null, testList.removeHead());
    } 

    @Test
    public void testIsEmpty() {
        DLList testList = new DLList();
        assertTrue(testList.isEmpty());
        testList.prepend("anObject");
        assertFalse(testList.isEmpty());
    } 

    @Test
    public void testSize() {
        DLList testList = new DLList();
        assertEquals(0, testList.size());

        testList.prepend("anObject");
        assertEquals(1, testList.size());

        testList.removeHead();
        assertEquals(0, testList.size());
    } 

    @Test
    public void testInsert() {
        DLList testList = new DLList();
        testList.insert("Object1", 1);
        assertEquals("([1,Object1])", testList.toString());
        assertEquals(1, testList.size());

        testList.insert("Object3", 3);
        assertEquals("([1,Object1] [3,Object3])", testList.toString());
        assertEquals(2, testList.size());

        testList.insert("Object0", 0);
        assertEquals("([0,Object0] [1,Object1] [3,Object3])", testList.toString());
        assertEquals(3, testList.size());

        testList.insert("Object2", 2);
        assertEquals("([0,Object0] [1,Object1] [2,Object2] [3,Object3])", testList.toString());
        assertEquals(4, testList.size());

    } 

    @Test
    public void testToString() {
        DLList testList = new DLList();
        testList.prepend("Object1");
        testList.prepend("Object2");
        testList.prepend("Object3");
        assertEquals("([-2,Object3] [-1,Object2] [0,Object1])", testList.toString());
    } 

    @Test
    public void testToStringReversed() {
        DLList testList = new DLList();
        testList.prepend("Object1");
        testList.prepend("Object2");
        testList.prepend("Object3");
        assertEquals("([0,Object1] [-1,Object2] [-2,Object3])", testList.reverseToString());
    } 

}
