[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/scripts/validator.js"></script>
<script type="text/javascript" src="${base}/static/scripts/md5.js"></script>
[@b.form action="!save"]
<table width="80%" align="center" class="formTable">
	<tr  class="thead">
	  <td colspan="2"><font color="red"><em>我的账户</em></font></td>
	</tr>
	<tr>
	 <td id="f_oldPassword" class="title" width="40%">${b.text("user.oldPassword")}:</td>
	 <td class="text1"><input type="password" name="oldPassword" maxlength="64"/><input type="hidden" name="oldPassword_encoded" value="${user.password}"/></td>
	</tr>
	<tr>
	 <td id="f_newPassword" class="title">${b.text("user.newPassword")}:</td>
	 <td class="text1"><input type="password" name="password"  maxlength="64"/></td>
	</tr>
	<tr>
	 <td id="f_repeatedPassword" class="title">${b.text("user.repeatPassword")}:</td>
	 <td class="text1"><input type="password" name="repeatedPassword" maxlength="64"/></td>
	</tr>
	<tr>
	 <td id="f_mail"  class="title" width="40%">${b.text("common.email")}:</td>
	 <td class="text1"><input type="text" name="mail"  value="${user.mail!('')}" maxlength="100"/></td>
	</tr>
	<tr>
	 <td colspan="2" align="center" class="tfoot">
		[@b.submit  value="action.submit" onsubmit="validate"/]&nbsp;
	   <input type="reset" value="${b.text("action.reset")}" name="reset1"  class="buttonStyle" />
	 </td>
	</tr>
</table>
[/@]
<script type="text/javascript">
function validate(form){
	var a_fields = {
	 'oldPassword':{'l':'${b.text("user.oldPassword")}', 'r':true, 't':'f_oldPassword'},
	 'password':{'l':'${b.text("user.newPassword")}', 'r':true, 't':'f_newPassword'},
	 'repeatedPassword':{'l':'${b.text("user.repeatPassword")}', 'r':true, 't':'f_repeatedPassword'},
	 'mail':{'l':'${b.text("common.email")}', 'r':true, 'f':'email', 't':'f_mail'}
	};
	
	var v = new validator(form, a_fields, null);
	if (v.exec()) {
		if(form['oldPassword_encoded'].value!=hex_md5(form.oldPassword.value)) {alert ("旧密码输入不正确.");return;}
		if(form['password'].value!=form['repeatedPassword'].value){alert("新密码与重复输入的不相同");return;}
		else{
			form['password'].value=hex_md5(form['password'].value);
			form['password'].value=form['password'].value;
			return true;
		}
	}
	return false;
}
</script>
[@b.foot/]