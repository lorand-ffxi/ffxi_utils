package gear;

public enum Slot {
	MAIN("Main"),SUB("Sub"),RANGE("Range"),AMMO("Ammo"),
	HEAD("Head"),BODY("Body"),HANDS("Hands"),LEGS("Legs"),
	FEET("Feet"),NECK("Neck"),WAIST("Waist"),EAR1("Ear"),
	EAR2("Ear"),RING1("Ring"),RING2("Ring"),BACK("Back");
	
	private final String en;
	Slot(final String en){
		this.en = en;
	}
	
	public static Slot getSlot(final int id) {
		return values()[id];
	}
	
	public String getEn() {return en;}
}
