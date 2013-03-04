/*
 * @(#)FileUtil.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Dec 14, 2006    LIBkk11577   Initial creation.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modificationss.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * dhq348   Feb 15, 2007    LIBkk22912   CR (LIBkk22912) improvements.
 * dhq348   Feb 26, 2007    LIBkk22912   Rework of inspection LX147697.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * wmr068   Aug 07, 2008    LIBqq64190   Method getUseCaseDocumentFiles removed. Clients must call loadFilesFromDirectory instead.
 * lmn3		Jun 20, 2009				 Addition of method getFileExtension.
 * lmn3		Jun 20, 2008				 Remotion of method getUseCaseDocumentFiles.
 */
package br.ufpe.cin.target.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Class that contains some utility methods used for file manipulation.
 * 
 * <pre>
 * CLASS:
 * Class that contains some utility methods used for file manipulation.
 * 
 * USAGE:
 * All methods should be accessed in a static way.
 */
public class FileUtil
{

    /** Folder to place temporary files */
    public static String TEMP_FOLDER = System.getProperty("java.io.tmpdir");

    /**
     * This method extracts the absolute paths of a collection of <code>File</code> instances.
     * 
     * @param files The collection of <code>File</code> instances.
     * @return The collection of absolute paths.
     */
    public static Collection<String> getAbsolutePaths(Collection<File> files)
    {
        Collection<String> result = new HashSet<String>();

        for (File file : files)
        {
            result.add(file.getAbsolutePath());
        }
        return result;
    }
    
    /**
     * Loads files from <code>sourceDir</code> that has the characteristics specified by a
     * <code>FileFilter</code> passed as parameter.
     * 
     * @param sourceDir The folder from which the files will be loaded.
     * @param filter The filter of the files that will be loaded.
     * @return A collection of loaded files. If the given filter is null then all pathnames are accepted.
     * @throws IOException If the source folder does not exist or is not valid.
     */
    public static Collection<File> loadFilesFromDirectory(String sourceDir, FileFilter filter)
            throws IOException
    {
        File directory = new File(sourceDir);
        /* checks if the directory exists */
        if (!directory.exists())
        {
            throw new IOException("The specified directory (" + sourceDir + ") does not exist.");
        }

        /* checks if it is really a directory */
        if (!directory.isDirectory())
        {
            throw new IOException("The specified directory (" + sourceDir
                    + ") is not a valid directory.");
        }

        File tmpFile[] = directory.listFiles(filter);
        Collection<File> resultFiles = new ArrayList<File>();
        for (int i = 0; i < tmpFile.length; i++)
        {
            if (tmpFile[i].exists() && !tmpFile[i].isHidden())
            {
                resultFiles.add(tmpFile[i]);
            }
        }

        return resultFiles;
    }

    /**
     * Gets the file extension as a string. For example, given the file "test.xml",
     * the result of this method consists of "xml". Notice that if no extension is
     * found in the file, the string returned is empty.
     * 
     * @param file The file in which the extension must be extracted.
     * @return The extension of a given file as a string.
     */
    public static String getFileExtension(File file)
    {
        String fileName = file.getName();

        return (fileName.lastIndexOf(".") == -1) ? "" : fileName.substring(fileName
                .lastIndexOf(".") + 1, fileName.length());
    }
    
    /**
     * Gets the file extension as a string. For example, given the file "test.xml",
     * the result of this method consists of "xml". Notice that if no extension is
     * found in the file, the string returned is empty.
     * 
     * @param filefileName the name of the file.
     * @return The extension of a given file name as a string.
     */
    //INSPECT - Lais New Method
    public static String getFileExtension(String fileName)
    {
        return (fileName.lastIndexOf(".") == -1) ? "" : fileName.substring(fileName
                .lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * Copy a list of files to a specifc folder.
     * 
     * @param sourceFiles An array containing the name of the files to be copied.
     * @param toDir The destination forlder.
     * @param overwrite Indicates if the existing files in the destination folder will be
     * overwritten.
     * @return The new absolute file names.
     * @throws IOException If there is a file in the destination folder with same source name.
     */
    public static String[] copyFiles(String[] sourceFiles, String toDir, boolean overwrite)
            throws IOException
    {
        String[] newFileNames = new String[sourceFiles.length];
        for (int i = 0; i < sourceFiles.length; i++)
        {
            File inFile = new File(sourceFiles[i]);
            File outFile = new File(toDir + getSeparator() + inFile.getName());
            boolean fileExists = false;
            if (outFile.exists())
            {
                fileExists = true;
                if (overwrite)
                {
                    if (!outFile.delete())
                    {
                        throw new IOException(outFile.getAbsolutePath() + " cannot be overwritten.");
                    }
                    fileExists = false;
                }
            }

            if (!fileExists)
            {
                byte[] b = new byte[(int) inFile.length()];
                InputStream in = new FileInputStream(inFile);
                OutputStream out = new FileOutputStream(outFile);
                in.read(b);
                out.write(b);
                out.close();
                in.close();
                newFileNames[i] = outFile.getAbsolutePath();
            }
        }
        return newFileNames; // return the new locations
    }

    /**
     * Deletes all files and folders from <code>directoryNames</code>.
     * 
     * @deprecated See deleteAllFiles.
     * @param directoryName The name of the directory whose files will be deleted.
     */
    public static boolean cleanUpDirectory(String directoryName)
    {
        File directory = new File(directoryName);
        boolean result = true;
        if (directory.exists() && directory.isDirectory())
        {
            result = deleteFiles(directory.listFiles());
        }
        return result;
    }

    /**
     * Delete the files and folders passed as parameter. It is recursive, so it deletes the files
     * included in the subfolders.
     * 
     * @param files The name of the files to be deleted.
     * @return <i>True</i> if any file was deleted. <i>False</i> otherwise.
     */
    public static boolean deleteFiles(File[] files)
    {
        boolean result = true;
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isDirectory())
            {
                result = deleteFiles(files[i].listFiles()) && files[i].delete();
            }
            else
            {
                if (files[i].exists())
                {
                    result = files[i].delete();
                }
            }
        }
        return result;
    }

    /**
     * Delete the files and folders passed as parameter..
     * 
     * @param files The name of the files to be deleted.
     * @return <i>True</i> if any file was deleted. <i>False</i> otherwise.
     */
    public static boolean deleteFiles(String[] files)
    {
        boolean result = true;
        for (int i = 0; i < files.length; i++)
        {
            File file = new File(files[i]);
            if (file.isDirectory())
            {
                result = deleteFiles(file.list()) && file.delete();
            }
            else
            {
                if (file.exists() && file.canWrite())
                {
                    result = file.delete();
                }
            }
            file = null;
        }
        return result;
    }

    /**
     * Gets the file name from its absolute path.
     * 
     * @param completePath The absolute path.
     * @return The file name.
     */
    public static String getFileName(String completePath)
    {
        int index = completePath.lastIndexOf(getSeparator());
        return (index > 0) ? completePath.substring(index + 1) : completePath;
    }

    /**
     * Gets the location of the file from its absolute path.
     * 
     * @param completePath The absolute path.
     * @return The location path of the file.
     */
    public static String getFilePath(String completePath)
    {
        int index = completePath.lastIndexOf(getSeparator());
        return (index > 0) ? completePath.substring(0, index) : "";
    }

    /**
     * Returns the system separator.
     * 
     * @return The system separator string.
     */
    public static String getSeparator()
    {
        return System.getProperty("file.separator");
    }

    /**
     * Loads a collection of files from its names.
     * 
     * @param names The file names
     * @return The collection of <code>File</code> instances.
     */
    public static Collection<File> loadFiles(String[] names)
    {
        Collection<File> result = new ArrayList<File>();
        for (int i = 0; i < names.length; i++)
        {
            result.add(new File(names[i]));
        }
        return result;
    }

    /**
     * Renames a file from oldname to newone.
     * 
     * @param oldname The name of the file to be renamed.
     * @param newone The new file name.
     * @return <i>True</ib> if everything went fine and <i>false</i> otherwise.
     */
    public static boolean renameFile(String oldname, String newone)
    {
        boolean result = false;
        File file = new File(oldname);
        if (file.exists())
        {
            result = file.renameTo(new File(newone));
        }
        return result;
    }

    /**
     * Returns the user work directory.
     * 
     * @return A string containing the path to the directory.
     */
    public static String getUserDirectory()
    {
        return System.getProperty("user.dir");
    }

    /**
     * Checks if there are duplicated files.
     * 
     * @param names The names of the documents that will be searched for duplication.
     * @param featureDocuments The names of the feature documents.
     * @return A list with the name of duplicated files.
     */
    public static List<String> getDuplicatedFiles(Collection<String> names,
            String[] featureDocuments)
    {
        List<String> duplicated = new ArrayList<String>();
        for (String completename : names)
        {
            String filename = FileUtil.getFileName(completename);
            for (int i = 0; i < featureDocuments.length; i++)
            {
                if (filename.equalsIgnoreCase(FileUtil.getFileName(featureDocuments[i])))
                {
                    duplicated.add(featureDocuments[i]);
                }
            }
        }
        return duplicated;
    }

    /**
     * Deletes all files in inputFolder backing them up in <code>swapFolder</code>. This method
     * had to be implemented since file.canWrite() together with file.delete() do not work properly
     * due to a JAVA bug. See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4041126.
     * 
     * @param input The name of folder (origin) whose files will be removed and backed up.
     * @param swapFolder The temporary folder used as swap.
     * @return The first file that could not be deleted.
     * @throws IOException Thrown when it is not possible to copy a file.
     */
    private static File deleteAllFilesAux(String input, File swapFolder) throws IOException
    {
        File result = null;
        /* creates a file from the input */
        File file = new File(input);
        if (file.exists()) // verifies if the file exists
        {
            if (file.isDirectory()) // checks if it is a directory
            {
                File originalFolder = file; // keeps a reference from the current folder
                File swap = null;

                /*
                 * creates the directory trying to keep the same directory structure in the swap
                 * folder
                 */
                swap = new File(swapFolder + getSeparator() + file.getName());
                swap.mkdir();

                /* Iterates over all elements of the current directory 'file' */
                File[] files = file.listFiles();
                for (File file2 : files)
                {
                    result = deleteAllFilesAux(file2.getAbsolutePath(), swap);
                    if (result != null)
                    {
                        break;
                    }
                }
                if (result == null)
                {
                    originalFolder.delete();
                }
            }
            else
            {
                /* copies the file to the swap directory */
                copyFiles(new String[] { file.getAbsolutePath() }, swapFolder.getAbsolutePath(),
                        false);

                /* tries to delete the current file */
                if (!file.delete())
                {
                    result = file;
                }
                else
                {
                    result = null;
                }
            }
        }
        return result;
    }

    /**
     * Tries to delete all files in <code>input</code>. While deleting it makes backup of the
     * deleted files in a temporary folder. If it is not possoble to delete one file then it stops,
     * copies all files that were backed up and then delete the temporary folder.
     * 
     * @param input The name of folder (origin) whose files will be removed and backed up.
     * @return The first file that could not be deleted.
     * @throws IOException
     */
    public static File deleteAllFiles(String input) throws IOException
    {
        /* clean the destination folder */
        File tmpFolder = new File(TEMP_FOLDER + FileUtil.getSeparator()
                + System.currentTimeMillis() + "_delete_backup");
        tmpFolder.mkdir();

        File result = FileUtil.deleteAllFilesAux(input, tmpFolder);
        if (result != null)
        {
            FileUtil.copyFolder(tmpFolder.getAbsolutePath(), FileUtil.getFilePath(input), false);
        }

        /* removes the backup folder */
        FileUtil.deleteFiles(new File[] { tmpFolder });

        return result;
    }

    /**
     * Method responsible for copying the content of the specified <code>from</code> folder to the
     * <code>to</code> folder.
     * 
     * @param from The path of the folder which content will be copied.
     * @param to The path of the destination folder.
     * @param overwrite Indicates if the existing files in the destination folder will be
     * overwritten.
     * @throws IOException Thrown when it is not possible to overwrite a file.
     */
    public static void copyFolder(String from, String to, boolean overwrite) throws IOException
    {
        File fromFolder = new File(from);
        File toFolder = new File(to);
        if (fromFolder.isDirectory() && toFolder.isDirectory())
        {
            File[] files = fromFolder.listFiles();
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    File tmpDir = new File(to + getSeparator() + file.getName());
                    if (!tmpDir.exists())
                    {
                        tmpDir.mkdir();
                    }
                    copyFolder(file.getAbsolutePath(), tmpDir.getAbsolutePath(), overwrite);
                }
                else
                {
                    copyFiles(new String[] { file.getAbsolutePath() }, to, overwrite);
                }
            }
        }
    }
}
