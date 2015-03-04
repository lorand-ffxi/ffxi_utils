package gear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Player {
	public static void main(final String[] args) throws Exception {
		Hashtable<Integer,String> itemInfo = Player.getGearInfo("C:\\Program Files (x86)\\Windower4\\addons\\info\\data\\settings.xml");
		Player p = new Player();
		p.populateItemList(itemInfo);
		p.printItems();
		
		//EquippableItem ei = Populator.getItems().get(21183);
		//System.out.println(ei.getName() + " " + ei.getDescription());
	}
	
	List<EquippableItem> items;
	
	public Player() {
		items = new ArrayList<>();
	}
	
	public void populateItemList(final Hashtable<Integer,String> itemInfo) {
		Hashtable<Integer,EquippableItem> item_list = Populator.getItems();
		Set<Integer> idset = itemInfo.keySet();
		Integer[] ids = new Integer[idset.size()];
		idset.toArray(ids);
		Arrays.sort(ids);
		for (int id : ids) {
			if (item_list.containsKey(id)) {
				items.add(item_list.get(id));
			}
		}
	}
	
	public void printItems() {
		for (EquippableItem ei : items) {
			System.out.println("[" + ei.getId() + "] " + ei.getName() + " : " + ei.getStats());
		}
	}
	
	public static Hashtable<Integer,String> getGearInfo(final String path) throws Exception {
		Hashtable<Integer,String> gearInfo = new Hashtable<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(path);
		NodeList settings = doc.getDocumentElement().getChildNodes();
		NodeList global = settings.item(1).getChildNodes();
		for (int n = 0; n < global.getLength(); n++) {
			Node node = global.item(n);
			if (node.getNodeType() == 1) {
				NodeList nl = node.getChildNodes();
				Node id = nl.item(1).getChildNodes().item(0);
				if (id != null) {
					String sid = id.getNodeValue();
					//TODO: Fix augment detection
					try {
						int iid = Integer.parseInt(sid);
						Node name = nl.item(3).getChildNodes().item(0);
						String sname = name.getNodeValue();
						gearInfo.put(iid,sname);
					} catch (Exception e) {}
				}
			}
		}
		return gearInfo;
	}
	
	
}