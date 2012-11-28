/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.security.blueprint;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.pojo.EnabledEntity;
import org.beangle.commons.entity.pojo.TemporalEntity;
import org.beangle.commons.entity.pojo.TimeEntity;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface User extends Entity<Long>, TimeEntity, TemporalEntity, EnabledEntity, Principal {

  // 新建用户的缺省密码
  static final String DEFAULT_PASSWORD = "1";

  /**
   * 名称
   * 
   * @return user's name
   */
  String getName();

  /**
   * 用户真实姓名
   */
  String getFullname();

  /**
   * 用户密码(不限制是明码还是密文)
   */
  String getPassword();

  /**
   * 用户邮件
   */
  String getMail();

  /**
   * 对应角色成员
   */
  Set<Member> getMembers();

  /**
   * 对应角色
   * 
   * @return 按照角色代码排序的role列表
   */
  List<Role> getRoles();

  /**
   * 创建者
   */
  User getCreator();

  /**
   * 是否启用
   */
  boolean isEnabled();

  /**
   * 备注
   */
  String getRemark();

  /**
   * 账户是否过期
   */
  boolean isAccountExpired();

  /**
   * 是否密码过期
   */
  boolean isPasswordExpired();

}
