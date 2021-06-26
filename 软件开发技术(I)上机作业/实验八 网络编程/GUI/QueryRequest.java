package GUI;

import java.io.Serializable;

/**
 * 查询请求包，用以封装客户端到服务器的数据库查询请求
 * 
 * @since 1.1
 * @author TagBug {@link https://github.com/tagbug}
 */
public class QueryRequest implements Serializable {
    /**
     * 客户端与服务器协定好的查询请求种类
     */
    public static enum TYPE {
        ID, NAME
    }

    private TYPE type;
    private String data;

    /**
     * 初始化构造方法，将查询请求封装
     * 
     * @param type 查询种类
     * @param data 请求数据
     */
    public QueryRequest(TYPE type, String data) {
        this.type = type;
        this.data = data;
    }

    public TYPE getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
