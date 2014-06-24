package br.com.caelum.jstestrunner.framework;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

public class ScrewUnitRunner extends Runner {

	@Override
	public Description getDescription() {
		Description suiteDescription = Description.createSuiteDescription("Javascript Test Suite");
		return suiteDescription;
	}

	@Override
	public void run(RunNotifier notifier) {
        EachTestNotifier testNotifier = new EachTestNotifier(notifier,
                getDescription());
        try {
            runTests(notifier);
        } catch (AssumptionViolatedException e) {
            testNotifier.addFailedAssumption(e);
        } catch (StoppedByUserException e) {
            throw e;
        } catch (Throwable e) {
            testNotifier.addFailure(e);
        }
	}
}
