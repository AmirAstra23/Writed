import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress one = new GameProgress(5, 5, 5, 1.5);
        GameProgress two = new GameProgress(6, 6, 6, 1.6);
        GameProgress three = new GameProgress(7, 7, 7, 1.7);
        //
        saveGames(one, "/home/oem/work/Games/savegames/one.dat");
        saveGames(two, "/home/oem/work/Games/savegames/two.dat");
        saveGames(three, "/home/oem/work/Games/savegames/three.dat");
        //
        List<String> namesFilesToZip = Arrays.asList(
                "/home/oem/work/Games/savegames/one.dat",
                "/home/oem/work/Games/savegames/two.dat",
                "/home/oem/work/Games/savegames/three.dat");
        //
        String zipName = "/home/oem/work/Games/savegames/zip_output.zip";
        //
        zipFiles(namesFilesToZip, zipName);
        //
        List<File> filesOutZip = Arrays.asList(new File(namesFilesToZip.get(0)),
                new File(namesFilesToZip.get(1)),
                new File(namesFilesToZip.get(2)));
        for (File f : filesOutZip) {
            if (f.delete()) {
                System.out.println("File deleted");
            }
        }
    }

    private static void saveGames(GameProgress progress, String pathe) {
        // откроем выходной поток для записи в файл
        try (FileOutputStream fos =
                     new FileOutputStream(pathe);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
// запишем экземпляр класса в файл
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //
    private static void zipFiles(List<String> files, String archiveName) {
        try {
            ZipOutputStream out = new ZipOutputStream(
                    new FileOutputStream(archiveName));
            for (String file : files) {
                FileInputStream fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file));
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                out.write(bytes);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}