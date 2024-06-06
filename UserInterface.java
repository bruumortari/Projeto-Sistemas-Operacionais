import java.io.IOException;

public class UserInterface implements Runnable, SubmissionInterface, NotificationInterface {

    ShortTermScheduler sts = new ShortTermScheduler();

    private int maxLoad;

    public void maxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public void run() {

    }

    public boolean submitJob(String fileName) {
        /*
         * Ao receber a solicitação de submissão de um programa, o simulador deve criar
         * um processo para representar o programa internamente (CHECK)
         * O processo criado deve ser armazenado na fila de processos a serem admitidos
         * no sistema (CHECK)
         * Estes processos serão então encaminhados pelo escalonador de longo prazo ao
         * escalonador de curto prazo de acordo com a carga de processos no mesmo.
         */

        try {
            /*
             * Definição da fila de processos a serem admitidos no
             * sistema(submissionQueue)
             */

            // Criar um ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(fileName);

            // Iniciar o processo
            Process process = pb.start();

            if (sts.submissionQueue.size() < maxLoad) {
                // Colocar o processo criado na fila de submissionQueue
                sts.submissionQueue.add(process);
            }

        } catch (IOException io) {
            System.err.println("Ocorreu um erro de E/S: " + io.getMessage());
            return false;
        } catch (SecurityException se) {
            System.err.println("Ocorreu um erro de seguranca: " + se.getMessage());
            return false;
        } catch (NullPointerException e) { // Se o comando fornecido for nulo
            System.err.println("Ocorreu um erro com o comando fornecido: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) { // Se o comando for uma string vazia.
            System.err.println("Ocorreu um erro com o comando fornecido: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void displaySubmissionQueue() {
        /*
         * Utilizada para solicitar a descrição da fila de processos submetidos ao
         * escalonador de longo prazo, mas ainda não encaminhados ao escalonador de
         * curto prazo.
         */

        System.out.println("\nFila de prontos");
        for (Process process : sts.submissionQueue) {
            System.out.println("Process: " + process);
            System.out.println("Process id: " + process.pid());
        }
    }

    public void display(String info) {
        /*
         * Utilizada pelos escalonadores de curto e longo prazo para notificar qualquer
         * informação de interesse relacionada ao acompanhamento da simulação para o
         * thread
         * UserInterface.
         */
    }

}
