package local.hal.st31.android.favoriteshops70394;

/**
 * ショップ情報を格納するエンティティクラス
 */
public class Shops {
    /**
     * 主キーのID値
     */
    private long _id;
    /**
     *店の名前
     */
    private String _name;
    /**
     * 電話番号
     */
    private String  _tel;
    /**
     * URLの文字列
     */
    private String _url;
    /**
     * ノートの内容
     */
    private String _note;

    //以下アクセサリメソッド

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getTel() {
        return _tel;
    }

    public void setTel(String tel) {
        _tel = tel;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        _url = url;
    }

    public String getNote() {
        return _note;
    }

    public void setNote(String note) {
        _note = note;
    }
}
