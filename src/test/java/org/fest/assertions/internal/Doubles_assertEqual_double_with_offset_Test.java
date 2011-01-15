/*
 * Created on Oct 28, 2010
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

import static org.fest.assertions.data.Offset.offset;
import static org.fest.assertions.error.IsNotEqualWithOffset.isNotEqual;
import static org.fest.assertions.test.ErrorMessages.offsetIsNull;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.TestData.someInfo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.data.Offset;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link Doubles#assertEqual(AssertionInfo, Double, double, Offset)}</code>.
 *
 * @author Alex Ruiz
 */
public class Doubles_assertEqual_double_with_offset_Test {

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private Doubles doubles;

  @Before public void setUp() {
    failures = spy(new Failures());
    doubles = new Doubles();
    doubles.failures = failures;
  }

  @Test public void should_throw_error_if_offset_is_null() {
    thrown.expectNullPointerException(offsetIsNull());
    doubles.assertEqual(someInfo(), new Double(8d), 8d, null);
  }

  @Test public void should_pass_if_doubles_are_equal() {
    doubles.assertEqual(someInfo(), new Double(8d), 8d, offset(1d));
  }

  @Test public void should_pass_if_doubles_are_equal_within_offset() {
    doubles.assertEqual(someInfo(), new Double(6d), 8d, offset(2d));
  }

  @Test public void should_fail_if_doubles_are_not_equal_within_offset() {
    AssertionInfo info = someInfo();
    Offset<Double> offset = offset(1d);
    try {
      doubles.assertEqual(info, new Double(6d), 8d, offset);
    } catch (AssertionError e) {
      verify(failures).failure(info, isNotEqual(6d, 8d, offset));
      return;
    }
    fail("expected AssertionError not thrown");
  }
}
