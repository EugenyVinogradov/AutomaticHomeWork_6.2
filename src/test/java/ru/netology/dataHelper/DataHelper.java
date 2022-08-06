package ru.netology.dataHelper;

import lombok.Value;

public class DataHelper {
    private DataHelper(){};
    @Value
    public static class AuthInfo {
        private String login;
        private String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }
    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class CardsInfo {
        private String cardNumber;
        private int cardBalance;

        public CardsInfo(String cardNumber, int cardBalance) {
            this.cardNumber = cardNumber;
            this.cardBalance = cardBalance;
        }

    }
    public static CardsInfo getFirstCardsInfo() {
        return new CardsInfo("5559 0000 0000 0001", 10000);
    }
    public static CardsInfo getSecondCardsInfo() {
        return new CardsInfo("5559 0000 0000 0002", 10000);
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
