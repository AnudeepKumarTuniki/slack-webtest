#!/bin/sh

#Provide browser name either firefox/chrome for this setup
#E.g: browser= "chrome"
browser= "<browser>"
#Slack registered user email
email="<Email>"
#Slack web url for the registered user.
# E.g: slack_url=https://anudeeptuniki.slack.com
slack_url="<Slack URL>"
#Slack web password
passwd="<Passwd>"
#User channel name
#E.g: channel="automation"
channel="<Channel name>"
#Host where the selenium grid is running.As a prerequisite you have to run "docker-compose up" in your local
#This spins up a grid network. Which can be verified at "http://localhost:4444". If successful pass host as 'localhost' or leave empty if it is a local run
#E.g: hub_host="localhost"
hub_host="<grid host>"
#suite xml file to run from 'ui/suites' directory.
# E.g suiteXmlFile="ui/suites/regression.xml"
suiteXmlFile="<suiteXmlFile>"

mvn clean test -Dbrowser=$browser -DsuiteXmlFile=$suiteXmlFile -Demail=$email -Dslack_url=$slack_url -Dchannel=$channel -Dpasswd=$passwd -Dhub_host=$hub_host