package kr.co.sist.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbConnection {
    private static DbConnection dbCon;

    private DbConnection() {}

    public static DbConnection getInstance() {
        if (dbCon == null) {
            dbCon = new DbConnection();
        }
        return dbCon;
    }

    public Connection getConn() {
        Properties prop = new Properties();
        String driver, url, id, pass;

        // properties 파일 경로 설정
        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir + "/src/properties/database.properties");

        if (!file.exists()) {
            throw new RuntimeException("database.properties가 지정된 경로에 존재하지 않습니다.");
        }

        // 파일 로딩
        try (FileInputStream fis = new FileInputStream(file)) {
            prop.load(fis);
            driver = prop.getProperty("driverClass");
            url = prop.getProperty("url");
            id = prop.getProperty("id");
            pass = prop.getProperty("pass");
        } catch (IOException e) {
            throw new RuntimeException("DB 설정 파일 로딩 실패", e);
        }

        try {
            // 드라이버 로딩
            Class.forName(driver);
            // DB 연결
            return DriverManager.getConnection(url, id, pass);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }

    public void closeDB(ResultSet rs, Statement stmt, Connection con) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}