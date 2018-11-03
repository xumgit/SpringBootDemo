import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.callTestCase(findTestCase('Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Upload clone'), [:], FailureHandling.STOP_ON_FAILURE)

responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.TVDiscoveryHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.ReadyForUpgradeHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Assign clone'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(10)

'Check if clone identifier is blue'
WebUI.callTestCase(findTestCase('Check clone identifier color'), [('css_color') : 'rgba(0, 0, 255, 1)'], FailureHandling.STOP_ON_FAILURE)

//Upgrade attempt 1
responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.ReadyForUpgradeHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.delay(10)

'Send an upgrade inprogress ipcloneserver'
responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.InProgressHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.delay(10)

'Check if clone identifier is orange'
WebUI.callTestCase(findTestCase('Check clone identifier color'), [('css_color') : 'rgba(255, 191, 0, 1)'], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(10)

//Upgrade attempt 2
'Return a successfull upgrade with incorrect clone item identifiers and readyforupgrade'
responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.FailedUpgradeHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

'Check if clone identifier is orange'
WebUI.callTestCase(findTestCase('Check clone identifier color'), [('css_color') : 'rgba(255, 191, 0, 1)'], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

'Send an upgrade inprogress ipcloneserver'
responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.InProgressHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

'Check if clone identifier is orange'
WebUI.callTestCase(findTestCase('Check clone identifier color'), [('css_color') : 'rgba(255, 191, 0, 1)'], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(10)

//Upgrade attempt 3
'Return a successfull upgrade with incorrect clone item identifiers and readyforupgrade'
responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.FailedUpgradeHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

'Check if clone identifier is orange'
WebUI.callTestCase(findTestCase('Check clone identifier color'), [('css_color') : 'rgba(255, 191, 0, 1)'], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

'Send an upgrade inprogress ipcloneserver'
responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.InProgressHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

'Check if clone identifier is orange'
WebUI.callTestCase(findTestCase('Check clone identifier color'), [('css_color') : 'rgba(255, 191, 0, 1)'], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(20)

'Return a successfull upgrade with incorrect clone item identifiers and readyforupgrade'
responseText = WebUI.callTestCase(findTestCase('WebServices/SendWebService'), [('httpBody') : GlobalVariable.FailedUpgradeHttpBody], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

//third attempt failed
'Check if clone identifier is red'
WebUI.callTestCase(findTestCase('Check clone identifier color'), [('css_color') : 'rgba(255, 0, 0, 1)'], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('CleanUp'), [:], FailureHandling.STOP_ON_FAILURE)

