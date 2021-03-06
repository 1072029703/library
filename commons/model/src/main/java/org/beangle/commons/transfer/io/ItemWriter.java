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
package org.beangle.commons.transfer.io;

/**
 * <p>
 * ItemWriter interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface ItemWriter extends Writer {

  /**
   * <p>
   * write.
   * </p>
   * 
   * @param obj a {@link java.lang.Object} object.
   */
  void write(Object obj);

  /**
   * <p>
   * writeTitle.
   * </p>
   * 
   * @param titleName a {@link java.lang.String} object.
   * @param data a {@link java.lang.Object} object.
   */
  void writeTitle(String titleName, Object data);

}
