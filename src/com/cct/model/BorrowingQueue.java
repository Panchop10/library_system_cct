package com.cct.model;

public class BorrowingQueue {
    private int size;
    private NodeQueue front;
    private NodeQueue rear;

    public BorrowingQueue(){
        this.size = 0;
        this.front = null;
        this.rear = null;
    }

    /**
     * Adds an entry to the back of the queue
     * @param newBorrowing new entry to add to the queue
     */
    public void enqueue(BorrowingModel newBorrowing){
        NodeQueue newNode = new NodeQueue(newBorrowing, null,null);
        // if the size is 0, add the new element
        if(size == 0){
            this.rear = newNode;
            this.front = newNode;
            size = 1;
        }
        // if the size > 0 then new node goes to the rear and its next node is the old rear.
        else{
            this.rear.setPrevious(newNode);
            newNode.setNext(this.rear);

            this.rear = newNode;
            this.size++;
        }
    }

    /**
     * Removes an entry from the front of the queue
     * @return BorrowingModel in the front.
     */
    public BorrowingModel dequeue(){
        // return null if the queue is empty
        if(size==0){
            return null;
        }

        // element that will be returned
        BorrowingModel toReturn = this.front.getBorrowing();

        // remove the element by assigning front to the previous element.
        this.front = this.front.getPrevious();
        size --;

        // if after removing the front element, the queue is empty, then rear points to null.
        if(size==0){
            this.rear = null;
        }

        return toReturn;
    }

    /**
     * Looks at the entry at the front of the queue without removing it
     * @return BorrowingModel at the front of the queue.
     */
    public BorrowingModel first(){
        if(size==0){
            return null;
        }

        return this.front.getBorrowing();
    }

    /**
     * Looks at the entry at the back of the queue without removing it
     * @return BorrowingModel at the rear of the queue.
     */
    public BorrowingModel last(){
        if(size==0){
            return null;
        }

        return this.rear.getBorrowing();
    }

    /**
     * Returns the number of items in the queue
     * @return integer with the numbers of items in the queue.
     */
    public int size(){
        return this.size;
    }

    /**
     * Returns a boolean indicating if the queue is empty
     * @return true is the queue is empty, otherwise it returns false.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public String toString() {
        String toReturn = "[ ";

        // pointer to the actual node
        NodeQueue tempQueue = this.rear;

        // iterate over the nodes of the queue.
        for(int i = 1; i <= size; i++) {
            toReturn += tempQueue.getBorrowing().getId() + " ";
            tempQueue = tempQueue.getNext();
        }
        // remove blank spaces
        toReturn = toReturn.trim();
        toReturn += "]";

        return toReturn;
    }
}
