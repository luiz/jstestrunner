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

package br.com.caelum.jstestrunner.framework;

/**
 * Adapter for the Screw.Unit JS test framework
 *
 * @author luiz
 */
public class ScrewUnit implements JavascriptTestFramework {

	/**
	 * @return "./*[contains(@class, 'error')]", i.e, elements with the "error"
	 *         class.
	 * @see {@link JavascriptTestFramework#failedTestXPath()}
	 */
	@Override
	public String failedTestXPath() {
		return "./*[contains(@class, 'error')]";
	}

	/**
	 * @return "./h2", i.e, each test will be identified by a &lt;h2&gt; tag
	 * @see {@link JavascriptTestFramework#testNameXPath()}
	 */
	@Override
	public String testNameXPath() {
		return "./h2";
	}

	/**
	 * @return ".//*[contains(@class, 'it enqueued')]", i.e, each test result
	 *         will be inside a tag with the classes "it" and "enqueued".
	 * @see {@link JavascriptTestFramework#testXPath()}
	 */
	@Override
	public String testXPath() {
		return ".//*[contains(@class, 'it enqueued')]";
	}
}
