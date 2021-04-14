package ru.netology;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.TestService.serviceCity;


public class TestApp {
    @BeforeEach
    void shouldOpenWebApp() {
        open("http://localhost:9999");
    }

    @SneakyThrows
    @Test
    void shouldTestValidation() {
        $("[data-test-id=city] input").setValue(serviceCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(TestService.serviceDate(3));
        $("[data-test-id=name] input").setValue(TestService.serviceFIO());
        $("[data-test-id=phone] input").setValue(TestService.servicePhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        assertEquals(TestService.serviceDate(3), $("[data-test-id=date] input").getValue());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(TestService.serviceDate(5));
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        assertEquals(TestService.serviceDate(5), $("[data-test-id=date] input").getValue());
    }

    //  java -jar artifacts/app-card-delivery.jar
}
