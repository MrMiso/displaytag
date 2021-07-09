/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.jsptests;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * The Class Displ230.
 */
public class Displ230Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-230.jsp";
    }

    /**
     * Check that model modifications made by table decorator specified with in the decorator property the table tag
     * show up in the csv export.
     *
     * @throws Exception
     *             any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        this.testWithPlacement("top");
        this.testWithPlacement("bottom");
        this.testWithPlacement("both");
    }

    /**
     * Test with placement.
     *
     * @param placement
     *            the placement
     *
     * @throws MalformedURLException
     *             the malformed URL exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws SAXException
     *             the SAX exception
     */
    private void testWithPlacement(final String placement) throws MalformedURLException, IOException, SAXException {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        request.setParameter("placement", placement);

        final WebResponse response = this.runner.getResponse(request);

        final HTMLElement[] elements = response.getElementsWithClassName("testitem");

        if (StringUtils.equals(placement, "top")) {
            Assert.assertEquals(2, elements.length);
            Assert.assertEquals("SPAN", elements[0].getTagName());
            Assert.assertEquals("TABLE", elements[1].getTagName());
        } else if (StringUtils.equals(placement, "bottom")) {
            Assert.assertEquals(2, elements.length);
            Assert.assertEquals("TABLE", elements[0].getTagName());
            Assert.assertEquals("SPAN", elements[1].getTagName());
        } else if (StringUtils.equals(placement, "both")) {
            Assert.assertEquals(3, elements.length);
            Assert.assertEquals("SPAN", elements[0].getTagName());
            Assert.assertEquals("TABLE", elements[1].getTagName());
            Assert.assertEquals("SPAN", elements[2].getTagName());
        }
    }

}