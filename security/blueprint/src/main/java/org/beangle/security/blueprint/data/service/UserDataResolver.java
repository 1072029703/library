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
package org.beangle.security.blueprint.data.service;

import java.util.Collection;
import java.util.List;

import org.beangle.security.blueprint.data.ProfileField;

public interface UserDataResolver {

  /**
   * Marshal list of objects to text format
   * 
   * @param field
   * @param items
   */
  String marshal(ProfileField field, Collection<?> items);

  /**
   * Convert text to list of objects
   * 
   * @param <T>
   * @param field
   * @param text
   */
  <T> List<T> unmarshal(ProfileField field, String text);
}
