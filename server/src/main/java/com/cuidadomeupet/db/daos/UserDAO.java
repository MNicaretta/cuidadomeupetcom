package com.cuidadomeupet.db.daos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import com.cuidadomeupet.model.Entity;
import com.cuidadomeupet.model.User;
import com.cuidadomeupet.db.Database;
import com.cuidadomeupet.db.Schemas.Users;

public class UserDAO {

    public void insertUser(Database db, User user) throws Exception {

        Users U = Users.alias("");

        String sql = "insert into " + U.name + " ( " +
                        U.columns.REVISION     + ", " +
                        U.columns.NAME         + ", " +
                        U.columns.EMAIL        + ", " +
                        U.columns.PASSWORD     + ", " +
                        U.columns.IDENTITY     + ", " +
                        U.columns.PHONE        + ", " +
                        U.columns.DESCRIPTION  + ", " +
                        U.columns.CREATED_DATE +
                     " ) values ( ?, ?, ?, ?, ?, ?, ?, ? ) returning " + U.columns.ID;

        PreparedStatement ps = db.getPreparedStatement(sql);

        try {
            int count = 1;

            ps.setInt(count++, user.getRevision());
            ps.setString(count++, user.getName());
            ps.setString(count++, user.getEmail());
            ps.setString(count++, user.getPassword());
            ps.setString(count++, user.getIdentity());
            ps.setString(count++, user.getPhone());
            ps.setString(count++, user.getDescription());
            ps.setDate(count++, new Date(System.currentTimeMillis()));
            
            user.setId(db.queryInt(ps));
        } finally {
            ps.close();
        }
    }

    public void updateUser(Database db, User user) throws Exception {

        Users U = Users.alias("");

        String sql = "update " + U.name + " set " +
                        U.columns.NAME         + " = ?, " +
                        U.columns.EMAIL        + " = ?, " +
                        U.columns.PASSWORD     + " = ?, " +
                        U.columns.IDENTITY     + " = ?, " +
                        U.columns.PHONE        + " = ?," +
                        U.columns.DESCRIPTION  + " = ?," +
                        U.columns.CREATED_DATE + " = ?" +
                     " where " +
                        U.columns.ID + " = ?" +
                     " and " +
                        U.columns.REVISION + " = ?";

        PreparedStatement ps = db.getPreparedStatement(sql);

        try {
            int count = 1;

            // SET
            ps.setString(count++, user.getName());
            ps.setString(count++, user.getEmail());
            ps.setString(count++, user.getPassword());
            ps.setString(count++, user.getIdentity());
            ps.setString(count++, user.getPhone());
            ps.setString(count++, user.getDescription());
            // WHERE
            ps.setInt(count++, user.getId());
            ps.setInt(count++, user.getRevision());

            db.executeCommand(ps);
        } finally {
            ps.close();
        }
    }

    public void deleteUser(Database db, User user) throws Exception {

        Users U = Users.alias("");

        String sql = "delete from " + U.name +
                     " where " +
                        U.columns.ID + " = " + user.getId() +
                     " and " +
                        U.columns.REVISION + " = " + user.getRevision();

        db.executeCommand(sql);
    }

    public User getUser(Database db, Entity entity) throws Exception {

        Users U = Users.table;

        String sql = U.select +
                     " where " +
                     U.columns.ID + " = " + entity.getId() +
                     " and " +
                     U.columns.REVISION + " = " + entity.getRevision();

        return db.fetchOne(sql, Users.fetcher);
    }

    public User getUserByEmail(Database db, String email) throws Exception {

        Users U = Users.table;

        String sql = U.select +
                     " where " +
                     U.columns.EMAIL + " = ?" +
                     " and " +
                     U.columns.REVISION + " = " + 0;

        PreparedStatement ps = db.getPreparedStatement(sql);

        try {
            ps.setString(1, email);

            return db.fetchOne(ps, Users.fetcher);
        } finally {
            ps.close();
        }
    }

    public List<User> getUsers(Database db) throws Exception {

        Users U = Users.table;

        String sql = U.select;

        return db.fetchAll(sql, Users.fetcher);
    }
}