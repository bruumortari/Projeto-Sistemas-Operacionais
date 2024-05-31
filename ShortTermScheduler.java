import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ShortTermScheduler implements Runnable, ControlInterface, InterSchedulerInterface {

    // Lista de prontos
    List<Process> submissionQueue = new ArrayList<>();
    // Lista de bloqueados
    List<Process> blockedQueue = new ArrayList<>();
    // Lista de terminados
    List<Process> finishedQueue = new ArrayList<>();
    // Lista de processos em execucao
    List<Process> execQueue = new ArrayList<>();

    int quantum = 200;
    boolean isRunning = false;

    public void run() {
        startSimulation();
    }

    public void startSimulation() {
        /*
         * Utilizada para iniciar a simulação. A partir da invocação desta
         * operação,
         * o simulador deve escolher um processo que esteja pronto para execução.
         * 
         * O simulador deve processar uma instrução por vez. Duas diferentes
         * instruções são providas
         * pela linguagem de definição de programas simulados: execute, a qual
         * representa um burst de CPU
         * (execução de um conjunto de instruções), e block, a qual representa um
         * burst de CPU
         * seguido de uma instrução de E/S (bloqueio).
         * A execução de uma instrução execute deve ser realizada por meio de uma
         * espera cronometrada.
         * 
         * Ao final de um quantum, o simulador deve proceder de acordo com a política
         * de escalonamento implementada.
         * A execução de uma instrução block deve ser realizada por meio de uma
         * espera cronometrada,
         * seguida do bloqueio do processo em execução por um determinado período
         * (medido em quantum).
         * 
         * Ao final de cada quantum, o escalonador deverá verificar se algum dos
         * processos bloqueados
         * tornou-se disponível, tornando-o pronto para execução.
         * 
         * A simulação termina quando não houver mais processos prontos ou bloqueados
         * para execução.
         */

        isRunning = true;
        Process currentProcess;

        while (isRunning) {

            // Caso em que a fila de prontos e a fila de bloqueados estão vazias
            if (submissionQueue.isEmpty() && blockedQueue.isEmpty()) {
                System.out.println("Simulacao finalizada");
                break;
            }

            // Caso em que a fila de prontos não está vazia
            if (!submissionQueue.isEmpty()) {
                currentProcess = submissionQueue.remove(0);
                execQueue.add(currentProcess);

                // Simular a instrução 'execute' cronometrada com o quantum definido
                try {
                    Thread.sleep(quantum);
                } catch (InterruptedException ie) {
                    System.err.println("A thread foi interrmpida: " + ie.getMessage());
                }

                // Depois da execução de 'execute', decidir próxima ação
                if (Math.random() > 50) { // Chance de 50% do processo realizar operação de E/S e ser bloqueado
                    execQueue.remove(currentProcess);
                    blockedQueue.add(currentProcess);

                    // Simular tempo que o processo fica bloqueado com o quantum definido
                    try {
                        Thread.sleep(quantum);
                    } catch (InterruptedException ie) {
                        System.err.println("A thread foi interrompida: " + ie.getMessage());
                    }
                } else {
                    execQueue.remove(currentProcess);
                    finishedQueue.add(currentProcess);
                }

                // Verificar fila de bloqueados
                if (!blockedQueue.isEmpty()) {
                    if (Math.random() > 50) { // Chance de 50% da operação de E/S ter terminadp
                        blockedQueue.remove(currentProcess);
                        submissionQueue.add(currentProcess);
                    }
                }
            }
        }

    }

    public void suspendSimulation() {
        /*
         * Utilizada para interromper temporariamente a simulação.
         * As informações sobre os processos em execução, prontos, bloqueados e
         * terminados não devem ser alteradas.
         */
        isRunning = false;
        System.out.println("Simulacao suspensa");
    }

    public void resumeSimulation() {
        /*
         * Utilizada para continuar a simulação a partir do ponto em que ela foi
         * interrompida temporariamente.
         */
        System.out.println("Simulacao retomada");
        startSimulation();
    }

    public void stopSimulation() {
        /*
         * Utilizada para encerrar definitivamente uma simulação.
         */
        isRunning = false;
        submissionQueue.clear();
        blockedQueue.clear();
        execQueue.clear();
        finishedQueue.clear();
        System.out.println("Simulacao finalizada");
    }

    public void displayProcessQueues() {
        /*
         * Utilizada para solicitar a descrição das informações sobre todos os
         * processos no escalonador
         * de curto prazo (processo em execução, processos prontos, processos
         * bloqueados e processos terminados).
         */

        System.out.println("\nFila de processos em execucao");
        for (Process process : execQueue) {
            System.out.println("Process: " + process);
            System.out.println("Process id: " + process.pid());
        }

        System.out.println("\nFila de prontos");
        for (Process process : submissionQueue) {
            System.out.println("Process: " + process);
            System.out.println("Process id: " + process.pid());
        }

        System.out.println("\nFila de bloqueados");
        for (Process process : blockedQueue) {
            System.out.println("Process: " + process);
            System.out.println("Process id: " + process.pid());
        }

        System.out.println("\nFila de terminados");
        for (Process process : finishedQueue) {
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
        int load = 0;
        load = execQueue.size();
        return load;
    }
}
