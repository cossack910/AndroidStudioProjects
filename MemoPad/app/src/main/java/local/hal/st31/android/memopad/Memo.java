package local.hal.st31.android.memopad;

import java.sql.Timestamp;

public class Memo {

    private long _id;

    private String _title;

    private String _content;

    private Timestamp _updateAt;

    public long getId(){
        return _id;
    }
    public void setId(long id){
        _id = id;
    }
    public String getTitle(){
        return _title;
    }
    public void setTitle(String title){
        _title = title;
    }
    public String getContent(){
        return  _content;
    }
    public void setContent(String content){
        _content = content;
    }
    public Timestamp getUpdateAt(){
        return _updateAt;
    }
    public void setUpdateAt(Timestamp updateAt){
        _updateAt = updateAt;
    }
}
