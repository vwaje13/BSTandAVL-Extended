/**
 * @author rbk, sa
 * Binary search tree (starter code)
 **/

// replace package name with your netid
package vsw230001;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;
    // define stack
    Stack<Entry<T>> s = new Stack<>();

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    //Helper method find
    public Entry<T> find(T x) {
        return find(root, x);
    }

    public Entry<T> find(Entry<T> t, T x) {
        s.clear();
        s.push(null);
        if((t == null) || (t.element == x)) {
            return t;
        }
        //LI: Stack contains path from root to t leading to x ; s.peek() = parent of t
        while(true) {
            if (x.compareTo(t.element) < 0) {
                if(t.left == null) {
                    break;
                }
                s.push(t);
                t = t.left;
            }
            else if (t.element == x) {
                break;
            }
            else if (t.right == null) {
                break;
            }
            else {
                s.push(t);
                t = t.right;
            }
        }
        return t;
    }

    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        return (t != null) && (t.element == x);
    }


    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        return add(new Entry<T>(x, null, null));
    }

    public boolean add(Entry<T> ent) {
        if(size == 0) {
            root = ent;
            size++;
            return true;
        }
        else {
            Entry<T> t = find(ent.element);
            if(t != null) {
                if (t.element == ent.element) {
                    return false;
                }
                if (ent.element.compareTo(t.element) < 0) {
                    t.left = ent;
                } else {
                    t.right = ent;
                }

            }
        }
        size++;
        return true;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        if(size == 0) {
            return null;
        }
        Entry<T> t = find(x);
        if (t != null) {
            if(t.element != x)  {
                return null;
            }
            if ((t.left == null) || (t.right == null)) {
                splice(t);
            }
            else {
                s.push(t);
                Entry<T> minRight = find(t.right, x);
                t.element = minRight.element;
                splice(minRight);
            }
        }
        size--;
        return x;
    }

    public void splice(Entry<T> t) {
        Entry<T> parent = s.peek();
        Entry<T> child = (t.left == null ? t.right:t.left);
        if(parent == null) {
            root = child;
        }
        else if (parent.left == t) {
            parent.left = child;
        }
        else {
            parent.right = child;
        }
    }


 


// Start of Optional problems

    /** Optional problem : Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }

    // Optional problem
    public T min() {
        return null;
    }

    public T max() {
        return null;
    }

    // Optional problem.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

   // Optional: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        /* write code to place elements in array here */
        return arr;
    }
	
// End of Optional problems

    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<Long> bst = new BinarySearchTree<>();



        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        // Initialize the timer
        Timer timer = new Timer();

        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if (bst.add(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (bst.remove(operand) != null) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains": {
                    operand = sc.nextInt();
                    if (bst.contains(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
            }
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
}




