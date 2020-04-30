package com.some_domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.PageFactory;

// TODO: this is template class for testing user's access to employee contacts. Now it's not working because of wrong url, login and password
public class TestUserAccessToEmployeeContacts extends WebDriverSettings {
    @Test
    public void TestUserCanSearchHisCompanyMember() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.login("some_login", "some_password");

        String url = "http://some_domain/company/myCompanyID/users?user=SomeColleague";

        int statusCode = getResponseStatusCode(url);

        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void TestUserCannotSearchDifferentCompanyMember() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.login("some_login", "some_password");

        String url = "http://some_domain/company/differentCompanyID/users?user=SomeUser";

        int statusCode = getResponseStatusCode(url);

        Assert.assertEquals(statusCode, 403);
    }

    private int getResponseStatusCode(String url) {
        driver.get(url);

        String currentURL = driver.getCurrentUrl();

        LogEntries logs = driver.manage().logs().get("performance");

        int statusCode = -1;

        for (LogEntry entry : logs) {
            try {
                JSONObject json = new JSONObject(entry.getMessage());

                JSONObject message = json.getJSONObject("message");
                String method = message.getString("method");

                if ("Network.responseReceived".equals(method)) {
                    JSONObject params = message.getJSONObject("params");

                    JSONObject response = params.getJSONObject("response");
                    String messageUrl = response.getString("url");

                    if (currentURL.equals(messageUrl)) {
                        statusCode = response.getInt("status");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return statusCode;
    }
}