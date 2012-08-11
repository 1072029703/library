/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.function.internal;

import static org.testng.Assert.assertEquals;

import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.function.service.internal.FuncPermissionServiceImpl;
import org.testng.annotations.Test;
@Test
public class PermissionServiceImplTest {

  FuncPermissionService permissionService = new FuncPermissionServiceImpl();

  public void testextractResource() throws Exception {
    assertEquals(permissionService.extractResource("a.jsp"), "a");
    assertEquals(permissionService.extractResource("/b.do"), "/b");
    assertEquals(permissionService.extractResource("/c/d.action"), "/c/d");
    assertEquals(permissionService.extractResource("c/d.action"), "c/d");
    assertEquals(permissionService.extractResource("//c/d.action"), "//c/d");

    // assertEquals(authorityService.extractResource("  //c/d.action "), "c/d");
    // assertEquals(authorityService.extractResource("  c/d.action "), "c/d");

    assertEquals(permissionService.extractResource("c/d!remove.action "), "c/d");
  }
}
