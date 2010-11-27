/*
 * MathSet.java
 *
 * Copyright 2004 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package programming5.collections;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.Random;
import programming5.arrays.RandomOrderGenerator;
import programming5.arrays.SetOperations;
import programming5.arrays.SubsetGenerator;

/**
 *This generic class is an extension of the java.util.HashSet collection that provides useful set manipulation 
 *operations, to be applied from an instance of the class or as static methods.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class MathSet<E> extends HashSet<E> {
    
	private RandomOrderGenerator sampleOrderGen, singleOrderGen;
	private Random viewRand;
	private int singleSamples = 0;
	
	/**
         *Creates an empty set
         */
        public MathSet() {
		super();
		sampleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		singleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		viewRand = new Random(System.currentTimeMillis());
	}
	
	/**
         *Creates a new set out of the array elements. Any repeated elements in the array will only be inserted once.
         */
        public <T extends E> MathSet(T[] array) {
		super();
		for (T elem : array) {
			this.add(elem);
		}
		sampleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		singleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		viewRand = new Random(System.currentTimeMillis());
	}
	
	/**
         *Creates a new set out of the collection elements. Any repeated elements in the collection will only be inserted once.
         */
        public MathSet(Collection<? extends E> c) {
		super(c);
		sampleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		singleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		viewRand = new Random(System.currentTimeMillis());
	}
	
	/**
         *Creates an empty set initialized to the given size
         */
        public MathSet(int size) {
		super(size);
		sampleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		singleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		viewRand = new Random(System.currentTimeMillis());
	}
	
	/**
         *Creates an empty set initialized to the given size with the given growth percentage
         */
        public MathSet(int size, float pctGrowth) {
		super(size, pctGrowth);
		sampleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		singleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		viewRand = new Random(System.currentTimeMillis());
	}

	/**
         *@return a new MathSet that is the intersection of this set with the given set
         */
        public MathSet<E> intersect(Set<? extends E> set) {
		E[] set1 = (E[])this.toArray();
		E[] set2 = (E[])set.toArray();
		E[] result = (E[])SetOperations.intersect(set1, set2);
		return new MathSet<E>(result);
	}

	/**
         *@return a new MathSet that is the intersection of the two given sets
         */
        public static <T> Set<T> intersect(Set<? extends T> set1, Set<? extends T> set2) {
		MathSet<T> mset1 = new MathSet<T>(set1);
		return mset1.intersect(set2);
	}

	/**
         *@return a new MathSet that is the union of this set with the given set
         */
        public MathSet<E> union(Set<? extends E> set) {
		E[] set1 = (E[])this.toArray();
		E[] set2 = (E[])set.toArray();
		E[] result = (E[])SetOperations.union(set1, set2);
		MathSet<E> ret = null;
		if (result != null) {
			ret = new MathSet<E>(result);
		}
		return ret;
	}

	/**
         *@return a new MathSet that is the union of the two given sets
         */
        public static <T> Set<T> union(Set<? extends T> set1, Set<? extends T> set2) {
		MathSet<T> mset1 = new MathSet<T>(set1);
		return mset1.union(set2);
	}

	/**
         *@return a new MathSet that is the difference of this set with the given set
         */
        public MathSet<E> difference(Set<? extends E> set) {
		E[] set1 = (E[])this.toArray();
		E[] set2 = (E[])set.toArray();
		E[] result = (E[])SetOperations.difference(set1, set2);
		return new MathSet<E>(result);
	}
        
	/**
         *@return a new MathSet that is the difference of the two given sets
         */
        public static <T> Set<T> difference(Set<? extends T> set1, Set<? extends T> set2) {
		MathSet<T> mset1 = new MathSet<T>(set1);
		return mset1.difference(set2);
	}

	/**
         *@return a new MathSet of two-element vectors that is the cartesian product of this set with the given set
         */
        public MathSet<Vector<E>> product(Set<? extends E> set) {
		MathSet<Vector<E>> result = new MathSet<Vector<E>>();
		for (E e1 : this) {
			for (E e2 : set) {
				Vector<E> tuple = new Vector<E>();
				tuple.add(e1);
				tuple.add(e2);
				result.add(tuple);
			}
		}
		return result;
	}

	/**
         *@return a new MathSet of two-element vectors that is the cartesian product of the given sets
         */
        public static <T> Set<Vector<T>> product(Set<? extends T> set1, Set<? extends T>... sets) {
		Set<Vector<T>> result = null;
		MathSet<T> mset1 = new MathSet<T>(set1);
		for (Set<? extends T> set : sets) {
			if (result == null) {
				result = mset1.product(set);
			}
			else {
				result = product(result, set);
			}
		}
		return result;
	}
	
	private static <T> Set<Vector<T>> product(Set<Vector<T>> tupleSet, Set<? extends T> set) {
		MathSet<Vector<T>> result = new MathSet<Vector<T>>();
		for (Vector<T> tuple : tupleSet) {
			Vector<T> tupleClone = null;
			for (T elem : set) {
					tupleClone = (Vector<T>)tuple.clone();
					tupleClone.add(elem);
					result.add(tupleClone);
			}
		}
		return result;
	}

	/**
	 *@return true if set is a subset of this object
	 */
	public boolean isSubset(Set<? extends E> set) {
		E[] set1 = (E[])this.toArray();
		E[] set2 = (E[])set.toArray();
		return SetOperations.isSubset(set2, set1);
	}
	
	/**
	 *@return true if set1 is a subset of set2
	 */
	public static <T> boolean isSubset(Set<? extends T> set1, Set<? extends T> set2) {
		MathSet<T> mset1 = new MathSet<T>(set2);
		return mset1.isSubset(set1);
	}

	/**
	 *@return the set of all subsets of this set, except for the empty set
	 */
	public MathSet<Set<E>> parts() {
		MathSet<Set<E>> result = new MathSet<Set<E>>();
		E[] elements = (E[])this.toArray();
		SubsetGenerator subsetGen = new SubsetGenerator(this.size());
		int[] subsetIndices;
		while ((subsetIndices = subsetGen.nextSet()) != null) {
			MathSet<E> subset = new MathSet<E>();
			for (int index : subsetIndices) {
				subset.add(elements[index]);
			}
			result.add(subset);
		}
		return result;
	}
	
	/**
	 *@return the set of all subsets of the given set, except for the empty set
	 */
	public static <T> Set<Set<T>> parts(Set<T> set) {
		MathSet<Set<T>> result = new MathSet<Set<T>>();
		T[] elements = (T[])set.toArray();
		SubsetGenerator subsetGen = new SubsetGenerator(set.size());
		int[] subsetIndices;
		while ((subsetIndices = subsetGen.nextSet()) != null) {
			MathSet<T> subset = new MathSet<T>();
			for (int index : subsetIndices) {
				subset.add(elements[index]);
			}
			result.add(subset);
		}
		return result;
	}

	/**
	 *Seeds the random objects used to generate random samples to the given value. If this method is not called, the 
	 *seed used is the current system time.
	 */
	public void seedRandom(long seed) {
		sampleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		singleOrderGen = new RandomOrderGenerator(System.currentTimeMillis());
		viewRand = new Random(System.currentTimeMillis());
	}

	/**
         *@return a random sample of the given size from the elements of the set
         */
        public MathSet<E> getRandomSample(int size) {
		MathSet<E> ret = new MathSet<E>();
		E[] elements = (E[])this.toArray();
		int[] order = sampleOrderGen.generate(this.size());
		for (int i = 0; i < size; i++) {
			ret.add(elements[order[i]]);
		}
		return ret;
	}

	/**
	 *@return an element of the set chosen at random, with replacement
	 */
	public E viewRandomElement() {
		E[] elements = (E[])this.toArray();
		return elements[viewRand.nextInt(this.size())];
	}

	/**
	 *@return An element of the set chosen at random, without replacement. If called successively, it will return all 
	 *elements of the set, but the original set content will not be modified.
	 */
	public E takeRandomElement() {
		if (singleSamples == 0) {
			singleOrderGen.generate(this.size());
		}
		E[] elements = (E[])this.toArray();
		E ret = elements[singleOrderGen.nextIndex()];
		singleSamples++;
		if (singleSamples == this.size()) {
			singleSamples = 0;
		}
		return ret;
	}	
}
