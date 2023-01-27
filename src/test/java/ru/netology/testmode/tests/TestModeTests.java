package ru.netology.testmode.tests;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;


public class TestModeTests {

    @BeforeEach
    void setupTest() {

        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void ShouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $$("span.button__text").find(Condition.exactText("Продолжить")).click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void ShouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $$("span.button__text").find(Condition.exactText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void ShouldGetErrorIfBlockedRegisteredUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $$("span.button__text").find(Condition.exactText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login is wrong login")
    void ShouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $$("span.button__text").find(Condition.exactText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if password is wrong pasword")
    void ShouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomLogin();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $$("span.button__text").find(Condition.exactText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }

}
