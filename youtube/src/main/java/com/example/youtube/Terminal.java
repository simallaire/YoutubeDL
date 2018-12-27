package com.example.youtube;

import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Terminal {

    public static String output = "";
    public static String[] formatsArray;

    public static void getFormats(String url) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("youtube-dl", "-F", url);
        builder.redirectErrorStream(true);
        final Process process = builder.start();
        read(process);

    }

    public static String getTitle(String url) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("youtube-dl", "--get-title", url);
        builder.redirectErrorStream(true);

        final Process process = builder.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            output = input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;

    }

    public static void dlFile(String source, String destination, Format format, Frame frame) throws IOException {
        String line = null;

        frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        ProcessBuilder builder = new ProcessBuilder("youtube-dl", "-f", String.valueOf(format.id), source);

        builder.directory(new File(destination));
        builder.redirectErrorStream(true);

        final Process process = builder.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            while ((line = input.readLine()) != null) {

                System.out.println(line);
                if (line.indexOf("%") > 0) {
                    String pourcentage = line.substring(11, line.indexOf("%") + 1);
                    if (pourcentage != null) {
                        frame.progress(pourcentage);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }

    public static String getThumbnailUrl(String url) throws IOException {
        String output = null;
        ProcessBuilder builder = new ProcessBuilder("youtube-dl", "--get-thumbnail", url);
        builder.redirectErrorStream(true);
        final Process process = builder.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            output = input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    private static void read(final Process process) {
        ArrayList<String> outputArrayList = new ArrayList<>();

        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        try {
            while ((line = input.readLine()) != null) {
                output += line + "\n";
                outputArrayList.add(line);
            }
            formatsArray = outputArrayList.toArray(new String[outputArrayList.size()]);
            getFormats();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] getFormats() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 4; i < formatsArray.length; i++) {
            temp.add(formatsArray[i]);
        }
        formatsArray = temp.toArray(new String[temp.size()]);
        return formatsArray;
    }

}