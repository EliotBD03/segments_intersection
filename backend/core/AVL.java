package core;
import java.util.Random;

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

    private class Node<T extends Comparable<T>> implements Comparable<Node<T>>
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
        private Node(Node<T> node)
        {
            if(node == null)
                throw new NullPointerException("The constructor must be provided with a not null node");
            this.data = node.data;
            this.left = node.left;
            this.right = node.right;
            this.height = node.height;
        }

        /**
         * Setter for the right child of the current node.
         * @param node right child
         */
        public void setRight(Node<T> node)
        {
            this.right = node;
        }

        /**
         * Setter for the left child of the current node.
         * @param node left child
         */
        public void setLeft(Node<T> node)
        {
            this.left = node;
        }

        /**
         * Change the height of the current node according to the changes made earlier.
         */
        private void updateHeight()
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
            return "(" + data.toString() + ","+ height +","+ getBalanceFactor()+")";
        }

        /**
         * Will look for the minimum of a tree.
         * @return the minimal node.
         */
        private Node<T> lookForMinimum()
        {
            Node<T> current = this;
            while(current.getLeft() != null)
                current = current.left;
            return current;
        }

        /**
         * Will look for the maximum of a tree.
         * @return the maximal node.
         */
        private Node<T> lookForMaximum()
        {
            Node<T> current = this;
            while(current.getRight() != null)
                current = current.right;
            return current;
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
        if(current == null || current.compareTo(nodeToInsert) == 0)
            return nodeToInsert;
        else if(nodeToInsert.compareTo(current) < 0)
        {
            current.setLeft(insert(current.getLeft(), nodeToInsert));
            current.updateHeight();
            current.balance();

        }
        else if(nodeToInsert.compareTo(current) > 0)
        {
            current.setRight(insert(current.getRight(), nodeToInsert));
            current.updateHeight();
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
     * @param current the current node were in.
     * @param nodeToRemove the node to remove from the tree.
     * @return the modified tree
     * @throws Exception if the data does not exist inside the tree.
     */
    private Node<T> remove(Node<T> current, Node<T> nodeToRemove) throws Exception
    {
        if (current == null)
            throw new Exception("the current AVL does not have the node with the given data : " + nodeToRemove.data);
        else if(nodeToRemove.compareTo(current) < 0)
        {
            current.setLeft(remove(current.getLeft(), nodeToRemove));
            current.updateHeight();
            current.balance();
        }
        else if(nodeToRemove.compareTo(current) > 0)
        {
            current.setRight(remove(current.getRight(), nodeToRemove));
            current.updateHeight();
            current.balance();
        }
        else
        {
            if(current.getLeft() == null && current.getRight() == null)
                return null;
            else if(current.getLeft() == null)
                return current.getRight();
            else if(current.getRight() == null)
                return current.getLeft();
            else
            {
                if(current.getLeft().getHeight() < current.getRight().getHeight())
                {
                    Node<T> minimum = current.right.lookForMinimum();
                    remove(current, minimum); //TODO could both removing and saving data
                    current.data = minimum.data;
                }
                else
                {
                    Node<T> maximum = current.left.lookForMaximum();
                    remove(current, maximum); //TODO could both removing and saving data
                    current.data = maximum.data;

                }
            }
        }
        return current;
    }

    /**
     * wrapper for the private remove function
     * @param data the data to remove from the tree
     * @throws Exception if the data does not exist inside the tree.
     */
    protected void remove(T data) throws Exception
    {
        remove(root, new Node<>(data));
    }

    /**
     * Display the AVL in an inorder way.
     * @param currentNode the current node were in.
     * @param space the space between each father-children.
     */
    private void display(Node<T> currentNode, int space)
    {
        if(currentNode != null)
        {
            space += 5;
            display(currentNode.getRight(), space);
            System.out.print("\n");
            for(int i = 5; i < space; i++)
                System.out.print(" ");
            System.out.print(currentNode + "\n");
            display(currentNode.getLeft(), space);
        }

    }
    /**
     * wrapper for the private display function.
     * Display the AVL in an inorder way.
     */
    protected void display()
    {
        display(root, 0);
    }
}


