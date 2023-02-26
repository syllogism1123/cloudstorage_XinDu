package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void createNewCredential(Credential credential) {
        credential.setKey(this.encryptionService.encodeKey());
        credential.setPassword(this.encryptPassword(credential));
        this.credentialMapper.insertCredential(credential);
    }

    public void updateCredential(Credential credential) {
        String key = this.credentialMapper.getKeyByCredentialId(credential.getCredentialId());
        String encodedPassword = this.encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(encodedPassword);
        this.credentialMapper.updateCredential(credential);
    }

    public List<Credential> getAllCredentialsByUser(Integer userId) {
        return this.credentialMapper.getAllCredentialsByUser(userId);
    }

    public String encryptPassword(Credential credential) {
        return this.encryptionService.encryptValue(credential.getPassword(), credential.getKey());
    }

    public String decryptPassword(Credential credential) {
        return this.encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    public void deleteCredential(Integer id) {
        this.credentialMapper.deleteCredential(id);
    }

    public Credential getCredentialById(int id) {
        return this.credentialMapper.getCredentialById(id);
    }

}
