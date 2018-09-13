package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//public class PictureDAO implements AbstractDAO<Picture> {
//    private static final String INSERT_PICTURE = "INSERT INTO pictures (userId,pic) VALUES (?, LOAD_FILE(?))";
//    private static final String SELECT_PICTURE_BY_USER_ID = "SELECT * FROM pictures WHERE userId = ?";
//    private static final String UPDATE_PICTURE = "UPDATE pictures SET pic = LOAD_FILE(?) WHERE userId = ?";
//    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();
//
//    @Override
//    public List<Picture> getAll() {
//        return null;
//    }
//
//    @Override
//    public Picture getById(int id) {
//        return null;
//    }
//
//    @Override
//    public int add(Picture picture) throws SQLException {
//        File tempFile = null;
//        try {
//            File tempDir = new File("/var/lib/mysql-files/");
//            tempFile = File.createTempFile("pic-", ".jpg", tempDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
//            outputStream.write(picture.getFileData());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Connection connection = connectionPool.getConnection();
//        PreparedStatement ps = connection.prepareStatement(INSERT_PICTURE);
//        int userId = picture.getUserId();
//        ps.setInt(1, userId);
//        String filePath = tempFile.getAbsolutePath();
//        ps.setString(2, filePath);
//        ps.executeUpdate();
//        tempFile.delete();
//        return 0;
//    }
//
//    public void update(Picture picture) throws SQLException {
//        File tempFile = null;
//        try {
//            File tempDir = new File("/var/lib/mysql-files/");
//            tempFile = File.createTempFile("pic-", ".jpg", tempDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
//            outputStream.write(picture.getFileData());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Connection connection = connectionPool.getConnection();
//        PreparedStatement ps = connection.prepareStatement(UPDATE_PICTURE);
//        String filePath = tempFile.getAbsolutePath();
//        ps.setString(1, filePath);
//        int userId = picture.getUserId();
//        ps.setInt(2, userId);
//        ps.executeUpdate();
//        tempFile.delete();
//    }
//
//    public Picture getByUserId(int userId) {
//        try (Connection connection = connectionPool.getConnection()) {
//            PreparedStatement ps = connection.prepareStatement(SELECT_PICTURE_BY_USER_ID);
//            ps.setInt(1, userId);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    return createPictureFromResult(rs);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void delete(int id) {
//    }
//
//    private Picture createPictureFromResult(ResultSet rs) throws SQLException {
//        Picture picture = new Picture();
//        byte[] fileData = rs.getBytes("pic");
//        int userId = rs.getInt("userId");
//        picture.setFileData(fileData);
//        picture.setUserId(userId);
//        return picture;
//    }
//}