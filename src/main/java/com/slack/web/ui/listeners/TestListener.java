package com.slack.web.ui.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

public class TestListener implements ITestListener {

    private Logger logger = Logger.getLogger(TestListener.class);
    private static ExtentReports extentReports;
    private static ExtentSparkReporter sparkReporter;
    private static ExtentTest extentTest;

    public void onStart(ITestContext context) {
        logger.info("*** Test Suite " + context.getName() + " started ***");
        extentReports = new ExtentReports();
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/target/Test-Automation-Report.html");
        extentReports.attachReporter(sparkReporter);
        extentTest = extentReports.createTest(context.getCurrentXmlTest().getName());
    }

    public void onFinish(ITestContext context) {
        logger.info("*** Test Suite " + context.getName() + " ending ***");
        extentReports.flush();
    }

    public void onTestStart(ITestResult result) {
        logger.info(("*** Running test method " + result.getMethod().getMethodName() + "..."));

    }

    public void onTestSuccess(ITestResult result) {
        logger.info("*** Executed " + result.getMethod().getMethodName() + " test successful...");
        extentTest.log(Status.PASS,result.getMethod().getMethodName() + " test successful...");
    }

    public void onTestFailure(ITestResult result) {
        logger.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        extentTest.log(Status.FAIL,result.getName());
        extentTest.log(Status.FAIL,result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        extentTest.log(Status.SKIP,result.getMethod().getMethodName() + " skipped...");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
        extentTest.log(Status.FAIL,"Test failed but within percentage % " + result.getMethod().getMethodName());
    }
}
