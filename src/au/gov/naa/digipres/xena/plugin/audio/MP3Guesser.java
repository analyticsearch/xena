/**
 * This file is part of Xena.
 * 
 * Xena is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 * 
 * Xena is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Xena; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * @author Andrew Keeling
 * @author Dan Spasojevic
 * @author Justin Waddell
 */

/*
 * Created on 21/06/2006 justinw5
 * 
 */
package au.gov.naa.digipres.xena.plugin.audio;

import java.io.IOException;

import au.gov.naa.digipres.xena.javatools.FileName;
import au.gov.naa.digipres.xena.kernel.XenaException;
import au.gov.naa.digipres.xena.kernel.XenaInputSource;
import au.gov.naa.digipres.xena.kernel.guesser.FileTypeDescriptor;
import au.gov.naa.digipres.xena.kernel.guesser.Guess;
import au.gov.naa.digipres.xena.kernel.guesser.Guesser;
import au.gov.naa.digipres.xena.kernel.guesser.GuesserManager;
import au.gov.naa.digipres.xena.kernel.type.Type;

public class MP3Guesser extends Guesser {

	private static final byte[][] mp3Magic = { {(byte) 0xFF, (byte) 0xFB, 0x30}, {'I', 'D', '3'}};
	private static final String[] mp3Extensions = {"mp3"};
	private static final String[] mp3Mime = {"audio/mp3", "audio/mpg", "audio/mpeg3", "audio/mpeg"};

	private FileTypeDescriptor[] descriptorArr = {new FileTypeDescriptor(mp3Extensions, mp3Magic, mp3Mime)};

	private Type type;

	/**
	 * @throws XenaException 
	 * 
	 */
	public MP3Guesser() {
		super();
	}

	@Override
	public void initGuesser(GuesserManager guesserManager) throws XenaException {
		this.guesserManager = guesserManager;
		type = getTypeManager().lookup(MP3Type.class);
	}

	@Override
    public Guess guess(XenaInputSource source) throws IOException, XenaException {
		Guess guess = new Guess(type);
		String type = source.getMimeType();

		// get the mime type...
		if (type != null && !type.equals("")) {
			for (int i = 0; i < descriptorArr.length; i++) {
				if (descriptorArr[i].mimeTypeMatch(type)) {
					guess.setMimeMatch(true);
					break;
				}
			}
		}

		// Get the extension...
		FileName name = new FileName(source.getSystemId());
		String extension = name.extenstionNotNull();

		boolean extMatch = false;
		if (!extension.equals("")) {
			for (int i = 0; i < descriptorArr.length; i++) {
				if (descriptorArr[i].extensionMatch(extension)) {
					extMatch = true;
					break;
				}
			}
		}
		guess.setExtensionMatch(extMatch);

		// Get the magic number.
		byte[] first = new byte[3];
		source.getByteStream().read(first);
		boolean magicMatch = false;

		for (int i = 0; i < descriptorArr.length; i++) {
			if (descriptorArr[i].magicNumberMatch(first)) {
				magicMatch = true;
				break;
			}
		}
		guess.setMagicNumber(magicMatch);

		return guess;
	}

	@Override
    public String getName() {
		return "MP3Guesser";
	}

	@Override
	protected Guess createBestPossibleGuess() {
		Guess guess = new Guess();
		guess.setMagicNumber(true);
		guess.setMimeMatch(true);
		guess.setExtensionMatch(true);
		return guess;
	}

	@Override
	public Type getType() {
		return type;
	}

}
