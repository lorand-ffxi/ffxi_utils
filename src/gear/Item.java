package gear;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

public class Item {
	final private int id;
	final private String name;
	final private String description;
	final private Hashtable<String,String> fields;
	
	@Deprecated
	public Item(final int id, final String name, final String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		fields = null;
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
		fields = itemInfo;
	}
	
	public Item(final Hashtable<String,String> itemInfo, final String itemDescription) {
		id = Integer.parseInt(itemInfo.get("id"));
		name = WordUtils.capitalizeFully(itemInfo.get("enl"));
		description = itemDescription;
		fields = itemInfo;
	}

	public int getId() {				return id;}
	public String getName() {			return name;}
	public String getDescription() {	return description;}
	
	public List<Integer> getBitPacked(final String field) {
		String str = fields.get(field);
		if (str == null) {return null;}
		Integer num = Integer.parseInt(str);
		if (num == null) {return null;}
		List<Integer> bits = new ArrayList<>();
		
		int c = 0;
		while (num > 0) {
			if ((num % 2) > 0) {
				bits.add(c);
			}
			num /= 2;
			c++;
		}
		return bits;
	}
	
	public List<String> getJobs() {
		List<Integer> jobs = getBitPacked("jobs");
		if (jobs == null) {return null;}
		List<String> jobNames = new ArrayList<>();
		for (Integer jobid : jobs) {
			jobNames.add(Job.getJob(jobid).getEn());
		}
		return jobNames;
	}
	
	public String getJobString() {
		List<String> jobs = getJobs();
		if (jobs == null) {
			return null;
		}
		String jobString = "";
		for (String job : jobs) {
			if (jobString.length() > 1) {
				jobString += "/";
			}
			jobString += job;
		}
		return jobString;
	}
	
	public String getSlot() {
		List<Integer> slots = getBitPacked("slots");
		if (slots == null) {return null;}
		int slot = slots.get(0);
		return Slot.getSlot(slot).getEn();
	}
}