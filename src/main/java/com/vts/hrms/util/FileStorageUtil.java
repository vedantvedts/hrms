package com.vts.hrms.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorageUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageUtil.class);

    /**
     * Handles file storage for insert/update cases
     *
     * @param basePath       Base folder path (e.g., appStorage)
     * @param oldObsNo       Old observation number (null for insert)
     * @param newObsNo       New observation number
     * @param oldFileName    Old file name (null if no previous file)
     * @param folderName     folder name (eg: Observation, Reply etc)
     * @param newFile        New file uploaded (can be null)
     * @return               The final file name stored (null if no file stored)
     */
    public static String handleFileUpdate(Path basePath,
                                          String oldObsNo,
                                          String newObsNo,
                                          String oldFileName,
                                          String folderName,
                                          MultipartFile newFile) throws IOException {

        String oldSafeObsNo = oldObsNo != null ? oldObsNo.replace("/", "_") : null;
        String newSafeObsNo = newObsNo.replace("/", "_");

        Path oldFolder = oldObsNo != null ? basePath.resolve(oldSafeObsNo).resolve(folderName) : null;
        Path newFolder = basePath.resolve(newSafeObsNo).resolve(folderName);

        boolean obsNoChanged = oldObsNo != null && !oldObsNo.equals(newObsNo);
        boolean newFileUploaded = newFile != null && !newFile.isEmpty();

        String finalFileName = oldFileName;

        // CASE 1: Obs No same, new file uploaded
        if (!obsNoChanged && newFileUploaded) {
            deleteFileIfExists(oldFolder, oldFileName);
            finalFileName = saveFile(newFolder, newFile.getOriginalFilename(), newFile);
        }
        // CASE 2: Obs No changed, no new file
        else if (obsNoChanged && !newFileUploaded) {
            renameFolder(oldFolder, newFolder);
        }
        // CASE 3: Obs No changed + new file uploaded
        else if (obsNoChanged) {
            deleteFileIfExists(oldFolder, oldFileName);
            renameFolder(oldFolder, newFolder);
            finalFileName = saveFile(newFolder, newFile.getOriginalFilename(), newFile);
        }
        return finalFileName;
    }

    /** Save a file */
    public static String saveFile(Path uploadPath, String fileName, MultipartFile multipartFile) throws IOException {
        logger.info("Saving file: {} at {}", fileName, uploadPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
    }

    /** Delete a file safely */
    public static void deleteFileIfExists(Path folder, String fileName) {
        if (folder != null && fileName != null) {
            Path filePath = folder.resolve(fileName);
            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    logger.info("Deleted old file: {}", filePath);
                }
            } catch (Exception ex) {
                logger.warn("Failed to delete old file: {}", filePath, ex);
            }
        }
    }

    /** Rename/move folder */
    private static void renameFolder(Path oldFolder, Path newFolder) {
        if (oldFolder != null && Files.exists(oldFolder)) {
            try {
                Files.createDirectories(newFolder.getParent());
                Files.move(oldFolder.getParent(), newFolder.getParent(), StandardCopyOption.REPLACE_EXISTING);
                logger.info("Renamed folder from {} to {}", oldFolder.getParent(), newFolder.getParent());
            } catch (Exception ex) {
                logger.warn("Failed to rename folder from {} to {}", oldFolder, newFolder, ex);
            }
        }
    }

}

