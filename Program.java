import java.util.ArrayList;
import java.util.List;

public class Program {
    private String programName;
    private List<String> instructions;
    private int executeCount;
    private int blockCount;

    public Program(String programName) {
        this.programName = programName;
        this.instructions = new ArrayList<>();
        this.executeCount = 0;
        this.blockCount = 0;
    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
        if (instruction.equals("execute")) {
            executeCount++;
        } else if (instruction.startsWith("block")) {
            blockCount++;
        }
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public int getExecuteCount() {
        return executeCount;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public String getProgramName() {
        return programName;
    }
}
