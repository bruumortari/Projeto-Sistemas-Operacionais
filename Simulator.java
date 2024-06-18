import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulator {
    private ShortTermScheduler shortTermScheduler;
    private LongTermScheduler longTermScheduler;

    private JTextArea instructionTextArea;

    public Simulator() {
        // Create ShortTermScheduler and LongTermScheduler instances
        shortTermScheduler = new ShortTermScheduler();
        longTermScheduler = new LongTermScheduler();

        // Set LongTermScheduler instance to ShortTermScheduler
        shortTermScheduler.setLongTermScheduler(longTermScheduler);

        // Create UI components
        JFrame frame = new JFrame("Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton startButton = new JButton("Iniciar Simulação");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        JButton stopButton = new JButton("Parar Simulação");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        instructionTextArea = new JTextArea();
        instructionTextArea.setEditable(false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(instructionTextArea), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void startSimulation() {
        // Esvaziar as listas para não acumular
        shortTermScheduler.readyQueue.clear();
        shortTermScheduler.blockedQueue.clear();
        shortTermScheduler.unblockQueue.clear();
        // Iniciar a simulação em uma nova
        Thread simulationThread = new Thread(() -> {
            // Pass JTextArea to ShortTermScheduler
            shortTermScheduler.setInstructionTextArea(instructionTextArea);
            // Load programs
            shortTermScheduler.loadPrograms("program1.txt");
            shortTermScheduler.loadPrograms("program2.txt");
            shortTermScheduler.loadPrograms("program3.txt");
            // Start simulation
            shortTermScheduler.startSimulation();
        });
        simulationThread.start();
    }

    private void stopSimulation() {
        // Stop the simulation
        shortTermScheduler.stopSimulation();

        // Clear the instruction text area
        instructionTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Simulator();
            }
        });
    }
}
