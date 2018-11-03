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

WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/a_TVs (1) (1)'))

WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/a_TV List (1) (1)'))

'Check if TV is selected'
isTVSelected = WebUI.verifyElementChecked(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/input_Actions_select_3 (1)'), 
    0, FailureHandling.OPTIONAL)

if (!(isTVSelected)) {
    'Select TV'
    WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/input_Actions_select_3 (1)'))
}

WebUI.delay(5)

'Open the clone dropdown list'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/button_None (1) (1)'))

WebUI.delay(5)

'Select the clone'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/span_ATX_clone_2k16MS_0 (1)'))

WebUI.delay(5)

'Deselect TV'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/input_Actions_select_3 (1)'))

