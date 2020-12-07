package com.cct.model;

class NodeQueue {
    private Model borrowingModel;
    private NodeQueue previous;
    private NodeQueue next;

    NodeQueue(Model borrowingModel, NodeQueue previous, NodeQueue next){
        this.previous = previous;
        this.next = next;
        this.borrowingModel = borrowingModel;
    }

    Model getBorrowing() {
        return this.borrowingModel;
    }

    NodeQueue getPrevious() {
        return previous;
    }

    void setPrevious(NodeQueue previous) {
        this.previous = previous;
    }

    NodeQueue getNext() {
        return next;
    }

    void setNext(NodeQueue next) {
        this.next = next;
    }
}
