package com.song.util.codec;

public class MD5 {
    private static final String ALG_MD5 = "MD5";

    public static final byte[] digest(byte[] data) {
        try {
            java.security.MessageDigest md5 = java.security.MessageDigest
                    .getInstance(ALG_MD5);
            return md5.digest(data);
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static final byte[] digest(byte[] data, int offset, int length) {
        try {
            java.security.MessageDigest md5 = java.security.MessageDigest
                    .getInstance(ALG_MD5);
            md5.update(data, offset, length);
            return md5.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }
}
