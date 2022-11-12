package cn.qmso.wxPay.base;

import cn.qmso.wxPay.v2.config.WxPayV2Config;
import cn.qmso.wxPay.v2.pojo.WxPayV2Content;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;


/**
 * @author lijuntao
 */
@Slf4j
public class PayV2 {

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param wxPayV2Config 配置信息
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, WxPayV2Config wxPayV2Config) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WxPayContent.FIELD_SIGN)) {
                continue;
            }
            // 参数值为空，则不参与签名
            if (data.get(k).trim().length() > 0)
            {
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(wxPayV2Config.getKey());
        if (WxPayContent.SIGN_TYPE_MD5.equals(wxPayV2Config.getSignType())) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (WxPayContent.SIGN_TYPE_HMAC_SHA256.equals(wxPayV2Config.getSignType())) {
            return HMACSHA256(sb.toString(), wxPayV2Config.getKey());
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", wxPayV2Config.getSignType()));
        }
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @return 签名
     */
    protected static String generateSignature(final Map<String, String> data, String key)
            throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            // 参数值为空，则不参与签名
            if (data.get(k).trim().length() > 0) {
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        return HMACSHA256(sb.toString(), key);
    }


    /**
     * 生成 HMACSHA256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    static String mapToXml(Map<String, String> data) throws Exception {
        Document document = newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }

    static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }

    /**
     * 请求
     *
     * @param url         地址
     * @param connManager
     * @param mchId
     * @param signedXml
     * @return
     * @throws Exception
     */
    static Map<String, String> request(String url, BasicHttpClientConnectionManager connManager, String mchId, String signedXml) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().build();
        httpPost.setConfig(requestConfig);

        String UserAgent = "WXPaySDK/3.0.9 (" + System.getProperty("os.arch") + " "
                + System.getProperty("os.name") + " " + System.getProperty("os.version") + ") Java/"
                + System.getProperty("java.version") + " HttpClient/"
                + HttpClient.class.getPackage().getImplementationVersion();

        StringEntity postEntity = new StringEntity(signedXml, "UTF-8");
        postEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/xml"));
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", UserAgent + " " + mchId);
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String body = EntityUtils.toString(httpEntity, "UTF-8");
        return xmlToMap(body);
    }

    /**
     * xml转换为Map
     *
     * @param strXML 需要转换的字符串
     * @return
     * @throws Exception
     */
    protected static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param wxPayV2Config  v2配置
     * @return 含有sign字段的XML
     */
    protected static String generateSignedXml(final Map<String, String> data, WxPayV2Config wxPayV2Config) throws Exception {
        String sign = generateSignature(data, wxPayV2Config);
        data.put("sign", sign);
        return mapToXml(data);
    }


    /**
     * 携带证书的POST请求
     *
     * @param wxPayV2Config 配置信息
     * @param url       请求地址
     * @param signedXml 发送的xml加密报文
     * @return 返回map格式的返回信息
     * @throws Exception
     */
    protected static Map<String, String> carryCertificateRequestPost(WxPayV2Config wxPayV2Config, String url, String signedXml) throws Exception {
        // 证书
        char[] password = wxPayV2Config.getMchId().toCharArray();
        InputStream certStream = null;
        try {
            certStream = new FileInputStream(wxPayV2Config.getCertPath());
        }catch (Exception e){
            log.error("证书加载失败");
        }finally {
            assert certStream != null;
            certStream.close();
        }

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);
        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);
        // 创建 SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory).build(), null, null, null);
        return request(url, connManager, wxPayV2Config.getMchId(), signedXml);
    }

    /**
     * 未携带证书的POST请求
     *
     * @param mchId     商户ID
     * @param url       请求地址
     * @param signedXml 请求的xml报文
     * @return
     * @throws Exception
     */
    protected static Map<String, String> notCarryCertificateRequestPost(String mchId, String url, String signedXml) throws Exception {
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory()).build(),
                null, null, null);
        return request(url, connManager, mchId, signedXml);
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
                    log.error("流关闭失败");
                }
            }
        }
    }

    /**
     * 通知微信
     *
     * @param response
     * @param plainText
     * @throws Exception
     */
    protected static void sendMessage(HttpServletResponse response, String plainText) throws Exception {
        Map<String, String> map = new HashMap<String, String>(12);
        // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
        if (StringUtils.isNotEmpty(plainText)) {
            response.setStatus(200);
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "SUCCESS");
        } else {
            response.setStatus(500);
            map.put("return_code", "ERROR");
            map.put("return_msg", "签名错误");
        }
        PrintWriter writer = response.getWriter();
        writer.println(mapToXml(map));
        writer.flush();
        writer.close();
    }

    /**
     * 请求公钥
     */
    public static void refreshPublicKey(WxPayV2Config wxPayV2Config) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("mch_id",wxPayV2Config.getMchId());
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        String signedXml = generateSignedXml(map, wxPayV2Config);
        Map<String, String> post = carryCertificateRequestPost(wxPayV2Config, WxPayV2Content.REFRESH_PUBLIC_KEY_URL, signedXml);
        if (post.get("return_code").equals(WxPayV2Content.SUCCESS) &&
                post.get("result_code").equals(WxPayV2Content.SUCCESS)){
            wxPayV2Config.setPublicKey(post.get("pub_key"));
        }else {
            log.error("请求公钥失败：return_code：{}，return_msg：{}，result_code：{}，err_code：{}，err_code_des：{}",
                    post.get("return_code"),post.get("return_msg"),post.get("result_code"),post.get("err_code"),post.get("err_code_des"));
        }
    }


    /**
     * 得到公钥
     * @param wxPayV2Config  配置信息
     * @throws Exception
     */
    public static void getPublicKey(WxPayV2Config wxPayV2Config) throws Exception {
        if (StringUtils.isEmpty(wxPayV2Config.getPublicKey())){
            refreshPublicKey(wxPayV2Config);
        }
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(wxPayV2Config.getPublicKey()));
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        wxPayV2Config.setPubKey(publicKey);
    }


    /**
     * 敏感信息加密
     * @param message 敏感数据
     * @param wxPayV2Config 配置信息
     * @return
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String rsaEncryptOAEP(String message, WxPayV2Config wxPayV2Config)
            throws Exception {
        if (wxPayV2Config.getPubKey() == null){
            getPublicKey(wxPayV2Config);
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, wxPayV2Config.getPubKey());

            byte[] data = message.getBytes(StandardCharsets.UTF_8);
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

}