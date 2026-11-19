package service;

import enums.Gender;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final Map<Integer, User> userMap;

    public UserServiceImpl() {
        this.userMap = new HashMap<>();
    }

    @Override
    public void addUser(String name, String gender, String phoneNumber, String pincode) {
        Gender userGender = "male".equalsIgnoreCase(gender) ? Gender.MALE : "female".equalsIgnoreCase(gender) ? Gender.FEMALE : Gender.OTHER;
        User user = new User(name, userGender, phoneNumber, pincode);
        addUserToMap(user);
    }

    private void addUserToMap(User user) {
        int userId = user.getUserId();
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, user);
        }
    }

    @Override
    public User getUser(int userId) {
        return userMap.get(userId);
    }
}
