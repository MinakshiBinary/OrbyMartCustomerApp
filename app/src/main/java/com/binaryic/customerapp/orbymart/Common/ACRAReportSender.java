package com.binaryic.customerapp.orbymart.Common;


import android.util.Log;


import com.binaryic.customerapp.orbymart.Controller.HomeController;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

/**
 * Created by User on 12-04-2016.
 */
public class ACRAReportSender implements ReportSender {



    public ACRAReportSender() {
        super();

    }


    /**
     * Extract the required data out of the crash report.
     */
    private String createCrashReport(CrashReportData report) {

        // I've extracted only basic information.
        // U can add loads more data using the enum ReportField. See below.
        StringBuilder body = new StringBuilder();
        body
                .append("Device : " + report.getProperty(ReportField.BRAND) + "-" + report.getProperty(ReportField.PHONE_MODEL))
                .append("\n")
                .append("Android Version :" + report.getProperty(ReportField.ANDROID_VERSION))
                .append("\n")
                .append("App Version : " + report.getProperty(ReportField.APP_VERSION_CODE))
                .append("\n")
                .append("STACK TRACE : \n" + report.getProperty(ReportField.STACK_TRACE));

        //Log.e("createCrashReport", body.toString());
        return body.toString();
    }

    @Override
    public void send(CrashReportData errorContent) throws ReportSenderException {
        String reportBody = createCrashReport(errorContent);
        new HomeController().sendCrashReportApi(errorContent.getProperty(ReportField.BRAND),errorContent.getProperty(ReportField.PHONE_MODEL),errorContent.getProperty(ReportField.ANDROID_VERSION),"com.binaryic.customerapp.orbymart",errorContent.getProperty(ReportField.APP_VERSION_CODE),errorContent.getProperty(ReportField.STACK_TRACE),"OrbyMart");
        Log.e("ReportBody",reportBody);
    }


}
