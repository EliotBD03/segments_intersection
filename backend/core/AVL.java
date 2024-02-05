package core;

public class AVL<T extends Comparable<T>>
{
    private Node<T> root;

    public AVL(T data)
    {
        root = new Node<>(data);
    }
    public class Node<T extends Comparable<T>> implements Comparable<Node<T>>
    {
        private final T data;
        private Node<T> left, right;
        private int height;

        private int balanceFactor;

        public Node(T data)
        {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
            this.balanceFactor = 0;
        }

        private Node(Node<T> node)
        {
            this.data = node.data;
            this.left = node.left;
            this.right = node.right;
            this.height = node.height;
            this.balanceFactor = root.balanceFactor;
        }

        public void setRight(Node<T> node)
        {
            this.right = node;
        }

        public void setLeft(Node<T> node)
        {
            this.left = node;
        }
        public Node<T> getLeft()
        {
            return left;
        }

        public Node<T> getRight()
        {
            return right;
        }

        public int getHeight()
        {
            return height;
        }

        public void updateHeight()
        {
            if(root.left == null && root.right == null)
                height = 1; //leaf
            else if(root.left != null && root.right == null)
                height =  root.left.height + 1;
            else if(root.left == null && root.right != null)
                height =  root.right.height + 1;
            else
                height = Math.max(root.left.height, root.right.height) + 1;
        }

        public void updateBalanceFactor()
        {
            if(root.left == null && root.right == null)
                balanceFactor = 0; //leaf
            else if(root.left != null && root.right == null)
                height = root.left.height;
            else if(root.left == null && root.right != null)
                height =  root.right.height;
            else
                height = root.right.height - root.left.height;
        }

        public void balance()
        {
            if(balanceFactor == -2 || balanceFactor == 2)
            {
                if(balanceFactor == 2)
                {
                    if(right.balanceFactor < 0)
                        doubleLeftRotation(this);
                    leftRotation(this);
                }
                if(left.balanceFactor > 0)
                    doubleRightRotation(this);
                rightRotation(this);
            }
        }

        private Node<T> leftRotation(Node<T> node)
        {
            Node<T> temp = new Node<>(node);
            node = temp.right;
            temp.right = node.left;
            node.left = temp;
            temp.updateHeight();
            node.updateHeight();
            return node;
        }

        private Node<T> rightRotation(Node<T> node)
        {
            Node<T> temp = new Node<>(node);
            node = temp.left;
            temp.left = node.right;
            node.right = temp;
            temp.updateHeight();
            node.updateHeight();
            return node;
        }
        private void doubleLeftRotation(Node<T> node)
        {
            node.left = rightRotation(node.left);
            leftRotation(node);
        }
        private void doubleRightRotation(Node<T> node)
        {
            node.right = leftRotation(node.right);
            rightRotation(node);
        }

        @Override
        public int compareTo(Node<T> other)
        {
            return data.compareTo(other.data);
        }

        @Override
        public String toString()
        {
            return data.toString();
        }
    }

    private Node<T> insert(Node<T> current, Node<T> nodeToInsert)
    {
        if(current == null)
            return nodeToInsert;
        else if(nodeToInsert.compareTo(current) < 0)
        {
            current.setLeft(insert(current.getLeft(), nodeToInsert));
            current.updateHeight();
            current.updateBalanceFactor();
            current.balance();

        }
        else if(nodeToInsert.compareTo(current) > 0)
        {
            current.setRight(insert(current.getRight(), nodeToInsert));
            current.updateHeight();
            current.updateBalanceFactor();
            current.balance();
        }
        return current;
    }

    public void insert(T data)
    {
        root = insert(root, new Node<>(data));
    }

    private Node<T> remove(Node<T> current, Node<T> nodeToRemove)
    {
        if (current == null)
            return null;
        else if(current.compareTo(nodeToRemove) < 0)
        {
            current.setRight(remove(current.getRight(), nodeToRemove));
            current.updateHeight();
            current.updateBalanceFactor();
            current.balance();
        }
        else if(current.compareTo(nodeToRemove) > 0)
        {
            current.setLeft(remove(current.getLeft(), nodeToRemove));
            current.updateHeight();
            current.updateBalanceFactor();
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
                    Node<T> minimum = lookForMinimum();
                    remove(current, minimum); //TODO could both removing and saving data
                }
                else
                {
                    Node<T> maximum = lookForMaximum();
                    remove(current, maximum); //TODO could both removing and saving data
                }
            }
        }
        return nodeToRemove;
    }

    private Node<T> lookForMinimum()
    {
        Node<T> current = root;
        while(current.getLeft() != null)
            current = current.left;
        return current;
    }

    private Node<T> lookForMaximum()
    {
        Node<T> current = root;
        while(current.getRight() != null)
            current = current.right;
        return current;
    }

    private void display(Node<T> currentNode, int space)
    {
        if(currentNode != null)
        {
            space += 2;
            display(currentNode.getRight(), space);
            System.out.print("\n");
            for(int i = 0; i < space; i++)
                System.out.println(" ");
            System.out.println(root.data + "\n");
            display(currentNode.getLeft(), space);
        }

    }

    public void display()
    {
        display(root, 1);
    }

    public static void main(String[] args)
    {
        AVL<Integer> avl = new AVL<>(1);
        avl.display();
        avl.insert(2);
        avl.display();

    }
}


