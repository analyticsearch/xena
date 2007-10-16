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

package au.gov.naa.digipres.xena.plugin.html.javatools.wget;

import java.net.*;
import java.util.regex.*;

public class WgetPatternURLValidator implements WgetURLValidator {
	String host;

	String hostPattern;

	Pattern hostPatternP;

	String protocolPattern;

	Pattern protocolPatternP;

	// boolean neverFollow;

	public WgetPatternURLValidator() {
	}

	public boolean isURLValid(URL url) {
		/*
		 * if (neverFollow) { return false; }
		 */
		if (host != null && !host.equals(url.getHost())) {
			return false;
		}
		if (hostPatternP != null && !hostPatternP.matcher(url.getHost()).matches()) {
			return false;
		}
		if (protocolPatternP != null && !protocolPatternP.matcher(url.getProtocol()).matches()) {
			return false;
		}
		return true;
	}

	public String getHostPattern() {
		return hostPattern;
	}

	public void setHostPattern(String hostPattern) {
		if (hostPattern != null) {
			hostPatternP = Pattern.compile(hostPattern, Pattern.CASE_INSENSITIVE);
		}
		this.hostPattern = hostPattern;
	}

	public String getProtocolPattern() {
		return protocolPattern;
	}

	public void setProtocolPattern(String protocolPattern) {
		if (protocolPattern != null) {
			protocolPatternP = Pattern.compile(protocolPattern, Pattern.CASE_INSENSITIVE);
		}
		this.protocolPattern = protocolPattern;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}
	/*
	 * public boolean isNeverFollow() { return neverFollow; } public void setNeverFollow(boolean neverFollow) {
	 * this.neverFollow = neverFollow; }
	 */

}
