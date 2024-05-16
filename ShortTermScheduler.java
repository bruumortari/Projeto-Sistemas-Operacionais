public class ShortTermScheduler implements ControlInterface, InterSchedulerInterface {
    public void startSimulation() {
    /*
     * Utilizada para iniciar a simulação. A partir da invocação desta operação,
     * o simulador deve escolher um processo que esteja pronto para execução.
     * 
     * O simulador deve processar uma instrução por vez. Duas diferentes instruções são providas
     * pela linguagem de definição de programas simulados: execute, a qual representa um burst de CPU
     * (execução de um conjunto de instruções), e block, a qual representa um burst de CPU
     * seguido de uma instrução de E/S (bloqueio).
     * A execução de uma instrução execute deve ser realizada por meio de uma espera cronometrada.
     * 
     * Ao final de um quantum, o simulador deve proceder de acordo com a política de escalonamento implementada.
     * A execução de uma instrução block deve ser realizada por meio de uma espera cronometrada,
     * seguida do bloqueio do processo em execução por um determinado período (medido em quantum).
     * 
     * Ao final de cada quantum, o escalonador deverá verificar se algum dos processos bloqueados
     * tornou-se disponível, tornando-o pronto para execução.
     * 
     * A simulação termina quando não houver mais processos prontos ou bloqueados para execução.
     */

    }

    public void suspendSimulation() {
    /*
     * Utilizada para interromper temporariamente a simulação.
     * As informações sobre os processos em execução, prontos, bloqueados e terminados não devem ser alteradas.
     */

    }

    public void resumeSimulation() {
    /*
     * Utilizada para continuar a simulação a partir do ponto em que ela foi interrompida temporariamente.
     */

    }

    public void stopSimulation() {
    /*
     * Utilizada para encerrar definitivamente uma simulação.
     */
    }

    public void displayProcessQueues() {
    /*
     * Utilizada para solicitar a descrição das informações sobre todos os processos no escalonador
     * de curto prazo (processo em execução, processos prontos, processos bloqueados e processos terminados).
     */
    }

    public void addProcess(Process bcp) {
    /*
     * Utilizada para submeter um processo à execução.
     * Esta operação tem como parâmetro um objeto do tipo Process (a ser definido);
     */
    }

    public int getProcessLoad() {
    /*
     * Utilizada para obter a carga atual de processos no escalonador de curto prazo.
     */

        int i = 1;
        return i;
    }
}
