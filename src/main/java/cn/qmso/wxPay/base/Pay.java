package cn.qmso.wxPay.base;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import cn.qmso.wxPay.AesUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/4
 * @since 1.0.0
 */
public class Pay  {

    private static final String charset = "UTF-8";

    // 定义全局容器 保存微信平台证书公钥  注意线程安全
    private static final Map<String, Certificate> CERTIFICATE_MAP = new ConcurrentHashMap<>();

    /**
     * 生成组装请求头
     *
     * @param method             请求方式
     * @param url                请求地址
     * @param mercId             商户ID
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 私钥路径
     * @param body               请求体
     * @return 组装请求的数据
     * @throws Exception
     */
    protected static String getToken(String method, HttpUrl url, String mercId, String serial_no, String privateKeyFilePath, String body) throws Exception {
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes("UTF-8"), privateKeyFilePath);
        return "mchid=\"" + mercId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + serial_no + "\","
                + "signature=\"" + signature + "\"";
    }

    /**
     * 生成签名
     *
     * @param message            请求体
     * @param privateKeyFilePath 私钥的路径
     * @return 生成base64位签名信息
     * @throws Exception
     */
    protected static String sign(byte[] message, String privateKeyFilePath) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(getPrivateKey(privateKeyFilePath));
        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 组装签名加载
     *
     * @param method    请求方式
     * @param url       请求地址
     * @param timestamp 请求时间
     * @param nonceStr  请求随机字符串
     * @param body      请求体
     * @return 组装的字符串
     */
    static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }
        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 微信获取平台证书
     *
     * @param url_prex           请求域名
     * @param url                请求路由
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return
     * @throws Exception
     */
    protected static JSONObject getCertificates(String url_prex, String url, String mchid, String serial_no, String privateKeyFilePath,String apiV3Key) throws Exception {
        JSONObject body = null;
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpGet httpGet = new HttpGet(url_prex + url);
        // 处理请求头报文
        String post = getToken("GET", HttpUrl.parse(url_prex + url), mchid, serial_no, privateKeyFilePath, "");
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "https://zh.wikipedia.org/wiki/User_agent");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization",
                "WECHATPAY2-SHA256-RSA2048 " + post);
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpGet);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = JSONObject.fromObject(EntityUtils.toString(entity, charset));
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

    private static void refreshCertificate(String url_prex, String url, String mchid, String serial_no, String privateKeyFilePath,String apiV3Key) throws Exception {
        JSONObject certificates = getCertificates(url_prex, url, mchid, serial_no, privateKeyFilePath, apiV3Key);
        JSONArray data = certificates.getJSONArray("data");
        for (Object datum : data) {
            JSONObject jsonObject = JSONObject.fromObject(datum);
            JSONObject encryptCertificate = jsonObject.getJSONObject("encrypt_certificate");
            String publicKey = decryptResponseBody(apiV3Key, encryptCertificate.getString("associated_data"), encryptCertificate.getString("nonce"), encryptCertificate.getString("ciphertext"));
            // 下面是刷新方法 refreshCertificate  的核心代码
            final CertificateFactory cf = CertificateFactory.getInstance("X509");

            ByteArrayInputStream inputStream = new ByteArrayInputStream(publicKey.getBytes(StandardCharsets.UTF_8));
            Certificate certificate = null;
            try {
                certificate = cf.generateCertificate(inputStream);
            } catch (CertificateException e) {
                e.printStackTrace();
            }
            String responseSerialNo = jsonObject.getString("serial_no");
            // 清理HashMap
            CERTIFICATE_MAP.clear();
            // 放入证书
            CERTIFICATE_MAP.put(responseSerialNo, certificate);
        }
    }

    public static Map<String, Certificate> getCertificate(String url_prex, String url, String mchid, String serial_no, String privateKeyFilePath,String apiV3Key) throws Exception {
        // 当证书容器为空 或者 响应提供的证书序列号不在容器中时  就应该刷新了
        if (CERTIFICATE_MAP.isEmpty()) {
            refreshCertificate(url_prex, url, mchid, serial_no, privateKeyFilePath, apiV3Key);
        }
        // 然后调用
        return CERTIFICATE_MAP;
    }

    /**
     * 解密响应体.
     *
     * @param apiV3Key       API V3 KEY  API v3密钥 商户平台设置的32位字符串
     * @param associatedData  response.body.data[i].encrypt_certificate.associated_data
     * @param nonce          response.body.data[i].encrypt_certificate.nonce
     * @param ciphertext     response.body.data[i].encrypt_certificate.ciphertext
     * @return the string
     * @throws GeneralSecurityException the general security exception
     */
    public static String decryptResponseBody(String apiV3Key, String associatedData, String nonce, String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec key = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(128, nonce.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData.getBytes(StandardCharsets.UTF_8));

            byte[] bytes;
            try {

                bytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            } catch (GeneralSecurityException e) {
                throw new IllegalArgumentException(e);
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * 敏感信息加密
     * @param message 敏感数据
     * @param publicKey 公钥
     * @return
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String rsaEncryptOAEP(String message, PublicKey publicKey)
            throws IllegalBlockSizeException, IOException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] data = message.getBytes("utf-8");
            byte[] cipherdata = cipher.doFinal(data);
            return Base64.getEncoder().encodeToString(cipherdata);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的证书", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("加密原串的长度不能超过214字节");
        }
    }



    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    static PrivateKey getPrivateKey(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    /**
     * 构造签名串
     *
     * @param signMessage 待签名的参数
     * @return 构造后带待签名串
     */
    protected static String buildSignMessage(ArrayList<String> signMessage) {
        if (signMessage == null || signMessage.size() <= 0) {
            return null;
        }
        StringBuilder sbf = new StringBuilder();
        for (String str : signMessage) {
            sbf.append(str).append("\n");
        }
        return sbf.toString();
    }


    /**
     * v3 支付异步通知验证签名
     *
     * @param body 异步通知密文
     * @param key  api 密钥
     * @return 异步通知明文
     * @throws Exception 异常信息
     */
    protected static String verifyNotify(String body, String key) throws Exception {
        // 获取平台证书序列号
        cn.hutool.json.JSONObject resultObject = JSONUtil.parseObj(body);
        cn.hutool.json.JSONObject resource = resultObject.getJSONObject("resource");
        String cipherText = resource.getStr("ciphertext");
        String nonceStr = resource.getStr("nonce");
        String associatedData = resource.getStr("associated_data");
        AesUtil aesUtil = new AesUtil(key.getBytes(StandardCharsets.UTF_8));
        // 密文解密
        return aesUtil.decryptToString(
                associatedData.getBytes(StandardCharsets.UTF_8),
                nonceStr.getBytes(StandardCharsets.UTF_8),
                cipherText
        );
    }

    /**
     * 处理返回对象
     *
     * @param request
     * @return
     */
    protected static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 微信支付GET请求
     *
     * @param url_prex           请求域名
     * @param url                请求路由
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return
     * @throws Exception
     */
    protected static Object getRequest(String url_prex, String url,Object param, String mchid, String serial_no, String privateKeyFilePath) throws Exception {

        JSONObject body = null;
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        List<NameValuePair> nvps = new ArrayList<>();
        if (param != null){
            Field[] fields = param.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                //参数
                nvps.add(new BasicNameValuePair(field.getName(), field.get(param).toString()));
            }
        }
        //创建post方式请求对象
        URIBuilder uriBuilder = new URIBuilder(url_prex + url);
        uriBuilder.setParameters(nvps);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // 处理请求头报文
        String post = getToken("GET", HttpUrl.parse(url_prex + url + "?" + httpGet.getURI().getQuery()), mchid, serial_no, privateKeyFilePath,"");
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization",
                "WECHATPAY2-SHA256-RSA2048 " + post);

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpGet);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = JSONObject.fromObject(EntityUtils.toString(entity, charset));
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

    /**
     * 微信支付POST请求
     *
     * @param url_prex           请求域名
     * @param url                请求路由
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @param jsonStr            请求体Json字符串
     * @return
     * @throws Exception
     */
    protected static Object postRequest(String url_prex, String url, String mchid, String serial_no,String platformSerialNo, String privateKeyFilePath, String jsonStr) throws Exception {
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url_prex + url);
        //装填参数
        StringEntity s = new StringEntity(jsonStr, charset);
//        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        String post = getToken("POST", HttpUrl.parse(url_prex + url), mchid, serial_no, privateKeyFilePath, jsonStr);
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Authorization",
                "WECHATPAY2-SHA256-RSA2048 " + post);
        if (StringUtils.isNotEmpty(platformSerialNo)){
            httpPost.setHeader("Wechatpay-Serial",platformSerialNo);
        }
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, charset);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

    /**
     * 通知微信
     *
     * @param response
     * @param plainText
     * @throws Exception
     */
    protected static void sendMessage(HttpServletResponse response, String plainText) throws Exception {
        Map<String, String> map = new HashMap<>(12);
        // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
        if (StrUtil.isNotEmpty(plainText)) {
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message", "SUCCESS");
        } else {
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "签名错误");
        }
        response.setHeader("Content-type", ContentType.JSON.toString());
        response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }


}