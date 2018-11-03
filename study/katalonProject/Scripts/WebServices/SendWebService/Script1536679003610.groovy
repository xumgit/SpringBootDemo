import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent 
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import internal.GlobalVariable as GlobalVariable



String endpoint = GlobalVariable.cmndUrl
String requestMethod = "POST"
String authHeader = "whateverYouNeedForAuthentication"
TestObjectProperty header1 = new TestObjectProperty("Authorization", ConditionType.EQUALS, authHeader)
TestObjectProperty header2 = new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json")
TestObjectProperty header3 = new TestObjectProperty("Accept", ConditionType.EQUALS, "application/json")
ArrayList defaultHeaders = Arrays.asList(header1, header2, header3)


RequestObject ro = new RestRequestObjectBuilder()
.withRestUrl(endpoint)
.withHttpHeaders(defaultHeaders)
.withRestRequestMethod(requestMethod)
.build()

ro.setBodyContent(new HttpTextBodyContent(httpBody))

'Send a REST request after its URL has been changed'
def response = WS.sendRequest(ro)
responseText = response.getResponseText()

println("Response text:" + responseText)

return responseText
