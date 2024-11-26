import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {

    // API Key y URL base de la API
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/eabc513d0820c0010fcf1f89/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostrar las opciones disponibles
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Convertir moneda");
            System.out.println("2. Salir");
            System.out.print("Opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer de entrada

            if (option == 2) {
                System.out.println("¡Gracias por usar el convertidor! Saliendo...");
                break; // Salir del programa
            } else if (option == 1) {
                // Realizar la conversión de moneda
                convertCurrency(scanner);
            } else {
                System.out.println("Opción no válida, por favor elija nuevamente.");
            }
        }
    }

    // Método para realizar la conversión de monedas
    private static void convertCurrency(Scanner scanner) {
        // Mostrar las monedas soportadas
        System.out.println("Seleccione una moneda de origen (código de 3 letras):");
        System.out.println("ARS - Peso argentino");
        System.out.println("BOB - Boliviano boliviano");
        System.out.println("BRL - Real brasileño");
        System.out.println("CLP - Peso chileno");
        System.out.println("COP - Peso colombiano");
        System.out.println("USD - Dólar estadounidense");

        // Leer la moneda de origen
        String fromCurrency = scanner.nextLine().toUpperCase();

        // Validar si la moneda es válida
        if (!isValidCurrency(fromCurrency)) {
            System.out.println("Moneda no válida. El programa se cerrará.");
            return;
        }

        System.out.println("Ingrese la cantidad que desea convertir:");
        double amount = scanner.nextDouble();

        System.out.println("Seleccione la moneda de destino (código de 3 letras):");
        String toCurrency = scanner.next().toUpperCase();

        // Validar si la moneda de destino es válida
        if (!isValidCurrency(toCurrency)) {
            System.out.println("Moneda no válida. El programa se cerrará.");
            return;
        }

        // Realizar la conversión
        try {
            double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
            if (exchangeRate == -1) {
                System.out.println("No se pudo obtener el tipo de cambio.");
            } else {
                double convertedAmount = amount * exchangeRate;
                System.out.printf("%.2f %s es igual a %.2f %s\n", amount, fromCurrency, convertedAmount, toCurrency);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
        }
    }

    // Método para verificar si el código de la moneda es válido
    private static boolean isValidCurrency(String currency) {
        return currency.equals("ARS") || currency.equals("BOB") || currency.equals("BRL") ||
                currency.equals("CLP") || currency.equals("COP") || currency.equals("USD");
    }

    // Método para obtener el tipo de cambio entre dos monedas
    private static double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        // Crear la URL para la API
        String url = API_URL + fromCurrency;

        // Crear el cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Hacer la solicitud HTTP y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Error al obtener datos de la API. Código de respuesta: " + response.statusCode());
        }

        // Parsear la respuesta JSON
        JSONObject jsonResponse = new JSONObject(response.body());
        if (!jsonResponse.getString("result").equals("success")) {
            throw new Exception("Error en la respuesta de la API.");
        }

        // Obtener el tipo de cambio de la moneda de origen a la moneda destino
        JSONObject rates = jsonResponse.getJSONObject("conversion_rates");
        if (!rates.has(toCurrency)) {
            throw new Exception("Tipo de cambio no disponible para la moneda " + toCurrency);
        }

        return rates.getDouble(toCurrency);
    }
}
