package Trees;

import java.util.Random;

public class Treap extends AbstractSelfBalancingBinarySearchTree {

	private Random random = new Random(System.currentTimeMillis());

	public Node insert(int element) {

		TreapNode insertedNode = (TreapNode) super.insert(element);

		while (insertedNode != root) {
			TreapNode parent = (TreapNode) insertedNode.parent;
			if (parent.priority < insertedNode.priority) {
				if (insertedNode.equals(parent.left)) {
					rotateRight(parent);
				} else {
					rotateLeft(parent);
				}
			} else {
				break;
			}
		}
		return insertedNode;
	}

	protected Node delete(Node deleteNode) {
		if (deleteNode != null) {
			// rotate node down to leaf
			Node replaceNode = rotateDown((TreapNode) deleteNode);
			// then delete it normally
			super.delete(deleteNode);
			return replaceNode;
		}
		return null;
	}

	protected Node createNode(int value, Node parent, Node left, Node right) {
		return new TreapNode(value, parent, left, right, random.nextInt(10000));
	}

	private Node rotateDown(TreapNode node) {
		Node replaceNode = null;
		while (true) {
			if (node.left != null) {
				boolean leftNodePriorityLarger = node.right == null
						|| ((TreapNode) node.left).priority >= ((TreapNode) node.right).priority;
				if (leftNodePriorityLarger) {
					Node replace = rotateRight(node);
					if (replaceNode == null) {
						replaceNode = replace;
					}
				} else {
					Node replace = rotateLeft(node);
					if (replaceNode == null) {
						replaceNode = replace;
					}
				}
			} else if (node.right != null) {
				Node replace = rotateLeft(node);
				if (replaceNode == null) {
					replaceNode = replace;
				}
			} else {
				break;
			}
		}
		return replaceNode;
	}

	protected static class TreapNode extends Node {
		public int priority;

		public TreapNode(int value, Node parent, Node left, Node right, int priority) {
			super(value, parent, left, right);
			this.priority = priority;
		}
	}
}