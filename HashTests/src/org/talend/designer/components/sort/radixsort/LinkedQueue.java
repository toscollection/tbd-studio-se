package org.talend.designer.components.sort.radixsort;

public class LinkedQueue extends Object {

    private intNode start;

    private intNode end;

    private int length; // number of elements

    // Constructor
    LinkedQueue() {
        start = null;
        end = null;
        length = 0;
    }

    // Add an item to the end of the queue
    public void enqueue(int num) {
        length++; // increment the number of objects

        // Create new node with given info
        intNode temp = new intNode(num);

        // If this is the first time, then do this
        if (start == null) {
            start = temp; // make start = to this
            end = start; // start and end point to the same node
        } else {
            // make the new node's next pointer
            // point to the end
            end.next = temp;
            // make start point to the new object
            end = temp;
        }
        temp = null;
    }

    // Dequeue -- returns the integer in the intNode
    // From the front of the queue
    // Assumes that the queue is not empty
    public int dequeue() {
        length--;

        int temp = start.value;
        intNode tempNode;

        tempNode = start;

        start = start.next; // let start point to the next node

        tempNode = null; // clear the first object

        return temp;
    }

    // returns true if its empty
    public boolean isEmpty() {
        return (length == 0);
    }

}
