/**
 * Manages memory allocation with a list of allocated and free memory blocks.
 * Methods "malloc" and "free" allocate and recycle memory blocks, respectively.
 */
public class MemorySpace {
    
    private LinkedList allocatedList; // List of allocated memory blocks
    private LinkedList freeList;     // List of free memory blocks

    /**
     * Initializes memory space with a single free block covering the full size.
     * 
     * @param maxSize Total size of the managed memory.
     */
    public MemorySpace(int maxSize) {
        allocatedList = new LinkedList();
        freeList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

    /**
     * Allocates a memory block of the requested size.
     * 
     * @param length Size of the block to allocate.
     * @return Base address of the allocated block, or -1 if allocation fails.
     */
    public int malloc(int length) {      
        Node current = freeList.getFirst();
        while (current != null && current.block.length < length) {
            current = current.next;
        }

        if (current == null) {
            return -1; 
        }

        MemoryBlock allocatedBlock = new MemoryBlock(current.block.baseAddress, length);
        allocatedList.addLast(allocatedBlock);
        
        if (current.block.length == length) {
            freeList.remove(current);
        } else {
            current.block.baseAddress += length;
            current.block.length -= length;
        }
        
        return allocatedBlock.baseAddress;
    }

    /**
     * Frees a memory block with the specified base address.
     * 
     * @param address Base address of the block to free.
     */
    public void free(int address) {
        Node current = allocatedList.getFirst();
        
        while (current != null && current.block.baseAddress != address) {
            current = current.next;
        }

        if (current == null) {
            throw new IllegalArgumentException("Memory block not found");
        }

        allocatedList.remove(current.block);
        freeList.addLast(current.block);
    }
    
    /**
     * Returns a textual representation of free and allocated memory blocks.
     */
    @Override
    public String toString() {
        return "Free List: " + freeList.toString() + "\nAllocated List: " + allocatedList.toString();
    }
    
    /**
     * Merges adjacent free memory blocks to optimize memory usage.
     */
    public void defrag() {
        for (int i = 0; i < freeList.getSize(); i++) {
            MemoryBlock block = freeList.getBlock(i);
            int nextAddress = block.baseAddress + block.length;
            Node current = freeList.getFirst();
            
            while (current != null) {
                MemoryBlock nextBlock = current.block;
                if (nextBlock.baseAddress == nextAddress) {
                    block.length += nextBlock.length;
                    freeList.remove(current);
                    defrag();
                    break;
                }
                current = current.next;
            }
        }
    }
}
