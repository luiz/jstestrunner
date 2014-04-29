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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JavascriptTestSuite extends TestSuite {

	private static final Log LOG = LogFactory.getLog(JavascriptTestSuite.class);

	public static TestSuite suite() throws IOException {
		return new JavascriptTestSuite();
	}

	/**
	 * Creates the suite with all detected Javascript tests.
	 *
	 * @throws IOException If there's a problem accessing one of the Javascript test pages found.
	 */
	public JavascriptTestSuite() throws IOException {
		this.setName(getSuiteName());
		File[] testPages = findTestPages();
		if (LOG.isInfoEnabled()) {
			logTestPages(testPages);
		}
		WebClient client = createWebClient();
		for (File page : testPages) {
			HtmlPage htmlPage = client.getPage(page.toURI().toURL());
			addTestsInPageToSuite(htmlPage);
		}
	}

	/**
	 * Extend to configure. The default is "Javascript Test Suite".
	 *
	 * @return The name of your test suite
	 */
	protected String getSuiteName() {
		return "Javascript Test Suite";
	}

	/**
	 * Extend to configure the creation of a HTMLUnit web client. The default is a Firefox 3 WebClient.
	 *
	 * @return A configured web client
	 */
	protected WebClient createWebClient() {
		return new WebClient(BrowserVersion.FIREFOX_24);
	}

	/**
	 * Extend to configure the root folder of your Javascript test pages.
	 * The default is a "test" folder in the working directory.
	 *
	 * @return A File object representing the Javascript test pages root folder
	 */
	protected File testPagesFolder() {
		return new File("test");
	}

	/**
	 * Extend to configure a filter to select the Javascript test pages inside the Javascript test page folder.
	 * The default is to accept only files with ".html" extension.
	 *
	 * @return A FileFilter that accepts the Javascript test page files
	 */
	protected FileFilter testPageFileFilter() {
		return new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".html");
			}
		};
	}

	/**
	 * Extend to configure the XPath used to identify the failed tests inside a Javascript test page.
	 * The default is "./*[contains(@class, 'error')]", i.e, elements with the "error" class.
	 *
	 * @return A XPath that identifies the failed tests
	 */
	protected String failedTestXPath() {
		return "./*[contains(@class, 'error')]";
	}

	/**
	 * Extend to configure how to identify the names of the tests in this page.
	 * The default is "./h2", i.e, each test will be identified by a &lt;h2&gt; tag
	 *
	 * @return A XPath that identifies the tests' names
	 */
	protected String testNameXPath() {
		return "./h2";
	}

	/**
	 * Extend to configure how to identify the tests' results.
	 * The default is ".//*[contains(@class, 'it enqueued')]", i.e,
	 * each test result will be inside a tag with the classes "it" and "enqueued".
	 *
	 * @return A XPath that identifies the tests' results
	 */
	protected String testXPath() {
		return ".//*[contains(@class, 'it enqueued')]";
	}

	private File[] findTestPages() {
		return testPagesFolder().listFiles(testPageFileFilter());
	}

	@SuppressWarnings("unchecked")
	private void addTestsInPageToSuite(HtmlPage page) {
		List<DomNode> testList = (List<DomNode>) page.getByXPath(testXPath());
		for (DomNode testListItem : testList) {
			List<HtmlHeading2> testNameCandidates = (List<HtmlHeading2>) testListItem.getByXPath(testNameXPath());
			String testName = testNameCandidates.iterator().next().getTextContent();
			LOG.info("Found test '" + testName + "'");
			this.addTest(new JavascriptTestCase(testName, testListItem, failedTestXPath()));
		}
	}

	private void logTestPages(File[] testPages) {
		for (File page: testPages) {
			LOG.info("Found test page '" + page.getAbsolutePath() + "'");
		}
	}
}
