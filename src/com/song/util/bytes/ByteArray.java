package com.song.util.bytes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteArray {
    private static final Pattern MATCH_HEX_STRING = Pattern.compile("^([0-9a-fA-F][0-9a-fA-F])+$");

    /**
     * Convert a bytes array to a hex string
     * Example: [0x1A,0x02] -> "1A02"
     * @param octets
     * @return
     */
    public static String toHexString(byte[] octets) {
        return toHexString(octets, 0, octets.length);
    }
    
    /**
     * Convert a bytes array to a hex string
     * Example: [0x1A,0x02] -> "1A02"
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
     * Example: "1A02" -> [0x1A,0x02]
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
    
    /**
     * 
     * @param octets
     * @param offset
     * @param length
     * @return
     */
    public static byte[] middleOf(byte[] octets, int offset, int length) {
        byte[] buf = new byte[length];
        System.arraycopy(octets, offset, buf, 0, length);
        return buf;
    }
    
    /**
     * 
     * @param octets
     * @param length
     * @return
     */
    public static byte[] endOf(byte[] octets, int length) {
        return middleOf(octets, octets.length - length, length);
    }
    
    /**
     * 
     * @param octets
     * @param length
     * @return
     */
    public static byte[] beginOf(byte[] octets, int length) {
        return middleOf(octets, 0, length);
    }
    
    /**
     * Expand the number of byte(0x00) on the left side
     * @param octets
     * @param number
     * @return
     */
    public static byte[] leftExpand(byte[] octets, int number) {
        byte[] buf = new byte[octets.length + number];
        System.arraycopy(octets, 0, buf, number, octets.length);
        return buf;
    }
    
    /**
     * Expand the number of byte(0x00) on the right side
     * @param octets
     * @param number
     * @return
     */
    public static byte[] rightExpand(byte[] octets, int number) {
        byte[] buf = new byte[octets.length + number];
        System.arraycopy(octets, 0, buf, 0, octets.length);
        return buf;
    }
    
    /**
     * Expand a byte on the left side
     * @param octets
     * @param expand
     * @return
     */
    public static byte[] leftExpand(byte[] octets, byte expand) {
        byte[] buf = new byte[octets.length + 1];
        System.arraycopy(octets, 0, buf, 1, octets.length);
        buf[0] = expand;
        return buf;
    }
    
    /**
     * Expand a byte on the right side
     * @param octets
     * @param expand
     * @return
     */
    public static byte[] rightExpand(byte[] octets, byte expand) {
        byte[] buf = new byte[octets.length + 1];
        System.arraycopy(octets, 0, buf, 0, octets.length);
        buf[octets.length] = expand;
        return buf;
    }
    
}
