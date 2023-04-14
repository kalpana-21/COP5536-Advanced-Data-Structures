
/* class for Heap Nodes */
public class HeapRide {

    //    variable for ride number
    int rNo;
    //    variable for ride cost
    int rCost;
    //    variable for trip duration
    int tDur;
    //    variable for storing the position of the node in the heap
    int pos;

    //    A pointer to link HeapNode with the corresponding RBTNode
    RBRide link;

    //    constructor to initialize HeapRide objects
    public HeapRide(int rNo, int rCost, int tDur, int pos){
        this.link = null;
        this.pos = pos;
        this.rNo = rNo;
        this.rCost = rCost;
        this.tDur = tDur;
    }
}
