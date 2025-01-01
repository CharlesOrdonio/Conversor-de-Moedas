import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Float> Valores = new ArrayList<>();

        // URL e chave da API
        String apiUrl = "https://v6.exchangerate-api.com/v6/c2e322037e032d865684970a/latest/USD"; 
        String apiKey = "c2e322037e032d865684970a"; 

        // Chamar a API
        String apiResponse = Api.fetchApiData(apiUrl, apiKey);

        // Moedas disponíveis
        ArrayList<String> Moedas = new ArrayList<>();
        Moedas.add("USD");
        Moedas.add("EUR");
        Moedas.add("BRL");
        Moedas.add("ARS");
        Moedas.add("JPY");
        Moedas.add("CNY");

        // Preenchendo valores com base na resposta da API
        for (String moeda : Moedas) {
            String key = "\"" + moeda + "\":";
            int startIndex = apiResponse.indexOf(key) + key.length();
            int endIndex = apiResponse.indexOf(",", startIndex);
            if (endIndex == -1) endIndex = apiResponse.indexOf("}", startIndex); // Última moeda
            String valorString = apiResponse.substring(startIndex, endIndex).trim();
            Valores.add(Float.parseFloat(valorString));
        }

        // Início da interação com o usuário
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        while (loop) {
            System.out.println("\n1 - Converter Moeda");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            if (opcao == 1) {
                System.out.println("Moedas disponíveis: Dolar: USD, Euro: EUR, Real: BRL, Peso Argentino: ARS, Yene: JPY, Yuan: CNY.");
                System.out.println("Use as siglas");
                System.out.print("Digite a moeda que você tem: ");
                String moedaOrigem = scanner.nextLine();
                System.out.print("Digite a quantidade: ");
                float quantidade = scanner.nextFloat();
                scanner.nextLine(); // Consumir a quebra de linha

                System.out.print("Digite a moeda para a qual deseja converter: ");
                String moedaDestino = scanner.nextLine();

                // Encontrando índices das moedas
                int indiceOrigem = getIndiceMoeda(Moedas, moedaOrigem);
                int indiceDestino = getIndiceMoeda(Moedas, moedaDestino);

                if (indiceOrigem == -1 || indiceDestino == -1) {
                    System.out.println("Moeda inválida. Tente novamente.");
                } else {
                    float valorOrigem = Valores.get(indiceOrigem);
                    float valorDestino = Valores.get(indiceDestino);
                    float resultado = (quantidade / valorOrigem) * valorDestino;

                    System.out.printf("%.2f %s equivalem a %.2f %s.\n", quantidade, moedaOrigem, resultado, moedaDestino);
                }
            } else if (opcao == 2) {
                System.out.println("Encerrando...");
                loop = false;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    // Método para obter o índice da moeda na lista
    public static int getIndiceMoeda(ArrayList<String> moedas, String moeda) {
        for (int i = 0; i < moedas.size(); i++) {
            if (moedas.get(i).equalsIgnoreCase(moeda)) {
                return i;
            }
        }
        return -1; // Retorna -1 se a moeda não for encontrada
    }
}
