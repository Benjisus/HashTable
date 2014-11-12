package hash.controller;

import hash.model.HashTable;

public class Test
{

	public static void main(String[] args) 
	{
		HashTable<Integer> hash = new HashTable<Integer>(.9, true);
		
		hash.insert("Taylor", 456);
		hash.insert("Taylor", 789);
		hash.insert("Taylor", 123);
		
		hash.print();
	}

}
