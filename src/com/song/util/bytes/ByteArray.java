package com.song.util.bytes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteArray {
    private static final Pattern MATCH_HEX_STRING = Pattern.compile("^([0-9a-fA-F][0-9a-fA-F])+$");

    /**
     * Convert a bytes array to a hex string
     * Example:[0x1A,0x02] -> "1A02"
     * @param octets
     * @return
     */
    public static String toHexString(byte[] octets) {
        return ByteArray.toHexString(octets, 0, octets.length);
    }
    
    /**
     * Convert a bytes array to a hex string
     * Example:[0x1A,0x02] -> "1A02"
     * @param octets
     * @param offset
     * @param length
     * @return
     */
    public static String toHexString(byte[] octets, int offset, int length) {
        StringBuilder str = new StringBuilder(length*2);
        length += offset;
        while (offset < length) {
            str.append(Integer.toHexString(octets[offset] | 0xFFFFFF00).substring(6));
            offset++;
        }
        return str.toString().toUpperCase();
    }
    
    /**
     * Convert a hex string to a byte array
     * Example:"1A02" -> [0x1A,0x02]
     * @param hexstr
     * @return if hexstr is not a hex string, return null.
     */
    public static byte[] fromHexString(String hexstr) {
        if (null == hexstr || "".equalsIgnoreCase(hexstr)) {
            return new byte[0];
        }
        hexstr = hexstr.trim();
        Matcher matcher = MATCH_HEX_STRING.matcher(hexstr);
        if (!matcher.matches()) {
            return null;
        }
        int len = hexstr.length() / 2;
        byte[] out = new byte[len];
        for (int i = 0; i < len; i++) {
            int beg = i * 2;
            out[i] = (byte) Integer.parseInt(hexstr.substring(beg, beg + 2), 16);
        }
        return out;
    }
    
    /**
     * Merge all byte arrays to a new array
     * @param byteArrays
     * @return
     */
    public static byte[] merge(byte[] ... byteArrays) {
        int pos = 0;
        for (byte[] bs : byteArrays) {
            pos += bs.length;
        }
        byte[] buf = new byte[pos];
        pos = 0;
        for (byte[] bs : byteArrays) {
            System.arraycopy(bs, 0, buf, pos, bs.length);
            pos += bs.length;
        }
        return buf;
    }
}
