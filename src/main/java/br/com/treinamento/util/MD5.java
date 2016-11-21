package br.com.treinamento.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {
    
    private MD5() {
        throw new UnsupportedOperationException("Can't instantiate " + this.getClass().getSimpleName());
    }
    
    public static String generateHash(String content) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        md.update(content.getBytes());
        byte[] bytes = md.digest();

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
}