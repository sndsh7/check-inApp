package co.in.socailbuzz.socialact;

public interface PostCallback<T> {
    void onResultCalled(boolean isSuccess,T result);
}
