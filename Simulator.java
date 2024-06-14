public class Simulator {
    public static void main(String[] args) {

        int maxLoad = 0;

        // Verificar número de argumentos
        if (args.length > 0) {
            try {
                maxLoad = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Ocorreu um erro de na formatacao de args: " + e.getMessage());
            }
        }

        else {
            System.out.println("O valor de args deve ser maior que 0!");
        }

        // Criar instâncias de cada classe
        UserInterface userInterface = new UserInterface();
        LongTermScheduler longTermScheduler = new LongTermScheduler();
        ShortTermScheduler shortTermScheduler = new ShortTermScheduler();

        // Passar a carga máxima para as classes
        longTermScheduler.maxLoad(maxLoad);

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