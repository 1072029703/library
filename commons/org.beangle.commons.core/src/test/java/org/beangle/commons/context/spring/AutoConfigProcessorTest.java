/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.spring;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.beangle.commons.context.testbean.SomeAction;
import org.beangle.commons.context.testbean.SpringResourcesConsumer;
import org.beangle.commons.context.testbean.TestService;
import org.beangle.commons.context.testbean.UserDaoProvider;
import org.beangle.commons.context.testbean.UserLdapProvider;
import org.beangle.commons.lang.time.Stopwatch;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

/**
 * Test Bean definition in Java config
 * 
 * @author chaostone
 */
@Test
public class AutoConfigProcessorTest {

  /**
   * Test get normal and factory bean.
   */
  public void testGet() {
    Stopwatch watch = new Stopwatch().start();
    ApplicationContext factory = new ClassPathXmlApplicationContext(
        "/org/beangle/commons/context/spring/context-auto.xml");
    testBean(factory);
    testFactoryBean(factory);
    System.out.println("config  context-auto completed using " + watch);
  }

  public void testAdvance() {
    Stopwatch watch = new Stopwatch().start();
    ApplicationContext factory = new ClassPathXmlApplicationContext(
        "/org/beangle/commons/context/spring/context-auto.xml");
    // test Alias
    assertNotNull(factory.getBean(TestService.class.getName()));
    SpringResourcesConsumer consumer=(SpringResourcesConsumer)factory.getBean(SpringResourcesConsumer.class.getName());
    assertNotNull(consumer);
    assertNotNull(consumer.getResources());
    System.out.println("config  advance context-auto completed using " + watch);
    
  }

  private void testFactoryBean(ApplicationContext factory) {
    TestService testService = factory.getBean("testService", TestService.class);
    assertNotNull(testService);
    assertNotNull(testService.getTestDao());
  }

  private void testBean(ApplicationContext factory) {
    // two user provider
    UserDaoProvider daoProvider = (UserDaoProvider) factory.getBean("userDaoProvider");
    assertNotNull(daoProvider);

    UserDaoProvider daoProvider2 = (UserDaoProvider) factory.getBean("userDaoProvider");
    assertNotNull(daoProvider2);

    assertTrue(daoProvider2 == daoProvider);

    UserLdapProvider ldapProvider = (UserLdapProvider) factory.getBean("userLdapProvider");
    assertNotNull(ldapProvider);

    // userService
    SomeAction action = (SomeAction) factory.getBean(SomeAction.class.getName());

    assertNotNull(action);

    assertTrue(action.hasDaoProvider());
    assertTrue(action.hasLdapProvider());

    assertTrue(action.getUserDaoProvider() == daoProvider);
    assertTrue(action.getLdapProvider() == ldapProvider);

    SomeAction action2 = (SomeAction) factory.getBean(SomeAction.class.getName());

    assertTrue(action2 != action);
  }
}
