[#ftl]
<link href="${base}/static/css/code.css" rel="stylesheet" type="text/css"/>
	<table width="100%" align="center">
		<tr>
		  <td>
			  文件:${file.absolutePath}/${file.name}</a>
		  </td>
		</tr>
		<tr>
		  <td>
			<img src="${base}/static/icons/beangle/48x48/actions/go-previous.png" width="18px" height="18px"/>
			[@b.a href="!list?path=${file.parent?js_string}" target="filelist"]返回[/@]
			<img src="${base}/static/images/action/download.gif"/>
			<a href="${b.url('!download')}?path=${file.absolutePath?js_string}&download=1">下载</a>
		  </td>
		</tr>
		<tr>
		  <td  colspan="2" style="font-size:0px">
			  <img src="${base}/static/icons/default/action/keyline.gif" height="2" width="100%" align="top"/>
		  </td>
		</tr>
	</table>

<table class="filecontent CodeRay">
	<tbody>
		[#list lines as line]
		<tr><th class="line-num" id="L${line_index+1}"><a href="#L${line_index+1}">${line_index+1}</a></th>
			<td class="line-code"><pre>${line?html}</pre></td>
		</tr>
		[/#list]
	</tbody>
</table>
