package com.d2956987215.mow.DESUtils;


import com.d2956987215.mow.constant.Params;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtil {

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    /**
     * DES缁犳纭堕敍灞藉鐎碉拷
     *
     * @param data 瀵板懎濮炵�鍡楃摟缁楋缚瑕�
     * @param key  閸旂姴鐦戠粔渚�寽閿涘矂鏆辨惔锔跨瑝閼宠棄顧勭亸蹇庣艾8娴ｏ拷
     * @return 閸旂姴鐦戦崥搴ｆ畱鐎涙濡弫鎵矋閿涘奔绔撮懜顒傜波閸氬湐ase64缂傛牜鐖滄担璺ㄦ暏
     */
    public static String encode(String key,String data) throws Exception
    {
        return encode(key, data.getBytes());
    }
    /**
     * DES缁犳纭堕敍灞藉鐎碉拷
     *
     * @param data 瀵板懎濮炵�鍡楃摟缁楋缚瑕�
     * @param key  閸旂姴鐦戠粔渚�寽閿涘矂鏆辨惔锔跨瑝閼宠棄顧勭亸蹇庣艾8娴ｏ拷
     * @return 閸旂姴鐦戦崥搴ｆ畱鐎涙濡弫鎵矋閿涘奔绔撮懜顒傜波閸氬湐ase64缂傛牜鐖滄担璺ㄦ暏
     * @throws Exception 瀵倸鐖�
     */
    public static String encode(String key,byte[] data) throws Exception
    {
        try
        {
            DESKeySpec dks = new DESKeySpec(key.getBytes());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key閻ㄥ嫰鏆辨惔锔跨瑝閼宠棄顧勭亸蹇庣艾8娴ｅ秴鐡ч懞锟�         
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(Params.DESIV.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);

            byte[] bytes = cipher.doFinal(data);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }
    public static String encode2(String key,String data) throws Exception
    {
        return encode2(key, data.getBytes());
    }
    /**
     * DES缁犳纭堕敍灞藉鐎碉拷
     *
     * @param data 瀵板懎濮炵�鍡楃摟缁楋缚瑕�
     * @param key  閸旂姴鐦戠粔渚�寽閿涘矂鏆辨惔锔跨瑝閼宠棄顧勭亸蹇庣艾8娴ｏ拷
     * @return 閸旂姴鐦戦崥搴ｆ畱鐎涙濡弫鎵矋閿涘奔绔撮懜顒傜波閸氬湐ase64缂傛牜鐖滄担璺ㄦ暏
     * @throws Exception 瀵倸鐖�
     */
    public static String encode2(String key,byte[] data) throws Exception
    {
        try
        {
            DESKeySpec dks = new DESKeySpec(key.getBytes());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key閻ㄥ嫰鏆辨惔锔跨瑝閼宠棄顧勭亸蹇庣艾8娴ｅ秴鐡ч懞锟�
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(JiaMiUtils.DESIV.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);

            byte[] bytes = cipher.doFinal(data);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }
    /**
     * DES缁犳纭堕敍宀冃掔�锟�     *
     * @param data 瀵板懓袙鐎靛棗鐡х粭锔胯
     * @param key  鐟欙絽鐦戠粔渚�寽閿涘矂鏆辨惔锔跨瑝閼宠棄顧勭亸蹇庣艾8娴ｏ拷
     * @return 鐟欙絽鐦戦崥搴ｆ畱鐎涙濡弫鎵矋
     * @throws Exception 瀵倸鐖�
     */
    public static byte[] decode(String key,byte[] data) throws Exception
    {
        try
        {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key閻ㄥ嫰鏆辨惔锔跨瑝閼宠棄顧勭亸蹇庣艾8娴ｅ秴鐡ч懞锟�         
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(Params.DESIV.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e)
        {
//         e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 閼惧嘲褰囩紓鏍垳閸氬海娈戦崐锟�     * @param key
     * @param data
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String decodeValue(String key,String data) throws Exception
    {
        byte[] datas;
        String value = null;

        datas = decode(key, new BASE64Decoder().decodeBuffer(data));

        
        value = new String(datas);
        if (value.equals("")){
            throw new Exception();
        }
        return value;
    }
	
	
}
