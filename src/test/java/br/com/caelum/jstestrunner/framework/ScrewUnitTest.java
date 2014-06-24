package br.com.caelum.jstestrunner.framework;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class ScrewUnitTest {
	@Test
	public void detectsTwoTestsOneFailing() throws Exception {
		Runner runner = new ScrewUnitRunner();
		RunNotifier notifier = spy(new RunNotifier());
		runner.run(notifier);
		verify(notifier).fireTestFailure(argThat(failure("should not work")));
	}

	private Matcher<Failure> failure(final String testName) {
		return new TypeSafeMatcher<Failure>() {
			@Override
			public void describeTo(Description desc) {
				desc.appendText("test named " + testName);
			}

			@Override
			protected boolean matchesSafely(Failure fail) {
				return testName.equals(fail.getTestHeader());
			}
		};
	}
}

@RunWith(ScrewUnitRunner.class)
@TestsFolder("test/screwUnit")
class ScrewUnitTestSuite {
}
