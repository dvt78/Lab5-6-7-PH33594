package code.name.monkey.lab6lab7.model;

public class UserClient {
    private static UserClient instance;

    private String _id;
    private String name;
    private String email;
    private String accessToken;

    private UserClient() {
    }

    public static UserClient getInstance() {
        if (instance == null) {
            synchronized (UserClient.class) {
                if (instance == null) {
                    instance = new UserClient();
                }
            }
        }
        return instance;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "UserClient{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
