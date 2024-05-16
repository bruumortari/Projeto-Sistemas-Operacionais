public class LongTermScheduler implements SubmissionInterface, InterSchedulerInterface {
    public boolean submitJob(String fileName) {
        return true;
    }

    public void displaySubmissionQueue() {
    /*
     * Utilizada para solicitar a descrição da fila de processos submetidos ao
     * escalonador de longo prazo, mas ainda não encaminhados ao escalonador de
     * curto prazo.
     */

    }

    public void addProcess(Process bcp) {
    /*
     * Utilizada para submeter um processo à execução.
     * Esta operação tem como parâmetro um objeto do tipo Process (a ser definido);
     */

    }

    public int getProcessLoad() {
    /*
     * Utilizada para obter a carga atual de processos no escalonador de curto prazo.
     */

        int i = 1;
        return i;
    }
}
