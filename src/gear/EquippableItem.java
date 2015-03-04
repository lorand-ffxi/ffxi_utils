package gear;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquippableItem extends Item {
	private String[] statNames = {"STR","DEX","VIT","AGI","INT","MND","CHR","Attack","Accuracy","Magic Accuracy","MAB"};
	private Hashtable<String,String> abbrev_map = new Hashtable<>();
	
	private enum EquipmentType {ARMOR,WEAPON};
	private EquipmentType type;
	private int STR,DEX,VIT,AGI,INT,MND,CHR;
	private int DMG,DEF,HP,MP;
	private Hashtable<String,Integer> stats;
	
	public EquippableItem(Hashtable<String, String> itemInfo, Hashtable<String, String> itemDescription) {
		super(itemInfo, itemDescription);
		populateStats();
	}
	public EquippableItem(Hashtable<String, String> itemInfo, String itemDescription) {
		super(itemInfo, itemDescription);
		populateStats();
	}
	public EquippableItem(int id, String name, String description) {
		super(id, name, description);
		populateStats();
	}

	public String getStats() {
		String statString = "";
		for (String stat : statNames) {
			int val = getStat(stat);
			if (val != 0) {
				if (statString.length() > 0) {
					statString += " | ";
				}
				String op = (val < 0) ? "" : "+";
				statString += stat + op + val;
			}
		}
		return statString;
	}
	
	public String getType() {
		switch (type) {
			case ARMOR:		return "Armor";
			case WEAPON:	return "Weapon";
			default:		return "None";
		}
	}
	
	public int getStat(final String stat) {
		//If it's a known stat...
		if (stats.containsKey(stat)) {
			return stats.get(stat);
		}
		//Or a known attribute...
		switch (stat) {
			case "STR":	return STR;		case "DEX":	return DEX;		case "VIT":	return VIT;
			case "AGI":	return AGI;		case "INT":	return INT;		case "MND":	return MND;
			case "CHR":	return CHR;		case "DMG":	return DMG;		case "DEF":	return DEF;
			case "HP":	return HP;		case "MP":	return MP;
		}
		//Otherwise, look for it
		int val = parseFor(stat);
		if (val != 0) {
			//And save it if it was found
			stats.put(stat, val);
		}
		return val;
	}
	
	private void populateStats() {
		abbrev_map.put("MAB","Magic Atk. Bonus");
		
		stats = new Hashtable<>();
		STR = parseFor("STR");	DEX = parseFor("DEX");	VIT = parseFor("VIT");
		AGI = parseFor("AGI");	INT = parseFor("INT");	MND = parseFor("MND");
		CHR = parseFor("CHR");	DMG = parseFor("DMG");	DEF = parseFor("DEF");
		HP = parseFor("HP");	MP = parseFor("MP");
		
		if (DMG > 0) {
			type = EquipmentType.WEAPON;
		} else {
			type = EquipmentType.ARMOR;
		}
	}
	
	private int parseFor(final String stat) {
		String lookfor = stat;
		if (abbrev_map.containsKey(stat)) {
			lookfor = abbrev_map.get(stat);
		}
		String d = getDescription().replaceAll("\\\\\"", " ");
		String pat = ".*?(?<!Magic )" + lookfor + "(?:[ :+]+|(-))(\\d+).*";
		Pattern p = Pattern.compile(pat);
		Matcher m = p.matcher(d);
		if (m.matches()) {
			String neg = m.group(1);
			String val = m.group(2);
			int ival = Integer.parseInt(val);
			if (neg != null) {
				ival *= -1;
			}
			//Ensure match isn't for an Avatar stat
			int av = d.indexOf("Avatar");
			String si = lookfor + ((ival < 0) ? "" : "+") + ival;
			int fv = d.indexOf(si);
			if (fv == -1) {
				si = lookfor + " " + ((ival < 0) ? "" : "+") + ival;
				fv = d.indexOf(si);
			}
			if ((av != -1) && (av < fv)) {	
				return 0;
			}
			return ival;
		}
		return 0;
	}
}