package Trees;

public class SplayTree extends AbstractSelfBalancingBinarySearchTree {


    public Node search(int element) {
        Node node = super.search(element);
        if (node != null) {
            splay(node);
        }
        return node;
    }
    
  
    public Node insert(int element) {
        Node insertNode = super.insert(element);
        splay(insertNode);
        return insertNode;
    }

 
    public Node delete(int element) {
       
        Node deleteNode = super.search(element); 
        Node successor = null;
        if (deleteNode != null) {
            Node parent = deleteNode.parent;
            successor = super.delete(deleteNode);
            if (parent != null) {
                splay(parent);
            }
        }
        return successor;
    }
    
    
    protected Node createNode(int value, Node parent, Node left, Node right) {
        return new Node(value, parent, left, right);
    }

   
    protected void splay(Node node) {

        while (node != root) {
       
            Node parent = node.parent;
            if (parent.equals(root)) {
                if (node.equals(parent.left)) {
                    rotateRight(parent);
                } else if (node.equals(parent.right)) {
                    rotateLeft(parent);
                }
                break;
            } else {
                Node grandParent = parent.parent;
                boolean nodeAndParentLeftChildren = node.equals(parent.left) && parent.equals(grandParent.left);
                boolean nodeAndParentRightChildren = node.equals(parent.right) && parent.equals(grandParent.right);
                boolean nodeRightChildParentLeftChild = node.equals(parent.right) && parent.equals(grandParent.left);
                boolean nodeLeftChildParentRightChild = node.equals(parent.left) && parent.equals(grandParent.right);
             
                if (nodeAndParentLeftChildren) {
                    rotateRight(grandParent);
                    rotateRight(parent);
                }  
               
                else if (nodeAndParentRightChildren) {
                    rotateLeft(grandParent);
                    rotateLeft(parent);
                }

                else if (nodeRightChildParentLeftChild) {
                    rotateLeft(parent);
                    rotateRight(grandParent);
                }
                else if (nodeLeftChildParentRightChild) {
                    rotateRight(parent);
                    rotateLeft(grandParent);
                }
            }
        }
    }

}
