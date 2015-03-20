package gear;

public enum Job {
	NONE(null,null),WAR("Warrior","WAR"),MNK("Monk","MNK"),
	WHM("White Mage","WHM"),BLM("Black Mage","BLM"),RDM("Red Mage","RDM"),
	THF("Thief","THF"),PLD("Paladin","PLD"),DRK("Dark Knight","DRK"),
	BST("Beastmaster","BST"),BRD("Bard","BRD"),RNG("Ranger","RNG"),
	SAM("Samurai","SAM"),NIN("Ninja","NIN"),DRG("Dragoon","DRG"),
	SMN("Summoner","SMN"),BLU("Blue Mage","BLU"),COR("Corsair","COR"),
	PUP("Puppetmaster","PUP"),DNC("Dancer","DNC"),SCH("Scholar","SCH"),
	GEO("Geomancer","GEO"),RUN("Rune Fencer","RUN"),MON("Monipulator","MON");
	
	private final String enl, en;
	Job(final String enl, final String en) {
		this.enl = enl;
		this.en = en;
	}

	public static Job getJob(final int id) {
		return values()[id];
	}
	
	public String getEnl() {return enl;}
	public String getEn() {return en;}
}