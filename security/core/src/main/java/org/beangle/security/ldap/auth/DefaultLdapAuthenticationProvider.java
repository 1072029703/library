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
package org.beangle.security.ldap.auth;

import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.auth.dao.AbstractUserDetailAuthenticationProvider;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

/**
 * 读取ldap的用户信息<br>
 * 
 * @author chaostone
 */
public class DefaultLdapAuthenticationProvider extends AbstractUserDetailAuthenticationProvider {

  protected LdapValidator ldapValidator;

  private UserDetailService userDetailService;

  @Override
  protected void additionalAuthenticationChecks(UserDetail user, Authentication authentication)
      throws AuthenticationException {
    if (!ldapValidator.verifyPassword(user.getUsername(), (String) authentication.getCredentials())) { throw new BadCredentialsException(); }
  }

  @Override
  protected UserDetail retrieveUser(String username, Authentication authentication)
      throws AuthenticationException {
    return userDetailService.loadDetail(username);
  }

  public void setUserDetailService(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }

  public void setLdapValidator(LdapValidator ldapValidator) {
    this.ldapValidator = ldapValidator;
  }

}
