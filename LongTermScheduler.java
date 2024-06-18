import java.util.ArrayList;
import java.util.List;

public class LongTermScheduler implements Runnable, SubmissionInterface, InterSchedulerInterface {

    // Lista de prontos
    List<Process> submissionQueue = new ArrayList<>();

    private ShortTermScheduler shortTermScheduler;

    public void setShortTermScheduler(ShortTermScheduler sts) {
        shortTermScheduler = sts;
    }

    // Variável que define a carga máxima
    private int maxLoad;

    public void run() {
        shortTermScheduler.loadPrograms("program1.txt");
        shortTermScheduler.loadPrograms("program2.txt");
        shortTermScheduler.loadPrograms("program3.txt");
        shortTermScheduler.startSimulation();
    }

    public void maxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public boolean submitJob(String fileName) {
        while (submissionQueue.size() < maxLoad) {
            try {
                /*
                 * Definição da fila de processos a serem admitidos no
                 * sistema(submissionQueue)
                 */
                // Cria um novo processo usando ProcessBuilder
                ProcessBuilder processBuilder = new ProcessBuilder(fileName);
                Process process = processBuilder.start();

                // Adiciona o processo à fila de submissão
                submissionQueue.add(process);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
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
        submissionQueue.add(bcp);

    }

    public int getProcessLoad() {
        /*
         * Utilizada para obter a carga atual de processos no escalonador de curto
         * prazo.
         */
        return shortTermScheduler.getProcessLoad();
    }
}
