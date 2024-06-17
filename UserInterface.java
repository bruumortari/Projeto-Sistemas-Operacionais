public class UserInterface implements Runnable, SubmissionInterface, NotificationInterface {

    private LongTermScheduler longTermScheduler;

    public void setLongTermScheduler(LongTermScheduler lts) {
        longTermScheduler = lts;
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

        longTermScheduler.submitJob(fileName);

        return true;
    }

    public void displaySubmissionQueue() {
        /*
         * Utilizada para solicitar a descrição da fila de processos submetidos ao
         * escalonador de longo prazo, mas ainda não encaminhados ao escalonador de
         * curto prazo.
         */
        longTermScheduler.displaySubmissionQueue();
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
