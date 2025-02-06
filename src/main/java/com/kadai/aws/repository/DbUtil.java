package com.kadai.aws.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * データベース接続を管理するユーティリティクラス
 */
public class DbUtil {

    /**
     * コネクションのオートコミットモードを表すenum
     */
    public enum AutoCommitMode {
        ON, OFF,
        ;
    }

    private static final Logger logger = LogManager.getLogger(DbUtil.class);

    private static Properties properties = new Properties();

    public static final String DEFAULT_SETTING_PROPERTIES_FILE = "application.properties";

    private static boolean isInit = false;

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    /**
     * オートコミットモードをオフにしてコネクションを取り出す
     * 
     * @return コネクション
     * @throws SQLException コネクションの取得に失敗した場合
     */
    public static Connection getConnection() throws SQLException {
        return getConnection(AutoCommitMode.OFF);
    }

    /**
     * 指定されたオートコミットモードでコネクションを取り出す
     * @param autoCommitMode
     * @return コネクション
     * @throws SQLException コネクションの取得に失敗した場合
     */
    public static Connection getConnection(AutoCommitMode autoCommitMode) throws SQLException {
        init();

        //コネクションの取得
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        if (autoCommitMode == AutoCommitMode.ON) {
            conn.setAutoCommit(true);
        } else {
            conn.setAutoCommit(false);
        }
        return conn;
    }

    /**
     * トランザクションをコミットする
     * @param connection
     * @throws SQLException コミット処理に失敗した場合
     */
    public static void commit(Connection connection) throws SQLException {
        if (connection != null) {
            connection.commit();
        }
    }

    /**
     * トランザクションをロールバックする
     * @param connection
     * @throws SQLException ロールバック処理に失敗した場合
     */
    public static void rollback(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
        }
    }

    /**
     * データベース接続をクローズする。
     * @param connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * プロパティファイルから設定を読み込み、JDBCドライバをロードする
     * @param filename
     * @throws SQLException 設定ファイルの読み込みまたはJDBCドライバのロードに失敗した場合
     */
    protected static void init(String filename) throws SQLException {
        //		try (InputStream in = new FileInputStream(new File(filename))) {
        try (InputStream in = DbUtil.class.getClassLoader().getResourceAsStream(filename)) {
            properties.load(in);
        } catch (IOException e) {
            logger.error("初期化に失敗しました。[filename={}]", filename, e);
            throw new SQLException("初期化に失敗しました。", e);
        }
        loadDriver();
        URL = properties.getProperty("spring.datasource.url");
        USERNAME = properties.getProperty("spring.datasource.username");
        PASSWORD = properties.getProperty("spring.datasource.password");
        isInit = true;
    }

    /**
     * JDBCドライバを手動でロードする
     * @throws SQLException ドライバのロードに失敗した場合
     */
    private static void loadDriver() throws SQLException {
        // HSQLDB JDBCドライバの手動ロード
        try {
            Class.forName(properties.getProperty("spring.datasource.driverClassName"));
        } catch (ClassNotFoundException e) {
            logger.error("JDBCドライバのロードに失敗しました", e);
            throw new SQLException("JDBCドライバのロードに失敗しました", e);
        }
    }

    /**
     * デフォルトの設定ファイルを使用して初期化を行う
     * @throws SQLException 初期化に失敗した場合
     */
    protected static void init() throws SQLException {
        if (isInit) {
            return;
        }
        init(DEFAULT_SETTING_PROPERTIES_FILE);
    }

}