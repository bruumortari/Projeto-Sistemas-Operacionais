import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShortTermScheduler implements Runnable, ControlInterface, InterSchedulerInterface {

    private LongTermScheduler longTermScheduler;

    public void setLongTermScheduler(LongTermScheduler lts) {
        longTermScheduler = lts;
    }

    // Lista de bloqueados
    List<Process> blockedQueue = new ArrayList<>();
    // Lista de terminados
    List<Process> finishedQueue = new ArrayList<>();

    List<Program> programs = new ArrayList<>();

    int quantum = 200;
    boolean isRunning = false;

    public void run() {

        try (BufferedReader reader = new BufferedReader(new FileReader("program1.txt"))) {
            String line;
            Program currentProgram = null;
            boolean readingInstructions = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("program")) {
                    String fileName = line.split(" ")[1];
                    currentProgram = new Program(fileName);
                    programs.add(currentProgram);
                } else if (line.equals("begin")) {
                    readingInstructions = true;
                } else if (line.equals("end")) {
                    readingInstructions = false;
                } else if (readingInstructions && currentProgram != null) {
                    currentProgram.addInstruction(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Program program : programs) {
            longTermScheduler.submitJob(program.getFileName());
        }
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

            // Caso em que a fila de prontos, fila de bloqueados e a fila de execução estão
            // vazias
            if (longTermScheduler.submissionQueue.isEmpty() && blockedQueue.isEmpty()) {
                stopSimulation();
                break;
            }

            // Caso em que a fila de prontos não está vazia
            if (!longTermScheduler.submissionQueue.isEmpty()) {
                for (Program program : programs) {
                    currentProcess = longTermScheduler.submissionQueue.remove(0);
                    System.out.println("Pid do processo: %d" + currentProcess.pid());

                    for (String instruction : program.getInstructions()) {
                        if (instruction.equals("execute")) {
                            // Simular a instrução 'execute' cronometrada com o quantum definido
                            try {
                                Thread.sleep(quantum);
                            } catch (InterruptedException ie) {
                                System.err.println("A thread foi interrompida: " + ie.getMessage());
                            }
                        } else if (instruction.startsWith("block")) {
                            String[] parts = instruction.split(" ");
                            int blockPeriod = Integer.parseInt(parts[1]);
                            blockedQueue.add(currentProcess);
                            // Simular a instrução 'block'
                            try {
                                Thread.sleep(quantum * blockPeriod);
                            } catch (InterruptedException ie) {
                                System.err.println("A thread foi interrompida: " + ie.getMessage());
                            }
                            blockedQueue.remove(currentProcess);
                        }
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
        longTermScheduler.submissionQueue.clear();
        blockedQueue.clear();
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

        System.out.println("\nFila de prontos");
        for (Process process : longTermScheduler.submissionQueue) {
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
        longTermScheduler.submissionQueue.add(bcp);
    }

    public int getProcessLoad() {
        /*
         * Utilizada para obter a carga atual de processos no escalonador de curto
         * prazo.
         */
        int load = 0;
        load = longTermScheduler.submissionQueue.size();
        return load;
    }
}
