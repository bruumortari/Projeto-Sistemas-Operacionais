public class Simulator {
    public static void main(String[] args) {

        // Verificar número de argumentos
        if (args.length <= 0) {
            System.out.println("Numero de argumentos precisa ser maior que 0!");
        }

        else {
            try {
                int maxLoad = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Ocorreu um erro de na formatacao de args: " + e.getMessage());
            }
        } 
    }
}