import java.util.Random;
import java.util.Arrays;

public class SimpleListTester {
    static final int SIZE = 10;
    static Random rand = new Random(10);
    
    public static void main(String[] args) {
	// choose a SimpleList implementation to test
	SimpleList<Integer> list = new LinkedSimpleList<Integer>();
	//SimpleList<Integer> list = new SkipList<Integer>();

	System.out.println("Constructing add sequence...");
	int[] addIndices = getAddIndices();
	int[] arr = getArray(addIndices);

	System.out.println("Testing add and get methods...");
	if (testAdd(list, addIndices, arr)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}

	System.out.println("Testing remove method...");
	if (testRemove(list, addIndices)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}
	

	System.out.println("Testing set method...");
	if (testSet(list)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}
	

	
    }

    static int[] getAddIndices() {
	int[] ind = new int[SIZE];

	ind[0] = 0;

	// use fisher-yates shuffle to make arr a random permutation of 0,1,...,SIZE - 1
	for (int i = 1; i < ind.length; ++i) {
	    ind[i] = rand.nextInt(i+1);
	}

	return ind;
    }

    static int[] getArray(int[] ind) {
	int[] arr = new int[ind.length];

	arr[0] = 0;
	
	for (int i = 1; i < arr.length; ++i) {
	    
	    for (int j = i; j > ind[i]; --j) {
		arr[j] = arr[j-1]; 
	    }

	    arr[ind[i]] = i;
	}

	return arr;
    }

    static boolean testAdd(SimpleList<Integer> list, int[] addIndices, int[] arr) {
	for (int i = 0; i < addIndices.length; ++i) {
	    list.add(addIndices[i], i);

	    if (list.size() != i + 1) {
		System.out.println(   "incorrect size after add(" + addIndices[i] + ", " + i + ")");
		return false;
	    }

	    if (!list.get(addIndices[i]).equals(i)) {
		System.out.println("   get(" + addIndices[i] + ") returned incorrect value after add(" + addIndices[i] + ", " + i + ")");
		return false;
	    }
	}

	for (int i = 0; i < list.size(); ++i) {
	    if (list.get(i) != arr[i]) {
		System.out.println("   incorrect contents after adding elements");
		System.out.println("   contents should be " + Arrays.toString(arr));
		return false;
	    }
	}

	return true;
    }

    static boolean testRemove(SimpleList<Integer> list, int[] indices) {
	
	for (int i = indices.length - 1; i >= 0; --i) {
	    int val = list.remove(indices[i]);

	    if (list.size() != i) {
		System.out.println("   incorrect size after call to remove(" + indices[i] + ")");
		return false;
	    }

	    if (val != i) {
		System.out.println("   remove(" + indices[i] + ") returned incorrect value");
		return false;
	    }
	}

	return true;
    }

    static boolean testSet(SimpleList<Integer> list) {
	for (int i = 0; i < SIZE; ++i) {
	    list.add(0, 0);
	}
	
	int initSize = list.size();
	
	for (int i = 0; i < list.size(); ++i) {
	    list.set(i, i);

	    if (list.size() != initSize) {
		System.out.println("   size changed after call to set(" + i + ", " + i + ")");
		return false;
	    }

	    if (!list.get(i).equals(i)) {
		System.out.println("   set(" + i + ", " + i + ") failed to properly set value");
		return false;
	    }
	}

	for (int i = 0; i < initSize; ++i) {
	    if (list.get(i) != i) {
		System.out.println("   incorrect contents after setting values");
		return false;
	    }
	}

	return true;
    }
}
