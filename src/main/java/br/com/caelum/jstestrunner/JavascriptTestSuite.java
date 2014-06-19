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

import br.com.caelum.jstestrunner.framework.JavascriptTestFramework;
import br.com.caelum.jstestrunner.framework.ScrewUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Aggregates all JS tests found in the project (see {@link #testPagesFolder()}
 * and {@link #testPageFileFilter()}), extracting their status
 *
 * @author luiz
 */
public class JavascriptTestSuite extends TestSuite {

	private static final Log LOG = LogFactory.getLog(JavascriptTestSuite.class);

	private final JavascriptTestFramework framework;

	public static TestSuite suite() throws IOException {
		return new JavascriptTestSuite(new ScrewUnit());
	}

	/**
	 * Creates the suite with all detected Javascript tests.
	 *
	 * @param framework Javascript test framework adapter
	 * @see {@link JavascriptTestFramework}
	 */
	public JavascriptTestSuite(JavascriptTestFramework framework) {
		this.framework = framework;
		this.setName(getSuiteName());
		File[] testPages = findTestPages();
		if (LOG.isInfoEnabled()) {
			logTestPages(testPages);
		}
		WebClient client = createWebClient();
		for (File page : testPages) {
			HtmlPage htmlPage;
			try {
				htmlPage = client.getPage(page.toURI().toURL());
			} catch (FailingHttpStatusCodeException | IOException e) {
				throw new RuntimeException("Error running page " + page.getAbsolutePath(), e);
			}
			addTestsInPageToSuite(htmlPage);
		}
	}

	/**
	 * Extend to configure. Returns a test suite name for JUnit
	 *
	 * @return The String "Javascript Test Suite"
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

	private File[] findTestPages() {
		return testPagesFolder().listFiles(testPageFileFilter());
	}

	@SuppressWarnings("unchecked")
	private void addTestsInPageToSuite(HtmlPage page) {
		List<DomNode> testList = (List<DomNode>) page.getByXPath(framework.testXPath());
		for (DomNode testListItem : testList) {
			List<HtmlHeading2> testNameCandidates = (List<HtmlHeading2>) testListItem.getByXPath(framework.testNameXPath());
			String testName = testNameCandidates.iterator().next().getTextContent();
			LOG.info("Found test '" + testName + "'");
			this.addTest(new JavascriptTestCase(testName, testListItem, framework.failedTestXPath()));
		}
	}

	private void logTestPages(File[] testPages) {
		for (File page: testPages) {
			LOG.info("Found test page '" + page.getAbsolutePath() + "'");
		}
	}
}
