package org.talend.designer.components.sort.radixsort;
public class intNode extends Object {

	intNode(int a) {
		value = a;
		next = null;
		prev = null;
	}

	intNode() {
		next = null;
		prev = null;
		value = 0;		//sets value to zero if no value is explicitly given
	}

	public int value;

	public intNode next;
	public intNode prev;

}