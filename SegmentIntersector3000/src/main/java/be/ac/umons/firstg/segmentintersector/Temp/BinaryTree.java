package be.ac.umons.firstg.segmentintersector.Temp;

public class BinaryTree<T> {
    public BinaryTree<T> left;
    public BinaryTree<T> right;
    public T data;
    public int height;

    public BinaryTree(T data){
        this(data, null, null);
    }
    public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right){
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf(){
        return this.left == null && this.right == null;
    }
}
