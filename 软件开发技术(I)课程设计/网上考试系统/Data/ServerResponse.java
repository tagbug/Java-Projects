package Data;

import java.io.*;
import java.util.*;

/**
 * 服务器响应包，用于封装服务器响应
 */
public class ServerResponse implements Serializable {
    private boolean isSucceed;
    private String failReason;
    private ArrayList<Map<String, String>> result;

    public ServerResponse() {
        isSucceed = false;
        failReason = "未处理（默认响应）";
        result = null;
    }

    public ServerResponse(boolean isSucceed, String failReason, ArrayList<Map<String, String>> result) {
        this.isSucceed = isSucceed;
        this.failReason = failReason;
        this.result = result;
    }

    // Getters&Setters
    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public ArrayList<Map<String, String>> getResult() {
        return result;
    }

    public void setResult(ArrayList<Map<String, String>> result) {
        this.result = result;
    }
}
