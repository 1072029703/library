/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang.reflect;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class BridgeMethodTest {

  public void testBridgeMethods() {

    for (Method m : Dog.class.getMethods()) {
      if (m.getName().equals("getAge") && null == m.getReturnType()) {
        if (m.getReturnType().equals(Integer.class)) Assert.assertFalse(m.isBridge());

        else if (m.getReturnType().equals(Number.class)) {
          Assert.assertTrue(m.isBridge());
          Assert.assertEquals(m.getDeclaringClass(), Dog.class);
        }
      }
    }
  }
}

interface Animal {
  Number getAge();
}

class Dog implements Animal {
  public Integer getAge() {
    return 0;
  }
}
