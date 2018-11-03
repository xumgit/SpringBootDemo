<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>TV Discovery Service</name>
   <tag></tag>
   <elementGuidId>f7a1d857-a5b9-4398-a372-20a41e6da3c4</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\&quot;CommandDetails\&quot;:{\&quot;TVDiscoveryParameters\&quot;:{\&quot;PowerStatus\&quot;:\&quot;Standby\&quot;,\&quot;TVIPAddress\&quot;:\&quot;192.168.103.159\&quot;,\&quot;TVMACAddress\&quot;:\&quot;1C:5A:6B:B5:C5:7F\&quot;,\&quot;TVModelNumber\&quot;:\&quot;32HFL5011T/12\&quot;,\&quot;TVRoomID\&quot;:\&quot;00017\&quot;,\&quot;TVSerialNumber\&quot;:\&quot;FZ1A1614060553\&quot;,\&quot;VSecureTVID\&quot;:\&quot;800000000000000F4F91\&quot;},\&quot;WebServiceParameters\&quot;:{\&quot;PollingFrequency\&quot;:10,\&quot;TVUniqueID\&quot;:\&quot;FZ1A16140605531C5A6BB5C57F\&quot;}},\&quot;CmdType\&quot;:\&quot;Response\&quot;,\&quot;Cookie\&quot;:293,\&quot;Fun\&quot;:\&quot;TVDiscoveryService\&quot;,\&quot;Svc\&quot;:\&quot;WebServices\&quot;,\&quot;SvcVer\&quot;:\&quot;3.0\&quot;}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
   </httpHeaderProperties>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>http://${GlobalVariable.ipAddressCmnd}:8080/webservices.jsp</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceFunction></soapServiceFunction>
   <variables>
      <defaultValue>'192.168.103.2'</defaultValue>
      <description></description>
      <id>638a2369-d42f-4a9a-92ae-5420483b4228</id>
      <masked>false</masked>
      <name>ipAddress</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()
</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
