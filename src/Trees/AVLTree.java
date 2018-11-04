package Trees;

import Utils.MathUtils;

public class AVLTree extends AbstractSelfBalancingBinarySearchTree {

	public Node insert(int element) {
		Node newNode = super.insert(element);
		rebalance((AVLNode) newNode);
		return newNode;
	}

	public Node delete(int element) {
		Node deleteNode = super.search(element);
		if (deleteNode != null) {
			Node successorNode = super.delete(deleteNode);
			if (successorNode != null) {

				AVLNode minimum = successorNode.right != null ? (AVLNode) getMinimum(successorNode.right)
						: (AVLNode) successorNode;
				recomputeHeight(minimum);
				rebalance((AVLNode) minimum);
			} else {
				recomputeHeight((AVLNode) deleteNode.parent);
				rebalance((AVLNode) deleteNode.parent);
			}
			return successorNode;
		}
		return null;
	}

	protected Node createNode(int value, Node parent, Node left, Node right) {
		return new AVLNode(value, parent, left, right);
	}

	private void rebalance(AVLNode node) {
		while (node != null) {

			Node parent = node.parent;

			int leftHeight = (node.left == null) ? -1 : ((AVLNode) node.left).height;
			int rightHeight = (node.right == null) ? -1 : ((AVLNode) node.right).height;
			int nodeBalance = rightHeight - leftHeight;

			if (nodeBalance == 2) {
				if (node.right.right != null) {
					node = (AVLNode) avlRotateLeft(node);
					break;
				} else {
					node = (AVLNode) doubleRotateRightLeft(node);
					break;
				}
			} else if (nodeBalance == -2) {
				if (node.left.left != null) {
					node = (AVLNode) avlRotateRight(node);
					break;
				} else {
					node = (AVLNode) doubleRotateLeftRight(node);
					break;
				}
			} else {
				updateHeight(node);
			}

			node = (AVLNode) parent;
		}
	}

	private Node avlRotateLeft(Node node) {
		Node temp = super.rotateLeft(node);

		updateHeight((AVLNode) temp.left);
		updateHeight((AVLNode) temp);
		return temp;
	}

	private Node avlRotateRight(Node node) {
		Node temp = super.rotateRight(node);

		updateHeight((AVLNode) temp.right);
		updateHeight((AVLNode) temp);
		return temp;
	}

	protected Node doubleRotateRightLeft(Node node) {
		node.right = avlRotateRight(node.right);
		return avlRotateLeft(node);
	}

	protected Node doubleRotateLeftRight(Node node) {
		node.left = avlRotateLeft(node.left);
		return avlRotateRight(node);
	}

	private void recomputeHeight(AVLNode node) {
		while (node != null) {
			node.height = maxHeight((AVLNode) node.left, (AVLNode) node.right) + 1;
			node = (AVLNode) node.parent;
		}
	}

	private int maxHeight(AVLNode node1, AVLNode node2) {
		if (node1 != null && node2 != null) {
			return node1.height > node2.height ? node1.height : node2.height;
		} else if (node1 == null) {
			return node2 != null ? node2.height : -1;
		} else if (node2 == null) {
			return node1 != null ? node1.height : -1;
		}
		return -1;
	}

	private static final void updateHeight(AVLNode node) {
		int leftHeight = (node.left == null) ? -1 : ((AVLNode) node.left).height;
		int rightHeight = (node.right == null) ? -1 : ((AVLNode) node.right).height;
		node.height = 1 + MathUtils.getMax(leftHeight, rightHeight);
	}

	protected static class AVLNode extends Node {
		public int height;

		public AVLNode(int value, Node parent, Node left, Node right) {
			super(value, parent, left, right);
		}
	}

}
