package com.example.springSecurityJwtRsa.auth.utils;

import com.example.springSecurityJwtRsa.util.utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
@Data
public class RsaKeyProperties {
    @Value("${rsa.key.public}")
    private String pubKeyFile;

    @Value("${rsa.key.private}")
    private String priKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(pubKeyFile);
        privateKey = RsaUtils.getPrivateKey(priKeyFile);
    }
}
