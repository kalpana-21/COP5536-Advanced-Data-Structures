import java.util.ArrayList;

/* Class for Heap that has operations to insert, deleteMin, delete, update operations*/
public class Heap {
/*   Datastructure for heap*/
    private ArrayList<HeapRide> heap;

/*   Constructor to initialize a heap */
    public Heap()
    {
        heap = new ArrayList<>();
    }


/*    Gives the position of parent
node in the heap datastructure
*/
    public int parentPos(int pos){
        return (pos-1)/2;
    }

/*    Gives the position of left child
node in the heap datastructure
 */
    public int leftPos(int pos){
        return (2*pos + 1);
    }

/* Gives the position of right child
node in the heap datastructure
*/
    public int rightPos(int pos){
        return (2*pos + 2);
    }

/* Swaps rides with positions ride1
and ride2 in the heap
*/
    public void swapRides(int ride1, int ride2) {
        HeapRide swap1 = heap.get(ride1);
        HeapRide swap2 = heap.get(ride2);
        swap1.pos = ride2;
        swap2.pos = ride1;
        heap.set(ride1, swap2);
        heap.set(ride2, swap1);
    }


/* Returns a HeapRide object which is
used for inserting into the heap datastructure
 */
    public HeapRide addHeapRide(int rNo, int rCost, int tDur)
    {
        return new HeapRide(rNo,rCost,tDur, heap.size());
    }


/* Adds a ride to the datastructure
and performs heapify operation
*/
    public void addHeapify(HeapRide ride)
    {
        //     Adds ride to the heap datastructure at the end.
        heap.add(ride);
        //        heapifyTop performs and adjusts the heap until the meanheap property satisfies
        heapfyTop(heap.size() - 1);
    }


/*  performs and adjusts the heap from the position 'pos'
until the minheap property satisfies
*/
    public void heapfyTop(int pos)
    {
        //        We check till the root of the heap
        while (pos > 0)
        {
            //            Finds the position of the parent node of the current node
            int parentPos = parentPos(pos);
            //            if the cost of parent node is greater, we swap the nodes and repeat the process
            if (heap.get(parentPos).rCost > heap.get(pos).rCost)
            {
            //                swaps current and parent rides
                swapRides(pos, parentPos);
            //                Now parent is the current node
                pos = parentPos;
            }
/*           if the cost of parent and the current node are the same and parent's trip duration is more than that of
 the current node,we swap them and repeat the process
 */
            else if(heap.get(pos).rCost == heap.get(parentPos).rCost && heap.get(parentPos).tDur > heap.get(pos).tDur)
            {
                swapRides(pos, parentPos);
                pos = parentPos;
            }
            //            otherwise we stop heapfying
            else
                break;
        }
    }

/* Deletes and returns the root
of the  heap
*/
    public HeapRide deleteMin() {
        //        if the heap is empty, we cannot delete
        if (heap.size() == 0 || heap.isEmpty())
        {
            throw new IllegalStateException("Cannot delete");
        }
        //        Otherwise swap the root node with the last node in the heap
        swapRides(0,heap.size()-1);
        HeapRide del = null;
        //        Now the tail node contains the min node, we remove it from the heap and return it
        del = heap.remove(heap.size()-1);
        //        if heap is not empty, we perform heapfy bottom operation until it satisfies the heap property
        if(heap.size() != 0 && !heap.isEmpty())
            heapfyBot(0);
        return del;
    }

/* Deletes the ride from the heap in the
given position
 */
    public void arbitraryDelete(int pos) {
        //        if the heap is empty, we cannot delete
        if (heap.size()==0 || heap.isEmpty())
        {
            throw new IllegalStateException("cannot delete");
        }
        //        otherwise, we swap the node in the given position with the last node in the heap
        swapRides(pos,heap.size()-1);
        //       then we remove the last node from the heap
        heap.remove(heap.size()-1);
        //        if the heap is not empty, then we perform heapfy bottom operation till the heap property satisfies
        if(heap.size() != 0 && !heap.isEmpty())
            heapfyBot(pos);
    }


/*    Performs Heapfy Bottom operation, and
 adjust the heap from the current position to the last node
until the heap property is satisfied

 */
    public void heapfyBot(int pos) {
        int n = heap.size();
        //        we perfrom this till we reach the last node in the heap
        while (pos < n)
        {
            //            position of the left child in the heap datastructure
            int leftPos = leftPos(pos);
            //            position of the left child in the heap datastructure
            int rightPos = rightPos(pos);
            //            if leftchild position is greater than the size of heap we stop the process
            if (n <= leftPos)
                break;
            //            we initially consider the left node to have minimum ridecost
            int minPos = leftPos;
            //            if right node has less cost than the left one, then assign rightPos to minPos
            if (rightPos < n && heap.get(leftPos).rCost > heap.get(rightPos).rCost)
            {
                minPos = rightPos;
            }
            //           both left and right nodes have same cost but right one has less duration, then assign rightPos to minPos
            else if(rightPos < n && heap.get(rightPos).rCost == heap.get(leftPos).rCost && heap.get(leftPos).tDur > heap.get(rightPos).tDur){
                minPos = rightPos;
            }
            //            if the minchild node's ride cost is less than that of the current node , we swap and continue the process.
            if (heap.get(minPos).rCost < heap.get(pos).rCost)
            {
                swapRides(pos, minPos);
                pos = minPos;
            }
            /*            if the minchild node's ride cost is equal to the
            current node's ride cost and minchild' s trip duration is less
            than the duration of the current node, we swap and continue the process */
            else if(heap.get(pos).rCost == heap.get(minPos).rCost && heap.get(pos).tDur > heap.get(minPos).tDur)
            {
                swapRides(pos, minPos);
                pos = minPos;
            }
            //            otherwise we stop this process
            else
                break;
        }
    }


/* updates the duration of the trip with new duration*/
    public void updateTrip(HeapRide ride, int newTrDur) {
        //        assigns new duration to the ride
        ride.tDur = newTrDur;
        //        since the duration has been changed, we need to check with the parent nodes and adjust the heap
        heapfyTop(ride.pos);
    }

}
