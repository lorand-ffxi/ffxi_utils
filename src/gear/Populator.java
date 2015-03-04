package gear;

import java.util.Hashtable;
import java.util.List;

import luaParser.LuaReader;

public class Populator {
	public static void main(final String[] args) {
		String p1 = "C:\\Program Files (x86)\\Windower4\\res\\items.lua";
		String p2 = "C:\\Program Files (x86)\\Windower4\\res\\item_descriptions.lua";
		LuaReader lr1 = new LuaReader(p1);
		LuaReader lr2 = new LuaReader(p2);
		List<Hashtable<String,String>> items = lr1.getElements();
		List<Hashtable<String,String>> descriptions = lr2.getElements();
		System.out.println("Number of items: " + items.size() + " | Number of descriptions: " + descriptions.size());
		
		Hashtable<Integer,Hashtable<String,String>> desc = new Hashtable<>();
		for (Hashtable<String,String> description : descriptions) {
			int id = Integer.parseInt(description.get("id"));
			desc.put(id, description);
		}
		
		Hashtable<String,EquippableItem> itemlist = new Hashtable<>();
		for (Hashtable<String,String> iht : items) {
			String category = iht.get("category");
			if (category.equals("Weapon") || category.equals("Armor")) {
				int id = Integer.parseInt(iht.get("id"));
				Hashtable<String,String> dht = desc.get(id);
				
				EquippableItem i;
				if (dht != null) {
					i = new EquippableItem(iht, desc.get(id));
				} else {
					i = new EquippableItem(iht, "");
				}
				itemlist.put(i.getName(), i);
			}
		}
		System.out.println("Items created: " + itemlist.size());
		
		String[] inames = {"Coeus","Hagondes Coat +1"};
		
		for (String iname : inames) {
			EquippableItem ei = itemlist.get(iname);
			//System.out.println(ei.getName() + ": " + ei.getDescription());
			System.out.println(ei.getName() + ": mDMG: " + ei.getStat("Magic Damage") + " | MAB:" + ei.getStat("Magic Atk. Bonus") + " | INT: " + ei.getStat("INT"));
		}
	}
	
	public static Hashtable<Integer,EquippableItem> getItems() {
		String p1 = "C:\\Program Files (x86)\\Windower4\\res\\items.lua";
		String p2 = "C:\\Program Files (x86)\\Windower4\\res\\item_descriptions.lua";
		LuaReader lr1 = new LuaReader(p1);
		LuaReader lr2 = new LuaReader(p2);
		List<Hashtable<String,String>> items = lr1.getElements();
		List<Hashtable<String,String>> descriptions = lr2.getElements();
		Hashtable<Integer,Hashtable<String,String>> desc = new Hashtable<>();
		for (Hashtable<String,String> description : descriptions) {
			int id = Integer.parseInt(description.get("id"));
			desc.put(id, description);
		}
		Hashtable<Integer,EquippableItem> itemlist = new Hashtable<>();
		for (Hashtable<String,String> iht : items) {
			String category = iht.get("category");
			if (category.equals("Weapon") || category.equals("Armor")) {
				int id = Integer.parseInt(iht.get("id"));
				Hashtable<String,String> dht = desc.get(id);
				
				EquippableItem i;
				if (dht != null) {
					i = new EquippableItem(iht, desc.get(id));
				} else {
					i = new EquippableItem(iht, "");
				}
				itemlist.put(id, i);
			}
		}
		return itemlist;
	}
}
