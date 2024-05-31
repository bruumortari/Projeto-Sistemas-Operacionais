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

        // Criar instâncias de cada classe
        UserInterface userInterface = new UserInterface();
        LongTermScheduler longTermScheduler = new LongTermScheduler();
        ShortTermScheduler shortTermScheduler = new ShortTermScheduler();

        // Criar threads
        Thread userInterfaceThread = new Thread(userInterface);
        Thread longTermThread = new Thread(longTermScheduler);
        Thread shortTermThread = new Thread(shortTermScheduler);

        // Inicializar as threads
        userInterfaceThread.start();
        longTermThread.start();
        shortTermThread.start();

        // Sincronizar as threads para que main aguarde o término de todas as threads
        try {
            longTermThread.join();
            shortTermThread.join();
            userInterfaceThread.join();
        } catch (InterruptedException ie) {
            System.err.println("A thread foi interrompida: " + ie.getMessage());
        }

    }
}