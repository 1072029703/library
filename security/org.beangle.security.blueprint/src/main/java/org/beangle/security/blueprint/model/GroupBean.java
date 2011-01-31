/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.restrict.Restriction;

/**
 * 系统中用户组的基本信息和账号信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public class GroupBean extends LongIdTimeObject implements Group {

	private static final long serialVersionUID = -3404181949500894284L;

	/** 名称 */
	private String name;

	/** 关联的用户 */
	private Set<GroupMember> members = CollectUtils.newHashSet();

	/** 对应的用户类别 */
	private UserCategory category;

	/** 上级组 */
	private Group parent;

	/** 下级组 */
	private Set<Group> children = CollectUtils.newHashSet();

	/** 创建人 */
	private User owner;

	/** 备注 */
	protected String remark;

	/** 是否启用 */
	public boolean enabled = true;

	/** 访问限制 */
	protected Set<Restriction> restrictions = CollectUtils.newHashSet();

	/** 权限 */
	protected Set<Authority> authorities = CollectUtils.newHashSet();

	public GroupBean() {
		super();
	}

	public GroupBean(Long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory userCategory) {
		this.category = userCategory;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	public Set<Group> getChildren() {
		return children;
	}

	public void setChildren(Set<Group> children) {
		this.children = children;
	}

	public Set<GroupMember> getMembers() {
		return members;
	}

	public void setMembers(Set<GroupMember> members) {
		this.members = members;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String toString() {
		return name;
	}

}
