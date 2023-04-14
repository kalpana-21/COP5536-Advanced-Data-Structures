import java.util.ArrayList;

/* class for Red Black tree that has operations insert, delete, search*/
public class RBT {
    private RBRide dummyBlack;

    //    root of the RBT
    private RBRide root;

/*Constructor to initialize , we always initialize all the variables to zero and null*/
    public RBT()
    {
        dummyBlack = new RBRide();
        dummyBlack.par = null;
        dummyBlack.col = 0;
        dummyBlack.rNo = 0;
        dummyBlack.rCost = 0;
        dummyBlack.tDur = 0;
        dummyBlack.left = null;
        dummyBlack.right = null;
        root = dummyBlack;
    }

/* Creates and returns an RBT ride node */
    public RBRide addRBNode(int rNo, int rCost, int tDur)
    {
        RBRide RBRide = new RBRide();
        RBRide.par = null;
        // we always insert a red node
        RBRide.col = 1;
        RBRide.rNo = rNo;
        RBRide.rCost = rCost;
        RBRide.tDur = tDur;
        RBRide.left = dummyBlack;
        RBRide.right = dummyBlack;
        return RBRide;
    }

/* adds a node to the tree*/
    public void addToTree(RBRide ride)
    {

        RBRide temp = this.root;
        // stores the parent of the current node
        RBRide parent = null;
        //  we iterate till we reach the external node
        while (temp != dummyBlack)
        {
            parent = temp;
        // if the ride number is less than the parent's ride number, we traverse the left subtree
            if (ride.rNo < temp.rNo)
            {
                temp = temp.left;
            }
        // otherwise, we ytraverse the right subtree
            else
            {
                temp = temp.right;
            }
        }
        // once the parent of the ride inserted, is found out, we set the parent to the ride
        ride.par = parent;

        // if parent is null, our current ride is root
        if (parent == null)
        {
            root = ride;
        }
        // if ride's ride number is less than that of its parent, we set the current ride as left child of parent
        else if (ride.rNo < parent.rNo)
        {
            parent.left = ride;
        }
        //  otherwise we set it as the right child
        else
        {
            parent.right = ride;
        }
        //if it's a root node, we set its colour to black
        if (ride.par == null)
        {
            ride.col = 0;
            return ;
        }
        //if ride is a direct child of root, we do not need to make changes
        if (ride.par.par == null)
        {
            return ;
        }

        insertXYz(ride);
    }


 /* Rebalances post insertion*/
    public void insertXYz(RBRide ride)
    {
        RBRide temp;
        //Iterates till there are no 2 consecutive red nodes
        while (ride.par.col == 1)
        {
            //if ride's parent is in the right subtree
            if (ride.par == ride.par.par.right)
            {
                //set temp to parent's left sibling
                temp = ride.par.par.left;
                //if temp's red,then we flip the colors
                if (temp.col == 1)
                {
                    temp.col = 0;
                    ride.par.col = 0;
                    ride.par.par.col = 1;
                    ride = ride.par.par;
                }
                //if it's a black node
                else
                {
                    //if ride's in left subtree,perform right rotation
                    if (ride == ride.par.left)
                    {
                        ride = ride.par;
                        lRotation(ride);
                    }
                    //set parent's color to black
                    ride.par.col = 0;
                    //set grand parent's color to red
                    ride.par.par.col = 1;
                    //performs right rotation
                    rRotation(ride.par.par);
                }
            }
            //if ride's parent is in the right subtree
            else
            {
                //set temp to parent's right sibling
                temp = ride.par.par.right;
                //if the color of temp is red, we flip the colors
                if (temp.col == 1)
                {
                    temp.col = 0;
                    ride.par.col = 0;
                    ride.par.par.col = 1;
                    ride = ride.par.par;
                }
                //if temp is a black node
                else
                {
                    //if ride's in the right subtree, we perform right rotation
                    if (ride == ride.par.right)
                    {
                        ride = ride.par;
                        rRotation(ride);
                    }
                    //set parentnode to black
                    ride.par.col = 0;
                    //set grand parent node to red
                    ride.par.par.col = 1;
                    //performs right rotation
                    lRotation(ride.par.par);
                }
            }
            //we perform this until we reach the root and there are two consecutive red nodes
            if (ride == root)
            {
                break;
            }
        }

        //set the color of the root to black
        root.col = 0;
    }


/*  searches for a ride in the tree  */
    public RBRide searchRide(int rNo)
    {
        // searches in the tree using the root
        return searchRBT(this.root, rNo);
    }

/*     Searches the tree using ride number */
    public RBRide searchRBT(RBRide ride, int rNo)
    {
        //        base condition, if the object's ride number is equal to the given ride number, we will return the ride object
        if ( rNo == ride.rNo || ride == dummyBlack)
        {
            return ride;
        }
        //if the given ride Number is less than ride object's ride number, traverse the left subtree
        if (rNo < ride.rNo) {
            return searchRBT(ride.left, rNo);
        }
        //        otherwise traverse the right subtree
        return searchRBT(ride.right, rNo);
    }


/*Searches for a ride in the given range */
    public ArrayList<String> searchRide(int rNo1, int rNo2)
    {
        ArrayList<String> ridesInTheRange = new ArrayList<>();
        return searchRange(this.root, rNo1, rNo2,ridesInTheRange);
    }

/* searches and returns a list of rides in the given range
*/
    public ArrayList<String> searchRange(RBRide ride, int rNo1, int rNo2, ArrayList<String> listOfRides)
    {
        //  if ride is an external rode, we return null
        if (ride == dummyBlack)
        {
            return null;
        }
        // if first ride number is less than our current ride, we traverse the left subtree
        if(rNo1 < ride.rNo)
        {
            searchRange(ride.left, rNo1, rNo2, listOfRides);
        }
        //if our current ride's ride number is in the given range, we add it to the list
        if(rNo1 <= ride.rNo && ride.rNo <= rNo2)
        {
            listOfRides.add("(" +ride.rNo + "," + ride.rCost + "," + ride.tDur + ")");
        }
        //if our current ride's ride number is
        if(rNo2 > ride.rNo)
        {
            searchRange(ride.right, rNo1, rNo2, listOfRides);
        }
        return listOfRides;
    }

/*  finds the minimum node in the tree with root 'ride'*/
    public RBRide swapWithMinimum(RBRide ride)
    {
        // We traverse the left subtree till we reach the external node
        while (ride.left != dummyBlack)
        {
            ride = ride.left;
        }
        return ride;
    }

    /* deletes the node with the given ride number using the root*/
    public void deleteRide(int rNo)
    {
        deleteRide(this.root, rNo);
    }

/* deletes the ride with rNo using ride*/
    public void deleteRide(RBRide ride, int rNo)
    {
        //initialize z to external node
        RBRide zRide = dummyBlack;
        RBRide xRide, yRide;
        //iterates to find the node to be deleted
        while (ride != dummyBlack)
        {
            if (ride.rNo == rNo)
            {
                zRide = ride;
            }
            //if rNo is greater than ride's ridenumber, traverse the right subtree
            if (ride.rNo <= rNo)
            {
                ride = ride.right;
            }
            //otherwise left subtree
            else
            {
                ride = ride.left;
            }
        }
        //if it's an external node, just return
        if (zRide == dummyBlack)
        {
            return;
        }
        yRide = zRide;
        int yRideColour = yRide.col;
        //if it's a degree zero node or degree one node and has a right child,replace zRide with right child
        if (zRide.left == dummyBlack)
        {
            xRide = zRide.right;
            replace(zRide, zRide.right);
        }
        //if it's a degree one node and has a left child, replace zRide child with left child
        else if (zRide.right == dummyBlack)
        {
            xRide = zRide.left;
            replace(zRide, zRide.left);
        }
        //if it's a degree two node
        else
        {
            //swap with the minimum node in the right subtree
            yRide = swapWithMinimum(zRide.right);
            yRideColour = yRide.col;
            xRide = yRide.right;
            if (yRide.par == zRide)
            {
                xRide.par = yRide;
            }
            else
            {
                replace(yRide, yRide.right);
                yRide.right = zRide.right;
                yRide.right.par = yRide;
            }
            replace(zRide, yRide);
            yRide.left = zRide.left;
            yRide.left.par = yRide;
            yRide.col = zRide.col;
        }
        //if it's black node, rebalance the tree
        if (yRideColour == 0)
        {
            deleteXCn(xRide);
        }
    }


/*     realance the tree post deletion */
    public void deleteXCn(RBRide ride)
    {
        RBRide temp;
        //iterate till we reach root and it's not a black node
        while (ride != root && ride.col == 0)
        {
            //if ride is in the left subtree
            if (ride == ride.par.left)
            {
                temp = ride.par.right;
                //if it's a red node, we flip colors and perform right rotation
                if (temp.col == 1)
                {
                    temp.col = 0;
                    ride.par.col = 1;
                    rRotation(ride.par);
                    temp = ride.par.right;
                }
                //if both left and right child nodes are black, we make the current node color red
                if (temp.left.col == 0 && temp.right.col == 0)
                {
                    temp.col = 1;
                    ride = ride.par;
                }
                else
                {
                    //if right child is a blacknode, we perform colour flip, left rotation
                    if (temp.right.col == 0)
                    {
                        temp.left.col = 0;
                        temp.col = 1;
                        lRotation(temp);
                        temp = ride.par.right;
                    }
                    temp.col = ride.par.col;
                    //make ride's parent and right child of temp black
                    ride.par.col = 0;
                    temp.right.col = 0;
                    //perform right rotation
                    rRotation(ride.par);
                    ride = root;
                }
            }
            //if ride is in the right subtree
            else
            {
                //set temp to ride's left child
                temp = ride.par.left;
                //if it's a red node, we flip the colour and perform left rotation
                if (temp.col == 1)
                {
                    temp.col = 0;
                    ride.par.col = 1;
                    lRotation(ride.par);
                    temp = ride.par.left;
                }
                //if the left and right child nodes are black
                if (temp.right.col == 0 && temp.right.col == 0)
                {
                    temp.col = 1;
                    ride = ride.par;
                }

                else
                {
                    //if left child of temp is a black node, we flip the colours and perform right rotation
                    if (temp.left.col == 0)
                    {
                        temp.right.col = 0;
                        temp.col = 1;
                        rRotation(temp);
                        temp = ride.par.left;
                    }
                    //make ride's parent and temp's left child black
                    temp.col = ride.par.col;
                    ride.par.col = 0;
                    temp.left.col = 0;
                    //perform left rotation
                    lRotation(ride.par);
                    ride = root;
                }
            }
        }
        //make the current node black
        ride.col = 0;
    }

/*replaces ride1 with ride2 */
    public void replace(RBRide ride1, RBRide ride2) {
        //if ride1's the root, root will be ride2 now
        if (ride1.par == null)
        {
            root = ride2;
        }
        //if ride1 is the left child, we replace it with ride2
        else if (ride1 == ride1.par.left)
        {
            ride1.par.left = ride2;
        }
        //if ride1 is the right child, we replace it with ride2
        else
        {
            ride1.par.right = ride2;
        }
        //now we set the parent of ride2 to the parent of ride1
        ride2.par = ride1.par;
    }



/* rotates the ride in anticlockwise direction*/
    public void rRotation(RBRide ride)
    {
        //stores the right child
        RBRide temp = ride.right;
        //ride's right is left child of temp
        ride.right = temp.left;
        //if the left child of temp is not an external node, we set its parent to ride
        if (temp.left != dummyBlack)
        {
            temp.left.par = ride;
        }
        //assign ride's parent to temp's parent
        temp.par = ride.par;
        //if its' the root, set root to temp
        if (ride.par == null)
        {
            this.root = temp;
        }
        //if its' in left subtree, we put temp in the left subtree
        else if (ride == ride.par.left)
        {
            ride.par.left = temp;
        }
        //otherwise, in the right subtree
        else
        {
            ride.par.right = temp;
        }
        //make ride as the left child of temp
        temp.left = ride;
        //set the parent pointer of ride to temp
        ride.par = temp;
    }


/* rotates the ride in the clockwise direction*/

    public void lRotation(RBRide ride)
    {
        //stores the left child
        RBRide temp = ride.left;
        //ride's left is right child of temp
        ride.left = temp.right;
        //if the right child of temp is not an external node, we set its parent to ride
        if (temp.right != dummyBlack)
        {
            temp.right.par = ride;
        }
        //assign ride's parent to temp's parent
        temp.par = ride.par;
        //if its' the root, set root to temp
        if (ride.par == null)
        {
            this.root = temp;
        }
        //if its' in right subtree, we put temp in the right subtree
        else if (ride == ride.par.right)
        {
            ride.par.right = temp;
        }
        //otherwise, in the left subtree
        else
        {
            ride.par.left = temp;
        }
        //make ride as the right child of temp
        temp.right = ride;
        //set the parent pointer of ride to temp
        ride.par = temp;
    }



}