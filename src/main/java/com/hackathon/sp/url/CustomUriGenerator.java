package com.hackathon.sp.url;

import com.hackathon.sp.model.Photo;
import com.hackathon.sp.model.Upload;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;

public class CustomUriGenerator {

    private static final String VK_API_URL = "https://api.vk.com/method/";

    public static URI generateOvvaTvGuideUrl(String language, String channel) throws Exception {
        return new URI(String.format("https://api.ovva.tv/v2/%s/tvguide/%s", language, channel));
    }

    public static URI generateVkWallUploadServerUrl(String version, String token, String groupId) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(VK_API_URL + "photos.getWallUploadServer");

        return uriBuilder
                .addParameter("v", version)
                .addParameter("access_token", token)
                .addParameter("group_id", groupId)
                .build();
    }

    public static URI generateVkSaveWallPhotoUrl(String version, String token, String groupId, Upload upload) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(VK_API_URL + "photos.saveWallPhoto");

        return uriBuilder
                .addParameter("v", version)
                .addParameter("access_token", token)
                .addParameter("group_id", groupId)
                .addParameter("server", upload.getServer())
                .addParameter("photo", upload.getPhoto())
                .addParameter("hash", upload.getHash())
                .build();
    }

    public static URI generateVkWallPostUrl(String version, String token, String ownerId, String message, Photo photo) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(VK_API_URL + "wall.post");

        return uriBuilder
                .addParameter("v", version)
                .addParameter("access_token", token)
                .addParameter("owner_id", '-' + ownerId)
                .addParameter("from_group", "true")
                .addParameter("message", message)
                .addParameter("attachments", "photo" + photo.getOwnerId() + '_' + photo.getId())
                .build();
    }
}