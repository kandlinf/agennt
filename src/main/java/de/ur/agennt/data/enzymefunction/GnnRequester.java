package de.ur.agennt.data.enzymefunction;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.ProxySelector;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class GnnRequester
{
    private static final String uploadUri =   "http://efi.igb.illinois.edu/efi-gnt/upload.php";
    private static final String generateUri = "http://efi.igb.illinois.edu/efi-gnt/compute.php";
    private static final String downloadUri = "http://efi.igb.illinois.edu/efi-gnt/output/";
    private static final String nsMarker = "<td>Neighborhood Size</td><td>";
    private static final String coMarker = "<td>Input % Co-Occurrence</td><td>";
    private static final String endMarker = "</td>";

    private MultipartEntityBuilder builder;
    private File ssnFile;
    private String efiURL;
    private String id;
    private String key;

    private Integer neighborhoodSize;
    private Integer coocurrence;

    public GnnRequester(String url) {
        this.efiURL = url;
    }

    public GnnRequester(File ssnFile) {
        this.ssnFile = ssnFile;
        this.builder = MultipartEntityBuilder.create();
    }

    public void request(File gnnFile, File coloredFile) throws Exception {
        if(!downloadHTML()) throw new Exception();
        if(!downloadGNN(gnnFile)) throw new Exception();
        if(!downloadColored(coloredFile)) throw new Exception();
    }

    public void request(Integer neighborhoodSize, Integer coocurrence, String email, File gnnFile, File coloredFile) throws Exception {
        this.neighborhoodSize = neighborhoodSize;
        this.coocurrence = coocurrence;
        if(!upload(email)) throw new Exception("Uploading SSN failed");
        if(!generate()) throw new Exception("Generating GNN failed");
        if(!downloadGNN(gnnFile)) throw new Exception("Downloading GNN failed");
        if(!downloadColored(coloredFile)) throw new Exception("Downloading colored SSN failed");
    }

    private boolean downloadHTML() {
        try {
            List<NameValuePair> params = URLEncodedUtils.parse(new URI(efiURL), "UTF-8");
            for(NameValuePair nameValuePair : params) {
                if(nameValuePair.getName().equals("id")) {
                    id = nameValuePair.getValue();
                } else if(nameValuePair.getName().equals("key")) {
                    key = nameValuePair.getValue();
                }
            }

            SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
            CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();
            HttpGet httpGet = new HttpGet(efiURL);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200) {
                InputStream inputStream = response.getEntity().getContent();
                String html = IOUtils.toString(inputStream);

                int nsBegin = html.indexOf(nsMarker);
                int nsEnd = html.indexOf(endMarker, nsBegin+nsMarker.length());
                int coBegin = html.indexOf(coMarker);
                int coEnd = html.indexOf(endMarker, coBegin+coMarker.length());
                if(nsBegin >= 0 && coBegin >= 0 && nsEnd >= 0 && coEnd >= 0) {
                    String nsString = html.substring(nsBegin+nsMarker.length(),nsEnd);
                    String coString = html.substring(coBegin+coMarker.length(), coEnd);
                    coString = coString.replace("%","");
                    neighborhoodSize = Integer.parseInt(nsString);
                    coocurrence = Integer.parseInt(coString);
                    return true;
                }
            }
            return  false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean upload(String email) throws IOException, ParseException {
        SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
        CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();
            HttpPost httpPost = new HttpPost(uploadUri);
            HttpEntity entity = builder.addBinaryBody("ssn_file", ssnFile)
                    .addTextBody("email", email)
                    .addTextBody("cooccurrence", coocurrence.toString())
                    .addTextBody("neighbor_size", neighborhoodSize.toString())
                    .addTextBody("submit", "submit")
                    .addTextBody("MAX_FILE_SIZE", "2147483648").build();
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                InputStream resultStream = httpResponse.getEntity().getContent();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(resultStream));
                this.id = ((Long) jsonObject.get("id")).toString();
                this.key = (String) jsonObject.get("key");
                return true;
            } else {
                return false;
            }
    }

    private boolean generate() {
        try {
            SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
            int timeout = 1000*60*60;
            SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setSoTimeout(timeout).build();
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).setDefaultSocketConfig(socketConfig).setDefaultRequestConfig(requestConfig).build();
            HttpPost httpPost = new HttpPost(generateUri);
            HttpEntity entity = builder.addBinaryBody("ssn_file", ssnFile)
                    .addTextBody("id", this.id)
                    .addTextBody("key", this.key).build();
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (SocketTimeoutException ex) {
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean downloadGNN(File gnnFile) {
        try {
            String uri = downloadUri + id + "/" + id + "_pfam_co" + coocurrence + "_ns" + neighborhoodSize + ".xgmml";
            SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
            CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200) {
                InputStream inputStream = response.getEntity().getContent();
                FileOutputStream outputStream = new FileOutputStream(gnnFile);
                IOUtils.copy(inputStream,outputStream);
                outputStream.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean downloadColored(File coloredFile) {
        try {
            String uri = downloadUri + id + "/" + id + "_color_co" + coocurrence + "_ns" + neighborhoodSize + ".xgmml";
            SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
            CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200) {
                InputStream inputStream = response.getEntity().getContent();
                FileOutputStream outputStream = new FileOutputStream(coloredFile);
                IOUtils.copy(inputStream,outputStream);
                outputStream.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Integer getNeighborhoodSize() {
        return neighborhoodSize;
    }

    public Integer getCoocurrence() {
        return coocurrence;
    }
}
