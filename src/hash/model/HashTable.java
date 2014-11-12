package hash.model;

import java.lang.reflect.Array;
//aksdjfalskjdfalsjdf
public class HashTable<T>
{
	private double loadFactor;
	private int loaded;
	private HashObject[] array;
	private boolean linearProbing;
	
	public HashTable(double loadFactor, boolean linearProbing)
	{
		this.loadFactor = loadFactor;
		loaded = 0;
		array = (HashObject[])Array.newInstance(HashObject.class, 7);
		this.linearProbing = linearProbing;
		initializeArray();
	}
	
	private void initializeArray()
	{
		for(int count = 0; count < array.length; count++)
		{
			array[count] = null;
		}
	}
	
	public T get(String key)
	{
		return array[hashBad(key, array.length)].data;
	}
	
	public void insert(String key, T data)
	{
		loaded++;
		insert(new HashObject(key, data), array);
	}
	
	private void insert(HashObject hash, HashObject[] targetArray)
	{
		if(((double)loaded / (double)targetArray.length) >= loadFactor)
		{
			increaseSize();
		}
		
		int index = hashBad(hash.key, targetArray.length);
		if(targetArray[index] == null)//open space
		{
			targetArray[index] = hash;
		}
		else //collision
		{
			targetArray[probe(index, targetArray)] = hash;
		}
	}
	
	private Integer probe(int index, HashObject[] targetArray)
	{	
		int attempt = 0;
		
		if(linearProbing) //linear
		{
			while(targetArray[index] != null)
			{
				attempt++;
				
				index = (index + 1) % targetArray.length;
				
				if(attempt > targetArray.length*2)
				{
					return null;
				}
			}
		}
		else //quadratic
		{
			while(targetArray[index] != null)
			{
				attempt++;
				
				index = (index + (attempt * attempt)) % targetArray.length;
				
				if(attempt > targetArray.length*2)
				{
					return null;
				}
			}
		}
		
		return index;
	}
	
	private void increaseSize()
	{
		HashObject[] bigArray = (HashObject[])Array.newInstance(HashObject.class, array.length*2);
		for(int count = 0; count < array.length; count++)
		{
			if(array[count] != null)
			{
				insert(array[count], bigArray);
			}
		}
		array = bigArray;
	}
	
	//---------------------Hash Functions--------------------------------------
	private int hashBad(String key, int length)
	{
		return Math.abs(key.hashCode() % length);
	}
	
//	private int hashOkay(String key, int length)
//	{
//		
//	}
//	
//	private int hashGood(String key, int length)
//	{
//		
//	}
	
	public void print()
	{
		for(int count = 0; count < array.length; count++)
		{
			HashObject current = array[count];
			
			if(current != null)
			{
				System.out.println(count + ": " + current.key + ", " + current.data);
			}
			else
			{
				System.out.println(count + ": ");
			}
		}
	}
	
	//------------------------Hash Object Class-------------------------------------
	private class HashObject
	{
		public T data;
		public String key;
		
		public HashObject(String key, T data)
		{
			this.data = data;
			this.key = key;
		}
	}
}
