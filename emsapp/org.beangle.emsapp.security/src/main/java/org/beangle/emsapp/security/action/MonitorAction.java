/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PagedList;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.ems.security.session.CategoryProfile;
import org.beangle.ems.security.session.model.CategoryProfileBean;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.access.log.Accesslog;
import org.beangle.security.web.access.log.CachedResourceAccessor;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 */
public class MonitorAction extends SecurityActionSupport {

	private SessionRegistry sessionRegistry;

	private CachedResourceAccessor resourceAccessor;

	public String profiles() {
		put("categoryProfiles", entityDao.getAll(CategoryProfile.class));
		return forward();
	}

	public String sessionStats() {
		put("categoryProfiles", entityDao.getAll(CategoryProfile.class));
		put("sessionStats", sessionRegistry.getController().getSessionStats());
		return forward();
	}

	public String activities() {
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "authentication.principal asc";
		}
		List<SessionInfo> onlineActivities = sessionRegistry.getAll();
		Collections.sort(onlineActivities, new PropertyComparator<Object>(orderBy));
		put("onlineActivities", new PagedList<SessionInfo>(onlineActivities, getPageLimit()));

		return forward();
	}

	/**
	 * 保存设置
	 */
	public String saveProfile() {
		List<CategoryProfileBean> categories = entityDao.getAll(CategoryProfileBean.class);
		for (final CategoryProfileBean profile : categories) {
			Long categoryId = profile.getCategory().getId();
			Integer max = getInteger("max_" + categoryId);
			Integer maxSessions = getInteger("maxSessions_" + categoryId);
			Integer inactiveInterval = getInteger("inactiveInterval_" + categoryId);
			if (null != max && null != maxSessions && null != inactiveInterval) {
				profile.setCapacity(max);
				profile.setUserMaxSessions(maxSessions);
				profile.setInactiveInterval(inactiveInterval);
			}
		}
		entityDao.saveOrUpdate(categories);
		return redirect("profiles", "info.save.success");
	}

	public String invalidate() {
		String[] sessionIds = (String[]) getAll("sessionId");
		String mySessionId = ServletActionContext.getRequest().getSession().getId();
		boolean killed = getBool("kill");
		if (null != sessionIds) {
			for (String sessionId : sessionIds) {
				if (mySessionId.equals(sessionId)) continue;
				if (killed) sessionRegistry.remove(sessionId);
				else sessionRegistry.expire(sessionId);
			}
		}
		return redirect("activities", "info.action.success");
	}

	/**
	 * 访问记录
	 */
	public String accesslogs() {
		List<Accesslog> accessLogs = null;
		if (null == resourceAccessor) {
			accessLogs = Collections.emptyList();
		} else {
			accessLogs = CollectUtils.newArrayList(resourceAccessor.getAccessLogs());
		}
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "duration desc";
		}
		if (orderBy.startsWith("accesslog.")) {
			orderBy = StringUtils.substringAfter(orderBy, "accesslog.");
		}
		Collections.sort(accessLogs, new PropertyComparator<Object>(orderBy));
		put("accesslogs", new PagedList<Accesslog>(accessLogs, getPageLimit()));
		return forward();
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setResourceAccessor(CachedResourceAccessor resourceAccessor) {
		this.resourceAccessor = resourceAccessor;
	}

}