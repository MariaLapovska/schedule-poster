package com.hackathon.sp;

import com.hackathon.sp.http.CustomHttpClient;
import com.hackathon.sp.image.ProgramsImageGenerator;
import com.hackathon.sp.mapper.CustomMapper;
import com.hackathon.sp.model.Program;
import com.hackathon.sp.url.CustomUriGenerator;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class AppRunner {

    private static Properties ovvaProperties = new Properties();
    private static Properties imageProperties = new Properties();
    private static Properties vkProperties = new Properties();

    public static void main(String[] args) {
        try {
            loadProperties();

            CustomHttpClient httpClient = new CustomHttpClient();
            CustomMapper mapper = new CustomMapper();
            ProgramsImageGenerator imageGenerator = new ProgramsImageGenerator(
                    Integer.valueOf(imageProperties.getProperty("image_width")),
                    Integer.valueOf(imageProperties.getProperty("image_height")),
                    Integer.valueOf(imageProperties.getProperty("padding")),
                    Color.getColor(imageProperties.getProperty("background_color")),
                    imageProperties.getProperty("font_family"),
                    Font.BOLD,
                    Integer.valueOf(imageProperties.getProperty("font_size")),
                    new SimpleDateFormat(imageProperties.getProperty("time_format"))
            );

            List<Program> programs = mapper.mapJsonToProgramsList(
                    httpClient.get(CustomUriGenerator.generateOvvaTvGuideUrl(
                            ovvaProperties.getProperty("language"),
                            ovvaProperties.getProperty("channel"))
                    )
            );

            imageGenerator.generateProgramsImage(
                    programs,
                    imageProperties.getProperty("image_name"),
                    imageProperties.getProperty("image_extension")
            );

            BufferedReader response = httpClient.get(CustomUriGenerator.generateVkWallUploadServerUrl(
                    vkProperties.getProperty("version"),
                    vkProperties.getProperty("token"),
                    vkProperties.getProperty("group_id")
            ));

            response = httpClient.postMultipartFile(
                    mapper.mapJsonToServerUploadUri(response),
                    imageProperties.getProperty("image_name") + '.' + imageProperties.getProperty("image_extension"),
                    imageProperties.getProperty("image_type")
            );

            response = httpClient.get(CustomUriGenerator.generateVkSaveWallPhotoUrl(
                    vkProperties.getProperty("version"),
                    vkProperties.getProperty("token"),
                    vkProperties.getProperty("group_id"),
                    mapper.mapJsonToUpload(response)
            ));

            response = httpClient.get(CustomUriGenerator.generateVkWallPostUrl(
                    vkProperties.getProperty("version"),
                    vkProperties.getProperty("token"),
                    vkProperties.getProperty("group_id"),
                    vkProperties.getProperty("post_message"),
                    mapper.mapJsonToPhoto(response)
            ));

            response.lines().forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println("Hackathon is over!" + ex.getMessage());
        }
    }

    private static void loadProperties() throws Exception {
        ovvaProperties.load(AppRunner.class.getClassLoader().getResourceAsStream("ovva.properties"));
        imageProperties.load(AppRunner.class.getClassLoader().getResourceAsStream("image.properties"));
        vkProperties.load(AppRunner.class.getClassLoader().getResourceAsStream("vk.properties"));
    }
}