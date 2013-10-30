/**
 * An implementation of the List interface that is facilitated through an array.
 * 
 * @author Gabriel Nahmias
 * @version 
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class ArrayList<E> /* extends List<E> */implements List<E>, Cloneable, Serializable {
    private static final long serialVersionUID = 8683452581122892189L;

    // The default capacity for new ArrayLists.
    private static final int DEFAULT_CAPACITY = 10;

    // The number of elements in this list.
    private int size, modCount;

    // Data container.
    private transient E[] data;

    /*
     * Constructs a new ArrayList with the supplied initial capacity.
     * @param capacity initial capacity of this ArrayList
     * @throws IllegalArgumentException if capacity is negative
     */
    public ArrayList(int capacity) {
        // Must explicitly check, to get correct exception.
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        data = (E[]) new Object[capacity];
    }

    /*
     * Construct a new ArrayList with the default capacity (16).
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    private class myIterator implements Iterator<E> {
        private int nextIndex = 0;
        private int lastIndexReturned = -1;
      
        public boolean hasNext() {
            return(nextIndex < size);
        }
      
        public E next() {
            if (hasNext()) {
                lastIndexReturned = nextIndex;
                nextIndex++;
                return data[lastIndexReturned];
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (lastIndexReturned >= 0) {
                ArrayList.this.remove(lastIndexReturned);
                nextIndex--;
                lastIndexReturned = -1;
	        
            } else {
                throw new IllegalStateException();
            }   
        }
    }

    /*
     * Construct a new ArrayList, and initialize it with the elements
     * in the supplied ArrayList. The initial capacity is 110% of the
     * ArrayList's size.
     *
     * @param c the collection whose elements will initialize this list
     * @throws NullPointerException if c is null
     */
    public ArrayList(ArrayList<? extends E> c) {
        this((int) (c.size() * 1.1f));
        append(c);
    }
  
    public String toString() {
        String result = "[";

        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                result += data[i].toString();
            } else {
                result += (data[i] + ", ");
            }
        }
      
        result += "]";
        return result;
    }

    /*
     * Appends the supplied element to the end of this list.
     * The element, e, can be an object of any type or null.
     *
     * @param e the element to be appended to this list
     * @return true, the add will always succeed
     */
    public void add(E e) {
        modCount++;
        if (size == data.length) {
            ensureCapacity(size + 1);
        }
        data[size++] = e;
        // return true;
    }
  
    /**
     * Non-proprietary Methods
     */
  
    /*
     * Adds the supplied element at the specified index, shifting all
     * elements currently at that index or higher one to the right.
     * The element, e, can be an object of any type or null.
     *
     * @param index the index at which the element is being added
     * @param e the item being added
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
    public void add(int index, E e) {
        checkBoundInclusive(index);
        modCount++;
        if (size == data.length) {
            ensureCapacity(size + 1);
        }
        if (index != size) {
            System.arraycopy(data, index, data, index + 1, size - index);
        }
        data[index] = e;
        size++;
    }
  
    public int addAll(ArrayList a) {
        int total = 0;

        return total() + a.total();
    }

    public int total() {
        int total = 0;

        for (int i = 0; i < size(); i++) {
            int curr = (Integer) get(i);

            total += curr;
        }
        return total;
    }

    /*
     * Trims the capacity of this List to be equal to its size;
     * a memory saver.
     */
    public void trimToSize() {
        // Not a structural change from the perspective of iterators on this list,
        // so don't update modCount.
        if (size != data.length) {
            E[] newData = (E[]) new Object[size];

            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    /*
     * Guarantees that this list will have at least enough capacity to
     * hold minCapacity elements. This implementation will grow the list to
     * max(current * 2, minCapacity) if (minCapacity &gt; current). The JCL says
     * explictly that "this method increases its capacity to minCap", while
     * the JDK 1.3 online docs specify that the list will grow to at least the
     * size specified.
     *
     * @param minCapacity the minimum guaranteed capacity
     */
    public void ensureCapacity(int minCapacity) {
        int current = data.length;

        if (minCapacity > current) {
            E[] newData = (E[]) new Object[Math.max(current * 2, minCapacity)];

            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }
  
    /*
     * Returns the number of elements in this list.
     *
     * @return the list size
     */
    public int size() {
        return size;
    }

    /*
     * Checks if the list is empty.
     *
     * @return true if there are no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     * Returns true iff element is in this ArrayList.
     *
     * @param e the element whose inclusion in the List is being tested
     * @return true if the list contains e
     */
    public boolean contains(Object e) {
        return indexOf(e) != -1;
    }

    /*
     * Returns the lowest index at which element appears in this List, or
     * -1 if it does not appear.
     *
     * @param e the element whose inclusion in the List is being tested
     * @return the index where e was found
     */
    public int indexOf(Object e) {
        for (int i = 0; i < size; i++) {
            if (e.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Returns the highest index at which element appears in this List, or
     * -1 if it does not appear.
     *
     * @param e the element whose inclusion in the List is being tested
     * @return the index where e was found
     */
    public int lastIndexOf(Object e) {
        for (int i = size - 1; i >= 0; i--) {
            if (e.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Creates a shallow copy of this ArrayList (elements are not cloned).
     *
     * @return the cloned object
     */
    public Object clone() {
        ArrayList<E> clone = null;

        try {
            clone = (ArrayList<E>) super.clone();
            clone.data = (E[]) data.clone();
        } catch (CloneNotSupportedException e) {// Impossible to get here.
        }
        return clone;
    }

    /*
     * Returns an Object array containing all of the elements in this ArrayList.
     * The array is independent of this list.
     *
     * @return an array representation of this list
     */
    public Object[] toArray() {
        E[] array = (E[]) new Object[size];

        System.arraycopy(data, 0, array, 0, size);
        return array;
    }

    /*
     * Returns an Array whose component type is the runtime component type of
     * the passed-in Array.  The returned Array is populated with all of the
     * elements in this ArrayList.  If the passed-in Array is not large enough
     * to store all of the elements in this List, a new Array will be created
     * and returned; if the passed-in Array is <i>larger</i> than the size
     * of this List, then size() index will be set to null.
     *
     * @param a the passed-in Array
     * @return an array representation of this list
     * @throws ArrayStoreException if the runtime type of a does not allow
     *         an element in this list
     * @throws NullPointerException if a is null
     */
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        } else if (a.length > size) {
            a[size] = null;
        }
        System.arraycopy(data, 0, a, 0, size);
        return a;
    }

    /*
     * Retrieves the element at the user-supplied index.
     *
     * @param index the index of the element we are fetching
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public E get(int index) {
        checkBoundExclusive(index);
        return data[index];
    }

    /*
     * Sets the element at the specified index.  The new element, e,
     * can be an object of any type or null.
     *
     * @param index the index at which the element is being set
     * @param e the element to be set
     * @return the element previously at the specified index
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= 0
     */
    public void set(int index, E e) {
        checkBoundExclusive(index);
        E result = data[index];

        data[index] = e;
        // return result;
    }
  
    /*
     * Removes the element at the user-supplied index.
     *
     * @param index the index of the element to be removed
     * @return the removed Object
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    public void remove(int index) {
        checkBoundExclusive(index);
        E r = data[index];

        modCount++;
        if (index != --size) {
            System.arraycopy(data, index + 1, data, index, size - index);
        }
        // Aid for garbage collection by releasing this pointer.
        data[size] = null;
        // return r;
    }

    /*
     * Removes all elements from this List
     */
    public void clear() {
        if (size > 0) {
            modCount++;
            // Allow for garbage collection.
            Arrays.fill(data, 0, size, null);
            size = 0;
        }
    }
  
    /*
     * Add each element in the supplied ArrayList to this List. It is undefined
     * what happens if you modify the list while this is taking place; for
     * example, if the collection contains this list.  c can contain objects
     * of any type, as well as null values.
     *
     * @param c a ArrayList containing elements to be added to this List
     * @return true if the list was modified, in other words c is not empty
     * @throws NullPointerException if c is null
     */
    public boolean append(ArrayList<? extends E> c) {
        return append(size(), c);
    }

    /*
     * Add all elements in the supplied collection, inserting them beginning
     * at the specified index.  c can contain objects of any type, as well
     * as null values.
     *
     * @param index the index at which the elements will be inserted
     * @param c the ArrayList containing the elements to be inserted
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; 0
     * @throws NullPointerException if c is null
     */
    public boolean append(int index, ArrayList<? extends E> c) {
        checkBoundInclusive(index);
        Iterator<? extends E> itr = c.iterator();
        int csize = c.size();

        modCount++;
        if (csize + size > data.length) {
            ensureCapacity(size + csize);
        }
        int end = index + csize;

        if (size > 0 && index != size) {
            System.arraycopy(data, index, data, end, size - index);
        }
        size += csize;
        for (; index < end; index++) {
            data[index] = itr.next();
        }
        return csize > 0;
        // return csize > 0;
    }

    /*
     * Removes all elements in the half-open interval [fromIndex, toIndex).
     * Does nothing when toIndex is equal to fromIndex.
     *
     * @param fromIndex the first index which will be removed
     * @param toIndex one greater than the last index which will be removed
     * @throws IndexOutOfBoundsException if fromIndex &gt; toIndex
     */
    protected void removeRange(int fromIndex, int toIndex) {
        int change = toIndex - fromIndex;

        if (change > 0) {
            modCount++;
            System.arraycopy(data, toIndex, data, fromIndex, size - toIndex);
            size -= change;
        } else if (change < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    /*
     * Checks that the index is in the range of possible elements (inclusive).
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if index &gt; size
     */
    private void checkBoundInclusive(int index) {
        // Implementation note: we do not check for negative ranges here, since
        // use of a negative index will cause an ArrayIndexOutOfBoundsException,
        // a subclass of the required exception, with no effort on our part.
        if (index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }
    }

    /*
     * Checks that the index is in the range of existing elements (exclusive).
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if index &gt;= size
     */
    private void checkBoundExclusive(int index) {
        // Implementation note: we do not check for negative ranges here, since
        // use of a negative index will cause an ArrayIndexOutOfBoundsException,
        // a subclass of the required exception, with no effort on our part.
        if (index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }
    }
    
    public Iterator<E> iterator() {
        return new myIterator();
    }

    /*
     * Remove from this list all elements contained in the given collection.
     * This is not public, due to Sun's API, but this performs in linear
     * time while the default behavior of AbstractList would be quadratic.
     *
     * @param c the collection to filter out
     * @return true if this list changed
     * @throws NullPointerException if c is null
     */
    boolean removeAll(ArrayList<?> c) {
        int i;
        int j;

        for (i = 0; i < size; i++) {
            if (c.contains(data[i])) {
                break;
            }
        }
        if (i == size) {
            return false;
        }

        modCount++;
        for (j = i++; i < size; i++) {
            if (!c.contains(data[i])) {
                data[j++] = data[i];
            }
        }
        size -= i - j;
        return true;
    }

    /*
     * Retain in this vector only the elements contained in the given collection.
     * This is not public, due to Sun's API, but this performs in linear
     * time while the default behavior of AbstractList would be quadratic.
     *
     * @param c the collection to filter by
     * @return true if this vector changed
     * @throws NullPointerException if c is null
     * @since 1.2
     */
    boolean retainAll(ArrayList<?> c) {
        int i;
        int j;

        for (i = 0; i < size; i++) {
            if (!c.contains(data[i])) {
                break;
            }
        }
        if (i == size) {
            return false;
        }

        modCount++;
        for (j = i++; i < size; i++) {
            if (c.contains(data[i])) {
                data[j++] = data[i];
            }
        }
        size -= i - j;
        return true;
    }

    /*
     * Serializes this object to the given stream.
     *
     * @param s the stream to write to
     * @throws IOException if the underlying stream fails
     * @serialData the size field (int), the length of the backing array
     *             (int), followed by its elements (Objects) in proper order.
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        // The 'size' field.
        s.defaultWriteObject();
        // We serialize unused list entries to preserve capacity.
        int len = data.length;

        s.writeInt(len);
        // it would be more efficient to just write "size" items,
        // this need readObject read "size" items too.
        for (int i = 0; i < size; i++) {
            s.writeObject(data[i]);
        }
    }

    /*
     * Deserializes this object from the given stream.
     *
     * @param s the stream to read from
     * @throws ClassNotFoundException if the underlying stream fails
     * @throws IOException if the underlying stream fails
     * @serialData the size field (int), the length of the backing array
     *             (int), followed by its elements (Objects) in proper order.
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        // the `size' field.
        s.defaultReadObject();
        int capacity = s.readInt();

        data = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            data[i] = (E) s.readObject();
        }
    }
}
