package com.protobuf.app;

import com.protobuf.app.models.security.Credentials;
import com.protobuf.app.models.security.EmailCredentials;
import com.protobuf.app.models.security.PhoneOTP;

import javax.security.auth.login.CredentialException;

public class Step07Interfaces {
    public static void main(String[] args) {
        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                .setEmail("email@email.com")
                .setPassword("12345")
                .build();

        PhoneOTP phoneOTP = PhoneOTP.newBuilder()
                .setPhone(1234009090)
                .setCode(123445)
                .build();

        // one is required not both: 1st is erased ...
        Credentials bothModes =  Credentials.newBuilder()
                .setPhoneOTP(phoneOTP) // won't be displayed
                .setEmailCredentials(emailCredentials)
                .build();


        Credentials emailCreds = Credentials.newBuilder()
                .setEmailCredentials(emailCredentials).build();
        Credentials phoneCreds = Credentials.newBuilder()
                .setPhoneOTP(phoneOTP).build();
        login(emailCreds);
        System.out.println("******");
        login(phoneCreds);
        System.out.println("******");
        login(bothModes);


    }

    private static void login(Credentials credentials) {
        //System.out.println(credentials.getEmailCredentials());
        //System.out.println(credentials.getPhoneOTP());

        // getAuthModeCase is a generated enum
        // new switch cas way
        //switch (credentials.getAuthModeCase()) {
        //    case EMAILCREDENTIALS -> System.out.println("email " + credentials.getEmailCredentials());
        //    case PHONEOTP -> System.out.println("phone " + credentials.getPhoneOTP());
        //}

        // getAuthModeCase is a generated enum
        // new switch cas way
        String output = switch (credentials.getAuthModeCase()) {
            case EMAILCREDENTIALS -> "email > " + credentials.getEmailCredentials();
            case PHONEOTP -> "phone > " + credentials.getPhoneOTP();
            case AUTHMODE_NOT_SET -> "Unknown credential was used...";
        };

        System.out.println(output);

    }
}
