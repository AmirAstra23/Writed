import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        GameProgress one = new GameProgress(5, 5, 5, 1.5);
        GameProgress two = new GameProgress(6, 6, 6, 1.6);
        GameProgress three = new GameProgress(7, 7, 7, 1.7);
        saveGames(one, "/home/oem/work/Games/savegames/one.dat");
        saveGames(two, "/home/oem/work/Games/savegames/two.dat");
        saveGames(three, "/home/oem/work/Games/savegames/three.dat");
        List<String> patheList = Arrays.asList("/home/oem/work/Games/savegames/one.dat",
                "/home/oem/work/Games/savegames/two.dat",
                "/home/oem/work/Games/savegames/three.dat");
        List<String> objectsOfZipList = Arrays.asList("dat1.dat", "dat2.dat", "dat3.dat");

        //
        String patheZip = "/home/oem/work/Games/savegames/zip_output.zip";

        zipFiles(patheZip, patheList, 0, objectsOfZipList);
        zipFiles(patheZip, patheList, 1, objectsOfZipList);
        zipFiles(patheZip, patheList, 2, objectsOfZipList);

    }

    public static void saveGames(GameProgress progress, String pathe) {
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

    public static void zipFiles(String patheZip, List<String> patheList, int i, List<String> objectOfZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(patheZip));
             FileInputStream fis = new FileInputStream(patheList.get(i))) {
            ZipEntry entry = new ZipEntry(objectOfZip.get(i));
            zout.putNextEntry(entry);
// считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
// добавляем содержимое к архиву
            zout.write(buffer);
// закрываем текущую запись для новой записи
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}