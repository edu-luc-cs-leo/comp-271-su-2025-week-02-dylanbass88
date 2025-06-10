import java.util.Arrays; // only for Arrays.toString() printing

/**
 * Simple object implemention of the minimum-heap
 */
public class MinHeap {

    /** Underlying array */
    private int[] underlying;

    /** Utilization counter for the array */
    private int usage;

    /** Default array size constant */
    private static final int DEFAULT_SIZE = 4;

    public MinHeap() {
        this(DEFAULT_SIZE);
    }

    public MinHeap(int size) {
        this.underlying = new int[DEFAULT_SIZE];
        if (size > 0) {
            this.underlying = new int[size];
        }
        this.usage = 0;
    }

    public int getSize() {
        return this.underlying.length;
    }

    public int getUsage() {
        return this.usage;
    }

    private boolean index_is_valid(int i) {
        return i >= 0 && i < this.underlying.length;
    }

    private int leftChild(int parent) {
        int child = -1;
        if (index_is_valid(parent)) {
            child = 2 * parent + 1;
        }
        return child;
    }

    private int rightChild(int parent) {
        int child = -1;
        if (index_is_valid(parent)) {
            child = 2 * parent + 2;
        }
        return child;
    }

    private int parent(int child) {
        int parent = -1;
        if (index_is_valid(child)) {
            parent = (child - 1) / 2;
        }
        return parent;
    }

    public int getSmallest() {
        int smallest_value = this.underlying[0];
        int[] temporary = new int[this.underlying.length - 1];
        temporary[0] = this.underlying[this.usage - 1];
        for (int i = 1; i < temporary.length; i++) {
            temporary[i] = this.underlying[i];
        }
        this.underlying = temporary;
        this.usage -= 1;
        this.heapifyDown();
        return smallest_value;
    }

    public void add(int value) {
        if (this.usage == this.underlying.length) {
            int[] temporary = new int[this.underlying.length + 1];
            for (int i = 0; i < this.underlying.length; i++) {
                temporary[i] = this.underlying[i];
            }
            this.underlying = temporary;
        }
        this.underlying[this.usage] = value;
        this.usage += 1;
        this.heapifyUp();
    }

    private void swap(int parent, int child) {
        int temp = this.underlying[parent];
        this.underlying[parent] = this.underlying[child];
        this.underlying[child] = temp;
    }

    // ✅ heapifyUp method — this is the assignment
    private void heapifyUp() {
        int child = this.usage - 1; // Start with the last added element
        int parent = parent(child);

        // Bubble up the new value until heap rule is satisfied
        while (child > 0 && this.underlying[child] < this.underlying[parent]) {
            swap(parent, child); // Swap child with parent if out of order
            child = parent;
            parent = parent(child); // Recalculate new parent
        }
    }

    private void heapifyDown() {
        int parent = 0;
        int left, right, smallest;
        boolean minHeapViolated = true;

        while (minHeapViolated) {
            left = leftChild(parent);
            right = rightChild(parent);
            smallest = parent;

            if (left < this.usage && this.underlying[left] < this.underlying[smallest]) {
                smallest = left;
            }

            if (right < this.usage && this.underlying[right] < this.underlying[smallest]) {
                smallest = right;
            }

            if (smallest != parent) {
                swap(parent, smallest);
                parent = smallest;
            } else {
                minHeapViolated = false;
            }
        }
    }
}
