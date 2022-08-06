package ru.netology.data;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransfersPage {
    private SelenideElement heading = $x("//h1[contains(text(),'Пополнение карты')]");
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement transferFrom = $("[data-test-id=from] input");
    private SelenideElement transferTo = $("[data-test-id=to]");
    private SelenideElement actionButton = $("[data-test-id=action-transfer].button");
    private SelenideElement chancelButton = $("[data-test-id=action-cancel].button");


    public boolean isPageExist() {
        boolean exist = heading.isDisplayed();
        return exist;
    }
    public CardsPage transfer (int sum, String cardNumber) {
        amount.setValue(String.valueOf(sum));
        transferFrom.setValue(cardNumber);
        actionButton.click();
        return new CardsPage();
    }
}
