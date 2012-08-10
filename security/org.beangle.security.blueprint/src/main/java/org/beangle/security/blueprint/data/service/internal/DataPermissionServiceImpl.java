/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.predicates.PropertyEqualPredicate;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.Property;
import org.beangle.security.blueprint.data.PropertyMeta;
import org.beangle.security.blueprint.data.RoleProfile;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.UserProperty;
import org.beangle.security.blueprint.data.model.RoleDataPermissionBean;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.data.service.UserDataProvider;
import org.beangle.security.blueprint.data.service.UserDataResolver;
import org.beangle.security.blueprint.function.service.FunctionPermissionService;
import org.beangle.security.blueprint.service.UserService;

public class DataPermissionServiceImpl extends BaseServiceImpl implements DataPermissionService {

  protected UserService userService;

  protected Map<String, UserDataProvider> providers = CollectUtils.newHashMap();

  protected UserDataResolver dataResolver;

  protected FunctionPermissionService functionPermissionService;

  public List<UserProfile> getUserProfiles(Long userId, Map<String, Object> selectors) {
    OqlBuilder<UserProfile> builder = OqlBuilder.from(UserProfile.class, "up").where("up.user.id=:userId",
        userId);
    return entityDao.search(builder);
  }

  /**
   * 查找数据资源和功能资源对应的数据权限。
   * <p>
   * 默认按照声明资源的数据权限为先。
   * 
   * @param role
   * @param dataResource
   * @return
   */
  private List<? extends DataPermission> getPermissions(Role role, String dataResourceName,
      String funcResourceName) {
    OqlBuilder<RoleDataPermissionBean> builder = OqlBuilder.from(RoleDataPermissionBean.class, "dp")
        .where("dp.resource=:dataresource.name and dp.role =:role", dataResourceName, role).cacheable();
    List<RoleDataPermissionBean> rs = entityDao.search(builder);

    @SuppressWarnings("unchecked")
    List<RoleDataPermissionBean> permissions = (List<RoleDataPermissionBean>) CollectionUtils.select(rs,
        new PropertyEqualPredicate("funcResource.name", funcResourceName));
    return (permissions.isEmpty()) ? rs : permissions;
  }

  /**
   * 查询用户在指定模块的数据权限
   */
  public DataPermission getPermission(Long userId, String dataResourceName, String funcResourceName) {
    List<Role> roles = userService.getRoles(userId);
    Date now = new Date();
    for (Role role : roles) {
      for (DataPermission permission : getPermissions(role, dataResourceName, funcResourceName)) {
        if (null != permission.getEffectiveAt() && now.before(permission.getEffectiveAt())) continue;
        if (null != permission.getInvalidAt() && now.after(permission.getInvalidAt())) continue;
        return permission;
      }
    }
    // FIXME try alluser
    return null;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Collection<RoleProfile> getProfiles(Collection<Role> roles, Resource resource) {
    if (roles.isEmpty()) return Collections.EMPTY_LIST;
    OqlBuilder builder = OqlBuilder.from("from " + Permission.class.getName() + " au,"
        + RoleProfile.class.getName() + " gp");
    builder.where("au.role in (:roles) and au.resource = :resource and au.role=gp.role", roles, resource);
    builder.select("gp");
    return entityDao.search(builder);
  }

  private List<?> getPropertyValues(PropertyMeta field) {
    if (null == field.getSource()) return Collections.emptyList();
    String source = field.getSource();
    String prefix = Strings.substringBefore(source, ":");
    source = Strings.substringAfter(source, ":");
    UserDataProvider provider = providers.get(prefix);
    if (null != provider) {
      return provider.getData(field, source);
    } else {
      throw new RuntimeException("not support data provider:" + prefix);
    }
  }

  public List<?> getPropertyValues(String propertyName) {
    return getPropertyValues(getPropertyMeta(propertyName));
  }

  public Object getPropertyValue(String propertyName, UserProfile profile) {
    PropertyMeta prop = getPropertyMeta(propertyName);
    UserProperty property = profile.getProperty(prop);
    if (null == property) return null;
    return unmarshal(property.getValue(), prop);
  }

  /**
   * 获取数据限制的某个属性的值
   * 
   * @param property
   * @param restriction
   * @return
   */
  private Object unmarshal(String value, PropertyMeta property) {
    try {
      List<Object> returned = dataResolver.unmarshal(property, value);
      if (property.isMultiple()) {
        return returned;
      } else {
        return (1 != returned.size()) ? null : returned.get(0);
      }
    } catch (Exception e) {
      logger.error("exception with param type:" + property.getValueType() + " value:" + value, e);
      return null;
    }
  }

  public void apply(OqlBuilder<?> query, DataPermission permission, UserProfile profile) {
    List<Object> paramValues = CollectUtils.newArrayList();
    // 处理限制对应的模式
    if (Strings.isEmpty(permission.getFilters())) return;

    String patternContent = permission.getFilters();
    patternContent = Strings.replace(patternContent, "{alias}", query.getAlias());
    String[] contents = Strings.split(Strings.replace(patternContent, " and ", "$"), "$");

    StringBuilder conBuilder = new StringBuilder("(");
    for (int i = 0; i < contents.length; i++) {
      String content = contents[i];
      Condition c = new Condition(content);
      List<String> params = c.getParamNames();
      for (final String paramName : params) {
        UserProperty up = profile.getProperty(paramName);
        PropertyMeta prop = up.getMeta();
        String value = null == up ? null : up.getValue();
        if (Strings.isNotEmpty(value)) {
          if (value.equals(Property.AllValue)) {
            content = "";
          } else {
            paramValues.add(unmarshal(value, prop));
          }
        } else {
          throw new RuntimeException(paramName + " had not been initialized");
        }
      }
      if (conBuilder.length() > 1 && content.length() > 0) conBuilder.append(" and ");
      conBuilder.append(content);
    }

    if (conBuilder.length() > 1) {
      conBuilder.append(')');
      Condition con = new Condition(conBuilder.toString());
      con.params(paramValues);
      query.where(con);
    }
  }

  private PropertyMeta getPropertyMeta(String fieldName) {
    List<PropertyMeta> fields = entityDao.get(PropertyMeta.class, "name", fieldName);
    if (1 != fields.size()) { throw new RuntimeException("bad pattern parameter named :" + fieldName); }
    return fields.get(0);
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setFunctionPermissionService(FunctionPermissionService functionPermissionService) {
    this.functionPermissionService = functionPermissionService;
  }

  public Map<String, UserDataProvider> getProviders() {
    return providers;
  }

  public void setProviders(Map<String, UserDataProvider> providers) {
    this.providers = providers;
  }

  public void setDataResolver(UserDataResolver dataResolver) {
    this.dataResolver = dataResolver;
  }

}