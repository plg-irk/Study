import java.io.*;
import java.util.ArrayList;

public class WorkWithFile {
    ArrayList<String> fileArrayList;
    boolean sortType;

    public WorkWithFile(ArrayList<String> fileArrayList, boolean sortType) {
        this.fileArrayList = fileArrayList;
        this.sortType = sortType;
    }

    public void readWriteWithSort() {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(fileArrayList.get(0)))) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < fileArrayList.size(); i++) {
            try (PrintWriter tempFile =
                         new PrintWriter("temp.txt");
                 BufferedReader readerOut =
                         new BufferedReader(new FileReader(fileArrayList.get(0)));
                 BufferedReader readerIn =
                         new BufferedReader(new FileReader(fileArrayList.get(i)))) {

                System.out.println(fileArrayList.get(i));
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

            try (
                    PrintWriter writer =
                            new PrintWriter(new FileWriter(fileArrayList.get(0)));
                    BufferedReader reader =
                            new BufferedReader(new FileReader("temp.txt"))) {
                String strReader;
                System.out.println("begin write");
                while ((strReader = reader.readLine()) != null) {
                    writer.println(strReader);
                }
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
    }
}
