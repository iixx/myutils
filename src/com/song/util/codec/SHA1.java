package com.song.util.codec;

public class SHA1 {
    private static final String ALG_SHA1 = "SHA-1";

    public static final byte[] digest(byte[] data) {
        try {
            java.security.MessageDigest sha1 = java.security.MessageDigest
                    .getInstance(ALG_SHA1);
            return sha1.digest(data);
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static final byte[] digest(byte[] data, int offset, int length) {
        try {
            java.security.MessageDigest sha1 = java.security.MessageDigest
                    .getInstance(ALG_SHA1);
            sha1.update(data, offset, length);
            return sha1.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }
}
