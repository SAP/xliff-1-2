package com.sap.mlt.xliff12.impl.element.structural;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sap.mlt.xliff12.api.attribute.NonXliffAttribute;
import com.sap.mlt.xliff12.api.base.TextFragment;
import com.sap.mlt.xliff12.api.exception.ConstraintViolationException;
import com.sap.mlt.xliff12.impl.attribute.IdImpl;
import com.sap.mlt.xliff12.impl.attribute.NonXliffAttributeImpl;
import com.sap.mlt.xliff12.impl.attribute.TsImpl;
import com.sap.mlt.xliff12.impl.attribute.XmlLangImpl;
import com.sap.mlt.xliff12.impl.element.inline.XImpl;
import com.sap.mlt.xliff12.impl.text.TextImpl;
import com.sap.mlt.xliff12.test.util.Utils;

@SuppressWarnings("deprecation")
public class SourceImplTest {

	static private final String XLIFF_12_NAMESPACE = "urn:oasis:names:tc:xliff:document:1.2";

	@Test
	public void testSourceImplListOfQextendsTextFragment() {
		SourceImpl source = createDefaultSource();
		assertEquals(createDefaultContent(), source.getContent());
	}

	@Test
	public void testSourceImplElement() {
		Document doc = Utils.createDocument();
		Element elem = doc.createElementNS(XLIFF_12_NAMESPACE, "source");
		List<TextFragment> content = createDefaultContent();
		for (TextFragment tf : content) {
			elem.appendChild(tf.asXmlNode(doc));
		}
		try {
			SourceImpl source = new SourceImpl(elem);
			assertEquals(createDefaultContent(), source.getContent());
		} catch (ConstraintViolationException e) {
			//$JL-EXC$
			fail("Did not expect ConstraintViolationException");
		}
	}

	@Test
	public void testGetChildren() {
		SourceImpl source = createDefaultSource();
		assertEquals(createDefaultContent(), source.getChildren());
	}

	@Test
	public void testContent() {
		SourceImpl source = createDefaultSource();
		assertEquals(createDefaultContent(), source.getContent());
		ArrayList<TextFragment> content = new ArrayList<TextFragment>();
		content.add(new TextImpl("text1"));
		content.add(new XImpl(new IdImpl("x1")));
		source.setContent(content);
		assertEquals(content, source.getContent());
	}

	@Test
	public void testNonXliffAttributes() {
		SourceImpl source = createDefaultSource();
		Collection<NonXliffAttribute> nonXliffAttributes = source
				.getNonXliffAttributes();
		assertTrue(nonXliffAttributes.isEmpty());

		ArrayList<NonXliffAttribute> nxas = new ArrayList<NonXliffAttribute>();
		nxas.add(new NonXliffAttributeImpl("ns", "pre", "name", "value"));
		nxas.add(new NonXliffAttributeImpl("ns", "pre", "name2", "value2"));
		source.setNonXliffAttributes(nxas);

		nonXliffAttributes = source.getNonXliffAttributes();
		assertEquals(new Integer(2), new Integer(nonXliffAttributes.size()));
		assertTrue(nonXliffAttributes.contains(new NonXliffAttributeImpl("ns",
				"pre", "name", "value")));
		assertTrue(nonXliffAttributes.contains(new NonXliffAttributeImpl("ns",
				"pre", "name2", "value2")));

		nxas.clear();
		nxas.add(new NonXliffAttributeImpl("ns", "pre", "name2", "value2"));
		nxas.add(new NonXliffAttributeImpl("ns2", "pre", "name2", "value2"));
		source.setNonXliffAttributes(nxas);

		nonXliffAttributes = source.getNonXliffAttributes();
		assertEquals(new Integer(2), new Integer(nonXliffAttributes.size()));
		assertTrue(nonXliffAttributes.contains(new NonXliffAttributeImpl("ns",
				"pre", "name2", "value2")));
		assertTrue(nonXliffAttributes.contains(new NonXliffAttributeImpl("ns2",
				"pre", "name2", "value2")));

		nxas.clear();
		source.setNonXliffAttributes(nxas);
		nonXliffAttributes = source.getNonXliffAttributes();
		assertTrue(nonXliffAttributes.isEmpty());
	}

	@Test
	public void testTs() {
		SourceImpl source = createDefaultSource();
		source.setTs(new TsImpl("abc"));
		assertEquals(new TsImpl("abc"), source.getTs());
		source.setTs(null);
		assertNull(source.getTs());
	}

	@Test
	public void testXmlLang() {
		SourceImpl source = createDefaultSource();
		source.setXmlLang(new XmlLangImpl("fr-CA"));
		assertEquals(new XmlLangImpl("fr-CA"), source.getXmlLang());
		source.setXmlLang(null);
		assertNull(source.getXmlLang());
	}

	@Test
	public void testGetPlainText() {
		SourceImpl source = createDefaultSource();
		assertEquals("sometext", source.getPlainText());
	}

	@Test
	public void testClone() {
		SourceImpl source = createDefaultSource();
		SourceImpl clone = (SourceImpl) source.clone();
		assertEquals(source, clone);
		assertNotSame(source, clone);
	}

	private SourceImpl createDefaultSource() {
		SourceImpl source = new SourceImpl(createDefaultContent());
		return source;
	}

	private List<TextFragment> createDefaultContent() {
		ArrayList<TextFragment> content = new ArrayList<TextFragment>();
		content.add(new TextImpl("sometext"));
		return content;
	}

}
