package com.indigo.mudbot;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "user", schemaVersion = "1.0")
public class DatabaseUser {
    @Id
    private String userId;
    private String slot1;
    private String slot2;
    private String slot3;
    private String slot4;
}
