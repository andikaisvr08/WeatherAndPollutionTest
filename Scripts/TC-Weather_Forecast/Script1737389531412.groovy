import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper

// Mengirim permintaan API untuk mendapatkan perkiraan cuaca
def response = WS.sendRequestAndVerify(findTestObject('WeatherForecast_Request'))

// Mendapatkan respons body dalam bentuk JSON
def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())

// Mengecek status kode respons (harus 200 OK)
assert response.getStatusCode() == 200 : "Expected status code 200, but got: " + response.getStatusCode()
assert jsonResponse.city.name == "Jakarta" : "Expected city name 'Jakarta', but got: " + jsonResponse.city.name
assert jsonResponse.city.coord.lat == -6.2146 : "Expected latitude -6.2146, but got: " + jsonResponse.city.coord.lat
assert jsonResponse.city.coord.lon == 106.8451 : "Expected longitude 106.8451, but got: " + jsonResponse.city.coord.lon

// Verifikasi apakah data cuaca valid
assert jsonResponse.cod == "200" : "Expected '200' cod, but got: " + jsonResponse.cod
assert jsonResponse.list.size() == 5 : "Expected 5 forecasts, but got: " + jsonResponse.list.size()

// Verifikasi setiap cuaca dalam list
jsonResponse.list.each { forecast ->
	assert forecast.weather != null : "Weather data is missing"
	assert forecast.main != null : "Main data is missing"
}
