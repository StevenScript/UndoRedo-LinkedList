package com.undoredo.DoubleLinkedList;

/**
 * Implememt an application that support undo/redo functionality. Use a linked list to maintain a sequence of states.\
 * Each state change is stored as a node in the list, allowoing for easy navigation
 * 1<>2<>3<>4<>5
 */

public class UndoRedoManager<T> {

    // Private inner class representing a state node in the undo/redo chain.
    private class Node {
        private T state;
        private Node prev;
        private Node next;

        // Constructs a new Node with the specified state.
        private Node(T state) {
            this.state = state;
        }

    }

    // Pointer to the current state in the list.
    private Node currentState;

    // Performs an undo operation, moving the current state pointer to the previous state.
    public T undo() {
        if (currentState != null && currentState.prev != null) {
            currentState = currentState.prev;
            return currentState.state;
        }
        System.out.println("No previous state to undo.");
        return currentState != null ? currentState.state : null;
    }

    /**
     * Performs an operation by adding a new state to the chain.
     * Any redo states (future states) are discarded.
     */
    public void addState(T newState) {
        Node newNode = new Node(newState);
        if (currentState == null) {
            // This becomes the first state.
            currentState = newNode;
        } else {
            // Discard any redo states.
            currentState.next = null;
            // Link the new node after the current state.
            newNode.prev = currentState;
            currentState.next = newNode;
            // Update the current state pointer.
            currentState = newNode;
        }
    }

    // Performs a redo operation, moving the current state pointer to the next state
    public T redo(){
        if (currentState != null && currentState.next != null) {
            currentState = currentState.next;
            return currentState.state;
        }
        System.out.println("No next state to redo.");
        return currentState != null ? currentState.state : null;
    }

    /**
     * Main method to test the Undo/Redo functionality.
     */
    public static void main(String[] args) {
        // Create an UndoRedoManager for String states.
        UndoRedoManager<String> manager = new UndoRedoManager<>();

        // Add states 1 through 5.
        manager.addState("1");
        manager.addState("2");
        manager.addState("3");
        manager.addState("4");
        manager.addState("5");

        // Print current state (5)
        System.out.println("Current state: " + manager.currentState.state);

        // Undo twice (expected: from 5 -> 4, then 3)
        System.out.println("Undo: " + manager.undo()); // Expected "4"
        System.out.println("Undo: " + manager.undo()); // Expected "3"

        // Redo once (move from 3 -> 4)
        System.out.println("Redo: " + manager.redo()); // Expected "4"

        // Add a 6 state (should discard any redo)
        manager.addState("6");
        System.out.println("After adding new state, current: " + manager.currentState.state); // Expected "6"

        // Attempt redo
        System.out.println("Redo: " + manager.redo()); // Expected message and state "6"
    }
}

