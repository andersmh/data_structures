package Trees;

public class RedBlackTree extends AbstractSelfBalancingBinarySearchTree {

	protected enum ColorEnum {
		RED, BLACK
	};

	protected static final RedBlackNode nilNode = new RedBlackNode(null, null, null, null, ColorEnum.BLACK);

	public Node insert(int element) {
		Node newNode = super.insert(element);
		newNode.left = nilNode;
		newNode.right = nilNode;
		root.parent = nilNode;
		insertRBFixup((RedBlackNode) newNode);
		return newNode;
	}

	protected Node delete(Node deleteNode) {
		Node replaceNode = null;
		if (deleteNode != null && deleteNode != nilNode) {
			Node removedOrMovedNode = deleteNode;
			ColorEnum removedOrMovedNodeColor = ((RedBlackNode) removedOrMovedNode).color;

			if (deleteNode.left == nilNode) {
				replaceNode = deleteNode.right;
				rbTreeTransplant(deleteNode, deleteNode.right);
			} else if (deleteNode.right == nilNode) {
				replaceNode = deleteNode.left;
				rbTreeTransplant(deleteNode, deleteNode.left);
			} else {
				removedOrMovedNode = getMinimum(deleteNode.right);
				removedOrMovedNodeColor = ((RedBlackNode) removedOrMovedNode).color;
				replaceNode = removedOrMovedNode.right;
				if (removedOrMovedNode.parent == deleteNode) {
					replaceNode.parent = removedOrMovedNode;
				} else {
					rbTreeTransplant(removedOrMovedNode, removedOrMovedNode.right);
					removedOrMovedNode.right = deleteNode.right;
					removedOrMovedNode.right.parent = removedOrMovedNode;
				}
				rbTreeTransplant(deleteNode, removedOrMovedNode);
				removedOrMovedNode.left = deleteNode.left;
				removedOrMovedNode.left.parent = removedOrMovedNode;
				((RedBlackNode) removedOrMovedNode).color = ((RedBlackNode) deleteNode).color;
			}

			size--;
			if (removedOrMovedNodeColor == ColorEnum.BLACK) {
				deleteRBFixup((RedBlackNode) replaceNode);
			}
		}

		return replaceNode;
	}

	protected Node createNode(int value, Node parent, Node left, Node right) {
		return new RedBlackNode(value, parent, left, right, ColorEnum.RED);
	}

	protected Node getMinimum(Node node) {
		while (node.left != nilNode) {
			node = node.left;
		}
		return node;
	}

	protected Node getMaximum(Node node) {
		while (node.right != nilNode) {
			node = node.right;
		}
		return node;
	}

	protected Node rotateLeft(Node node) {
		Node temp = node.right;
		temp.parent = node.parent;

		node.right = temp.left;
		if (node.right != nilNode) {
			node.right.parent = node;
		}

		temp.left = node;
		node.parent = temp;

		if (temp.parent != nilNode) {
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
		if (node.left != nilNode) {
			node.left.parent = node;
		}

		temp.right = node;
		node.parent = temp;

		if (temp.parent != nilNode) {
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

	private Node rbTreeTransplant(Node nodeToReplace, Node newNode) {
		if (nodeToReplace.parent == nilNode) {
			this.root = newNode;
		} else if (nodeToReplace == nodeToReplace.parent.left) {
			nodeToReplace.parent.left = newNode;
		} else {
			nodeToReplace.parent.right = newNode;
		}
		newNode.parent = nodeToReplace.parent;
		return newNode;
	}

	private void deleteRBFixup(RedBlackNode x) {
		while (x != root && isBlack(x)) {

			if (x == x.parent.left) {
				RedBlackNode w = (RedBlackNode) x.parent.right;
				if (isRed(w)) {
					w.color = ColorEnum.BLACK;
					((RedBlackNode) x.parent).color = ColorEnum.RED;
					rotateLeft(x.parent);
					w = (RedBlackNode) x.parent.right;
				}
				if (isBlack(w.left) && isBlack(w.right)) {
					w.color = ColorEnum.RED;
					x = (RedBlackNode) x.parent;
				} else if (w != nilNode) {
					if (isBlack(w.right)) {
						((RedBlackNode) w.left).color = ColorEnum.BLACK;
						w.color = ColorEnum.RED;
						rotateRight(w);
						w = (RedBlackNode) x.parent.right;
					}
					w.color = ((RedBlackNode) x.parent).color;
					((RedBlackNode) x.parent).color = ColorEnum.BLACK;
					((RedBlackNode) w.right).color = ColorEnum.BLACK;
					rotateLeft(x.parent);
					x = (RedBlackNode) root;
				} else {
					x.color = ColorEnum.BLACK;
					x = (RedBlackNode) x.parent;
				}
			} else {
				RedBlackNode w = (RedBlackNode) x.parent.left;
				if (isRed(w)) {
					w.color = ColorEnum.BLACK;
					((RedBlackNode) x.parent).color = ColorEnum.RED;
					rotateRight(x.parent);
					w = (RedBlackNode) x.parent.left;
				}
				if (isBlack(w.left) && isBlack(w.right)) {
					w.color = ColorEnum.RED;
					x = (RedBlackNode) x.parent;
				} else if (w != nilNode) {
					if (isBlack(w.left)) {
						((RedBlackNode) w.right).color = ColorEnum.BLACK;
						w.color = ColorEnum.RED;
						rotateLeft(w);
						w = (RedBlackNode) x.parent.left;
					}
					w.color = ((RedBlackNode) x.parent).color;
					((RedBlackNode) x.parent).color = ColorEnum.BLACK;
					((RedBlackNode) w.left).color = ColorEnum.BLACK;
					rotateRight(x.parent);
					x = (RedBlackNode) root;
				} else {
					x.color = ColorEnum.BLACK;
					x = (RedBlackNode) x.parent;
				}
			}

		}
	}

	private boolean isBlack(Node node) {
		return node != null ? ((RedBlackNode) node).color == ColorEnum.BLACK : false;
	}

	private boolean isRed(Node node) {
		return node != null ? ((RedBlackNode) node).color == ColorEnum.RED : false;
	}

	private void insertRBFixup(RedBlackNode currentNode) {

		while (currentNode.parent != root && ((RedBlackNode) currentNode.parent).color == ColorEnum.RED) {
			RedBlackNode parent = (RedBlackNode) currentNode.parent;
			RedBlackNode grandParent = (RedBlackNode) parent.parent;
			if (parent == grandParent.left) {
				RedBlackNode uncle = (RedBlackNode) grandParent.right;

				if (((RedBlackNode) uncle).color == ColorEnum.RED) {
					parent.color = ColorEnum.BLACK;
					uncle.color = ColorEnum.BLACK;
					grandParent.color = ColorEnum.RED;

					currentNode = grandParent;
				}

				else {
					if (currentNode == parent.right) {
						currentNode = parent;
						rotateLeft(currentNode);
						parent = (RedBlackNode) currentNode.parent;
					}
					parent.color = ColorEnum.BLACK;
					grandParent.color = ColorEnum.RED;
					rotateRight(grandParent);
				}
			} else if (parent == grandParent.right) {
				RedBlackNode uncle = (RedBlackNode) grandParent.left;

				if (((RedBlackNode) uncle).color == ColorEnum.RED) {
					parent.color = ColorEnum.BLACK;
					uncle.color = ColorEnum.BLACK;
					grandParent.color = ColorEnum.RED;

					currentNode = grandParent;
				}

				else {
					if (currentNode == parent.left) {
						currentNode = parent;
						rotateRight(currentNode);
						parent = (RedBlackNode) currentNode.parent;
					}

					parent.color = ColorEnum.BLACK;
					grandParent.color = ColorEnum.RED;
					rotateLeft(grandParent);
				}
			}

		}

		((RedBlackNode) root).color = ColorEnum.BLACK;
	}

	protected static class RedBlackNode extends Node {
		public ColorEnum color;

		public RedBlackNode(Integer value, Node parent, Node left, Node right, ColorEnum color) {
			super(value, parent, left, right);
			this.color = color;
		}
	}

}