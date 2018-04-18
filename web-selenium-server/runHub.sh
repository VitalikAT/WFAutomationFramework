@echo off
echo -----------------------------------------------------------------------------
echo Selenium2 Grid Hub - Started: %date% - %time%
echo Parameters: host=localhost
echo             port=4444
echo -----------------------------------------------------------------------------
java -jar ./web-selenium-server/selenium-server-standalone-3.11.0.jar -role hub -hubConfig ./web-selenium-server/config/hubConfig.json
