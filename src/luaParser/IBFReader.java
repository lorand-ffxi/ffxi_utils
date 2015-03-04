/**
* Author: Douglas Skrypa
*/

package luaParser;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
* IBFReader provides a shortcut to retrieving the contents of a text file as an
* interator storing a String for each line of the given file.
*/
public class IBFReader implements Iterable<String>
{
	private final Iterator<String> iterator;
	
	public IBFReader(final String file) throws IOException
	{
		final List<String> contents = BFReader.quickRead(file);
		iterator = contents.iterator();
	}

	@Override
	public Iterator<String> iterator() {
		return iterator;
	}
}