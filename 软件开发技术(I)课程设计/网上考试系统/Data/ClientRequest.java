package Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户请求包，用于封装用户请求
 */
public class ClientRequest implements Serializable {
    public static enum TYPE {
        Register, Login, SetScore, SetQuestion, GetScore, GetQuestionList, GetQuestion, GetRandomQuestions,
        GetAllQuestions, DeleteQuestion, AddQuestionList, DeleteQuestionList, UpdateQuestion, RefreshTable
    }

    private TYPE requestType;
    private Map<String, String> data;

    public ClientRequest() {
        requestType = null;
        data = null;
    }

    public ClientRequest(TYPE requestType, Map<String, String> data) {
        this.requestType = requestType;
        this.data = data;
    }

    // Getters&Setters
    public TYPE getRequestType() {
        return requestType;
    }

    public void setRequestType(TYPE requestType) {
        this.requestType = requestType;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
