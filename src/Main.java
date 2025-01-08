import controller.*;
import model.CategoryMapper;
import model.UserMapper;
import org.apache.ibatis.session.SqlSession;
import util.MyBatisUtil;
import view.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        while (true) {
            String[] options = {
                "1. Register",
                "2. Login",
                "3. Change Password",
                "4. Forgot Password",
                "5. Update Profile",
                "6. View Categories",
                "7. Exit"
            };

            String choice = (String) JOptionPane.showInputDialog(
                null,
                "Choose an option:",
                "E-Waste Management",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == null || choice.equals("7. Exit")) {
                JOptionPane.showMessageDialog(null, "Exiting the application. Goodbye!");
                break;
            }

            SqlSession session = MyBatisUtil.getSqlSession();

            switch (choice) {
                case "1. Register":
                    RegisterView registerView = new RegisterView();
                    UserMapper registerMapper = session.getMapper(UserMapper.class);
                    new RegisterController(registerView, registerMapper);
                    registerView.setVisible(true);
                    break;

                case "2. Login":
                    LoginView loginView = new LoginView();
                    UserMapper loginMapper = session.getMapper(UserMapper.class);
                    new LoginController(loginView, loginMapper);
                    loginView.setVisible(true);
                    break;

                case "3. Change Password":
                    ChangePasswordView changePasswordView = new ChangePasswordView();
                    UserMapper changePasswordMapper = session.getMapper(UserMapper.class);
                    new ChangePasswordController(changePasswordView, changePasswordMapper);
                    changePasswordView.setVisible(true);
                    break;

                case "4. Forgot Password":
                    ForgotPasswordView forgotPasswordView = new ForgotPasswordView();
                    UserMapper forgotPasswordMapper = session.getMapper(UserMapper.class);
                    new ForgotPasswordController(forgotPasswordView, forgotPasswordMapper);
                    forgotPasswordView.setVisible(true);
                    break;

                case "5. Update Profile":
                    UpdateProfileView updateProfileView = new UpdateProfileView();
                    UserMapper updateProfileMapper = session.getMapper(UserMapper.class);
                    new UpdateProfileController(updateProfileView, updateProfileMapper);
                    updateProfileView.setVisible(true);
                    break;

                case "6. View Categories":
                    CategoryView categoryView = new CategoryView();
                    CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
                    new CategoryController(categoryView, categoryMapper);
                    categoryView.setVisible(true);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }

            session.close();
        }
    }
}
