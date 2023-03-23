import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        File file = new File(args[0]);
        int intervalo = Integer.parseInt(args[1]);
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

        int numeroDeThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numeroDeThreads];

        int blockSize = valores.size() / numeroDeThreads;

        for (int i = 0; i < numeroDeThreads; i++) {
            int startIdx = i * blockSize;
            int endIdx = i == numeroDeThreads - 1 ? valores.size() : (i + 1) * blockSize;

            threads[i] = new Thread(new CalcularDistancia(valores, startIdx, endIdx, intervalo, menoresDistancias));
            threads[i].start();

        }

        for (Thread thread : threads) {
            try {
                for (int i = 0; i < numeroDeThreads; i++) {
                    threads[i].join();
                }

            } catch (InterruptedException e) {
                System.out.println("Erro ao aguardar as threads: " + e.getMessage());
            }
        }

        System.out.println(menoresDistancias);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("menores_distancias.txt");
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de saída: " + e.getMessage());
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (double distancia : menoresDistancias) {
            try {
                bufferedWriter.write(Double.toString(distancia));
                bufferedWriter.newLine();
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo de saída: " + e.getMessage());
            }
        }

        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar arquivo de saída: " + e.getMessage());
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.println(elapsed);
    }

    public static class CalcularDistancia implements Runnable {
        private List<Double> valores;
        private int start;
        private int end;
        private int intervalo;
        private ArrayList<Double> menoresDistancias;

        public CalcularDistancia(List<Double> valores, int start, int end, int intervalo, ArrayList<Double> menoresDistancias) {
            this.valores = valores;
            this.start = start;
            this.end = end;
            this.intervalo = intervalo;
            this.menoresDistancias = menoresDistancias;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (i + intervalo > valores.size())
                    break;
                ArrayList<Double> subArray = new ArrayList<>(valores.subList(i, i + intervalo));
                double distanciaMinima = Double.MAX_VALUE;
                int distanciaMinimaIndex = 0;
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
                        distanciaMinima = Math.round(calculo * 10.0) / 10.0;
                        distanciaMinimaIndex = j;
                    }
                }
                menoresDistancias.add(distanciaMinima);


            }
        }
    }
}