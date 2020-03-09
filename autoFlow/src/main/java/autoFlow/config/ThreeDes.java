package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.sun.crypto.provider.SunJCE;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;

public class ThreeDes {
    private static final String Algorithm = "DESede";

    public ThreeDes() {
    }

    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(1, deskey);
            return c1.doFinal(src);
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        } catch (NoSuchPaddingException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return null;
    }

    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(2, deskey);
            return c1.doFinal(src);
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        } catch (NoSuchPaddingException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return null;
    }

    public static String decryptMode(String enkey, String encoded) {
        byte[] enk = hex(enkey);

        try {
            byte[] edb = Hex2Byte.hex2Byte(encoded);
            byte[] srcBytes = decryptMode(enk, edb);
            return new String(srcBytes);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String byte2Hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }

        return hs.toUpperCase();
    }

    public static byte[] hex(String username) {
        String f = DigestUtils.md5Hex(username);
        byte[] bkeys = (new String(f)).getBytes();
        byte[] enk = new byte[24];

        for(int i = 0; i < 24; ++i) {
            enk[i] = bkeys[i];
        }

        return enk;
    }

    public static void main(String[] args) throws IOException {
        byte[] enk = hex("jdbcPassword");
        Security.addProvider(new SunJCE());
        String password = "1234";
        System.out.println("加密前的字符串:" + password);
        String encoded = Hex2Byte.byte2Hex(encryptMode(enk, password.getBytes()));
        System.out.println("加密后的字符串:" + encoded);
        byte[] edb = Hex2Byte.hex2Byte(encoded);
        byte[] srcBytes = decryptMode(enk, edb);
        System.out.println("解密后的字符串:" + new String(srcBytes));
        System.out.println("解密后的字符串:" + decryptMode("jdbcPassword", encoded));
    }
}
