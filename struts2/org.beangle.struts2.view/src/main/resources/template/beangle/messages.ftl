[#ftl/]
<div class="ui-widget" id="${tag.id}">
[#if actionMessages?? && actionMessages?size > 0]
<div class="actionMessage">
	<div class="ui-state-highlight ui-corner-all"> 
		[#list actionMessages as message]
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
		<span>${message!}</span>
		[/#list]
	</div>
</div>
[/#if]
[#if actionErrors?? && actionErrors?size > 0]
<div class="actionError">
	<div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em;"> 
		[#list actionErrors as message]
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>
		<span>${message!}</span></p>
		[/#list]
	</div>
</div>
[/#if]
</div>
[#if tag.parameters['slash']??]
<script>
	setTimeout(function(){document.getElementById('${tag.id}').style.display="none";},${tag.parameters['slash']}*1000);
</script>
[/#if]