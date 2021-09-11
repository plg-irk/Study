import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        boolean sorting = true;
        String typeData = null;
        ArrayList<String> fileArrayList = new ArrayList<>();
        for (String str : args) {
            if (str.equals("-a")) sorting = true;
            else if (str.equals("-d")) sorting = false;
            else if ("-s".equals(str)) typeData = "s";
            else if ("-i".equals(str)) typeData = "i";
            else fileArrayList.add(str);
        }

        if (fileArrayList.size() == 0)
            System.out.println("Отсутствует имя выходного файла.");
        else if (fileArrayList.size() <= 1)
            System.out.println("Необходимо не менее одного входного файла.");
        else {

            if (typeData == null)
                System.out.println("Тип данных неопределен");

            else if (typeData.equals("s")) {
//                StringData stringData = new StringData(fileArrayList, sorting);
//                stringData.readWriteWithSort();
                StringDadaFile stringDadaFile = new StringDadaFile(fileArrayList, sorting);
                stringDadaFile.mergeSortBigFile();

            } else if (typeData.equals("i")) {
                IntegerData integerData = new IntegerData(fileArrayList, sorting);
                integerData.readWriteWithSort();
            }
        }
    }
}

