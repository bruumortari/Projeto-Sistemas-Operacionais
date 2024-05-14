public class LongTermScheduler implements SubmissionInterface, InterSchedulerInterface {
    @Override
    public boolean submitJob(String fileName) {
        return true;
    }

    @Override
    public void displaySubmissionQueue() {

    }

    @Override
    public void addProcess(Process bcp) {

    }

    @Override
    public int getProcessLoad() {
        int i = 1;
        return i;
    }
}
