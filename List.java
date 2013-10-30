
/**
 * The List interface specifies four basic operations that we
 * want to be able to perform on a list of items.  We will
 * implement this interface in two different ways: using an
 * array list and a linked list.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface List<T>
{
   
    // Returns the item at index i.
    public T get(int i);
    
    // Replaces the item at index i with a new item
    public void set(int i, T newValue);
    
    // Adds a new item to the end of the list
    public void add(T newValue);
    
    // Removes the item at index i from the list
    public void remove(int i);
}
