package co.in.socailbuzz.socialact;

import java.util.List;

public interface UserCallback {
  void onUsers(List<User> users);
  void onFailed(String message);
}
