/**
* Author: Douglas Skrypa
*/

package luaParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* This extension of java.io.BufferedReader adds the ability to retrieve the
* entire contents of a file at once in the form of a list, after which the file
* is closed.
*/
public class BFReader extends BufferedReader
{
	public BFReader(final String file) throws IOException
	{
		super(new FileReader(file));
	}

	public static List<String> quickRead(final String file) throws IOException
	{
		final List<String> lines = new ArrayList<>();
		final BFReader br = new BFReader(file);
		String l = null;
		while((l = br.readLine())!= null){
			if(l.length() > 0){
				lines.add(l);
			}
		}
		br.close();
		
		return lines;
	}
}