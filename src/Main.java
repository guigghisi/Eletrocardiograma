import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/array.txt");
        int intervalo = 4;
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

        ArrayList<Double> menoresDistancias = new ArrayList<>();
        for (int i = 0; i < valores.size(); i++) {
            if (i + intervalo > valores.size())
                break;
            ArrayList<Double> subArray = new ArrayList<>(valores.subList(i, i + intervalo));
            double distanciaMinima = Double.MAX_VALUE;
            for (int j = 0; j < valores.size(); j++) {
                if (j + intervalo > valores.size())
                    break;
                ArrayList<Double> subArray2 = new ArrayList<>(valores.subList(j, j + intervalo));
                double calculo = 0;
                for (int k = 0; k < intervalo; k++) {
                    calculo += Math.pow(subArray.get(k) - subArray2.get(k), 2);

                }
                calculo = Math.sqrt(calculo);
                if (calculo < distanciaMinima && calculo != 0) {
                    distanciaMinima = calculo;
                }
                System.out.println("Array2" + subArray2);
            }
            menoresDistancias.add(distanciaMinima);
            System.out.println("Array1" + subArray);

        }
        System.out.println(menoresDistancias);
    }

}

