package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.AuthPage;
import ru.netology.dataHelper.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;


public class Tests {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    String sumTransfer = "5000";
    String sumTransferMoreBalance = "100000";

    @Test
    void shouldTransferFromFirstCardToSecondCardCardsOk() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getFirstCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        int balanceFirstCard = Integer.parseInt(cardsPage.returnFirstCardBalance());
        int balanceSecondCard = Integer.parseInt(cardsPage.returnSecondCardBalance());
        var transferPage = cardsPage.depositActionFirstCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getSecondCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        int expected1 = balanceFirstCard + Integer.parseInt(sumTransfer);
        int expected2 = balanceSecondCard - Integer.parseInt(sumTransfer);
        int actual1 = Integer.parseInt(cardsPage.returnFirstCardBalance());
        int actual2 = Integer.parseInt(cardsPage.returnSecondCardBalance());
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }
    @Test
    void shouldTransferFromSecondCardToFirstCardCardsOk() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        int balanceFirstCard = Integer.parseInt(cardsPage.returnFirstCardBalance());
        int balanceSecondCard = Integer.parseInt(cardsPage.returnSecondCardBalance());
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getFirstCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        int expected1 = balanceFirstCard - Integer.parseInt(sumTransfer);
        int expected2 = balanceSecondCard + Integer.parseInt(sumTransfer);
        int actual1 = Integer.parseInt(cardsPage.returnFirstCardBalance());
        int actual2 = Integer.parseInt(cardsPage.returnSecondCardBalance());
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }
    @Test
    void shouldNotTransferFromSecondCardToFirstIfNotEnoughMoney() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferMoreBalance, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorNotEnoughMoney();
    }
    @Test
    void shouldNotTransferFromSecondCardToFirstIfNotSumMoney() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer("", DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorEnterSumAmount();
    }

}
