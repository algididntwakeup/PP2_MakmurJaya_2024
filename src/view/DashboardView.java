package view;

import java.awt.*;
import javax.swing.*;
import model.User;
import controller.UserDocumentController;
import org.apache.ibatis.session.SqlSessionFactory;

public class DashboardView extends JFrame {
    private final SqlSessionFactory sqlSessionFactory;
    private final User user;
