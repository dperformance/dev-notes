package com.example.devnotes.global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestMain {
    public static void main(String[] args) throws ParseException {
        String pwdExpiredDate = "2024-05-01";

        Date expireDate = new SimpleDateFormat("yyyy-MM-dd").parse(pwdExpiredDate);
        Date currentDate = new Date();
        long diffDays = (currentDate.getTime() - expireDate.getTime()) / (24 * 60 * 60 * 1000);
        System.out.println(diffDays);
    }
}
