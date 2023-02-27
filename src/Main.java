import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
    File file = new File("src/array.txt");

        ArrayList<String> linhas = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linhaAtual;
            while ((linhaAtual = bufferedReader.readLine())!= null){
                linhas.add(linhaAtual);
            }
            String[] array = linhas.toArray(new String[0]);

            System.out.println(Arrays.toString(array));
        }catch (IOException e){
            System.out.println("Arquivo não encontrado"+ e.getMessage());
        }
    }

}