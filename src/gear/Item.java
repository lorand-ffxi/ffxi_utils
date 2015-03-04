package gear;

import java.util.Hashtable;

import org.apache.commons.lang3.text.WordUtils;

public class Item {
	final private int id;
	final private String name;
	final private String description;
	
	public Item(final int id, final String name, final String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Item(final Hashtable<String,String> itemInfo, final Hashtable<String,String> itemDescription) {
		String idsa = itemInfo.get("id");
		String idsb = itemDescription.get("id");
		if (!idsa.equals(idsb)) {
			throw new IllegalArgumentException("IDs don't match.");
		}
		id = Integer.parseInt(idsa);
		name = WordUtils.capitalizeFully(itemInfo.get("enl"));
		description = itemDescription.get("en");
	}
	
	public Item(final Hashtable<String,String> itemInfo, final String itemDescription) {
		id = Integer.parseInt(itemInfo.get("id"));
		name = WordUtils.capitalizeFully(itemInfo.get("enl"));
		description = itemDescription;
	}

	public int getId() {				return id;}
	public String getName() {			return name;}
	public String getDescription() {	return description;}
}