package page;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private static String authPageUrl = "https://ilswebreact-develop.azurewebsites.net/";

    public LoginPage openLoginPage(){
        open(authPageUrl);
        $("#login").shouldBe(visible);
        return this;
    }

    public LoginPage setCredentials(String login, String password) {
        $("#login").setValue(login);
        $("#password").setValue(password);
        return this;
    }

    public LoginPage pressRecoveryButton() {
        $("[href='/recovery']").click();
        return this;
    }

    public LoginPage setEmail(String email) {
        $("#email").setValue(email);
        return this;
    }

    public void pressButton(String buttonName) {
        $$("button").findBy(text(buttonName)).click();
    }

    public String getAlertText() {
       return  $("[role='alert']").text();
    }

}
