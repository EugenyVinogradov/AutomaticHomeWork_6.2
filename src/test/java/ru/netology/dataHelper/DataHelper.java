package ru.netology.dataHelper;

import lombok.Value;
//import org.checkerframework.checker.units.qual.A;
//
//import java.util.HashMap;
//import java.util.Map;

public class DataHelper {
    private DataHelper() {
    }

    ;

    @Value
    public static class AuthInfo {
        private String login;
        private String password;

    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class CardsInfo {
        private String cardId;
        private String cardNumber;
        private float cardBalance;

    }


    public static CardsInfo getFirstCardsInfo() {
        return new CardsInfo("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001", 10000);
    }

    public static CardsInfo getSecondCardsInfo() {
        return new CardsInfo("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002", 10000);
    }


    @Value
    public static class VerificationCode {
        private String code;

        public VerificationCode(String code) {
            this.code = code;
        }
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

}
