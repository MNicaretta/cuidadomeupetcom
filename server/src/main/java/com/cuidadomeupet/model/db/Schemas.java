package com.cuidadomeupet.model.db;

import com.cuidadomeupet.model.data.User;
import com.cuidadomeupet.model.db.fetchers.Fetcher;
import com.cuidadomeupet.model.db.fetchers.UserFetcher;

public class Schemas {

    public static class Users {

        public static Users alias(String alias) {
            return new Users(alias);
        }

        public static class Columns {

            public final String ID;
            public final String REVISION;
            public final String NAME;
            public final String EMAIL;
            public final String PASSWORD;
            public final String IDENTITY;
            public final String PHONE;

            public Columns(String alias) {

                if (!alias.isEmpty()) {
                    alias += ".";
                }

                ID       = alias + "id";
                REVISION = alias + "revision";
                NAME     = alias + "name";
                EMAIL    = alias + "email";
                PASSWORD = alias + "password";
                IDENTITY = alias + "identity";
                PHONE    = alias + "phone";
            }
    
            @Override
            public String toString() {

                return ID       + ", " +
                       REVISION + ", " +
                       NAME     + ", " +
                       EMAIL    + ", " +
                       PASSWORD + ", " +
                       IDENTITY + ", " +
                       PHONE;
            }
        }

        private final String TABLE_NAME = "users";

        public static final Users table = new Users(null);
        public static final Fetcher<User> fetcher = new UserFetcher();

        public final Columns columns;

        public final String select;
        public final String name;
        public final String alias;

        private Users(String alias) {

            this.name = alias != null ? TABLE_NAME + " " + alias : TABLE_NAME;

            this.alias = alias != null ? alias : TABLE_NAME;

            this.columns = new Columns(this.alias);

            this.select = "select " + columns + " from " + name;
        }
    }
}