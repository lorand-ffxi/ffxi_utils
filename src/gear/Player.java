package gear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import luaParser.BFWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Player {
	@SuppressWarnings("unused")
	private static class ItemInfo {
		private final String name;
		private final String augs;
		ItemInfo(final String name, final String augs) {
			this.name = name;
			if (augs != null) {
				this.augs = augs.replaceAll("quote","\"");
			} else {
				this.augs = null;
			}
		}
		public String getName() {return name;}
		public String getAugs() {return augs;}
	}
	
	public static void main(final String[] args) throws Exception {
		//Hashtable<Integer,String> itemInfo = Player.getGearInfo("C:\\Program Files (x86)\\Windower4\\addons\\info\\data\\settings.xml");
		Hashtable<Integer,ItemInfo> itemInfo = Player.getGearInfo("C:\\linux\\home\\user\\ffxi\\settings.xml");
		Player p = new Player();
		p.populateItemList(itemInfo);
		//p.printItems();
		p.saveItems("C:\\linux\\home\\user\\ffxi\\ladygear3.txt");
		
		//BFWriter bfw = new BFWriter("C:\\linux\\home\\user\\ffxi\\ladygear.txt");
		
		//EquippableItem ei = Populator.getItems().get(21183);
		//System.out.println(ei.getName() + " " + ei.getDescription());
	}
	
	List<EquippableItem> items;
	
	public Player() {
		items = new ArrayList<>();
	}
	
	public void populateItemList(final Hashtable<Integer,ItemInfo> itemInfo) {
		Hashtable<Integer,EquippableItem> item_list = Populator.getItems();
		Set<Integer> idset = itemInfo.keySet();
		Integer[] ids = new Integer[idset.size()];
		idset.toArray(ids);
		Arrays.sort(ids);
		for (int id : ids) {
			ItemInfo ii = itemInfo.get(id);
			String augs = ii.getAugs();
			if (item_list.containsKey(id)) {
				EquippableItem ei = item_list.get(id);
				ei.setAugments(augs);
				items.add(ei);
			}
		}
	}
	
	public void saveItems(final String path) throws IOException {
		BFWriter bfw = new BFWriter(path);
		for (EquippableItem ei : items) {
			String augs = ei.getAugments();
			String paugs = (augs != null) ? "\t" + augs : "";
			bfw.write(ei.getName() + "\t" + ei.getSlot() + "\t" + ei.getJobString() + "\t" + ei.getDescription() + paugs + "\n");
		}
		bfw.close();
		System.out.println("Done.");
	}
	
	public void printItems() {
		for (EquippableItem ei : items) {
			String augs = ei.getAugments();
			String paugs = (augs != null) ? " | " + augs : "";
			System.out.println("[" + ei.getId() + "] " + ei.getName() + " : " + ei.getStats() + paugs);
		}
	}
	
	public static Hashtable<Integer,ItemInfo> getGearInfo(final String path) throws Exception {
		Hashtable<Integer,ItemInfo> gearInfo = new Hashtable<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(path);
		NodeList settings = doc.getDocumentElement().getChildNodes();
		NodeList global = settings.item(1).getChildNodes();
		for (int n = 0; n < global.getLength(); n++) {
			Node node = global.item(n);
			if (node.getNodeType() == 1) {
				NodeList nl = node.getChildNodes();
				int nodes = nl.getLength();
				Node id, name, aug = null;
				if (nodes == 7) {			//There are augments
					id = nl.item(3).getChildNodes().item(0);
					name = nl.item(5).getChildNodes().item(0);
					aug = nl.item(1).getChildNodes().item(0);
				} else if (nodes == 5) {	//There are no augments
					id = nl.item(1).getChildNodes().item(0);
					name = nl.item(3).getChildNodes().item(0);
				} else {
					System.out.println("Error: Unknown info type - there are " + nodes + " fields.");
					continue;
				}
				
				String sid = id.getNodeValue();
				int iid = Integer.parseInt(sid);
				String sname = name.getNodeValue();
				String saugs = (aug == null) ? null : aug.getNodeValue();
				gearInfo.put(iid,new ItemInfo(sname, saugs));
			}
		}
		return gearInfo;
	}
	
	
}