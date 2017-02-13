package com.hackathon.sp.mapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;
import com.hackathon.sp.model.Photo;
import com.hackathon.sp.model.Program;
import com.hackathon.sp.model.Upload;

import java.io.BufferedReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomMapper {
    private static final JsonParser parser = new JsonParser();

    public URI mapJsonToServerUploadUri(BufferedReader input) throws URISyntaxException {
        JsonObject rootObject = parser.parse(input).getAsJsonObject();
        return new URI(rootObject
                .get("response").getAsJsonObject()
                .get("upload_url").getAsString());
    }

    public Upload mapJsonToUpload(BufferedReader input) {
        JsonObject rootObject = parser.parse(input).getAsJsonObject();
        Upload upload = new Upload();

        upload.setServer(rootObject.get("server").getAsString());
        upload.setHash(rootObject.get("hash").getAsString());
        upload.setPhoto(rootObject.get("photo").getAsString());

        return upload;
    }

    public Photo mapJsonToPhoto(BufferedReader input) {
        JsonObject rootObject = parser.parse(input).getAsJsonObject();
        JsonObject response = rootObject
                                .get("response").getAsJsonArray()
                                .get(0).getAsJsonObject();
        Photo photo = new Photo();

        photo.setId(response.get("id").getAsString());
        photo.setOwnerId(response.get("owner_id").getAsString());

        return photo;
    }

    public List<Program> mapJsonToProgramsList(BufferedReader input) {
        JsonObject rootObject = parser.parse(input).getAsJsonObject();
        JsonArray programsArray = rootObject
                                    .get("data").getAsJsonObject()
                                    .get("programs").getAsJsonArray();
        List<Program> programs = new ArrayList<>();

        for (JsonElement programElement : programsArray) {
            JsonObject programObject = programElement.getAsJsonObject();
            Program program = new Program();

            program.setTitle(programObject.get("title").getAsString());
            program.setSubtitle(programObject.get("subtitle").getAsString());
            program.setBegin(new Date(programObject.get("realtime_begin").getAsLong() * 1000));
            program.setEnd(new Date(programObject.get("realtime_end").getAsLong() * 1000));
            program.setImageUrl(programObject.get("image").getAsJsonObject().get("preview").getAsString());

            programs.add(program);
        }

        return programs;
    }
}
