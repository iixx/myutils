package com.song.util.web;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 不依赖额外jar, 并满足各种调用情况: <br>
 * 1. GET方式直接发送一个可带参网址, 用静态方法调用<br>
 * 2. 重复发送上次网址<br>
 * 3. append一个参数后直接调用doSend发送<br>
 * 4. 参数编码可定义<br>
 * 5. 先定义参数,发送时才指定主机url<br>
 * 6. url中可带部分参数 通过append可增加额外参数<br>
 * 7. 可发送post消息体, 指定post发送时, append参数到post消息体中<br>
 * 
 * @author Song
 * @version 2.1
 */
public class HttpHelper {

    private URL url = null;
    private Method method = Method.GET;
    private Map<String, String> params = new LinkedHashMap<String, String>();

    public enum Method {
        GET, POST;
    }

    /**
     * 使用GET方式请求可带参url, 支持锚点, 参数自动url编码, 可指定编码
     * 
     * @param url
     * @param charset
     * @return
     */
    public static String get(String url, String charset) {
        String result = "";
        HttpURLConnection connect = null;
        try {
            if (!(url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            URL u = new URL(url);
            if (u.getQuery() != null) {// url中已存在参数
                String path1 = url.substring(0, url.indexOf("?"));
                String path2 = u.getRef();// 锚点
                String[] params = u.getQuery().split("&");
                for (int i = 0; i < params.length; i++) {
                    String[] kv = params[i].split("=");
                    kv[0] = URLEncoder.encode(kv[0], charset);
                    kv[1] = URLEncoder.encode(kv[1], charset);
                    params[i] = kv[0] + "=" + kv[1];
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < params.length; i++) {
                    if (i == 0) {
                        sb.append("?").append(params[i]);
                    } else {
                        sb.append("&").append(params[i]);
                    }
                }
                if (path2 == null) {// 锚点
                    url = path1 + sb.toString();
                } else {
                    url = path1 + sb.toString() + "#" + path2;
                }
            }
            u = new URL(url);
            connect = (HttpURLConnection) u.openConnection();
            connect.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connect.getInputStream(), charset));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                connect.disconnect();
            }
        }
        return result;
    }

    /**
     * 使用GET方式请求可带参url, 支持锚点, 参数编码默认为utf-8
     * 
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(url, "utf-8");
    }

    /**
     * 使用POST方式发送xml消息体, 默认编码utf-8
     * 
     * @param url
     * @param body
     * @return
     */
    public static String post(String url, String body) {
        return post(url, body, "utf-8");
    }

    /**
     * 使用POST方式发送xml消息体, 可指定编码
     * 
     * @param url
     * @param body
     * @param charset
     * @return
     */
    public static String post(String url, String body, String charset) {
        byte[] data = body.getBytes();
        URL u = null;
        HttpURLConnection con = null;
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.setConnectTimeout(30000);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "text/xml; charset=" + charset);
        con.setRequestProperty("Content-Length", body.length() + "");
        OutputStream os = null;
        try {
            os = con.getOutputStream();
            os.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return sb.toString();
    }
    
    private HttpHelper() {
    }
    
    /**
     * 获得实例
     * @return
     */
    public static HttpHelper getInstance() {
        return new HttpHelper();
    }

    /**
     * 获得实例, 并指定url
     * @param url
     * @return
     */
    public static HttpHelper getInstance(String url) {
        HttpHelper h = new HttpHelper();
        h.setUrl(url);
        return h;
    }

    /**
     * 获得实例, 并指定url和发送方式GET/POST
     * @param url
     * @param m
     * @return
     */
    public static HttpHelper getInstance(String url, Method m) {
        HttpHelper h = new HttpHelper();
        h.setUrl(url);
        h.method = m;
        return h;
    }

    /**
     * 如果是GET方式: 在url中附加参数<br>
     * 如果是POST方式: 在消息体中附加参数
     * 
     * @param key
     * @param value
     * @return
     */
    public HttpHelper append(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public boolean replace(String key, String value) {
        if (this.params.get(key) == null) {
            return false;
        }
        this.params.put(key, value);
        return true;
    }

    /**
     * 默认编码utf-8发送
     * 
     * @return
     */
    public String doSend() {
        return doSend("utf-8");
    }

    /**
     * 自定义编码发送
     * 
     * @param charset
     * @return
     */
    public String doSend(String charset) {
        if (url == null) {
            return null;
        }
        StringBuilder getParam = null;// get方式的参数
        StringBuilder postParam = null;// post方式的参数
        String result = "";
        if (url.getQuery() != null) {// 将url中已存在参数进行URL编码
            String path = url.toString();
            String path1 = path.substring(0, path.indexOf("?"));
            String[] params = url.getQuery().split("&");
            for (int i = 0; i < params.length; i++) {
                String[] kv = params[i].split("=");
                try {
                    kv[0] = URLEncoder.encode(kv[0], charset);
                    kv[1] = URLEncoder.encode(kv[1], charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                params[i] = kv[0] + "=" + kv[1];
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < params.length; i++) {
                if (i == 0) {
                    sb.append("?").append(params[i]);
                } else {
                    sb.append("&").append(params[i]);
                }
            }
            try {
                url = new URL(path1 + sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if (params != null) {
            for (String key : params.keySet()) {
                String name = null;
                String value = null;
                try {
                    name = URLEncoder.encode(key, charset);
                    value = URLEncoder.encode(params.get(key), charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (method == Method.POST) {
                    if (postParam == null) {
                        postParam = new StringBuilder();
                        postParam.append(name).append("=").append(value);
                    } else {
                        postParam.append("&").append(name).append("=")
                                .append(value);
                    }
                    continue;
                }
                if (getParam == null) {
                    getParam = new StringBuilder();
                    getParam.append(name).append("=").append(value);
                } else {
                    getParam.append("&").append(name).append("=").append(value);
                }
            }
        }
        if (url.toString().indexOf("?") != -1) {// 如果url中包含参数
            if (getParam != null) {
                try {
                    url = new URL(url.toString() + "&" + getParam.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (getParam != null) {
                try {
                    url = new URL(url.toString() + "?" + getParam.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        HttpURLConnection connect = null;
        try {
            connect = (HttpURLConnection) url.openConnection();

            if (method == Method.POST) {
                connect.setRequestMethod("POST");
                if (postParam != null) {
                    connect.setDoOutput(true);
                    byte[] b = postParam.toString().getBytes();
                    connect.getOutputStream().write(b);
                }
            }

            connect.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connect.getInputStream(), charset));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connect != null)
                connect.disconnect();
        }
        return result;
    }

    /**
     * 获取完整的url路径, 包含参数
     * 
     * @return
     */
    public String getUrl() {
        return url.toString();
    }

    /**
     * 获取当前所有参数
     * 
     * @return
     */
    public String getParams() {
        return url.getQuery();
    }

    /**
     * 获取指定参数值
     * 
     * @param key
     * @return
     */
    public String getParam(String key) {
        if (this.params.get(key) == null) {
            return null;
        }
        return this.params.get(key);
    }

    /**
     * 获取当前发送方式
     * 
     * @return
     */
    public String getMethod() {
        return method.toString();
    }

    /**
     * 指定发送方式: POST/GET
     * 
     * @param method
     * @return
     */
    public void setMethod(Method method) {
        this.method = method;
    }

    /**
     * 设置url, url协议支持http和https, 默认http
     * 
     * @param url
     * @return
     */
    public boolean setUrl(String url) {
        if (url == null) {
            return false;
        }
        url = url.trim();
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            url = "http://" + url;
        }
        try {
            this.url = new URL(url);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
