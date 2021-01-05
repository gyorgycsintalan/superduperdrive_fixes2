package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credential.getUnencryptedPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credentialsMapper.addCredential(credential);
    }

    public int updateCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);

        credential.setPassword(
                encryptionService.encryptValue(credential.getUnencryptedPassword(), encodedKey)
        );
        return credentialsMapper.updateCredential(credential);
    }


    public Credential[] getCredentials(Integer userid) {
        Credential [] credentials = credentialsMapper.getCredentials(userid);
        for (Credential credential: credentials) {
            credential.setUnencryptedPassword(
                    encryptionService.decryptValue(credential.getPassword(), credential.getKey())
            );
        }
        return credentials;
    }


    public int deleteCredential(Integer credentialid, Integer userid) {
        return credentialsMapper.deleteCredential(credentialid, userid);
    }

}
