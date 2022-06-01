package kg.askaromurkanov.appliancestoreandroid.data.UserService;

import kg.askaromurkanov.appliancestoreandroid.data.App;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.UserDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.User;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;

public class UserService {
    private AppDatabase appDatabase;
    private UserDao userDao;

    private static int id;

    public static int getId() {
        return UserService.id;
    }

    public static void setId(int id) {
        UserService.id = id;
    }

    public UserService(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        this.userDao = appDatabase.userDao();
    }

    public boolean checkAuth(String email, String password){
        User user = userDao.getUser(email);
        if (user != null){
            System.out.println(user.getRole());
            return user.getPassword().equals(password);
        }
        else {
            return false;
        }

    }

    public void registerUser(User user){
        userDao.insert(user);
    }
    public void cleanTable(){
        userDao.cleanTable();
    }

    public int getUserId(String email, String password){
        return userDao.getUser(email, password).getId();
    }


}
