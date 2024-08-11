package com.tomtruyen.pomodoretimer.models

/**
 * Node for [LinkedList]
 *
 * @param data The data to be stored in the node
 * @param next The next node in the list
 */
data class Node<T>(val data: T, var next: Node<T>? = null)

/**
 * Linked List implementation
 *
 * @param head The first node in the list
 * @param current The current node in the list
 */
data class LinkedList<T>(
    private var head: Node<T>? = null,
    private var current: Node<T>? = null
) {
    /**
     * Adds a new node to the end of the list
     *
     * @param data The data to be added to the list
     */
    fun add(data: T) {
        val node = Node(data)
        
        // Set the head and current to the new node if this is the first node
        if(head == null) {
            head = node
            current = head
            return
        }
        
        var temp = current
        
        while(temp?.next != null) {
            temp = temp.next
        }
        
        // Add the new node to the end of the list
        temp?.next = node
    }
    
    /**
     * Reset the [LinkedList] to the first node
     *
     * @return The first node
     */
    fun reset(): Node<T>? {
        current = head
        
        return current
    }
    
    /**
     * Get the next node in the list
     *
     * @return The next node or null if there is no next node
     */
    fun next(): Node<T>? {
        current = current?.next
        
        return current
    }
    
    fun current() = current
}