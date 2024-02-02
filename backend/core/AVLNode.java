package core;

public class AVLNode<T extends Comparable<T>>
{
    private AVLNode<T> left, right;
    private int balanceFactor;
    private int height;
    private T data;
    private boolean isEmpty;

    public AVLNode(T data)
    {
        this.left = new AVLNode<T>();
        this.right = new AVLNode<T>();
        this.data = data;
        this.isEmpty = false;
        this.height = 1;
    }

    private AVLNode(T data, AVLNode<T> left, AVLNode<T> right)
    {
        this.left = left;
        this.right = right;
        this.data = data;
        this.isEmpty = false;
        updateHeight();
    }

    private AVLNode()
    {
        this.isEmpty = true;
    }

    public void setLeft(AVLNode<T> left)
    {
       this.left = left;
    }

    public void setRight(AVLNode<T> right)
    {
        this.right = right;
    }

    protected void setBalanceFactor()
    {
        this.balanceFactor = right.getHeight() - left.getHeight();
    }

    public void updateHeight()
    {
        this.height = Math.max(left.getHeight(), right.getHeight()) + 1;
    }

    public AVLNode<T> getLeft()
    {
        return left;
    }

    public AVLNode<T> getRight()
    {
        return right;
    }
    public int getBalanceFactor()
    {
        return balanceFactor;
    }

    public int getHeight()
    {
        return height;
    }
    public T getData()
    {
        return data;
    }
    public boolean isEmpty()
    {
        return this.isEmpty;
    }

    public void insert(AVLNode<T> node, T data)
    {
        if(node.isEmpty())
        {
            node.data = data;
            node.left = new AVLNode();
            node.right = new AVLNode();
            node.isEmpty = false;
            node.height = 1;
        }
        else
        {
            if(data.compareTo(node.getData()) < 0)
                insert(node, data);
            else if (data.compareTo(node.getData()) > 0)
                insert(node, data);

        }
    }

    public static AVLNode balance(AVLNode node)
    {
        if(node.getBalanceFactor() == -2 || node.getBalanceFactor() == 2)
        {
            if(node.getBalanceFactor() == 2)
            {
                if(node.getRight().getBalanceFactor() < 0)
                    return doubleLeftRotation(node);
                return leftRotation(node);
            }
            if(node.getLeft().getBalanceFactor() > 0)
                return doubleRightRotation(node);
            return rightRotation(node);
        }
        return node;
    }


    private static AVLNode leftRotation(AVLNode node)
    {
        AVLNode temp = new AVLNode(node.data, node.left, node.right);
        node = temp.getRight();
        temp.setRight(node.getLeft());
        node.setLeft(temp);
        temp.updateHeight();
        node.updateHeight();
        return node;
    }

    private static AVLNode rightRotation(AVLNode node)
    {
        AVLNode temp = new AVLNode(node.data, node.left, node.right);
        node = temp.getLeft();
        temp.setLeft(node.getRight());
        node.setRight(temp);
        temp.updateHeight();
        node.updateHeight();
        return node;
    }
    private static AVLNode doubleLeftRotation(AVLNode node)
    {
        node.setLeft(rightRotation(node.getLeft()));
        return leftRotation(node);
    }
    private static AVLNode doubleRightRotation(AVLNode node)
    {
        node.setRight(leftRotation(node.getRight()));
        return rightRotation(node);
    }

}
