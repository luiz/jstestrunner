/***
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.caelum.jstestrunner;

import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;

class JavascriptTestCase extends TestCase {
	private final DomNode item;
	private final String errorXPath;

	JavascriptTestCase(String name, DomNode item, String errorXPath) {
		super(name);
		this.item = item;
		this.errorXPath = errorXPath;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void runBare() {
		List<HtmlParagraph> errorMessages = (List<HtmlParagraph>) item.getByXPath(errorXPath);
		StringBuilder builder = new StringBuilder();
		for (HtmlParagraph htmlParagraph : errorMessages) {
			builder.append(htmlParagraph.getTextContent());
		}
		if (errorMessages.size() > 0) {
			throw new AssertionFailedError(builder.toString());
		}
	}
}
