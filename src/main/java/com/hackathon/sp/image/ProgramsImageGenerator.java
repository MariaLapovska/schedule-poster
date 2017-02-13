package com.hackathon.sp.image;

import com.hackathon.sp.model.Program;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProgramsImageGenerator {
    private int imageWidth;
    private int imageHeight;
    private int padding;
    private Color imageBackground;
    private Color primaryFontColor;
    private Color secondaryFontColor;
    private Font timeFont;
    private Font titleFont;
    private Font subtitleFont;
    private SimpleDateFormat timeFormat;

    public ProgramsImageGenerator(
            int imageWidth,
            int imageHeight,
            int padding,
            String imageBackground,
            String primaryFontColor,
            String secondaryFontColor,
            String fontFamily,
            int mainFontSize,
            SimpleDateFormat timeFormat
    ) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.padding = padding;
        this.imageBackground = rgbStringToColor(imageBackground);
        this.primaryFontColor = rgbStringToColor(primaryFontColor);
        this.secondaryFontColor = rgbStringToColor(secondaryFontColor);
        this.timeFont = new Font(fontFamily, Font.BOLD, mainFontSize);
        this.titleFont = new Font(fontFamily, Font.BOLD, (int) ((double) mainFontSize / 1.5));
        this.subtitleFont = new Font(fontFamily, Font.BOLD, mainFontSize / 2);
        this.timeFormat = timeFormat;
    }

    public boolean generateProgramsImage(List<Program> programs, String fileName, String fileExtension) throws Exception {
        BufferedImage image = new BufferedImage(
                padding * 3 + imageWidth * 2,
                (padding * 2 + imageHeight) * programs.size(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(imageBackground);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        int textXPosition = padding * 2 + imageWidth;
        int imageYPosition = padding;

        for (Program program : programs) {
            drawProgramSegment(graphics, program, imageYPosition, textXPosition);
            imageYPosition += imageHeight + padding * 2;
        }

        return ImageIO.write(image, fileExtension, new File(fileName + '.' + fileExtension));
    }

    private void drawProgramSegment(Graphics2D graphics, Program program, int imageYPosition, int textXPosition) throws Exception {
        graphics.drawImage(
                ImageIO.read(new URL(program.getImageUrl())),
                padding,
                imageYPosition,
                imageWidth,
                imageHeight,
                null
        );

        int textYPosition = imageYPosition + timeFont.getSize();

        graphics.setFont(timeFont);
        graphics.setColor(primaryFontColor);
        graphics.drawString(timeFormat.format(program.getBegin()) + " - " + timeFormat.format(program.getEnd()), textXPosition, textYPosition);
        textYPosition += titleFont.getSize() + padding;

        graphics.setFont(titleFont);
        graphics.drawString(program.getTitle(), textXPosition, textYPosition);
        textYPosition += subtitleFont.getSize() + padding / 2;

        graphics.setFont(subtitleFont);
        graphics.setColor(secondaryFontColor);
        graphics.drawString(program.getSubtitle(), textXPosition + 1, textYPosition);
    }

    private static Color rgbStringToColor(String rgbString) {
        String[] rgb = rgbString.split(",");
        return new Color(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2]));
    }
}
