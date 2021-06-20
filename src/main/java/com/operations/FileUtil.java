package com.operations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class FileUtil {

    private static void copyFileToLocation() {

        try {
                final String sourceDirectoryPath = PropertiesUtil.getProperties("source.directory.path");
                System.out.println("reading file from location: " + sourceDirectoryPath);
                final File lastModifiedFileInSourceFolder = getLastModified(sourceDirectoryPath);
                System.out.println("Source Folder - latest file picked: " + lastModifiedFileInSourceFolder.getName());

                final String designationDirectoryPath = PropertiesUtil.getProperties("destination.directory.path");

                System.out.println("Destination Directory location: " + designationDirectoryPath);
                Path fileToBeCopiedInDestinationFolder = Paths.get(designationDirectoryPath + "/" + lastModifiedFileInSourceFolder.getName());
                System.out.println("Destination File to be copied: " + fileToBeCopiedInDestinationFolder.toString());
                Path parent = fileToBeCopiedInDestinationFolder.getParent();
                if (parent != null) {
                    if (Files.notExists(parent)) {
                        System.out.println("Destination folder don't exists, creating folder");
                        Files.createDirectories(parent);
                    }
                }
                long currentTime = System.currentTimeMillis();
                Instant instant = Instant.now();
                if (Files.exists(fileToBeCopiedInDestinationFolder)) {
                    System.out.println("FILE ALREADY PRESENT in Destination Folder:::" + fileToBeCopiedInDestinationFolder);
                    System.out.println("changing the file name");
                    //File file = fileToBeCopiedInDestinationFolder.toFile();
                    String fileName = fileToBeCopiedInDestinationFolder.toString();
                    String newName = fileName.toString().substring(0, fileName.toString().lastIndexOf(".")) + currentTime
                            + fileName.toString().substring(fileName.toString().lastIndexOf("."));
                    System.out.println(newName);

                    fileToBeCopiedInDestinationFolder = Paths.get(newName);

                    System.out.println("new file name: " + fileToBeCopiedInDestinationFolder);
                }
           /* File file = fileToBeCopiedInDestinationFolder.toFile();
            file.setLastModified(currentTime);
            fileToBeCopiedInDestinationFolder = Paths.get(file.toString());*/


            Files.copy(lastModifiedFileInSourceFolder.toPath(), fileToBeCopiedInDestinationFolder);


           // LocalDate newLocalDate = LocalDate.now();
            // convert LocalDate to instant, need time zone
            //newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Files.setLastModifiedTime(fileToBeCopiedInDestinationFolder, FileTime.from(instant));

            System.out.println(Files.readAttributes(fileToBeCopiedInDestinationFolder,
                    BasicFileAttributes.class).lastModifiedTime());

            }catch(IOException ex){
                ex.printStackTrace();
             }
    }

    public static File getLastModified(String directoryFilePath) throws NoSuchFileException {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file!=null && file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        if (chosenFile==null){
            throw new NoSuchFileException(" no file found in :: " + directoryFilePath);
        }
        return chosenFile;
    }

    public static void main(String[] args) throws NoSuchFileException {
        System.out.println("Copy file is Start.");
        copyFileToLocation();
        System.out.println("Copy file is done.");
/*
        final String designationDirectoryPath = PropertiesUtil.getProperties("destination.directory.path");
        final String sourceDirectoryPath = PropertiesUtil.getProperties("source.directory.path");
        System.out.println("reading file from location: " + sourceDirectoryPath);
        final File lastModifiedFileInSourceFolder = getLastModified(sourceDirectoryPath);
        System.out.println("Source Folder - latest file picked: " + lastModifiedFileInSourceFolder.getName());

        System.out.println("Destination Directory location: " + designationDirectoryPath);
        Path fileToBeCopiedInDestinationFolder = Paths.get(designationDirectoryPath + "/" + lastModifiedFileInSourceFolder.getName());
        File file = fileToBeCopiedInDestinationFolder.toFile();
        String newName = fileToBeCopiedInDestinationFolder.getParent()+"/" + file.getName()+System.currentTimeMillis();
        System.out.println(
                file.toString().substring(0,file.toString().lastIndexOf("."))+ System.currentTimeMillis()
                        + file.toString().substring(file.toString().lastIndexOf(".")));*/


    }
}
