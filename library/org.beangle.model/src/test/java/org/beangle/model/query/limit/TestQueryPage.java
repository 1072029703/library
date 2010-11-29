/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query.limit;

import static org.testng.Assert.assertNotNull;

import java.util.Iterator;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.query.LimitQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.testng.annotations.Test;

@Test
public class TestQueryPage {

	public void testMove() throws Exception {
		OqlBuilder<String> query = OqlBuilder.from(String.class, "dd");
		query.limit(new PageLimit(1, 2));
		MockQueryPage page = new MockQueryPage((LimitQuery<String>) query.build());
		for (Iterator<String> iterator = page.iterator(); iterator.hasNext();) {
			String data = iterator.next();
			assertNotNull(data);
		}
	}
}
