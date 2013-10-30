/**
 * Demos of ArrayList's functionality.
 * 
 * @author  Gabriel Nahmias
 * @date    10/24/13
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.*;

public class ArrayListClient
{
    public static void main (String args[]){
        ArrayList<Integer> myList = new ArrayList<Integer>(),
                             myList2 = new ArrayList<Integer>();
        Scanner input = new Scanner(System.in);
        // Add to lists.
        for (int i = 0; i < 5; i++) {
            int num = 0;
            System.out.format("Enter #%d for first list: ", (i + 1));
            while (!input.hasNextInt()) {
                System.out.print("Please input a number: ");
                input.next();
            }
            num = input.nextInt();
            myList.add(num);
            System.out.format("Enter #%d for second list: ", (i + 1));
            while (!input.hasNextInt()) {
                System.out.print("Please input a number: ");
                input.next();
            }
            num = input.nextInt();
            myList2.add(num);
        }
        // Add the two lists together and show the result.
        System.out.format("The result of adding the two lists' total values is %d.\n", myList.addAll((myList2)));
        System.out.format("The first list does%s contain the number 1.\n", ((myList.contains(1)) ? "" : "n't" ));
        System.out.println("First list: " + myList);
        System.out.println("Second list: " + myList2);
        //System.out.println("Retaining all elements in first list that are in the second...");
        //myList.retainAll(myList2);
        //System.out.println("First list: " + myList);
        //System.out.println("Second list: " + myList2);
        
        /*
        ArrayList<Integer> myList = new ArrayList<Integer>();
        try{
       
            myList.add(5);
            myList.add(7);
            myList.add(8);
            myList.add(8);
            myList.add(8);
            myList.add(8);
            myList.add(8);
            myList.add(9);

            Iterator myIterator = myList.iterator();
            
            myIterator.next();
            myIterator.remove();
            
        }catch(NoSuchElementException e){
            System.out.println("You're doing it wrong...");
            System.out.println(myList);
        }
        
        ArrayList<String> list2 = new ArrayList<String>();
        try{
            list2.add("Line1");
            list2.add("Line2");
            System.out.println(list2);
            list2.remove(0);
            list2.set(0,"Line5555");
            System.out.println(list2);
        
        }catch(NoSuchElementException e){
            System.out.println("You're doing it wrong...");
            System.out.println(myList);
        }*/
    }
}
