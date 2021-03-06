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
package org.beangle.security.blueprint.data;

import org.beangle.security.blueprint.Permission;

/**
 * 数据访问授权
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface DataPermission extends Permission {

  /**
   * 对应的数据资源
   */
  DataResource getResource();

  /**
   * 允许访问的部分
   */
  String getAttrs();

  /**
   * 资源过滤器
   */
  String getFilters();

}
