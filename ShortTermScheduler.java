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

    int count = 0, aux = 0;
    String block;

    Program currentProcess;

    List<Program> blockedQueue = new ArrayList<>();
    List<Program> readyQueue = new ArrayList<>();
    List<Program> finishedQueue = new ArrayList<>();

    int quantum = 200;
    boolean isRunning = false;

    public void run() {

    }

    public void loadPrograms(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Program currentProgram = null;
            boolean readingInstructions = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("program")) {
                    String programName = line.split(" ")[1];
                    currentProgram = new Program(programName);
                    readyQueue.add(currentProgram);
                } else if (line.equals("begin")) {
                    readingInstructions = true;
                } else if (line.equals("end")) {
                    readingInstructions = false;
                } else if (readingInstructions && currentProgram != null) {
                    currentProgram.addInstruction(line);
                }
            }
            System.out.println();
            System.out.println(currentProgram.getProgramName());
            System.out.println(currentProgram.getInstructions());
            System.out.println();
            //System.out.println(readyQueue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSimulation() {
        isRunning = true;

        for (int i = 0; i < 2; i++) {
            // Executa os programas prontos
            executeReadyPrograms();

            // Verifica se há programas bloqueados para desbloquear
            checkBlockedPrograms(block, aux);

            // Se não houver mais programas prontos ou bloqueados, encerra a simulação
            if (readyQueue.isEmpty() && blockedQueue.isEmpty()) {
                stopSimulation();
            }
        }
    }

    private void executeReadyPrograms() {
        for (Program program : readyQueue) {
            List<String> instructions = program.getInstructions();
            for (String instruction : instructions) {
                if (instruction.equals("execute")) {
                    System.out.println("execute: " + program.getProgramName());
                } else {
                    System.out.println("block: " + program.getProgramName());
                    // Move o programa para a fila de bloqueados
                    blockedQueue.add(program);
                    block = instruction;
                    count++;
                    aux = count;
                    count = 0;
                    break; // Sai do loop de instruções do programa atual
                }
            }
        }
    }

    private void checkBlockedPrograms(String block, int count) {
        if (!blockedQueue.isEmpty()) {
            for (Program program : blockedQueue) {
                String[] aux = block.split(" ");
                int num = Integer.valueOf(aux[1]);
                if (count > num) {
                    readyQueue.add(program);
                    blockedQueue.remove(program);
                }
            }
        }

    }

    public void suspendSimulation() {
        isRunning = false;
        System.out.println("Simulação suspensa");
    }

    public void resumeSimulation() {
        System.out.println("Simulação retomada");
        startSimulation();
    }

    public void stopSimulation() {
        isRunning = false;
        longTermScheduler.submissionQueue.clear();
        blockedQueue.clear();
        finishedQueue.clear();
    }

    public void displayProcessQueues() {
        System.out.println("\nFila de prontos");
        synchronized (readyQueue) {
            for (Program program : readyQueue) {
                System.out.println("Process: " + program);
            }
        }

        System.out.println("\nFila de bloqueados");
        synchronized (blockedQueue) {
            for (Program program : blockedQueue) {
                System.out.println("Process: " + program);
            }
        }

        System.out.println("\nFila de terminados");
        synchronized (finishedQueue) {
            for (Program program : finishedQueue) {
                System.out.println("Process: " + program);
            }
        }
    }

    public void addProcess(Process bcp) {
        longTermScheduler.submissionQueue.add(bcp);
    }

    public int getProcessLoad() {
        return longTermScheduler.submissionQueue.size();
    }
}
