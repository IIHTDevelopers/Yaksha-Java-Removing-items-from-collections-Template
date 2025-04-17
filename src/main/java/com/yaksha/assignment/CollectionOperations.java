package com.yaksha.assignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionOperations {

	public static void main(String[] args) {
		// **Creating a List (ArrayList)**
		List<String> list = new ArrayList<>();

		// **Adding items to the List using add()**
		list.add("Apple");
		list.add("Banana");
		list.add("Cherry");
		System.out.println("List after adding items: " + list);

		// **Removing an item using remove()**
		list.remove("Banana");
		System.out.println("List after removing 'Banana': " + list);

		// **Looping through the List using an iterator**
		System.out.println("Looping through the List:");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		// **Finding the size of the List using size()**
		int size = list.size();
		System.out.println("Size of List: " + size);
	}
}
