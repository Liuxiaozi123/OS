package Instruction;

import lombok.Data;
@Data
public class Instruction {
    private int N_Runtime;//ָ������ʱ��
    private  int N_sRuntime;//ָ����Ҫ���е�ʱ�䳤��


    InstructionSet m_nInstructionID;//ָ�����ͱ��

    public Instruction (int N_sRuntime,InstructionSet m_nInstructionID) {
        this.N_sRuntime=N_sRuntime;
        this.m_nInstructionID=m_nInstructionID;

    }
    public void Update(){
        N_Runtime+=1;

    }
}
