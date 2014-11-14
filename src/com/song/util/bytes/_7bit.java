package com.song.util.bytes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class _7bit {

	private static HashMap<Character, Byte> ucs2To7bitBaseMap = new HashMap<Character, Byte>();
    private static HashMap<Character, Byte> ucs2To7bitExtMap = new HashMap<Character, Byte>();
    private static HashMap<Byte,Character> _7bitToUcs2BaseMap = new HashMap<Byte,Character>();
    private static HashMap<Byte,Character> _7bitToUcs2ExtMap = new HashMap<Byte,Character>();
    
    static {
        _7bitToUcs2BaseMap.put((byte)0x00,'@');
        _7bitToUcs2BaseMap.put((byte)0x10,'∆');
        _7bitToUcs2BaseMap.put((byte)0x40,'¡');
        _7bitToUcs2BaseMap.put((byte)0x60,'¿');
        _7bitToUcs2BaseMap.put((byte)0x01,'£');
        _7bitToUcs2BaseMap.put((byte)0x11,'_');
        _7bitToUcs2BaseMap.put((byte)0x02,'$');
        _7bitToUcs2BaseMap.put((byte)0x12,'Φ');
        _7bitToUcs2BaseMap.put((byte)0x03,'¥');
        _7bitToUcs2BaseMap.put((byte)0x13,'Γ');
        _7bitToUcs2BaseMap.put((byte)0x04,'è');
        _7bitToUcs2BaseMap.put((byte)0x14,'Λ');
        _7bitToUcs2BaseMap.put((byte)0x24,'¤');
        _7bitToUcs2BaseMap.put((byte)0x05,'é');
        _7bitToUcs2BaseMap.put((byte)0x15,'Ω');
        _7bitToUcs2BaseMap.put((byte)0x06,'ù');
        _7bitToUcs2BaseMap.put((byte)0x16,'Π');
        _7bitToUcs2BaseMap.put((byte)0x07,'ì');
        _7bitToUcs2BaseMap.put((byte)0x17,'Ψ');
        _7bitToUcs2BaseMap.put((byte)0x08,'ò');
        _7bitToUcs2BaseMap.put((byte)0x18,'Σ');
        _7bitToUcs2BaseMap.put((byte)0x09,'Ç');
        _7bitToUcs2BaseMap.put((byte)0x19,'Θ');
        _7bitToUcs2BaseMap.put((byte)0x1A,'Ξ');
        _7bitToUcs2BaseMap.put((byte)0x0B,'Ø');
        _7bitToUcs2BaseMap.put((byte)0x5B,'Ä');
        _7bitToUcs2BaseMap.put((byte)0x7B,'ä');
        _7bitToUcs2BaseMap.put((byte)0x0C,'ø');
        _7bitToUcs2BaseMap.put((byte)0x1C,'Æ');
        _7bitToUcs2BaseMap.put((byte)0x5C,'Ö');
        _7bitToUcs2BaseMap.put((byte)0x7C,'ö');
        _7bitToUcs2BaseMap.put((byte)0x1D,'æ');
        _7bitToUcs2BaseMap.put((byte)0x5D,'Ñ');
        _7bitToUcs2BaseMap.put((byte)0x7D,'ñ');
        _7bitToUcs2BaseMap.put((byte)0x0E,'Å');
        _7bitToUcs2BaseMap.put((byte)0x1E,'ß');
        _7bitToUcs2BaseMap.put((byte)0x5E,'Ü');
        _7bitToUcs2BaseMap.put((byte)0x7E,'ü');
        _7bitToUcs2BaseMap.put((byte)0x0F,'å');
        _7bitToUcs2BaseMap.put((byte)0x1F,'É');
        _7bitToUcs2BaseMap.put((byte)0x5F,'§');
        _7bitToUcs2BaseMap.put((byte)0x7F,'à');
        //----extension
        _7bitToUcs2ExtMap.put((byte)0x14,'^');
        _7bitToUcs2ExtMap.put((byte)0x28,'{');
        _7bitToUcs2ExtMap.put((byte)0x29,'}');
        _7bitToUcs2ExtMap.put((byte)0x2F,'\\');
        _7bitToUcs2ExtMap.put((byte)0x3C,'[');
        _7bitToUcs2ExtMap.put((byte)0x3D,'~');
        _7bitToUcs2ExtMap.put((byte)0x3E,']');
        _7bitToUcs2ExtMap.put((byte)0x40,'|');
        
        ucs2To7bitBaseMap.put('@', (byte) 0x00);
        ucs2To7bitBaseMap.put('∆', (byte) 0x10);
        ucs2To7bitBaseMap.put('¡', (byte) 0x40);
        ucs2To7bitBaseMap.put('¿', (byte) 0x60);
        ucs2To7bitBaseMap.put('£', (byte) 0x01);
        ucs2To7bitBaseMap.put('_', (byte) 0x11);
        ucs2To7bitBaseMap.put('$', (byte) 0x02);
        ucs2To7bitBaseMap.put('Φ', (byte) 0x12);
        ucs2To7bitBaseMap.put('¥', (byte) 0x03);
        ucs2To7bitBaseMap.put('Γ', (byte) 0x13);
        ucs2To7bitBaseMap.put('è', (byte) 0x04);
        ucs2To7bitBaseMap.put('Λ', (byte) 0x14);
        ucs2To7bitBaseMap.put('¤', (byte) 0x24);
        ucs2To7bitBaseMap.put('é', (byte) 0x05);
        ucs2To7bitBaseMap.put('Ω', (byte) 0x15);
        ucs2To7bitBaseMap.put('ù', (byte) 0x06);
        ucs2To7bitBaseMap.put('Π', (byte) 0x16);
        ucs2To7bitBaseMap.put('ì', (byte) 0x07);
        ucs2To7bitBaseMap.put('Ψ', (byte) 0x17);
        ucs2To7bitBaseMap.put('ò', (byte) 0x08);
        ucs2To7bitBaseMap.put('Σ', (byte) 0x18);
        ucs2To7bitBaseMap.put('Ç', (byte) 0x09);
        ucs2To7bitBaseMap.put('Θ', (byte) 0x19);
        ucs2To7bitBaseMap.put('Ξ', (byte) 0x1A);
        ucs2To7bitBaseMap.put('Ø', (byte) 0x0B);
        ucs2To7bitBaseMap.put('Ä', (byte) 0x5B);
        ucs2To7bitBaseMap.put('ä', (byte) 0x7B);
        ucs2To7bitBaseMap.put('ø', (byte) 0x0C);
        ucs2To7bitBaseMap.put('Æ', (byte) 0x1C);
        ucs2To7bitBaseMap.put('Ö', (byte) 0x5C);
        ucs2To7bitBaseMap.put('ö', (byte) 0x7C);
        ucs2To7bitBaseMap.put('æ', (byte) 0x1D);
        ucs2To7bitBaseMap.put('Ñ', (byte) 0x5D);
        ucs2To7bitBaseMap.put('ñ', (byte) 0x7D);
        ucs2To7bitBaseMap.put('Å', (byte) 0x0E);
        ucs2To7bitBaseMap.put('ß', (byte) 0x1E);
        ucs2To7bitBaseMap.put('Ü', (byte) 0x5E);
        ucs2To7bitBaseMap.put('ü', (byte) 0x7E);
        ucs2To7bitBaseMap.put('å', (byte) 0x0F);
        ucs2To7bitBaseMap.put('É', (byte) 0x1F);
        ucs2To7bitBaseMap.put('§', (byte) 0x5F);
        ucs2To7bitBaseMap.put('à', (byte) 0x7F);
        //----extension
        ucs2To7bitExtMap.put('^', (byte) 0x14);
        ucs2To7bitExtMap.put('{', (byte) 0x28);
        ucs2To7bitExtMap.put('}', (byte) 0x29);
        ucs2To7bitExtMap.put('\\', (byte) 0x2F);
        ucs2To7bitExtMap.put('[', (byte) 0x3C);
        ucs2To7bitExtMap.put('~', (byte) 0x3D);
        ucs2To7bitExtMap.put(']', (byte) 0x3E);
        ucs2To7bitExtMap.put('|', (byte) 0x40);
    }
    
    /**
     * convert 7bit to String
     * @param src unpacked 7bit byte[]
     * @return
     */
    public static String from7bit(byte[] src){
        String regex = "[\\x1B\\x00\\x10\\x40\\x60\\x01\\x11\\x02\\x12\\x03\\x13\\x04\\x14\\x24\\x05\\x15\\x06\\x16\\x07\\x17\\x08\\x18\\x09\\x19\\x1A\\x0B\\x5B\\x7B\\x0C\\x1C\\x5C\\x7C\\x1D\\x5D\\x7D\\x0E\\x1E\\x5E\\x7E\\x0F\\x1F\\x5F\\x7F\\x14\\x28\\x29\\x2F\\x3C\\x3D\\x3E\\x40]+";
        if(Pattern.compile(regex).matcher(new String(src)).find()){//匹配到需要转换的字符，查表
            StringBuilder sb = new StringBuilder();
            boolean isAfter1B = false;//标记是否是扩展字符
            for(int i = 0;i<src.length;i++){
                if(src[i]==0x1B){
                    isAfter1B = true;
                    continue;
                }
                Character ch = null;
                if(isAfter1B){//跟在1B之后，需要查扩展表
                    isAfter1B = false;
                    ch = _7bitToUcs2ExtMap.get(src[i]);
                }else{
                    ch = _7bitToUcs2BaseMap.get(src[i]);
                }
                if(ch!=null){
                    sb.append(ch);
                }else{
                    sb.append((char)src[i]);
                }
            }
            return sb.toString();
        }else{//是常用字符，不需查表
            return new String(src);
        }
    }
    
    /**
     * convert UTF-16BE to 7bit unpacked
     * @param src
     * @return
     */
    public static byte[] to7bit(String src){
        char[] charArray = src.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        
        //匹配需要转换的字符
        String regex = "[@∆¡¿£_$Φ¥ΓèΛ¤éΩùΠìΨòΣÇΘΞØÄäøÆÖöæÑñÅßÜüåÉ§à^{}\\\\\\[~\\]|]+";
        if(Pattern.compile(regex).matcher(src).find()){//匹配成功，需要查表
            for (int i = 0,j = i;i<charArray.length;i++) {
                if(ucs2To7bitBaseMap.get(charArray[i]) == null){
                    if(ucs2To7bitExtMap.get(charArray[i])==null){
                        byteArray[j] = (byte) charArray[i];
                        j++;
                        continue;
                    }else{
                        byteArray = Arrays.copyOf(byteArray, byteArray.length+1);
                        byteArray[j] = 0x1B;
                        byteArray[j+1] = ucs2To7bitExtMap.get(charArray[i]);
                        j += 2;
                        continue;
                    }
                }else{
                    byteArray[j] = ucs2To7bitBaseMap.get(charArray[i]);
                    j++;
                }
            }
        }else{//不需要查表
            for (int i = 0;i<charArray.length;i++) {
                byteArray[i] = (byte) charArray[i];
            }
        }
        return byteArray;
    }

    public static byte[] packSMS(byte[] octets) {
		return pack(octets, SMS_PADDING);
	}
	
	public static byte[] unpackSMS(byte[] septet, int length) {
		byte[] octets = unpack(septet);
		if (length < octets.length) {
			return Arrays.copyOfRange(octets, 0, length);
		}
		return octets;
	}
	
	public static byte[] packUSSD(byte[] octets) {
		int len = octets.length;
		/*
		 * If <CR> is intended to be the last character and the message (including the wanted < CR>) ends on an octet boundary,
		 * then another <CR> must be  added together with a padding bit 0. The receiving entity will perform the carriage return
		 * function twice, but this will not result in misoperation as the definition of <CR> in subclause 7.1.1 is identical to the
		 * definition of <CR><CR>. 
		 */
		if ((len % 8) == 0 && (octets[len - 1] == (byte) 0x0D)) {
			octets = ByteArray.rightExpand(octets, 1);
			octets[len] = (byte) 0x0D;
		}
		/*
		 * If the total number of characters to be sent equals (8n-1) where n=1,2,3 etc. then there are 7 spare bits at the end of the
		 * message. To avoid the situation where the receiving entity confuses 7 binary zero pad bits as the @ character, the
		 * carriage return or <CR> character  (defined in subclause 7.1.1) shall be used for padding in this situation, just as for Cell
		 * Broadcast. 
		 */
		return pack(octets, USSD_PADDING);
	}
	
	public static byte[] unpackUSSD(byte[] septet) {
		/*
		 * The receiving entity shall remove the final <CR> character where the message ends on an octet boundary with <CR> as
		 * the last character. 
		 */
		byte[] octets = unpack(septet);
		int len = octets.length;
		if ((len % 8) == 0 && (octets[len - 1] == (byte) 0x0D)) {
			return Arrays.copyOfRange(octets, 0, len - 1);
		}
		return octets;
	}
	
	private static byte[] pack(byte[] octets, int padding) {
		int len = (octets.length * 7) / 8;
		int rem = (octets.length * 7) % 8;
		byte[] out = new byte[(rem == 0) ? len : len + 1];
		int prebyte = 0;
		for (int i=0,j=0; i<octets.length; i++) {
			int curbyte = (octets[i] & 0x7F);
			int shift = i % 8;
			if (shift == 0) {
				prebyte = curbyte;
				continue;
			}
			curbyte <<= (8 - shift);
			prebyte |= curbyte;
			out[j++] = (byte) (prebyte & 0x0FF);
			prebyte >>= 8;
		}
		if (rem != 0) {
			if (rem == 1) {
				prebyte |= padding;
			}
			out[len] = (byte) (prebyte & 0x0FF);
		}
		return out;
	}
	
	private static byte[] unpack(byte[] septet) {
		byte[] out = new byte[(septet.length * 8) / 7];
		int prebyte = 0;
		for (int i=0,j=0; i<septet.length; i++) {
			int shift = i % 7;
			int curbyte = (septet[i] & 0x0FF);
			curbyte <<= shift;
			curbyte |= prebyte;
			out[j++] = (byte) (curbyte & 0x7F);
			prebyte = (curbyte >> 7);
			if (shift == 6) {
				out[j++] = (byte) (prebyte & 0x7F);
				prebyte = 0;
			}
		}
		return out;
	}
	
	private static final int SMS_PADDING = (0x00000000 << 1);
	private static final int USSD_PADDING = (0x0000000D << 1);
}
