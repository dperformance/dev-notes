package com.example.devnotes.global;

import org.springframework.security.core.parameters.P;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestMain {
    public static void main(String[] args) throws ParseException {
        String pwdExpiredDate = "2024-05-01";

        Date expireDate = new SimpleDateFormat("yyyy-MM-dd").parse(pwdExpiredDate);
        Date currentDate = new Date();
        long diffDays = (currentDate.getTime() - expireDate.getTime()) / (24 * 60 * 60 * 1000);
        System.out.println(diffDays);

        List<String> receiverList = new ArrayList<>();

        String receivers = "01033185023,01027416931,01033373659,01061360713";

        String[] receiver = receivers.split(",");

//        for (int idx=0; idx < receiver.length; idx++) {
//            receiverList.add(receiver[idx]);
//        }

        receiverList.addAll(Arrays.asList(receiver));

        // [01033185023, 01027416931, 01033373659, 01061360713]
        // [01033185023, 01027416931, 01033373659, 01061360713]
        System.out.println(receiverList);


        String auth = "ROLE_ECO_BDM";

        System.out.println(auth.contains("ROLE_ECO_BDM"));

        
    }
}
