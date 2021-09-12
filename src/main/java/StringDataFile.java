import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class StringDataFile {
    ArrayList<String> fileArrayList;
    boolean sortType;

    public StringDataFile(ArrayList<String> fileArrayList, boolean sortType) {
        this.fileArrayList = fileArrayList;
        this.sortType = sortType;
    }

    public void mergeSortBigFile() {
//        Сортировка попарно выходной файл с каждым входным, сравнивая элементы в разных файлах.

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(fileArrayList.get(0)))) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis();
        for (int i = 1; i < fileArrayList.size(); i++) {
            try (PrintWriter tempFile =
                         new PrintWriter("temp.txt");
                 BufferedReader readerOut =
                         new BufferedReader(new FileReader(fileArrayList.get(0)));
                 BufferedReader readerIn =
                         new BufferedReader(new FileReader(fileArrayList.get(i)))) {

                System.out.println("Обработка файла: " +fileArrayList.get(i));
                String strOut;
                String strIn;

                strOut = readerOut.readLine();
                strIn = readerIn.readLine();

                while (strOut != null || strIn != null) {
                    if (strOut != null) {
                        while (strIn != null && (strIn.compareTo(strOut) <= 0)) {
                            tempFile.println(strIn);
                            strIn = readerIn.readLine();
                        }
                    } else {
                        while (strIn != null) {
                            tempFile.println(strIn);
                            strIn = readerIn.readLine();
                        }
                    }
                    if (strIn != null) {
                        while (strOut != null && (strOut.compareTo(strIn) <= 0)) {
                            tempFile.println(strOut);
                            strOut = readerOut.readLine();
                        }
                    } else {
                        while (strOut != null) {
                            tempFile.println(strOut);
                            strOut = readerOut.readLine();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Не удается найти указанный файл: " + fileArrayList.get(i));
                System.out.println("Часть данных может быть утеряна.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (PrintWriter writer =
                         new PrintWriter(new FileWriter(fileArrayList.get(0)));
                 BufferedReader reader =
                         new BufferedReader(new FileReader("temp.txt"))) {
                String strReader;
                while ((strReader = reader.readLine()) != null) {
                    writer.println(strReader);
                }
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Время сортировки: " + (System.currentTimeMillis() - time) + "ms");

        if (!sortType) {
            ReverseUseStream reverseUseStream =
                    new ReverseUseStream(new File("temp.txt"),
                            new File(fileArrayList.get(0)));
            System.out.println("Сортировка по убыванию");
            reverseUseStream.reversedUseStream();
        }

        Path path = Path.of("temp.txt");
        try {
            Files.delete(path);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}

