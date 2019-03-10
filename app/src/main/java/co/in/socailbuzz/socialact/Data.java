package co.in.socailbuzz.socialact;

import java.util.List;

public class Data {

    List<User> data;

    public Data(List<User> data) {
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
