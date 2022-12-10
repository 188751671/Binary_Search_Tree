/**
 * Ordinary binary search trees, with int keys
 * With solutions to exercises
 * @version 3
 * @author akj22 (v3, 2), smk (v1)
 * Changes: main method added, minor edits made to insert() method, empty tree height edited, removed red-black trees
 *
 */
import java.util.Random;
public class BST2
{
    // instance variables
    private int key;
    private BST2 left,right;

    //fixed BST that is the dedicated empty tree, by convention
    public static final BST2 empty=new BST2();

    //global random number generator
    private static final Random rg=new Random();

    //private constructor, only used for empty tree, once
    private BST2() {}

    public static BST2 empty() { return empty; }
    public boolean isEmpty() { return this==empty; }

    /**
     * Constructor for singleton BST
     * Use this constructor
     */
    public BST2(int k)
    {
        key=k;
        left=right=empty;
    }

    public boolean search(int k) {
        if (isEmpty()) return false;
        if (k==key) return true;
        if (k<key) return left.search(k);
        else return right.search(k);
    }

    public int size() {
        if (isEmpty()) return 0;
        return left.size()+right.size()+1;
    }

    //add k to BST if it is not already there
    public BST2 insert(int k) {
        if (isEmpty()) return new BST2(k);
        if (k<key) {
            left=left.insert(k);
        } else if (k>key) {
            right=right.insert(k);
        }
        return this;
    }

    //create a random tree by inserting n distinct random numbers (between 1 and 4n)
    //into an empty tree
    public static BST2 randomTree(int n) {
        BST2 res=empty;
        for (int i=0; i<n; i++) {
            int k;
            do {//loop ensures number is fresh
                k=rg.nextInt(4*n)+1;
            } while (res.search(k));
            res=res.insert(k);
        }
        return res;
    }

    public int height() {
        if (isEmpty()) {
            return -1;
        }
        else {
            int leftHeight = left.height();
            int rightHeight = right.height();
            return Math.max(leftHeight, rightHeight)+1;
        }
    }


    public boolean isAVL() {
        return avlHeight() != -1;
    }

    private int avlHeight() {
        if (isEmpty()) return -1;
        int l=left.avlHeight();
        int r=right.avlHeight();
        if (l<-1 || r<-1) return -1;
        if (l+1<r || r+1<l) return -1;
        return Math.max(l,r)+1;
    }

    public static void randomReport(int size) {
        //try 100 random trees of size
        //what percentage is AVL?
        int counterAVL=0;
        int hs=0;
        for (int i=0; i<100; i++) {
            BST2 rt=randomTree(size);
            if (rt.isAVL()) counterAVL++;
            hs+=rt.height();
        }
        System.out.println("Task 3: size "+size+": AVL " + counterAVL +"%, average height: " + (hs/100.0));
    }

    public String toString() {
        return toString("");
    }
    private String toString(String lead) {
        if (isEmpty()) return lead + "E\n";
        return lead + key + "\n" + left.toString(lead+"    ") + right.toString(lead+"    ");
    }


    public static void main(String[] args)  {
        BST2 binSearchTree = new BST2(5);  // set up a new instance of BST with a root node key =5
        System.out.println(binSearchTree.toString()); // use the toString method to look at the contents of the tree
        System.out.println("Task 1: Height = "+binSearchTree.height());  // we expect this height to be 0
        System.out.println("Task 2: isAVL() = "+binSearchTree.isAVL());  // we expect isAVL = true

        binSearchTree.insert(6); // example of how to call the insert method to add a new node to our binSearchTree, with key = 6
        System.out.println(binSearchTree.toString()); // use the toString method to look at the contents of the tree

        System.out.println("Task 1: Height = "+binSearchTree.height()); // we expect this height to be 1
        System.out.println("Task 2: isAVL() = "+binSearchTree.isAVL()); // we expect isAVL = true

        binSearchTree.insert(7); // example of how to call the insert method to add a new node to our binSearchTree, with key = 7
        System.out.println(binSearchTree.toString()); // use the toString method to look at the contents of the tree

        System.out.println("Task 1: Height = "+binSearchTree.height()); // we expect this height to be 2
        System.out.println("Task 2: isAVL() = "+binSearchTree.isAVL()); // we expect isAVL = false

        binSearchTree.insert(4); // example of how to call the insert method to add a new node to our binSearchTree, with key = 4
        System.out.println(binSearchTree.toString()); // use the toString method to look at the contents of the tree

        System.out.println("Task 1: Height = "+binSearchTree.height()); // we expect this height to be 2
        System.out.println("Task 2: isAVL() = "+binSearchTree.isAVL()); // we expect isAVL = true

        randomReport(100);
    }
}