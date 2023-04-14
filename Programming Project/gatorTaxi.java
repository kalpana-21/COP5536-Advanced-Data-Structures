import java.io.*;
import java.util.ArrayList;

/* gatorTaxi class has Insert, CancelRide,GetNextRide, print,UpdateTrip operations */
public class gatorTaxi {

    //    Filewriter object to write to the output_file
    static FileWriter fw;

    static {
        try {
            fw = new FileWriter("output_file.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //  HeapRide object initialization
    HeapRide heapRide;
    //  RBRide object initialization
    RBRide rbRide;
    //    Heap object initialization
    Heap heap;
    //    RBTree object initialization
    RBT tree;


/*   Constructor to initialize heap and tree datastructures */
    public gatorTaxi() throws IOException {
        tree = new RBT();
        heap = new Heap();

    }

/* Inserts a ride object in both Heap and RBtree*/
    public void insert(int rNo, int rCost, int tDur) throws IOException {
        //        First we search for the ride in the tree
        RBRide ride = tree.searchRide(rNo);
        //        if the ride is not present in the tree
        if(ride.rNo ==0)
        {
            //  Create nodes in both RBTree and Heap
            rbRide = tree.addRBNode(rNo,rCost,tDur);
            heapRide = heap.addHeapRide(rNo,rCost,tDur);
            // Link both nodes
            rbRide.link = heapRide;
            heapRide.link = rbRide;
            // Then we adjust both heap and tree
            tree.addToTree(rbRide);
            heap.addHeapify(heapRide);

        }
        else
        {
            // otherwise it's a duplicate ride
            fw.write("Duplicate RideNumber\n");
            fw.close();
            //removes extra line from the output file
            RandomAccessFile raf = new RandomAccessFile("output_file.txt", "rw");
            long len = raf.length();
            if (len > 0)
            {
                raf.setLength(len-1);
            }
            raf.close();
            //abort
            System.exit(0);
        }
    }

/*  Gives the Next available Ride
 */
    public void getNextRide() throws IOException {
        try{
            //  We delete the root from the heap
            HeapRide ride = heap.deleteMin();
            //  using the ride number, we delete the corresponding ride from the tree
            tree.deleteRide(ride.rNo);
            //  Then we print this ride
            printRide(ride.rNo,ride.rCost, ride.tDur);
        }
        catch (IllegalStateException e)
        {
            // If the heap and tree are empty, there are no active ride requests
            fw.write("No active ride requests\n");
        }
    }

/*    prints  (ride number, cost, duration)*/
    public void printRide(int rNo, int rCost, int tDur) throws IOException
    {
        fw.write("(" +rNo + "," + rCost + "," +tDur + ")\n");
    }

/* Prints the ride with the given ride number
*/
    public void print(int rNo) throws IOException
    {
        //   First we search for the ride, with the given ride number in RBTree
        RBRide RBRide = tree.searchRide(rNo);
        //   Then we print this ride
        printRide(RBRide.rNo, RBRide.rCost, RBRide.tDur);
    }


 /*   prints the rides with ride numbers in the given range
  */

    public void print(int rNo1, int rNo2) throws IOException
    {
        //  holds all rides with ride numbers between rideNumber1 and rideNumber2
       ArrayList<String> rides = tree.searchRide(rNo1,rNo2);
       int n = rides.size();
        //  if number of rides in the given range are more than zero
        if(n >0)
        {
            //  we write to the output file
            for(int i= 0; i< n-1; i++)
            {
                fw.write(rides.get(i) +",");
            }
            fw.write(rides.get(rides.size()-1) +"\n");
        }
        // otherwise we write (0,0,0)
        else
            fw.write("(0,0,0)\n");
    }


/* deletes the ride with a given ride number*/
    public void cancelRide(int rNo)
    {
        //  we search for the ride with the given ride number in the tree and find the corresponding node in the heap
        HeapRide ride = tree.searchRide(rNo).link;
        //  then we delete this ride from the tree
        tree.deleteRide(rNo);
        // if the ride is present in the heap, we delete it
        if(ride != null)
        {
            heap.arbitraryDelete(ride.pos);
        }

    }


/* updates a ride's trip duration, given its ride number */
    public void updateTrip(int rNo, int updatedTDur) throws IOException
    {
        //   We search for the ride with the given ride number in the rb tree
        RBRide rbNode = tree.searchRide(rNo);
        //   holds original trip duration
        int tDur = rbNode.tDur;
        //   if the updated trip duration is more than twice that of the original duration, we cancel this ride
        if(updatedTDur > (2 *tDur))
        {
            cancelRide(rNo);
        }
        /*   if the original duration is less than the new duration and new duration is less than twice that
        of the original duration, we cancel the existing ride and insert a ride with a penalty of 10 */
        else if(tDur < updatedTDur && updatedTDur < (2 *tDur))
        {
            cancelRide(rNo);
            insert(rNo,rbNode.rCost +10,updatedTDur);
        }
        /*   if the original duration is more, then we
        update its duration in both the heap and tree */
        else if(tDur > updatedTDur)
        {
            tree.searchRide(rNo).tDur = updatedTDur;
            heap.updateTrip(rbNode.link, updatedTDur);
        }

    }



    public static void main(String[] args) throws IOException
    {
        gatorTaxi gatorTaxi = new gatorTaxi();
        String input_file = args[0];
        BufferedReader br = new BufferedReader(new FileReader(input_file));
        String command = br.readLine();
        while(command != null)
        {
            //  if the command is Insert
            if(command.contains("Insert"))
            {
                String prunedCommand = command.substring(command.indexOf("(")+1, command.indexOf(")"));
                String[] nums = prunedCommand.split(",", 0);
                gatorTaxi.insert(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
            }
            // if the command is GetNextRide
            else if(command.contains("GetNextRide"))
            {
                gatorTaxi.getNextRide();
            }
            // if the command is Print
            else if(command.contains("Print"))
            {
                String prunedCommand = command.substring(command.indexOf("(")+1, command.indexOf(")"));
                String[] nums = prunedCommand.split(",", 0);
                if(nums.length == 1)
                {
                    gatorTaxi.print(Integer.parseInt(nums[0]));
                }
                else
                {
                    gatorTaxi.print(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]));
                }
            }
            // if the command is CancelRide
            else if(command.contains("CancelRide"))
            {
                String rideNumber = command.substring(command.indexOf("(")+1, command.indexOf(")"));
                gatorTaxi.cancelRide( Integer.parseInt(rideNumber) );
            }
            //  if the command is GetNextRide
            else if(command.contains("UpdateTrip"))
            {
                String prunedCommand = command.substring(command.indexOf("(")+1, command.indexOf(")"));
                String[] nums = prunedCommand.split(",", 0);
                gatorTaxi.updateTrip(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
            }

            command = br.readLine();
        }
        fw.close();
        //  to delete an extra line from the output file
        RandomAccessFile raf = new RandomAccessFile("output_file.txt", "rw");
        long len = raf.length();
        if (len > 0)
        {
            raf.setLength(len-1);
        }
        raf.close();

    }

}
