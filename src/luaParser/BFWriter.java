/**
* Author: Douglas Skrypa
*/

package luaParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This extension of java.io.BufferedWriter simply adds the ability to use
 * a generic Object as a parameter for the overloaded write() method.  The
 * new method calls the toString() method on the given Object, and then
 * passes the value to the normal write(String) method.
 *
 */
public class BFWriter extends BufferedWriter
{
	public BFWriter(final String file) throws IOException
	{
		super(new FileWriter(file));
	}

	public void write(final Object o) throws IOException
	{
		write(o.toString());
	}

	public static void quickWrite(final String fileLocation, final String contents) throws IOException
	{
		final BFWriter bw = new BFWriter(fileLocation);
		bw.write(contents);
		bw.close();
	}
}