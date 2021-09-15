import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class IntegerDataFile {
    ArrayList<String> fileArrayList;
    boolean sortType;

    public IntegerDataFile(ArrayList<String> fileArrayList, boolean sortType) {
        this.fileArrayList = fileArrayList;
        this.sortType = sortType;
    }

    public void mergeSortBigFile() {
//        Сортировка попарно выходной файл с каждым входным. Запись меньшего элемента.

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

                System.out.println("Обработка входного файла: " + fileArrayList.get(i));
                String strOut;
                String strIn;

                strOut = readerOut.readLine();
                strIn = readerIn.readLine();

                while (strOut != null || strIn != null) {
                    if (strOut != null) {
                        while (strIn != null) {
                            try {
                                if (Integer.parseInt(strIn) -
                                        Integer.parseInt(strOut) <= 0) {
                                    tempFile.println(Integer.parseInt(strIn));
                                    strIn = readerIn.readLine();
                                } else break;
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка данных в файле: " + fileArrayList.get(i));
                                System.out.println("Возможна потеря данных.");
                                strIn = readerIn.readLine();
                            }
                        }
                    } else {
                        while (strIn != null) {
                            try {
                                tempFile.println(Integer.parseInt(strIn));
                                strIn = readerIn.readLine();
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка данных в файле: " + fileArrayList.get(i));
                                System.out.println("Возможна потеря данных.");
                                strIn = readerIn.readLine();
                            }
                        }
                    }

                    if (strIn != null) {
                        while (strOut != null) {
                            try {
                                if ((Integer.parseInt(strOut) -
                                        Integer.parseInt(strIn) <= 0)) {
                                    tempFile.println(Integer.parseInt(strOut));
                                    strOut = readerOut.readLine();
                                } else break;
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка данных в файле: " + fileArrayList.get(i));
                                System.out.println("Возможна потеря данных.");
                                strOut = readerOut.readLine();
                            }
                        }
                    } else {
                        while (strOut != null) {
                            try {
                                tempFile.println(Integer.parseInt(strOut));
                                strOut = readerOut.readLine();
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка данных в файле: " + fileArrayList.get(i));
                                System.out.println("Возможна потеря данных.");
                                strOut = readerOut.readLine();
                            }
                        }
                    }
                }
            } catch (
                    FileNotFoundException e) {
                System.out.println("Не удается найти указанный файл: " + fileArrayList.get(i));
                System.out.println("Часть данных может быть утеряна.");
            } catch (
                    IOException e) {
                e.printStackTrace();
            }

            try (
                    PrintWriter writer =
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
        System.out.println("Время сортировки: " + (System.currentTimeMillis() - time) + " ms");

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
