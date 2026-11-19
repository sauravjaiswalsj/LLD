package service;

import model.User;

public interface UserService {
    void addUser(String name, String gender, String phoneNumber, String pincode);

    User getUser(int userId);
}
