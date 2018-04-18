@echo off
echo -----------------------------------------------------------------------------
echo Selenium2 Grid node2 - Started: %date% - %time%
echo Parameters: hub=localhost:4444 browser=Chrome or Internet Explorer
echo -----------------------------------------------------------------------------
taskkill /f /im "chromedriver.exe"
echo -----------------------------------------------------------------------------
java -Dwebdriver.chrome.driver="./web-drivers/chromedriver.exe" -Dwebdriver.ie.driver=./web-drivers/IEDriverServer32.exe -jar ./web-selenium-server/selenium-server-standalone-3.11.0.jar -role node -nodeConfig ./web-selenium-server/config/nodeConfig2.json
