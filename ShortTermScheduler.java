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

    Program currentProcess;

    List<Program> blockedQueue = new ArrayList<>();

    List<Program> readyQueue = new ArrayList<>();

    List<Program> finishedQueue = new ArrayList<>();

    int quantum = 200;
    boolean isRunning = false;

    public void run() {
        loadPrograms("program1.txt");
        loadPrograms("program2.txt");
        startSimulation();
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
            System.out.println(currentProgram.getInstructions());
            System.out.println(readyQueue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSimulation() {
        isRunning = true;
        int count = 0;

        while (isRunning && count < readyQueue.size()) {
            // Caso em que a fila de prontos, fila de bloqueados e a fila de execução estão
            // vazias
            if (readyQueue.isEmpty()) {
                stopSimulation();
                break;
            }

            // Caso em que a fila de prontos não está vazia
            else {
                for (Program program : readyQueue) {
                    count++;
                    List<String> instructions = new ArrayList<>();
                    instructions = program.getInstructions();
                    for (String instruction : instructions) {
                        if (instruction.equals("execute")) {
                            System.out.println("execute: " + program.getProgramName());
                        } else {
                            System.out.println("block: " + program.getProgramName());
                        }
                    }
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
