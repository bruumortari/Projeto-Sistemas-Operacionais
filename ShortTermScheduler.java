import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShortTermScheduler implements Runnable, ControlInterface, InterSchedulerInterface {
    private LongTermScheduler longTermScheduler;

     private JTextArea instructionTextArea;

    // Método para receber o JTextArea da GUI
    public void setInstructionTextArea(JTextArea textArea) {
        this.instructionTextArea = textArea;
    }

    public void setLongTermScheduler(LongTermScheduler lts) {
        longTermScheduler = lts;
    }

    int count = 0, aux = 0;
    String block;

    Program currentProcess;

    List<Program> blockedQueue = new ArrayList<>();
    List<Program> readyQueue = new ArrayList<>();
    List<Program> finishedQueue = new ArrayList<>();
    List<Program> unblockQueue = new ArrayList<>();

    List<Integer> auxQueue = new ArrayList<>();
    List<String> auxBlock = new ArrayList<>();

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
                    // longTermScheduler.submitJob(currentProgram.getProgramName());
                } else if (line.equals("begin")) {
                    readingInstructions = true;
                } else if (line.equals("end")) {
                    readingInstructions = false;
                } else if (readingInstructions && currentProgram != null) {
                    currentProgram.addInstruction(line);
                }
            }
            //System.out.println();
            //System.out.println(currentProgram.getProgramName());
            //System.out.println(currentProgram.getInstructions());
            //System.out.println();
            // System.out.println(readyQueue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSimulation() {
        isRunning = true;

        // Executa os programas prontos
        executeReadyPrograms(readyQueue);
        // System.out.println(blockedQueue);
        // System.out.println(auxQueue);
        // System.out.println(auxBlock);

        // Verifica se há programas bloqueados para desbloquear;
        checkBlockedPrograms(auxQueue, auxBlock);

        executeReadyPrograms(unblockQueue);

        // Se não houver mais programas prontos ou bloqueados, encerra a simulação
        if (readyQueue.isEmpty() && blockedQueue.isEmpty()) {
            stopSimulation();
        }

    }

    private void executeReadyPrograms(List<Program> queue) {
        for (Program program : queue) {
            List<String> instructions = program.getInstructions();
            for (String instruction : instructions) {
                if (instruction.equals("execute")) {
                    String message = "execute: " + program.getProgramName();
                    writeToTextArea(message);
                    //System.out.println("execute: " + program.getProgramName());
                    count++;
                } else {
                    //System.out.println("block: " + program.getProgramName());
                    String message = "block: " + program.getProgramName();
                    writeToTextArea(message);
                    // Move o programa para a fila de bloqueados
                    blockedQueue.add(program);
                    auxBlock.add(instruction);
                    count++;
                    auxQueue.add(count);
                    count = 0;
                    break; // Sai do loop de instruções do programa atual
                }
            }
        }
    }

    private void writeToTextArea(String message) {
        if (instructionTextArea != null) {
            instructionTextArea.append(message + "\n");
        }
    }

    private void checkBlockedPrograms(List<Integer> auxQueue, List<String> auxBlock) {
        int i = 0;
        if (!blockedQueue.isEmpty()) {
            for (Program program : blockedQueue) {
                String[] aux = new String[0];
                // System.out.print(auxBlock.get(i));
                aux = auxBlock.get(i).split(" ");
                // System.out.print(aux[1]);
                int num = Integer.valueOf(aux[1]);
                // System.out.print(auxQueue.get(i));
                // System.out.print(num);
                if (auxQueue.get(i) <= num) {
                    unblockQueue.add(program);
                    // blockedQueue.remove(program);
                }
                i++;

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
        System.out.println("Simulacao finalizada");
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