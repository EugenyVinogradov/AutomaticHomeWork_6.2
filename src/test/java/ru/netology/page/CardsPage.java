package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.dataHelper.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;


public class CardsPage {
    private SelenideElement heading = $x("//*[text()='Ваши карты']");
    private ElementsCollection cards = $$(".list__item div");

    private SelenideElement firstCardInfo = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement firstCardButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private SelenideElement secondCardInfo = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private SelenideElement secondCardButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");
    private SelenideElement updateButton = $("[data-test-id=action-reload]");


    public void isPageExist() {
        heading.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }


    public TransfersPage depositActionFirstCard() {
        firstCardButton.click();
        return new TransfersPage();
    }

    public TransfersPage depositActionSecondCard() {
        secondCardButton.click();
        return new TransfersPage();
    }

    public SelenideElement getFirstCardInfo() {
        return firstCardInfo;
    }

    public SelenideElement getSecondCardInfo() {
        return secondCardInfo;
    }

    public String getCardBalance(String id) {
        for (SelenideElement card : cards) {
            if (card.getAttribute("data-test-id").equals(id)) {
                String balance = card.getText().split(":")[1].split("р")[0].trim();
                return balance;
            }
            throw new RuntimeException("ID карты " + id + " не найден!");
        }
        return null;
    }

    public String  ddd() {
        return cards.get(1).getAttribute("data-test-id");
    }

    public String returnFirstCardBalance() {
        String str = getFirstCardInfo().toString();
        return str.substring(86, str.length() - 19);
    }


    public String returnSecondCardBalance() {
        String str = getSecondCardInfo().toString();
        return str.substring(86, str.length() - 19);
    }

    public CardsPage updateCardsInfo() {
        updateButton.click();
        return new CardsPage();
    }


}
