package au.gov.naa.digipres.xena.demo.orgx.test;

import java.util.Vector;

import au.gov.naa.digipres.xena.core.Xena;
import au.gov.naa.digipres.xena.kernel.XenaInputSource;
import au.gov.naa.digipres.xena.kernel.XenaException;
import au.gov.naa.digipres.xena.kernel.guesser.Guess;
import au.gov.naa.digipres.xena.kernel.normalise.NormaliserResults;
import java.io.IOException;
import java.io.File;

public class NormaliseTester {
	public static void main(String[] argv) throws XenaException, IOException {
		Xena xena = new Xena();
		// our foo jar will already be on the class path, so load it by name...
		Vector<String> pluginList = new Vector<String>();
        pluginList.add("au/gov/naa/digipres/xena/demo/foo");
        pluginList.add("au/gov/naa/digipres/xena/demo/orgx");
		xena.loadPlugins(pluginList);
		// set the base path to be the current working directory
		xena.setBasePath(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.dir"));
		// create the new input source
		File f = new File("example_file2.foo");
		XenaInputSource xis = new XenaInputSource(f);
		// guess it's type
		Guess fooGuess = xena.getBestGuess(xis);
		//print the guess...
		System.out.println("Here is the best guess returned by Xena: ");
		System.out.println(fooGuess.toString());
		System.out.println("-----------------------------------------");
		// normalise the file!
		NormaliserResults results = xena.normalise(xis);
		System.out.println("Here are the results of the normalisation:");
		System.out.println(results.toString());
		System.out.println("-----------------------------------------"); 
	}
}