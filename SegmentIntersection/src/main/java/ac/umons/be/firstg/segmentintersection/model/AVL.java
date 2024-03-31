package ac.umons.be.firstg.segmentintersection.model;
import java.util.ArrayList;


/**
 * main representation of an AVL (abstraction of the inner class : Node)
 * @param <T> generic must implement Comparable for insertion
 */
public class AVL<T extends Comparable<T>>
{
    protected Node<T> root;
    protected AVL()
    {
        root = null;
    }


    public class Node<T extends Comparable<T>> implements Comparable<Node<T>>
    {
        private T data;
        private Node<T> left, right;
        private int height;

        /**
         * Constructor of a single node.
         * As the definition of an AVL, the node is in fact a leaf
         * @param data the data stored inside the node
         */
        public Node(T data)
        {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        /**
         * Private constructor used to "copy" a node
         * This is uniquely used inside the leftRotation and RightRotation method
         * @param node an already existing node
         */
        protected Node(Node<T> node)
        {
            if(node == null)
                throw new NullPointerException("The constructor must be provided with a not null node");
            this.data = node.data;
            this.left = node.left;
            this.right = node.right;
            this.height = node.height;
        }

        /**
         * Determines if the node is a leaf or not
         * @return true if the node is a leaf, false otherwise
         */
        public boolean isLeaf()
        {
            return this.left == null && this.right == null;
        }

        /**
         * Setter for the right child of the current node.
         * @param node right child
         */
        public void setRight(Node<T> node)
        {
            this.right = node;
            this.updateHeight();
        }

        /**
         * Setter for the left child of the current node.
         * @param node left child
         */
        public void setLeft(Node<T> node)
        {
            this.left = node;
            this.updateHeight();
        }

        /**
         * Change the height of the current node according to the changes made earlier.
         */
        public void updateHeight()
        {
            if(left == null && right == null)
                height = 1; //leaf
            else
            {
                if(left == null)
                    height = 1 + right.height;
                else
                {
                    if(right == null)
                        height = 1 + left.height;
                    else
                        height = Math.max(left.height, right.height) + 1;
                }
            }
        }

        /**
         * Getter for the left child.
         * @return the left child of the node
         */
        public Node<T> getLeft()
        {
            return left;
        }

        /**
         * Getter for the right child.
         * @return the left right of the node
         */
        public Node<T> getRight()
        {
            return right;
        }

        /**
         * Getter for the height.
         * @return integer correspond to the height of the current node
         */
        public int getHeight()
        {
            return height;
        }

        /**
         * Getter for the data contained inside the node.
         * @return the data contained inside the current node.
         */
        public T getData()
        {
            return data;
        }

        public void setData(T data)
        {
            this.data = data;
        }

        /**
         * Getter for the balance factor of the node.
         * @return an integer corresponding to the balance factor.
         */
        public int getBalanceFactor()
        {
            if(left == null && right == null)
                return 0; //leaf
            else
            {
                if(left == null)
                    return right.height;
                else
                {
                    if(right == null)
                        return - left.height;
                    else
                        return right.height - left.height;
                }
            }
        }

        /**
         * Equilibrate the current AVL/subtree with the current node as root.
         * This method apply the stabilizing theorem.
         */
        public void balance()
        {
            if(getBalanceFactor() == 2)
            {
                if(right.getBalanceFactor() >= 0)
                {
                    leftRotation();
                }
                else
                    doubleLeftRotation();
            }
            else
            {
                if(getBalanceFactor() == -2)
                {
                    if(left.getBalanceFactor() <= 0)
                        rightRotation();
                    else
                        doubleRightRotation();
                }
            }
            updateHeight();
        }

        /**
         * Apply a left rotation to the current tree formed by the current node.
         */
        public void leftRotation() //TODO must resolve the problem above
        {
            Node<T> temp = new Node<>(this);
            data = right.getData();
            left = right.getLeft();
            right = right.getRight();
            temp.right = left;
            left = temp;
            temp.updateHeight();
            updateHeight();
        }

        /**
         * Apply a right rotation to the current tree formed by the current node.
         */
        public void rightRotation()
        {
            Node<T> temp = new Node<>(this);
            data = left.getData();
            right = left.getRight();
            left = left.getLeft();
            temp.left = right;
            right = temp;
            temp.updateHeight();
            updateHeight();
        }

        /**
         * Apply a double left rotation to the current tree formed by the current node.
         */
        private void doubleLeftRotation()
        {
            right.rightRotation();
            leftRotation();
        }

        /**
         * Apply a double right rotation to the current tree formed by the current node.
         */
        private void doubleRightRotation()
        {
            left.leftRotation();
            rightRotation();
        }

        /**
         * Redefine the compareTo method from the interface Comparable.
         * @param other the object to be compared.
         * @return see ref compareTo from Comparable
         */
        @Override
        public int compareTo(Node<T> other)
        {
            return data.compareTo(other.data);
        }

        /**
         * Redefine the compareTo method from the Object class.
         * @return a string corresponding to the representation of a node (data,height,balance_factor)
         * Notice that this method is exclusively used for debugging purposes.
         */
        @Override
        public String toString()
        {
            return treeToString(0, false, new ArrayList<>());
            //return "(" + data.toString() + ","+ height +","+ getBalanceFactor()+")";
        }

        /**
         * Will look for the minimum of a tree.
         * @return the minimal node.
         */
        protected Node<T> lookForMinimum()
        {
            Node<T> current = this;
            while(current.left != null)
                current = current.left;
            return current;
        }

        /**
         * Will look for the maximum of a tree.
         * @return the maximal node.
         */
        protected Node<T> lookForMaximum()
        {
            Node<T> current = this;
            while(current.right != null)
                current = current.right;
            return current;
        }




        //_____________________Pretty Printer______________________

        private String leafToString(int depth, boolean dir, ArrayList<Integer> barList)
        {
            String res = "";
            for (int i = 0; i < depth; i++)
            {
                if(barList.contains(i))
                    res = res.concat("| ");
                else
                {
                    if (i == depth - 1)
                    {
                        if(dir)
                            res = res.concat("↱ ");
                        else
                            res = res.concat("↳ ");
                    }else
                        res = res.concat("  ");
                }
            }
            return res + "("+ this.data + ": " +height +", "+ getBalanceFactor() + ")\n";
        }
        private String treeToString(int depth, boolean goRight, ArrayList<Integer> barList)
        {
            String res = "";
            ArrayList<Integer> next;
            if(root.isLeaf())
                return leafToString(depth, goRight, barList);

            next = new ArrayList<>(barList);
            if (depth != 0 && ! goRight)
                next.add(depth - 1);
            if(this.right != null)
                res += this.right.treeToString(depth + 1, true, next);
            res += this.leafToString(depth, goRight, barList);
            next = new ArrayList<>(barList);
            if(depth != 0 && goRight)
                next.add(depth - 1);
            if(this.left != null)
                res += this.left.treeToString(depth + 1, false, next);
            return res;
        }
    }

    public T getRootData()
    {
        return root.getData();
    }

    /**
     * Insert a node inside an AVL.
     * As the definition mentioned, the AVL will be balanced during the process.
     * @param current the current node in the tree.
     * @param nodeToInsert The node to insert.
     * @return the AVL modified. (notice that the node if already exist will be replaced with the newest)
     */
    private Node<T> insert(Node<T> current, Node<T> nodeToInsert)
    {
        if(current == null)
            return nodeToInsert;
        int compareTo = nodeToInsert.compareTo(current);
        if(compareTo == 0)
            return current;
        else if(compareTo < 0)
        {
            current.setLeft(insert(current.getLeft(), nodeToInsert));
            current.balance();
        }
        else
        {
            current.setRight(insert(current.getRight(), nodeToInsert));
            current.balance();
        }
        return current;
    }

    /**
     * Wrapper to the private insert method.
     * @param data the data to be inserted inside the tree.
     */
    protected void insert(T data)
    {
        root = insert(root, new Node<>(data));
    }

    /**
     * Remove a given node from the tree
     * @param current the current node we are in.
     * @param nodeToRemove the node to remove from the tree.
     * @return the modified tree
     * @throws Exception if the data does not exist inside the tree.
     */
    protected Node<T> remove(Node<T> current, Node<T> nodeToRemove) throws Exception
    {
        if (current == null)
            throw new Exception("the current AVL does not have the node with the given data : " + nodeToRemove.data);
        else if(nodeToRemove.compareTo(current) < 0)
        {
            current.setLeft(remove(current.getLeft(), nodeToRemove));
            current.balance();
        }
        else if(nodeToRemove.compareTo(current) > 0)
        {
            current.setRight(remove(current.getRight(), nodeToRemove));
            current.balance();
        }
        else
        {
            if(current.isLeaf())
                return null;
            else if(current.getLeft() == null)
                return current.getRight();
            else if(current.getRight() == null)
                return current.getLeft();
            else
            {
                Pair<Node<T>, T> newCurr;
                if(current.getLeft().getHeight() < current.getRight().getHeight())
                {
                    newCurr = removeMax(current.right);
                    current.right = newCurr.getItem1();
                }
                else
                {
                    newCurr = removeMax(current.left);
                    current.left = newCurr.getItem1();
                }
                current.data = newCurr.getItem2();
            }
        }

        return current;
    }


    /**
     * Removes the root of the AVL
     */
    public void removeRoot()
    {
        this.root = removeRoot(this.root);
    }

    /**
     * Removes the root node of the given node
     * @param current   The node to modify
     * @return          The new node
     */
    protected Node<T> removeRoot(Node<T> current)
    {
        if (current.isLeaf())
            current = null;
        else
        {
            if(current.left == null)
                current = current.right;
            else if (current.right == null)
                current = current.left;
            else {
                Pair<Node<T>, T> removeMax = removeMax(current.left);
                current.data = removeMax.getItem2();
                current.left = removeMax.getItem1();
                current.balance();
            }
        }
        return current;
    }

    /**
     * Removes the node containing the max value of the given node.
     * Keep in mind, this will also modify the tree structure of curr, so
     * it is recommended to assign the returned value in item1.
     * @param curr  The node to remove the max
     * @return      A pair containing curr without its max and the max value
     */
    protected Pair<Node<T>, T> removeMax(Node<T> curr)
    {
        // Tree is empty
        if(curr == null)
            return null;

        T max = curr.data;
        // The current node is max
        if(curr.right == null)
        {
            // we replace it with his left node
            curr = curr.left;
        }else
        {
            // Else remove the max from the right child
            Pair<Node<T>, T> rec = removeMax(curr.right);
            curr.right = rec.getItem1();
            max = rec.getItem2();
        }
        if(curr != null)
            curr.balance();
        return new Pair<>(curr, max);
    }

    /**
     * Removes the node containing the min value of the given node.
     * Keep in mind, this will also modify the tree structure of curr, so
     * it is recommended to assign the returned value in item1.
     * @param curr  The node to remove the min
     * @return      A pair containing curr without its min and the min value
     */
    protected Pair<Node<T>, T> removeMin(Node<T> curr)
    {
        // Tree is empty
        if(curr == null)
            return null;

        T max = curr.data;
        // The current node is min
        if(curr.left == null)
        {
            // we replace it with his right node
            curr = curr.right;
        }else
        {
            // Else remove the max from the right child
            Pair<Node<T>, T> rec = removeMin(curr.left);
            curr.left = rec.getItem1();
            max = rec.getItem2();
        }
        if(curr != null)
            curr.balance();
        return new Pair<>(curr, max);
    }


    /**
     * wrapper for the private remove function
     * @param data the data to remove from the tree
     * @throws Exception if the data does not exist inside the tree.
     */
    protected void remove(T data) throws Exception
    {
        // Replace the root with the new tree
        this.root = remove(this.root, new Node<>(data));
    }

    /**
     * Returns the root node of this tree
     * @return  The current root node, null if it doesn't exist
     */
    public Node<T> getRoot()
    {
        return root;
    }

    /**
     * wrapper for the private display function.
     * Display the AVL in an inorder way.
     */
    public void display()
    {
        System.out.println(root);
    }


}




