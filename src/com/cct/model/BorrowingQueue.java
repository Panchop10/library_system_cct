package com.cct.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BorrowingQueue {
    private BorrowingModel borrowingModel;
    private int size;
    private NodeQueue front;
    private NodeQueue rear;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public BorrowingQueue(BorrowingModel borrowingModel){
        this.borrowingModel = borrowingModel;
        this.size = 0;
        this.front = null;
        this.rear = null;
    }

    /**
     * Create a new borrowing object and add it to the database, then enqueue it to the queue.
     * @param book_id book that is going to be added.
     * @param reader_id reader who borrowed the book.
     */
    public void enqueue(int book_id, int reader_id){
        BorrowingModel newBorrowing = borrowingModel.create(
                book_id,
                reader_id,
                null,
                null,
                "queued"
        );

        enqueueExisting(newBorrowing);
    }

    /**
     * Adds an entry to the back of the queue.
     * This method is used to load borrowing to a book, it won't create a new borrowing in the database.
     * @param newBorrowing new entry to add to the queue
     */
    public void enqueueExisting(Model newBorrowing){
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
    public Model dequeue(){
        // return null if the queue is empty
        if(size==0){
            return null;
        }

        // update status of the borrowing in the database.
        Date date = new Date();
        String[] filters = {"id: " + this.front.getBorrowing().getId()};
        String[] update = {"status: active", "date_borrowed: "+formatter.format(date)};

        // element that will be returned - execute update
        Model toReturn = borrowingModel.update(filters, update).get(0);

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
    public Model first(){
        if(size==0){
            return null;
        }

        return this.front.getBorrowing();
    }

    /**
     * Looks at the entry at the back of the queue without removing it
     * @return BorrowingModel at the rear of the queue.
     */
    public Model last(){
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

        toReturn += "]";

        return toReturn;
    }
}
