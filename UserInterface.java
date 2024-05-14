import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class UserInterface implements SubmissionInterface, NotificationInterface {
    @Override
    public boolean submitJob(String fileName) {
        /*
         * Ao receber a solicitação de submissão de um programa, o simulador deve criar
         * um processo para representar o programa internamente (CHECK)
         * O processo criado deve ser armazenado na fila de processos a serem admitidos
         * no sistema (CHECK)
         * Estes processos serão então encaminhados pelo escalonador de longo prazo ao
         * escalonador de curto prazo de acordo com a carga de processos no mesmo.
         */

        // Se o identificador do arquivo for inválido, retorna false
        if (fileName == null)
            return false;
        else {
            try {
                /*
                 * Definição de variáveis e da fila de processos a serem admitidos no
                 * sistema(submissionQueue)
                 */
                List<Process> submissionQueue = new ArrayList<>();

                // Criar um ProcessBuilder
                ProcessBuilder pb = new ProcessBuilder();

                // Iniciar o processo
                Process process = pb.start();

                // Colocar o processo criado na fila de submissionQueue
                submissionQueue.add(process);
            } catch (IOException io) {
                System.err.println("Ocorreu um erro de I/O: " + io.getMessage());
            }

        }
        return true;
    }

    @Override
    public void displaySubmissionQueue() {
        /*
         * Utilizada para solicitar a descrição da fila de processos submetidos ao
         * escalonador de longo prazo, mas ainda não encaminhados ao escalonador de
         * curto prazo.
         */
    }

    @Override
    public void display(String info) {
        /*
         * Utilizada pelos escalonadores de curto e longo prazo para notificar qualquer
         * informação
         * de interesse relacionada ao acompanhamento da simulação para o thread
         * UserInterface.
         */
    }

}
