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

package au.gov.naa.digipres.xena.plugin.html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

import au.gov.naa.digipres.xena.javatools.ClassName;
import au.gov.naa.digipres.xena.kernel.XenaException;
import au.gov.naa.digipres.xena.util.AbstractJdomNormaliser;

/**
 * Normaliser to convert HTML files into XHTML files. We rely on a couple of
 * external libraries configure to work together in the way that seems to have
 * proven most useful - TAGSOUP and JTIDY. TAGSOUP is a simple and well- written
 * HTML parser that does a good job of creating matching tags and so on. But it
 * falls down in not addressing the intricacies of XHTML and conversion. JTIDY
 * is a complex and kludgy tool to convert HTML into XHTML. However it tends to
 * be buggy and has lots of tricky edge conditions.
 * 
 * The fact is, converting HTML to XHMTL in a way that would allow it to
 * continue to render as it did originally is a very difficult if not impossible
 * mission. It may never be perfect, but if we spent a lot of time on it it may
 * be made a lot better than it is now.
 * 
 */
public class HtmlToXenaHtmlNormaliser extends AbstractJdomNormaliser {
	private static final String CONTENT_TYPE_ATT_VALUE = "Content-Type";
	private static final String CONTENT_TYPE_ATT_NAME = "content";
	private static final String HTTP_EQUIV_ATT_NAME = "http-equiv";

	public HtmlToXenaHtmlNormaliser() {
	}

	@Override
    public String getName() {
		return "HTML";
	}

	@Override
    public Element normalise(InputSource input) throws IOException, SAXException {
		Element rtn = null;
		try {
			rtn = normaliseTagSoup(input);
		} catch (TransformerConfigurationException x2) {
			throw new SAXException(x2);
		} catch (XenaException x2) {
			throw new SAXException(x2);
		} catch (JDOMException x2) {
			throw new SAXException(x2);
		}

		return rtn;
	}

	public Element normaliseTagSoup(final InputSource input) throws IOException, JDOMException, XenaException, SAXException,
	        TransformerConfigurationException {
		XMLReader tagsoupReader = new org.ccil.cowan.tagsoup.Parser();
		String ignoreBogonsFeature = "http://www.ccil.org/~cowan/tagsoup/features/ignore-bogons";
		tagsoupReader.setFeature(ignoreBogonsFeature, true);
		SAXTransformerFactory tf = (SAXTransformerFactory) TransformerFactory.newInstance();
		TransformerHandler writer = null;
		writer = tf.newTransformerHandler();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamResult streamResult = new StreamResult(out);
		writer.setResult(streamResult);

		// Use ContentTypeFilter to write out the content encoding meta tag, set to UTF-8
		ContentTypeFilter filter = new ContentTypeFilter();
		filter.setContentHandler(writer);

		tagsoupReader.setContentHandler(filter);
		tagsoupReader.parse(input);

		// This just helps with debugging - produces a temporary copy of the file which will be parsed by JDOM
		// byte[] bytes = out.toByteArray();
		// File outFile = new File("D:\\xena_data\\clean_destination\\" + "temp_html_" + ++OUTPUT_FILE_INDEX + ".html");
		// FileOutputStream outputFileStream = new FileOutputStream(outFile);
		// outputFileStream.write(bytes);
		// outputFileStream.flush();
		// outputFileStream.close();

		SAXBuilder sax = new SAXBuilder();
		sax.setValidation(false);
		sax.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) {
				int ind = systemId.lastIndexOf('/');
				if (0 < ind) {
					systemId = systemId.substring(ind + 1);
				}
				ClassLoader loader = normaliserManager.getPluginManager().getClassLoader();
				String resourceName = ClassName.joinPath(ClassName.classToPath(ClassName.packageComponent(getClass().getName())), systemId);
				return new InputSource(loader.getResourceAsStream(resourceName));
			}
		});

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		Reader reader = new InputStreamReader(in, "UTF-8");

		// Debugging version
		// FileInputStream in = new FileInputStream(outFile);
		// Reader reader = new InputStreamReader(in, "UTF-8");

		return sax.build(reader).detachRootElement();
	}

	/**
	 * This class is used to filter the HTML elements we are writing to the output XML.
	 * Specifically we are interested in the META elements. We want to write out the "Content-Type" meta tag and set it to UTF-8,
	 * as Internet Explorer will not automatically detect the encoding being used, and uses Western encoding by default, and will
	 * thus display weird characters (eg the nbsp character 160). We will write out this element whenever we have written the "head"
	 * element. However we also need to make sure that we don't duplicate the Content-Type meta element if this tag already existed
	 * in the original file.
	 *   
	 * created 27/04/2007
	 * html
	 * Short desc of class:
	 */
	private class ContentTypeFilter extends XMLFilterImpl {
		private boolean writtenContentHeader = false;
		private boolean skipNextMetaClose = false;

		/*
		 * (non-Javadoc)
		 * @see org.xml.sax.helpers.XMLFilterImpl#startElement(java.lang.String, java.lang.String, java.lang.String,
		 * org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
			if (localName.equalsIgnoreCase("head")) {
				super.startElement(uri, localName, qName, atts);

				// Write content-encoding element
				AttributesImpl metaAtts = new AttributesImpl();
				metaAtts.addAttribute(uri, HTTP_EQUIV_ATT_NAME, HTTP_EQUIV_ATT_NAME, "CDATA", CONTENT_TYPE_ATT_VALUE);
				metaAtts.addAttribute(uri, CONTENT_TYPE_ATT_NAME, CONTENT_TYPE_ATT_NAME, "CDATA", "text/html; charset=UTF-8");
				startElement(uri, "meta", "meta", metaAtts);
				endElement(uri, "meta", "meta");
			} else if (localName.equalsIgnoreCase("meta")) {
				String metaType = atts.getValue(HTTP_EQUIV_ATT_NAME);
				if (metaType != null && metaType.equalsIgnoreCase(CONTENT_TYPE_ATT_VALUE)) {
					// This the content-encoding element.
					if (!writtenContentHeader) {
						// We haven't already written the content-encoding header
						super.startElement(uri, localName, qName, atts);
						writtenContentHeader = true;
					} else {
						// If we have already written the content-encoding header, we don't want to write it out again.
						skipNextMetaClose = true;
					}
				} else {
					// A non-content encoding meta element is written as normal
					super.startElement(uri, localName, qName, atts);
				}
			} else {
				// A non-meta element is written as normal
				super.startElement(uri, localName, qName, atts);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see org.xml.sax.helpers.XMLFilterImpl#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (localName.equalsIgnoreCase("meta")) {
				if (!skipNextMetaClose) {
					// We haven't already written the content-encoding header
					super.endElement(uri, localName, qName);
				} else {
					// Only skip closing one meta element
					skipNextMetaClose = false;
				}
			} else {
				super.endElement(uri, localName, qName);
			}
		}
	}

}
