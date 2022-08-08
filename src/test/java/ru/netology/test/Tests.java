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
    String sumTransferIsNotInteger = "9999.99";
    String sumTransferMoreBalance = "100000";
    String sumTransferIsEmpty = "";
    String sumTransferIsNull = "0";
    String sumTransferIsNegative = "-1000";
    String sumTransferIsNotValidValue = "aaa";

    @Test
    void shouldTransferFromFirstCardToSecondCardCardsOk() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getFirstCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        float balanceFirstCard = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float balanceSecondCard = Float.parseFloat(cardsPage.returnSecondCardBalance());
        var transferPage = cardsPage.depositActionFirstCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getSecondCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        float expected1 = balanceFirstCard + Float.parseFloat(sumTransfer);
        float expected2 = balanceSecondCard - Float.parseFloat(sumTransfer);
        float actual1 = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float actual2 = Float.parseFloat(cardsPage.returnSecondCardBalance());
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
        float balanceFirstCard = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float balanceSecondCard = Float.parseFloat(cardsPage.returnSecondCardBalance());
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransfer, DataHelper.getFirstCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        float expected1 = balanceFirstCard - Float.parseFloat(sumTransfer);
        float expected2 = balanceSecondCard + Float.parseFloat(sumTransfer);
        float actual1 = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float actual2 = Float.parseFloat(cardsPage.returnSecondCardBalance());
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }
    @Test
    void shouldTransferFromFirstCardToSecondCardCardsIfSumTransferIsNotInteger() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getFirstCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        float balanceFirstCard = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float balanceSecondCard = Float.parseFloat(cardsPage.returnSecondCardBalance());
        var transferPage = cardsPage.depositActionFirstCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNotInteger, DataHelper.getSecondCardsInfo().getCardNumber());
        cardsPage.isPageExist();
        float expected1 = balanceFirstCard + Float.parseFloat(sumTransferIsNotInteger);
        float expected2 = balanceSecondCard - Float.parseFloat(sumTransferIsNotInteger);
        float actual1 = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float actual2 = Float.parseFloat(cardsPage.returnSecondCardBalance());
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
        transferPage.transfer(sumTransferIsEmpty, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorEnterSumAmount();
    }
    @Test
    void shouldNotTransferFromSecondCardToFirstIfNegativeSumAmount() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNegative, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorEnterSumAmount();
    }
    @Test
    void shouldNotTransferFromSecondCardToFirstIfSumAmountIsNull() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNull, DataHelper.getFirstCardsInfo().getCardNumber());
        transferPage.errorEnterSumAmount();
    }
    @Test
    void shouldNotTransferFromSecondCardToFirstIfSumTransferIsNotValidValue() {
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        var transferPage = cardsPage.depositActionSecondCard();
        transferPage.isPageExist();
        transferPage.transfer(sumTransferIsNotValidValue, DataHelper.getFirstCardsInfo().getCardNumber());
        System.out.println(sumTransferIsNotValidValue);
        transferPage.errorNotValidValue();
    }
    @Test
    void checkButtonUpdateBalance(){
        var authPage = new AuthPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardsPage = verificationPage.cardsPage(verificationCode);
        cardsPage.getSecondCardInfo().shouldBe(Condition.visible,Duration.ofSeconds(10));
        float balanceFirstCard = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float balanceSecondCard = Float.parseFloat(cardsPage.returnSecondCardBalance());
        cardsPage.updateCardsInfo();
        float actual1 = Float.parseFloat(cardsPage.returnFirstCardBalance());
        float actual2 = Float.parseFloat(cardsPage.returnSecondCardBalance());
        Assertions.assertEquals(balanceFirstCard, actual1);
        Assertions.assertEquals(balanceSecondCard, actual2);
    }
}
