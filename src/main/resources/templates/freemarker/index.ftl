<!DOCTYPE html>  
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"  
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">  
    <head>  
        <title>Hello World!</title>  
    </head>  
    <body>
       <center>
       <p>  
           welcome ${name} to freemarker!  
       </p>              
       <p>性别：  
           <#if sex==0>  
                  女  
           <#elseif sex==1>  
                  男  
           <#else>  
                  保密     
           </#if>  
        </p>      
       <h4>我的好友：</h4>  
       <#list friends as item>  
               姓名：${item.name} , 年龄${item.age}  
           <br />  
       </#list>  
       </center>
       <div>
       <#--https://www.cnblogs.com/xumBlog/p/9550530.html-->
       	<center>
       		<#setting number_format="number"/>
			<#assign answer=42/>
			<span>answer:</span>${answer} <br />
			<span>answer(String):</span>${answer?string} <#-- the same as ${answer} --> <br />
			<span>answer(Number):</span>${answer?string.number} <br />
			<span>answer(currency):</span>${answer?string.currency} <br />
			<span>answer(percent):</span>${answer?string.percent} <br />
			<span>answer:</span>${answer} <br />
			<#assign lastUpdated = "2009-01-07 15:05"?datetime("yyyy-MM-dd HH:mm")  />
      		${lastUpdated?string("yyyy-MM-dd HH:mm:ss zzzz")} <br />
      		${lastUpdated?string("EEE,MMM d,yy")} <br />
      		${lastUpdated?string("EEEE,MMMM dd,yyyy,hh:mm:ss a '('zzz')'")} <br />
      		${lastUpdated?string.short} <br />
      		${lastUpdated?string.long} <br />
       	</center>	
       </div>
       <div>
       	<#assign str = "abcdefghijklmn"/>
       	${str}  <br />
		${str?substring(0,4)}  <br />
		${str[0]}${str[4]}    <br />
		${str[1..4]}    　　　 <br />
		${(str?index_of("n"))?string.number}  <br />
		${"123<br>456"?html}   <br />
		${"str"?cap_first}    　<br />
		${"Str"?lower_case}       <br />
		${"Str"?upper_case}        <br />
		${"str"?trim}    	<br />
		<#assign foo=true/> <br />
		${foo?string("yes","no")} <br />
		<#assign week = ["first", "second", "third", "four", "five", "six", "seven"]>
		${week?size} <br />
		<#list week! as we> 
    			${we_index + 1}-${we} <br />
    			<#if (we="third" || we_index = 3)><#break></#if>
		</#list>
		<#list 0..(week!?size-1) as i>
   	 		${week[i]!} <br />
		</#list>
       </div>
    </body>  
</html>