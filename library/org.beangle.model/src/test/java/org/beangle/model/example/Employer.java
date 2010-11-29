/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.example;

import java.util.Map;

public interface Employer {

	Name getName();

	ContractInfo getContractInfo();

	Map<Long, Skill> getSkills();
}
