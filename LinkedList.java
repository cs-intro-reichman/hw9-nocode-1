/**
 * Represents a linked list of Nodes.
 */
public class LinkedList {
    
    private Node first; // Reference to the first node
    private Node last;  // Reference to the last node
    private int size;   // Count of nodes in the list

    /**
     * Constructs an empty list.
     */
    public LinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }

    public Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public void add(int index, MemoryBlock block) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        if (index == 0) {
            addFirst(block);
        } else if (index == size) {
            addLast(block);
        } else {
            Node prev = getNode(index - 1);
            Node newNode = new Node(block);
            newNode.next = prev.next;
            prev.next = newNode;
            size++;
        }
    }

    public void addLast(MemoryBlock block) {
        Node newNode = new Node(block);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    public void addFirst(MemoryBlock block) {
        Node newNode = new Node(block);
        if (size == 0) {
            first = last = newNode;
        } else {
            newNode.next = first;
            first = newNode;
        }
        size++;
    }

    public MemoryBlock getBlock(int index) {
        return getNode(index).block;
    }

    public int indexOf(MemoryBlock block) {
        Node current = first;
        for (int i = 0; i < size; i++) {
            if (current.block.equals(block)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    public void remove(Node node) {
        if (node == first) {
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node current = first;
            while (current.next != node) {
                current = current.next;
            }
            current.next = node.next;
            if (node == last) {
                last = current;
            }
        }
        size--;
    }

    public void remove(int index) {
        remove(getNode(index));
    }

    public void remove(MemoryBlock block) {
        remove(getNode(indexOf(block)));
    }

    public ListIterator iterator() {
        return new ListIterator(first);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        ListIterator itr = iterator();
        while (itr.hasNext()) {
            str.append("(").append(itr.current.block.baseAddress)
               .append(" , ").append(itr.current.block.length).append(") ");
            itr.next();
        }
        return str.toString();
    }
}
