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
import java.util.List as List
import java.util.ArrayList as ArrayList
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger

'Go to the TVs tab'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/a_TVs (2) (1)'))

'Go to the TV List tab'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/a_TV List (2) (1)'))

'Select the TV'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/input_Actions_select (2)'))

'Press the Delete selected button'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/label_DeleteSelected'))

WebUI.delay(5)

'Press the OK button of the Delete TV dialog'
WebUI.click(findTestObject('Page_SmartInstall - Manage TV  IP s/button_ok_1'))

WebUI.delay(5)

'Go to the Files tab'
WebUI.click(findTestObject('Object Repository/Page_SmartInstall - Manage TV  IP s/a_Files (1) (1)'))

WebUI.delay(5)

'Go to the Clones tab'
WebUI.click(findTestObject('Page_Smart Install - Manage Files/a_Clone List (1)'))

WebUI.delay(5)

WebUI.closeBrowser()

