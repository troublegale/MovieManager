package letsgo.lab6.server.managers.databaseManagers;

import letsgo.lab6.server.database.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DDLManager {

    public void createTables(DatabaseConfiguration configuration) throws SQLException {
        Connection connection = ConnectionManager.getConnection(configuration);
        PreparedStatement statement = connection.prepareStatement("""
                create table if not exists users (
                    id bigserial primary key,
                    name text not null,
                    password_digest char(96) not null,
                    salt text not null
                );
                
                create table if not exists coordinates (
                    id bigserial primary key,
                    x double precision,
                    y float not null,
                    creator_id bigint not null references users(id) on delete cascade
                );
                
                create table if not exists locations (
                    id bigserial primary key,
                    x float,
                    y float,
                    z bigint,
                    creator_id bigint not null references users(uid) on delete cascade
                );
                
                create table if not exists persons (
                    id bigserial primary key,
                    name text not null
                    height bigint,
                    eye_color text not null,
                    nationality text not null,
                    location_id bigint references locations(id) on delete cascade
                    creator_id bigint not null references users(uid) on delete cascade
                );
                
                create table if not exists movies (
                    id bigserial primary key,
                    name text not null,
                    coordinates_id bigint references coordinates(id) on delete cascade
                    creation_date text not null,
                    oscars_count bigint not null,
                    genre text not null,
                    mpaa_rating not null,
                    person_id bigint references persons(id) on delete cascade
                    creator_id bigint not null references users(uid) on delete cascade
                );
                 
                """);
        statement.execute();
    }

}
