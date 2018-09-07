<!DOCTYPE html>  
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"  
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">  
<head>  
	<title>Hello World!</title>  
</head> 
<body>
	<hr size="10" color="blue"> 
	<h1>if表达式</h1> 
	<ul> 
		<li>if</li> 
		<#assign var=99 /> 
		<#if var==99> 
			<font color="red">var=99</font> 
		</#if> <br /> 
		<#if var==99> 
			<font color="red">var=99</font> 
		<#else> 
			<font coloe="green">var!=99</font>
		</#if> <br />
	 	<#if (var > 99)> 
	 		<font color="red">var大于99</font> 
	 	<#elseif var==99> 
	 		<font coloe="green">var=99</font> 
	 	<#else> 
	 		<font coloe="blue">var小于99</font> 
	 	</#if> <br /> 
	 	<li>if多条件 ||，&&，!</li> 
	 	<#assign var='python'> 
	 	<#if var== 'python' || var=='Java'> 
	 		<font color="red">python or java</font> <br /> 
	 	</#if> 
	 	<#if var== 'python' && var?length==6> 
	 		<font color="yellow">python length 6</font><br/> 
	 	</#if> 
	 	<#if (var== 'python' && var?length== 6) || (var== 'java')> 
	 		<font color="black">python length 6或者Java</font> <br /> <br /> 
		</#if> 
	 	<#if !((var== 'python' && var?length== 6) || (var== 'java'))> 
	 		<font color="black">python length 6或者Java</font> <br /> <br /> 
	 	</#if> 
	</ul>

	<h1>内建函数</h1> 
	<ul> 
		<h2>字符串内建函数</h2> 
		<#list "a|b|c|d"?split("|") as item> 
			<li>${item}</li> 
		</#list> 
	</ul> 
	<hr size="10" color="red"> 
	<h1>字符串转日期</h1> 
	<ul> 
		<#assign var1="23/02/2017" ?date("MM/dd/yyyy")/> 
		<#assign var2="11:34:33" ?time("HH:mm:ss")/> 
		<#assign var3="2017-02-23 11:35 PM" ?datetime("yyyy-MM-dd hh:mm")/> 
		<li>${var1}</li> 
		<li>${var2}</li> 
		<li>${var3}</li> 
	</ul> 
	<hr size="10" color="blue"> 
	<h1>数字类型的内建函数</h1> 
	<ul> 
		<#assign numVar1=314.5662789 /> 
		<li>格式化：${numVar1?string("0.##")}</li> 
		<li>四舍五入：${numVar1?round}</li> 
		<li>去掉小数点：${numVar1?floor}</li> 
		<li>进1：${numVar1?ceiling}</li> 
	</ul> 
	<hr size="10" color="black"> 
	<h1>list内建函数</h1> 
	<ul> 
		<#assign listVar1=[1,2,3,4,11,12,13,14,21,22,23,24] /> 
		<li>分块处理取长度：${listVar1?chunk(4)?size}</li> 
		<li>长度：${listVar1?size}</li> 
		<#list listVar1?chunk(4)?last as item> 
			<li>${item}</li> 
		</#list> 
		<li>取list第一个值：${listVar1?first}</li> 
		<li>取list最后一个值：${listVar1?last}</li> 
	</ul> 
	<hr size="10" color="green"> 
	<h1>其他内建函数</h1> 
	<ul> 
		<#assign sVar = 'helloworld'/> 
		<li>${sVar?is_number?string('yes','no')}</li> 
		<li>${sVar?has_content?string('yes','no')}</li> 
		<li>${"1"+"2"?eval}</li> 
		<li>${(1+2)?eval}</li> 
		<li>${"1"+"2"?is_string?string('yes','no')}</li> 
		<li>${("1"+"2")?is_string?string('yes','no')}</li> 
	</ul>
	
	<h1>macro,nested,return:实战demo</h1> 
	<h2>1.macro:宏指令</h2> 
	<ul> 
		<li>FreeMarker:无参数的macro</li> 
		<div> 
			<#macro test> 
				<font color="red" size="18px">我是无参数的macro</font> 
			</#macro> <@test/> 
		</div> 
		<li>Freemarker2:有参数的macro</li> 
		<div> 
			<#macro test param1 param2> 
				<font color="blue" size="18px">我是有参数的macro,paeam1=${param1},param2=${param2}</font> <br/> 
			</#macro> <@test param1="java" param2="python"/> 
		</div> 
		<li>Freemarker3:有参数的macro</li> 
		<div> 
			<#macro test param1 param2="JavaScript"> 
				<font color="blue" size="18px">我是有参数的macro,paeam1=${param1},param2=${param2}</font> <br/> 
			</#macro> <@test param1="java" param2="hello python"/> 
		</div> 
		<li>Freemarker4:有多个参数的macro</li> 
		<div> 
			<#macro test param1 param2="python" paramExt...> 
				<font color="green" size="18px">我是有参数的macro,paeam1=${param1},param2=${param2}</font> <br/> 
				<font color="blue" size="18px">${paramExt['param3']}</font>  <br />
				<font color="blue" size="18px">${paramExt['param4']}</font> 
			</#macro> <@test param1="java" param2="python" param3="nodejs" param4="html"/> 
		</div> 
	</ul> 
	<hr size="10" color="red"> 
	<h2>2,nested</h2> 
	<div> 
		<ul> 
			<#macro test param1="java"> 
				${param1}<br/> 
				<#nested param1,"我的nested参数"><br/> 
			</#macro> 
			<li>调用</li> 
			<div> 
				<@test param1="java";loopVar1,loopVar2> 
					<font color="green" size="18px">hello ${loopVar1},${loopVar2}</font><br/> 
				</@test> 
				<@test param1="python"; loopVar1> hello ${loopVar1}<br/>
		 		</@test> 
		 	</div> 
		</ul> 
		<hr size="10" color="pink"> 
		<div> 
			<h2>3,函数</h2> 
		 	<ul> 
				<#function doAdd param1 param2> 
		 			<#return param1+param2> 
		 		</#function> 
		 		<li>调用</li> 
		 		你好，我是调用${doAdd(100,100)}
			</ul> 
		</div> 
	</div>
	<hr size="10" color="blue"> 
	<div>
		macro, nested, return 用法
	</div>
	<#macro test foo bar="Bar" baaz=-1> 
  		Test text, and the params: ${foo}, ${bar}, ${baaz} 
	</#macro> 
	<@test foo="a" bar="b" baaz=5*5-2/>  <br />
	<@test foo="a" bar="b"/>  <br />
	<@test foo="a" baaz=5*5-2/>  <br />
	<@test foo="a"/>  <br />
	<div>
		定义循环输出的宏 
	</div>
	<#macro list title items> 
  		<p>${title?cap_first}: </p>
  		<ul> 
   	 		<#list items as x> 
      			<li>${x?cap_first}</li>
    		</#list> 
  		</ul> 
	</#macro>
	<@list items=["mouse", "elephant", "python"] title="Animals"/> 
	<div>
		包含body的宏 
	</div>
	<#macro repeat count> 
  		<#list 1..count as x> 
    		<#nested x, x/2, x==count> 
  		</#list> 
	</#macro> 
	<@repeat count=4; c,halfc,last> 
  		${c}. ${halfc} <br />
  		<#if last> Last!</#if> <br /> 
	</@repeat>
</body>  
</html>
