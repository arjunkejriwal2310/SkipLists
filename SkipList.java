/**
 * An implementation of the SimpleList interface using a SkipList to
 * store elements. The expected running times of find, add, and remove
 * are all O(log n) (assuming comparisons are performed in O(1) time).
 *
 * @author Arjun Kejriwal
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class SkipList<E extends Comparable<E>> implements SimpleList<E>
{
    // create a new Random object and store in a variable
    // this is a random number generator used to determine heights of new nodes
    private Random rand = new Random();

    // create a reference to the sentinel node (head node) of the list
    private Node<E> head = new Node<E>(null, 0);

    // create an array of previous nodes, which will be updated by the findNode method
    protected Object[] prev = new Object[1];

    // this variable stores the number of elements in the list (size of the list)
    private int size = 0;

    //this method returns the size of the list
    @Override
    public int size()
    {
        return this.size;
    }

    //returns whether the size of the list is 0
    @Override
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    //returns the value of the element at index i in the list by calling the method findNode(i)
    @Override
    public E get(int i)
    {
        if(findNode(i).val == null)
        {
            return null;
        }

        return findNode(i).val;
    }

    //sets the value of the element at index i to the value x using the method findNode(i)
    @Override
    public void set(int i, E x)
    {
        Node<E> current = findNode(i);
        current.val=x;
    }

    //prints the contents of the list (the value of each successive node, with the options of printing the heights as well)
    @Override
    public String toString()
    {
        StringBuilder strBui = new StringBuilder();
        strBui.append("[");
        Node<E> nd = head;
        while (nd.getNext(0) != null)
        {
            strBui.append(nd.getNext(0).toString() + ", ");
            // you can uncomment the code line below to print the node contents along with their heights
            // strBui.append(nd.getNext(0).toString() + " (" + nd.getNext(0).height() + "), ");
            nd = nd.getNext(0);
        }
        // strBui.append(nd.toString() + " (" + nd.height() + ") ");
        strBui.append("]");
        return strBui.toString();
    }

    //this method adds a node containing value x to index i in the list
    @Override
    @SuppressWarnings("unchecked")
    public void add(int i,E x)
    {
        if (i < 0 || i > size)
        {
            throw new IndexOutOfBoundsException();
        }

        Node<E> node = new Node<E>(x);
        int height = head.height();

        // If the node is taller than the head, update head with the node
        if (node.height() > head.height())
        {
            head.setHeight(node.height(), node, i);
        }

        Node<E> current = head;
        int a = node.height();
        int count = -1;

        // Browse through previous elements at each height
        // Update the next element and the shortcut length.
        while (height >= 0)
        {
            while ((current.getNext(height) != null) && count + current.ShortcutLength[height] < i)
            {
                count = count + current.ShortcutLength[height];
                current = current.getNext(height);
            }

            current.ShortcutLength[height]++;

            if (height <= a)
            {
                node.setNext(height, current.getNext(height));
                current.setNext(height, node);
                node.ShortcutLength[height] = current.ShortcutLength[height] - (i - count);
                current.ShortcutLength[height] = (i - count);
            }
            height--;
        }
        // Increment the size of the array by 1
        ++size;
    }

    //Remove the node at index i from the list
    @Override
    @SuppressWarnings("unchecked")
    public E remove(int i)
    {
        E x = null;
        Node<E> current = head;
        int height = head.height();
        int count = -1;

        // Browse through previous elements at each height
        // Update the next element and the shortcut length.
        while (height >= 0)
        {
            while ((current.getNext(height) != null) && count + current.ShortcutLength[height] < i)
            {
                count = count + current.ShortcutLength[height];
                current = current.getNext(height);
            }

            //Decrement the shortcut length at the specific height by 1
            current.ShortcutLength[height]--;

            if ((current.getNext(height) != null) && (count + current.ShortcutLength[height] + 1) == i)
            {
                x = current.getNext(height).val;
                current.ShortcutLength[height] = current.ShortcutLength[height] + current.getNext(height).ShortcutLength[height];
                current.setNext(height,current.getNext(height).getNext(height));

                //If we are looking at the head, and there is no next at that height, trim it by calling the trim() method.
                if (current == null && current.getNext(height) == null)
                {
                    head.trim();
                }
            }
            //Decrement the height by 1
            height--;
        }

        //// Reduce the size of the list by 1 since element x was removed from the list
        --size;
        return x;
    }

    // Find the node storing value x, the node storing the smallest value larger than x, or null if no such node exists.
    // This method also updates the previous array such that prev[h] refers to the node where the search for x at height h
    // either succeeded or failed.
    // Note that this method searches for an index instead of the value
    protected Node<E> findNode(int i)
    {
        int height = head.next.length - 1;
        Node<E> current = head;
        int count = -1;

        while (height >= 0)
        {
            if (current.getNext(height) != null && count + current.ShortcutLength[height] < i)
            {
                count = count + current.ShortcutLength[height];
                current = current.getNext(height);
            }
            else
            {
                --height;
            }
        }
        return current.getNext(0);
    }

    //This method generates a list containing a random permutation of numbers from 1 to n
    public void generateRandomList(SimpleList<Integer> list, int n)
    {
        //Create a new random variable
        Random rand = new Random();

        for (int k = 1; k <= n; k++)
        {
            //Return and store a number between 0 and k-1 inclusive
            int j = rand.nextInt(k);

            //Add the value k at random index j
            list.add(j,k);
        }
    }

    //this method generates the statistics that answers Question 1
    public void generateStats1()
    {
        SimpleList<Integer> LinkedList = new LinkedSimpleList<Integer>();
        SimpleList<Integer> SkipList = new SkipList<Integer>();

        CSVWriter csv = new CSVWriter("Question1.csv");
        csv.addEntry("Value of n");
        csv.addEntry("Run time of LinkedSimpleList");
        csv.addEntry("Run time of SkipList");
        csv.endLine();

        //Creating RunTimers for the two different implementations
        RunTimer timer1 = new RunTimer();
        RunTimer timer2 = new RunTimer();

        //Finding run-times of both implementations for values of n from 1 to 50 inclusive
        for(int n = 1; n <= 3000; n++)
        {
            csv.addEntry(n);

            timer1.start();
            generateRandomList(LinkedList, n);
            timer1.stop();
            csv.addEntry(timer1.getElapsedMillis());

            timer2.start();
            generateRandomList(SkipList, n);
            timer2.stop();
            csv.addEntry(timer2.getElapsedMillis());

            csv.endLine();
        }
        csv.close();
    }

    //this method generates the statistics that answers Question 2
    public double[] generateStats2()
    {
        SimpleList<Integer> SkipList1 = new SkipList<Integer>();
        double[] results = new double[6];

        //Keeps a count of the number of trials in which at least one of the 10 students received their own quiz
        double duplicate1 = 0;
        //Keeps a count of the number of students out of 10 students who receive their own quiz
        double count1 = 0;

        //Conducting 20 trials for 10 students
        for(int i = 0; i < 20; i++)
        {
            generateRandomList(SkipList1, 10);

            for(int j = 0; j < 10; j++)
            {
                if (SkipList1.get(j) == j + 1)
                {
                    duplicate1++;
                    break;
                }
            }

            for(int j = 0; j < 10; j++)
            {
                if (SkipList1.get(j) == j + 1)
                {
                    count1++;
                }
            }
        }

        results[0] = duplicate1 / 20.0;
        results[3] = count1 / 20.0;

        SimpleList<Integer> SkipList2 = new SkipList<Integer>();
        //Keeps a count of the number of trials in which at least one of the 100 students received their own quiz
        double duplicate2 = 0;
        //Keeps a count of the number of students out of 100 students who receive their own quiz
        double count2 = 0;

        //Conducting 40 trials for 100 students
        for(int i = 0; i < 40; i++)
        {
            generateRandomList(SkipList2, 100);

            for(int j = 0; j < 100; j++)
            {
                if (SkipList2.get(j) == j + 1)
                {
                    duplicate2++;
                    break;
                }
            }

            for(int j = 0; j < 100; j++)
            {
                if (SkipList2.get(j) == j + 1)
                {
                    count2++;
                }
            }
        }

        results[1] = duplicate2 / 40;
        results[4] = count2 / 40;

        SimpleList<Integer> SkipList3 = new SkipList<Integer>();
        //Keeps a count of the number of trials in which at least one of the 1000 students received their own quiz
        double duplicate3 = 0;
        //Keeps a count of the number of students out of 1000 students who receive their own quiz
        double count3 = 0;

        //Conducting 60 trials for 1000 students
        for(int i = 0; i < 60; i++)
        {
            generateRandomList(SkipList3, 1000);

            for(int j = 0; j < 1000; j++)
            {
                if (SkipList3.get(j) == j + 1)
                {
                    duplicate3++;
                    break;
                }
            }

            for(int j = 0; j < 1000; j++)
            {
                if(SkipList3.get(j) != null)
                {
                    if (SkipList3.get(j) == j + 1)
                    {
                        count3++;
                    }
                }
            }
        }

        results[2] = duplicate3 / 60;
        results[5] = count3 / 60;

        return results;
    }

    //Defining a class that defines the representation of a "node" in the SkipList
    protected class Node<E>
    {
        // Define a variable that stores the value stored in a node
        protected E val;

        // Create an array of references to next nodes in the SkipList;
        // For example, next[h] is the next node at height h and next.length - 1 is the height of this node
        protected Object[] next;

        //Array of shortcut lengths for every height of the node.
        protected int[] ShortcutLength;

        /**
         * Create a new node storing the value val. The height is
         * chosen at random according to a geometric distribution with
         * parameter 1/2. That is, the probability that the node has
         * height h is 1/2^{h+1}.
         *
         * @param 'val' the value to be stored in the node
         */

        public Node(E value)
        {
            this.val = value;
            int height = 0;

            while (rand.nextBoolean())
            {
                height++;
            }

            next = new Object[height + 1];
            ShortcutLength = new int[height + 1];
        }

        /**
         * Create a new node storing value val with height height.
         *
         * @param 'val' the value to be stored
         * @param height the height of the node
         */

        public Node(E value, int height)
        {
            this.val = value;
            next = new Object[height + 1];
            ShortcutLength = new int[height+1];
        }

        /**
         * Get this node's next node at height h.
         *
         * @param h the height
         * @return this node's next node at height h
         */

        @SuppressWarnings("unchecked")
        public Node<E> getNext(int h)
        {
            return (Node<E>) next[h];
        }

        /**
         * Set this node's next at height h to nd.
         *
         * @param h the height
         * @param 'nd' the node to be assigned to next[h]
         */

        void setNext(int h, Node<E> node)
        {
            next[h] = node;
        }

        /**
         * Return the height of this node.
         *
         * @return the height
         */

        int height()
        {
            return next.length - 1;
        }

        /**
         * Set the height of this node to height, and for all h
         * greater than the previous height, set next[h] to refer to
         * nd.
         *
         * Important change: It also updates the array of shortcuts, and for all
         * h greater than the previous height, set ShortcutLength(h) to refer to the index of the new node.
         *
         *
         * @param height the new height of this node
         * @param 'nd' the value assigned to next[h] for h > previous
         * height
         */

        void setHeight(int height, Node<E> node, int index)
        {
            Object[] newNextArray = new Object[height + 1];
            int[] newShortcutLengths = new int[height+1];

            for (int i = 0; i < next.length && i < height; ++i)
            {
                newNextArray[i] = next[i];
                newShortcutLengths[i] = ShortcutLength[i];
            }

            for (int j = next.length; j <= height; ++j)
            {
                newNextArray[j] = node;
                newShortcutLengths[j] = index + 1;
            }

            next = newNextArray;
            ShortcutLength = newShortcutLengths;
        }

        /**
         * Decrease the height of this node to h, where h is the
         * largest value with next[h] != null.
         */

        void trim()
        {
            int count = 0;

            while (count < next.length && next[count] != null)
            {
                ++count;
            }

            if (count == next.length)
            {
                return;
            }

            Object[] newNextArray = new Object[count];

            for (int i = 0; i < count; ++i)
            {
                newNextArray[i] = next[i];
            }

            next = newNextArray;
        }

        @Override
        public String toString()
        {
            return (val == null) ? "null" : val.toString();
        }
    }
}