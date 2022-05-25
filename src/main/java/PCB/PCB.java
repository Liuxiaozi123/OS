package PCB;

import Instruction.Instruction;
import Instruction.InstructionSet;
import lombok.Data;

import java.util.LinkedList;
import java.util.UUID;

@Data
public class PCB {
    static int m_nRemainTime;//ʱ��Ƭ��С
    private String m_name;//��������

    public PCB() {
        m_UID=UUID.randomUUID().toString();
    }

    public PCB(String m_name) {
        this.m_name = m_name;
        m_UID=UUID.randomUUID().toString();
    }

    private String m_UID;//���̱�ʶ��
    private int m_runtime;//��ǰָ����������ʱ��
    private LinkedList<Instruction>m_nList;//ָ�򱾽��̵�ָ������
    private Instruction m_nRunIC;//ָ��ָ��Ҫ���е�ָ����������е�ָ��
    private PCB m_nextPCB;//���̶��е�ָ��

    public static int getM_nRemainTime() {
        return m_nRemainTime;
    }

    public static void setM_nRemainTime(int m_nRemainTime) {
        PCB.m_nRemainTime = m_nRemainTime;
    }

    public void loadPCBInfo(){
        this.m_nRunIC=getM_nRunIC();//��Ҫ���е�ָ��
        if(m_nRunIC!=null)
            this.m_runtime =m_nRunIC.getN_sRuntime();
        else{
            this.m_runtime=0;
        }

    }
    public Instruction getInstruction(){
        if(m_nList.size()>0)
            return m_nList.poll();
        else
            return null;
    }
}


