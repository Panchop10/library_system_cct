package com.cct.model;

public class NodeQueue {
    private BorrowingModel borrowingModel;
    private NodeQueue previous;
    private NodeQueue next;

    public NodeQueue(BorrowingModel borrowingModel, NodeQueue previous, NodeQueue next){
        this.previous = previous;
        this.next = next;
        this.borrowingModel = borrowingModel;
    }

    public BorrowingModel getBorrowing() {
        return this.borrowingModel;
    }

    public NodeQueue getPrevious() {
        return previous;
    }

    public void setPrevious(NodeQueue previous) {
        this.previous = previous;
    }

    public NodeQueue getNext() {
        return next;
    }

    public void setNext(NodeQueue next) {
        this.next = next;
    }
}
