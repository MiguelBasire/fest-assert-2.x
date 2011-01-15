/*
 * Created on Nov 29, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2011 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.error.DoesNotContain.doesNotContain;
import static org.fest.assertions.test.ErrorMessages.*;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.unexpectedNull;
import static org.fest.assertions.test.ObjectArrayFactory.emptyArray;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.set;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link ObjectArrays#assertContains(AssertionInfo, Object[], Object[])}</code>.
 *
 * @author Alex Ruiz
 */
public class ObjectArrays_assertContains_Test {

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private Object[] actual;
  private ObjectArrays arrays;

  @Before public void setUp() {
    failures = spy(new Failures());
    actual = array("Luke", "Yoda", "Leia");
    arrays = new ObjectArrays(failures);
  }

  @Test public void should_pass_if_actual_contains_given_values() {
    arrays.assertContains(someInfo(), actual, array("Luke"));
  }

  @Test public void should_pass_if_actual_contains_given_values_in_different_order() {
    arrays.assertContains(someInfo(), actual, array("Leia", "Yoda"));
  }

  @Test public void should_pass_if_actual_contains_all_given_values() {
    arrays.assertContains(someInfo(), actual, array("Luke", "Yoda", "Leia"));
  }

  @Test public void should_pass_if_actual_contains_given_values_more_than_once() {
    actual = array("Luke", "Yoda", "Leia", "Luke", "Luke");
    arrays.assertContains(someInfo(), actual, array("Luke"));
  }

  @Test public void should_pass_if_actual_contains_given_values_even_if_duplicated() {
    arrays.assertContains(someInfo(), actual, array("Luke", "Luke"));
  }

  @Test public void should_throw_error_if_array_of_values_to_look_for_is_empty() {
    thrown.expectIllegalArgumentException(valuesToLookForIsEmpty());
    arrays.assertContains(someInfo(), actual, emptyArray());
  }

  @Test public void should_throw_error_if_array_of_values_to_look_for_is_null() {
    thrown.expectNullPointerException(valuesToLookForIsNull());
    arrays.assertContains(someInfo(), actual, null);
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(unexpectedNull());
    arrays.assertContains(someInfo(), null, array("Yoda"));
  }

  @Test public void should_fail_if_actual_does_not_contain_values() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Han", "Luke" };
    try {
      arrays.assertContains(info, actual, expected);
    } catch (AssertionError e) {
      verify(failures).failure(info, doesNotContain(actual, expected, set("Han")));
      return;
    }
    fail("expected AssertionError not thrown");
  }
}
