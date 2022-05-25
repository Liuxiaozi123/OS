package Queue;

import Instruction.Instruction;
import Instruction.InstructionSet;
import PCB.PCB;
import lombok.Data;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
@Data
public class Que {
    private Deque<PCB> m_nPReady;//就绪队列
    private Deque<PCB> m_BackReady;//后备就绪队列
    private Deque<PCB> m_InputWaiting;//输入等待队列
    private Deque<PCB> m_OUTPutWaiting;//输出等待队列
    private static Que que = new Que();//单例模式

    private Que() {
        //初始化队列
        m_nPReady = new LinkedList<>();
        m_BackReady = new LinkedList<>();
        m_InputWaiting = new LinkedList<>();
        m_OUTPutWaiting = new LinkedList<>();
    }

    public static Que getInstance() {
        return que;
    }


    public PCB removePCB(String m) {
        PCB pcb = new PCB();
        switch (m) {
            case "R":
                pcb = m_nPReady.poll();
                break;
            case "I":
                pcb = m_InputWaiting.poll();
                break;
            case "O":
                pcb = m_OUTPutWaiting.poll();
                break;
            case "W":
                pcb = m_BackReady.poll();
                break;
        }
        return pcb;
    }

    public void addPCBToQue(String m, PCB pcb) {
        switch (m) {
            case "R":
                m_nPReady.offer(pcb);
                break;
            case "I":
                m_InputWaiting.offer(pcb);
                break;
            case "O":
                m_OUTPutWaiting.offer(pcb);
                break;
            case "W":
                m_BackReady.offer(pcb);
                break;
        }


    }
}




