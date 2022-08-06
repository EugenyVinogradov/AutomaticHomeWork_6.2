package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.AuthPage;
import ru.netology.data.CardsPage;
import ru.netology.dataHelper.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class Tests {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    int sumTransfer = 5000;
    int sumTransferMoreBalance = 100000;
    @Test
    void shouldTransferFromFirstCardToSecondCardCardsOk() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.isPageExist();
        int balanceFirstCard = Integer.parseInt(cardsPage.returnFirstCardBalance());
        int balanceSecondCard = Integer.parseInt(cardsPage.returnSecondCardBalance());
        var transferPage = cardsPage.depositAction(cardsPage.selectFirstCardButton());
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getSecondCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        int expected1 = balanceFirstCard + sumTransfer;
        int expected2 = balanceSecondCard - sumTransfer;
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
        cardsPage.isPageExist();
        int balanceFirstCard = Integer.parseInt(cardsPage.returnFirstCardBalance());
        int balanceSecondCard = Integer.parseInt(cardsPage.returnSecondCardBalance());
        var transferPage = cardsPage.depositAction(cardsPage.selectSecondCardButton());
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getFirstCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        int expected1 = balanceFirstCard - sumTransfer;
        int expected2 = balanceSecondCard + sumTransfer;
        int actual1 = Integer.parseInt(cardsPage.returnFirstCardBalance());
        int actual2 = Integer.parseInt(cardsPage.returnSecondCardBalance());
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

}
