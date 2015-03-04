package luaParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuaReader {
	private List<Hashtable<String, String>> elements;
	
	public static void main(final String[] args) {
		String path = "C:\\Program Files (x86)\\Windower4\\res\\monster_abilities.lua";
		LuaReader lr = new LuaReader(path);
		boolean first = true;
		Set<String> keys = null;
		for (Hashtable<String,String> ht : lr.getElements()){
			if (ht == null){
				continue;
			}
			if (ht.get("prefix").equals("\"/monsterskill\"")){
				if (first){
					keys = ht.keySet();
					for (String k : keys){
						System.out.print(k + "\t");
					}
					System.out.println();
					first = false;
				}
				for (String k : keys){
					System.out.print(ht.get(k) + "\t");
				}
				System.out.println();
			}
		}
	}
		
	public LuaReader(final String path) {
		elements = new ArrayList<>();
		try {
			IBFReader bfr = new IBFReader(path);
			for (String line : bfr){
				Hashtable<String,String> htl = parseLine(line); 
				if (htl != null) {
					elements.add(htl);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Hashtable<String, String> parseLine(String line) {
		Pattern p = Pattern.compile("^\\s+\\[\\d+\\]\\s+=\\s+\\{(.*)\\},$");
		Matcher m = p.matcher(line);
		if (m.matches()){
			Hashtable<String, String> tbl = new Hashtable<>();
			String content = m.group(1);
			List<String> toks = tokenize(content);
			for (String ele : toks) {
				String[] pair = ele.split("=");
				try{
					if (pair[1].startsWith("\"")) {
						tbl.put(pair[0], pair[1].substring(1, pair[1].length()-1));
					} else {
						tbl.put(pair[0], pair[1]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Invalid element in line: " + content + " | " + ele);
					printTokens(toks);
					//System.out.println("Invalid element: " + ele);
				}
			}
			return tbl;
		}
		return null;
	}
	
	private void printTokens(List<String> tokens) {
		String combined = "";
		for (String tok : tokens) {
			if (combined.length() > 0) {
				combined += " | ";
			}
			combined += tok;
		}
		System.out.println(combined);
	}
	
	private List<String> tokenize(final String line) {
		List<String> tokens = new ArrayList<>();
		Pattern p = Pattern.compile("([^=]+=(?=\")(\"(?:\\\"|[^\"])+?\",)|([^,]+,))(.*)");
		String remaining = line;
		while (remaining.length() > 0) {
			Matcher m = p.matcher(remaining);
			if (m.matches()) {
				tokens.add(decommafy(m.group(1)));
				remaining = m.group(4);
			} else {
				tokens.add(remaining);
				remaining = "";
			}
		}
		return tokens;
	}
	
	private String decommafy(final String s) {
		return s.substring(0,s.length()-1);
	}
	
	public List<Hashtable<String, String>> getElements(){
		return elements;
	}
}
