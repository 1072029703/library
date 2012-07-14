/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.entity.HierarchyEntity;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.orm.pojo.HierarchyLongIdObject;

/**
 * @author chaostone
 * @version $Id: AbstractHierarchyService.java Jul 29, 2011 1:34:01 AM chaostone $
 */
@SuppressWarnings({ "unchecked" })
public abstract class AbstractHierarchyService<T extends HierarchyLongIdObject<M>, M extends HierarchyEntity<M, Long>>
    extends BaseServiceImpl {

  public void move(T node, T location, int indexno) {
    if (Objects.equals(node.getParent(), location)) {
      if (Numbers.toInt(((T) node).getIndexno()) != indexno) {
        shiftCode(node, location, indexno);
      }
    } else {
      if (null != node.getParent()) {
        node.getParent().getChildren().remove(node);
      }
      node.setParent((M) location);
      shiftCode(node, location, indexno);
    }
  }

  private void shiftCode(T node, T newParent, int indexno) {
    @SuppressWarnings("rawtypes")
    List sibling = null;
    if (null != newParent) sibling = (List<T>) newParent.getChildren();
    else {
      sibling = CollectUtils.newArrayList();
      for (T m : getTopNodes(node)) {
        if (null == m.getParent()) sibling.add(m);
      }
    }
    Collections.sort(sibling);
    sibling.remove(node);
    indexno--;
    if (indexno > sibling.size()) {
      indexno = sibling.size();
    }
    sibling.add(indexno, node);
    int nolength = String.valueOf(sibling.size()).length();
    Set<T> nodes = CollectUtils.newHashSet();
    for (int seqno = 1; seqno <= sibling.size(); seqno++) {
      T one = (T) sibling.get(seqno - 1);
      generateCode(one, Strings.leftPad(String.valueOf(seqno), nolength, '0'), nodes);
    }
    entityDao.saveOrUpdate(nodes);
  }

  protected abstract List<T> getTopNodes(T m);

  private void generateCode(T node, String indexno, Set<T> nodes) {
    nodes.add(node);
    if (null != indexno) {
      ((T) node).generateCode(indexno);
    } else {
      ((T) node).generateCode();
    }
    if (null != node.getChildren()) {
      for (Object m : node.getChildren()) {
        generateCode((T) m, null, nodes);
      }
    }
  }
}
