package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import page.LoginPage;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Проверка авторизации")
@Tag("auth")
class AuthorizationTest {

    private static final String login = "rekame2869@seinfaq.com";
    private static final String password = "XRCa91zn4fsJzcHW";
    private static LoginPage loginPage;

    @BeforeEach
    void set() {
        loginPage = new LoginPage();
        loginPage.openLoginPage();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    public void tearDown() {
        SelenideLogger.removeListener("allure");
    }

    @DisplayName("Авторизация с корректными кредами")
    @Test
    void checkAuthWithCorrectCredentials(){
        loginPage
                .setCredentials(login, password)
                .pressButton("Войти");
        assertEquals("Тестер", $(".current-user").getText());
    }

    @DisplayName("Авторизация с некорректными кредами")
    @Test
    void checkAuthWithWrongCredentials(){
        loginPage
                .setCredentials(login,"wrongPassword")
                .pressButton("Войти");
        assertEquals("Ошибка авторизации. Не верные логин/пароль", loginPage.getAlertText());
    }

    @DisplayName("Востановление пароля")
    @Test
    void checkPasswordRecovery(){
        loginPage
                .setCredentials(login,password)
                .pressRecoveryButton()
                .setEmail(login)
                .pressButton("Отправить");
        assertEquals("На указанный email отправлено письмо со ссылкой на сброс пароля", loginPage.getAlertText());
    }

    @DisplayName("Отображение предупреждения.")
    @ParameterizedTest(name = "{2}")
    @MethodSource("credentials")
    void checkAuthWithEmptyField(String login, String password, String message){
        loginPage
                .setCredentials(login, password)
                .pressButton("Войти");
        assertEquals(message, loginPage.getAlertText());
    }

    private static Stream<Arguments> credentials() {
        return Stream.of(
                Arguments.of(login,"", "Пожалуйста, введите пароль"),
                Arguments.of("",password, "Пожалуйста, введите логин")
        );
    }
}
