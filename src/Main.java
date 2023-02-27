import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/array.txt");

        ArrayList<Double> valores = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String linhaAtual;
            while ((linhaAtual = bufferedReader.readLine()) != null) {
                double valor = Double.parseDouble(linhaAtual);
                valores.add(valor);
            }
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado" + e.getMessage());
        }
        for (int i = 0; i < valores.size(); i++) {
            System.out.println(valores.get(i));
        }


    }

}