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
    private Font timeFont;
    private Font titleFont;
    private Font subtitleFont;
    private SimpleDateFormat timeFormat;

    public ProgramsImageGenerator(
            int imageWidth,
            int imageHeight,
            int padding,
            Color imageBackground,
            String fontFamily,
            int fontStyle,
            int mainFontSize,
            SimpleDateFormat timeFormat
    ) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.padding = padding;
        this.imageBackground = imageBackground;
        this.timeFont = new Font(fontFamily, fontStyle, mainFontSize);
        this.titleFont = new Font(fontFamily, fontStyle, (int) ((double) mainFontSize / 1.5));
        this.subtitleFont = new Font(fontFamily, fontStyle, mainFontSize / 2);
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
        graphics.setColor(Color.BLACK);
        graphics.drawString(timeFormat.format(program.getBegin()) + " - " + timeFormat.format(program.getEnd()), textXPosition, textYPosition);
        textYPosition += titleFont.getSize() + padding;

        graphics.setFont(titleFont);
        graphics.drawString(program.getTitle(), textXPosition, textYPosition);
        textYPosition += subtitleFont.getSize() + padding / 2;

        graphics.setFont(subtitleFont);
        graphics.setColor(Color.GRAY);
        graphics.drawString(program.getSubtitle(), textXPosition + 1, textYPosition);
    }
}
