
/** Starter code for AVL Tree
 */
 
// replace package name with your netid
package vsw230001;

import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        int height;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    AVLTree() {
		super();
    }

	// TO DO
    @Override
    public boolean add(T x) {
		if(super.add(new Entry<T>(x, null, null))) {
			balanceTree((Entry<T>) root);
			for(int i = 0; i < s.size(); i++) {
				Entry<T> nod = (Entry<T>)s.pop();
				nod.height ++;
			}
			return true;
		}
		return false;
	}

	private void balanceTree(Entry<T> node) {
		if (node == null) {
			return;
		}
		balanceTree((Entry<T>) node.left);
		balanceTree((Entry<T>) node.right);
		balance(node);
	}

	private void balance(Entry<T> node) {
		if (node == null) {
			return;
		}
		int balanceFactor = getBalanceFactor(node);
		if (balanceFactor > 1) {
			if (getBalanceFactor((Entry<T>) node.left) >= 0) {
				rightRot(node);
			} else {
				leftRot((Entry<T>) node.left);
				rightRot(node);
			}
		}
		else if (balanceFactor < -1) {
			if (getBalanceFactor((Entry<T>) node.right) <= 0) {
				leftRot(node);
			} else {
				leftRot((Entry<T>) node.right);
				leftRot(node);
			}
		}
		return; // No balancing needed
	}

	private int getBalanceFactor(Entry<T> node) {
		return ((Entry<T>) node.left).height - ((Entry<T>) node.right).height;
	}

	private void rightRot(Entry<T> node) {
		Entry<T> temp = (Entry<T>) node.left;
		node.left = temp.right;
		temp.right = node;

		node.height = Math.max(((Entry<T>) node.left).height, ((Entry<T>) node.right).height) + 1;
		temp.height = Math.max(((Entry<T>) temp.left).height, ((Entry<T>) temp.right).height) + 1;
		return;
	}

	private void leftRot(Entry<T> node) {
		Entry<T> temp = (Entry<T>) node.right;
		node.right = temp.left;
		temp.left = node;

		node.height = Math.max(((Entry<T>) node.left).height, ((Entry<T>) node.right).height) + 1;
		temp.height = Math.max(((Entry<T>) temp.left).height, ((Entry<T>) temp.right).height) + 1;
		return;
	}

	/** TO DO
	 *	verify if the tree is a valid AVL tree, that satisfies 
	 *	all conditions of BST, and the balancing conditions of AVL trees. 
	 *	In addition, do not trust the height value stored at the nodes, and
	 *	heights of nodes have to be verified to be correct.  Make your code
	 *  as efficient as possible. HINT: Look at the bottom-up solution to verify BST
	*/
	boolean verify(){
		if(size == 0) {
			return true;
		}
		Coll res = verify((Entry<T>)root);
		return res.isAVL;
	}

	public Coll verify(Entry<T> ent) {
		T curr = ent.element;
		T lmin = curr;
		int lh = -1;
		T rmax = curr;
		int rh = -1;
		if(ent.left != null) {
			Coll c1 = verify((Entry<T>)ent.left);
			lmin = c1.min;
			lh = c1.height;
			if(!c1.isAVL || (c1.max.compareTo(curr)>=0 )) {
				return new Coll(false, lmin, c1.max, lh + 1);
			}
		}
		if(ent.right != null) {
			Coll c2 = verify((Entry<T>)ent.right);
			rmax = c2.max;
			rh = c2.height;
			if (!c2.isAVL || (curr.compareTo(c2.min)>= 0)) {
				return new Coll(false, lmin, rmax, 1+rh);
			}
		}
		if((ent.height != 1 + Math.max(lh, rh)) || (Math.abs(lh- rh) > 1)) {
			return new Coll(false, lmin, rmax, ent.height);
		}
		else {
			return new Coll(true, lmin, rmax, ent.height);
		}
	}

	class Coll {
		boolean isAVL;
		T min;
		T max;
		int height;

		Coll(boolean isAVL, T min, T max, int height) {
			this.isAVL = isAVL;
			this.min = min;
			this.max = max;
			this.height = height;
		}
	}



	//Optional. Complete for extra credit
	@Override
	public T remove(T x) {
		return super.remove(x);
	}

}

