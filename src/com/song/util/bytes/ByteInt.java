package com.song.util.bytes;

public class ByteInt {
    
    /**
     * Returns a 4 bytes array representation of the integer argument as an unsigned integer.
     * @param value
     * @return
     */
    public static byte[] getBytes(int value) {
        byte[] out = new byte[4];
        for (int i=3; i>=0; i--) {
            out[i] = (byte) (value);
            value >>>= 8;
        }
        return out;
    }
    
    /**
     * Append 4 bytes representation of the integer argument as an unsigned integer to a byte array.
     * @param value
     * @return
     */
    public static void getBytes(int value, byte[] out, int off) {
        for (int i=3; i>=0; i--) {
            out[off + i] = (byte) (value);
            value >>>= 8;
        }
    }

    /**
     * Returns an signed integer from the 4 bytes array.
     * @param bts
     * @return
     * For example: 0x00000001 -> 1
     */
    public static int parseInt(byte[] bts) {
        return parseInt(bts, 0);
    }
    
    /**
     * Returns an signed integer from the 4 bytes array.
     * @param bts
     * @return
     * For example: 0x00000001 -> 1
     */
    public static int parseInt(byte[] bts, int off) {
        int value = 0;
        int end = off + 4;
        while (off < end) {
            value <<= 8;
            value += (bts[off] & 0x0FF);
            off++;
        }
        return value;
    }
    
    public static int toInt(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;
        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * i);
        }
        return iOutcome;
    }
    
    /**
     * Make a byte array(1~4) to an integer type
     * @param octets
     * @param offset
     * @param length
     * @return
     */
    public static int toUnsignedInt(byte[] octets, int offset, int length) {
        if (length > 4) {
            throw new Error("Only support length between 1 to 4");
        }

        int ret = 0;
        for (int i = 0; i < length; i++) {
            ret = ret << 8;
            ret |= (octets[offset + i] & 0x00FF);
        }

        if (ret < 0) {
            throw new Error("Out of unsigned integer range");
        }
        return ret;
    }

    public static byte[] fromUnsignedInt(int i) {
        byte[] buf = new byte[4];
        buf[0] = (byte) ((i >> 24) & 0x0FF);
        buf[1] = (byte) ((i >> 16) & 0x0FF);
        buf[2] = (byte) ((i >> 8) & 0x0FF);
        buf[3] = (byte) (i);
        return buf;
    }

    /**
     * Make a byte array(1~8) to a long type
     * @param octets
     * @param offset
     * @param length
     * @return
     */
    public static long toUnsignedLong(byte[] octets, int offset, int length)  {
        if(length > 8) {
            throw new Error("Only support length between 1 to 8");
        }
        
        long ret = 0L;
        for(int i=0; i<length; i++) {
            ret = ret << 8;
            ret |= (octets[offset + i] & 0x00FF);
        }
        
        if (ret < 0) {
            throw new Error("Out of unsigned long range");
        }
        return ret;
    }

    public static byte[] fromUnsignedShort(short s) {
        byte[] buf = new byte[2];
        buf[0] = (byte) ((s >> 8) & 0x0FF);
        buf[1] = (byte) (s);
        return buf;
    }
    
}
