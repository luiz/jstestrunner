package br.com.caelum.jstestrunner;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.gargoylesoftware.htmlunit.html.DomNode;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JavascriptTestCaseTest {
	private static final String ERROR_X_PATH = "errorXPath";

	private @Mock DomNode testNode;
	private JavascriptTestCase testCase;

	@Before
	public void setUp() throws Exception {
		this.testCase = new JavascriptTestCase("test", testNode, ERROR_X_PATH);
	}

	@Test(expected=AssertionFailedError.class)
	public void failsIfThereAreElementsInTheGivenXPath() throws Exception {
		List errorNodes = Arrays.asList(testNode);
 		when(testNode.getByXPath(ERROR_X_PATH)).thenReturn(errorNodes);
 		testCase.runBare();
	}

	@Test
	public void doesNotFailIfThereAreNoElementsInTheGivenXPath() throws Exception {
		when(testNode.getByXPath(ERROR_X_PATH)).thenReturn(Collections.EMPTY_LIST);
		testCase.runBare();
	}
}
