package Trees;

public abstract class AbstractSelfBalancingBinarySearchTree extends AbstractBinarySearchTree {

	protected Node rotateLeft(Node node) {
		Node temp = node.right;
		temp.parent = node.parent;

		node.right = temp.left;
		if (node.right != null) {
			node.right.parent = node;
		}

		temp.left = node;
		node.parent = temp;

		if (temp.parent != null) {
			if (node == temp.parent.left) {
				temp.parent.left = temp;
			} else {
				temp.parent.right = temp;
			}
		} else {
			root = temp;
		}

		return temp;
	}

	protected Node rotateRight(Node node) {
		Node temp = node.left;
		temp.parent = node.parent;

		node.left = temp.right;
		if (node.left != null) {
			node.left.parent = node;
		}

		temp.right = node;
		node.parent = temp;

		if (temp.parent != null) {
			if (node == temp.parent.left) {
				temp.parent.left = temp;
			} else {
				temp.parent.right = temp;
			}
		} else {
			root = temp;
		}

		return temp;
	}

}