import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LongTermScheduler implements Runnable, SubmissionInterface, InterSchedulerInterface {

    // Lista de prontos
    List<Process> submissionQueue = new ArrayList<>();
    // Lista de exec
    List<Process> execQueue = new ArrayList<>();

    // Variável que define a carga máxima
    private int maxLoad;

    public void run() {

    }

    public void maxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public boolean submitJob(String fileName) {
        try {
            /*
             * Definição da fila de processos a serem admitidos no
             * sistema(submissionQueue)
             */

            // Criar um ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(fileName);

            // Iniciar o processo
            Process process = pb.start();

            if (submissionQueue.size() < maxLoad) {
                // Colocar o processo criado na fila de submissionQueue
                submissionQueue.add(process);
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
        for (Process process : submissionQueue) {
            System.out.println("Process: " + process);
            System.out.println("Process id: " + process.pid());
        }

    }

    public void addProcess(Process bcp) {
        /*
         * Utilizada para submeter um processo à execução.
         * Esta operação tem como parâmetro um objeto do tipo Process (a ser
         * definido);
         */
        execQueue.add(bcp);

    }

    public int getProcessLoad() {
        /*
         * Utilizada para obter a carga atual de processos no escalonador de curto
         * prazo.
         */
        int load = 0;
        load = execQueue.size();
        return load;
    }
}
