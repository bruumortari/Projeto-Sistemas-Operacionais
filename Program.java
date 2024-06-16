import java.util.ArrayList;
import java.util.List;

public class Program {
    private String fileName;
    private List<String> instructions;

    public Program(String fileName) {
        this.fileName = fileName;
        this.instructions = new ArrayList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }
}
