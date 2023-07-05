package com.beyounger.openapi.util;


import com.beyounger.openapi.enums.CharsetEnum;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.RuntimeErrorException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HmacSHA256Base64Util {
    public static Mac MAC;

    static {
        try {
            MAC = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeErrorException(new Error("Can't get Mac's instance."));
        }
    }

    public static String sign(String apiSecret, String signature) throws Exception {
        if (StringUtils.isEmpty(apiSecret) || StringUtils.isEmpty(signature)) {
            throw new Exception("secretKey or signature error");
        }
        System.out.println("origin sign data:{}" + signature);
        byte[] secretKeyBytes = apiSecret.getBytes(CharsetEnum.UTF_8.charset());
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        Mac mac = (Mac) MAC.clone();
        mac.init(secretKeySpec);
        return Base64.getEncoder().encodeToString(mac.doFinal(signature.getBytes(CharsetEnum.UTF_8.charset())));
    }

}