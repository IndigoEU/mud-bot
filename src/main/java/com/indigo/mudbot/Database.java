package com.indigo.mudbot;

import io.jsondb.JsonDBTemplate;

public class Database {
    private static final String location = "C:/DatabaseTest";
    private static final String basicScanPackage = "com.indigo.mudbot";
    private JsonDBTemplate jsonDBTemplate;
    public Database(){
        this.jsonDBTemplate = new JsonDBTemplate(location, basicScanPackage);
        jsonDBTemplate.createCollection(DatabaseCharacter.class);
        jsonDBTemplate.createCollection(DatabaseCharacterInventory.class);
        jsonDBTemplate.createCollection(DatabaseUser.class);
    }

    public JsonDBTemplate getJsonDBTemplate() {
        return jsonDBTemplate;
    }
}
