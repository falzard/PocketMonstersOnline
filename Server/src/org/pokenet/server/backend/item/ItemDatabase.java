package org.pokenet.server.backend.item;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * The item database
 * @author shadowkanji
 * @author Nushio
 * @author ZombieBear
 */
@Root
public class ItemDatabase {
	@ElementMap
	private HashMap<Integer, Item> m_items;
	
	private static ItemDatabase m_instance;
	
	/**
	 * Adds an item to the database
	 * @param id
	 * @param i
	 */
	public void addItem(int id, Item i) {
		if(m_items == null)
			m_items = new HashMap<Integer, Item>();
		m_items.put(id, i);
	}
	
	/**
	 * Returns an item based on its id
	 * @param id
	 * @return
	 */
	public Item getItem(int id) {
		return m_items.get(id);
	}
	
	/**
	 * Returns an item based on its name
	 * @param name
	 * @return
	 */
	public Item getItem(String name) {
		Iterator<Item> it = m_items.values().iterator();
		Item i;
		while(it.hasNext()) {
			i = it.next();
			if(i.getName().equalsIgnoreCase(name))
				return i;
		}
		return null;
	}
	
	/**
	 * Reloads the database
	 */
	public void reinitialise() {
		Serializer serializer = new Persister();
		File source = new File("res/items.xml");
		try {
			m_instance = serializer.read(ItemDatabase.class, source);
			System.out.println("INFO: Items database loaded.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR: Item database could not be loaded.");
		}
	}
	
	/**
	 * Sets the instance
	 * @param i
	 */
	public void setInstance(ItemDatabase i) {
		m_instance = i;
	}
	
	/**
	 * Returns the instance of item database
	 * @return the instance of item database
	 */
	public static ItemDatabase getInstance() {
		return m_instance;
	}
	/**
	 * Returns the instance of items in the database
	 * @return the instance of items in the database
	 */
	public static List<Item> getCategoryItems(String category) {
		List<Item> itemList = new ArrayList<Item>();
		for(int i=0;i<=m_instance.m_items.size();i++){
			try{
				Item item = m_instance.m_items.get(i);
				if(item.getCategory().equals(category))
					itemList.add(item);
			}catch(Exception e){}
		}
		return itemList;
	}

	/**
	 * Returns the ids of the items that should be added to the shop
	 * @return the ids of the items that should be added to the shop
	 */
	public List<Integer> getShopItems(){
		List<Integer> shopItems = new ArrayList<Integer>();
		for (int i : m_items.keySet()){
			if (m_items.get(i).getShop() == 1)
				shopItems.add(i);
		}
		return shopItems;
	}
}
