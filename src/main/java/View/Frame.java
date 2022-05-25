package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import Dispatch.Dispatch;
import Instruction.InstructionSet;
import PCB.*;
import Queue.Que;
import lombok.Data;
import lombok.SneakyThrows;

public class Frame extends JFrame{
	private JPanel contentPane;
	private JTextField textField;//ʱ��Ƭ��С�����
	private JTextField RunningProcess;//��ǰ���н��ȿ�
	private JTextArea ReadyQue;//�������п�
	private JTextArea InputWaiting;//����ȴ�����
	private JTextArea OutPutWaiting;//����ȴ�����
	private JTextArea Waiting;//�����ȴ�����
	private JButton btn_File;//��ȡ�ļ���ť
	private JButton btn_STop;//��ͣ���Ȱ�ť
	private JButton btn_Start;//��ʼ���Ȱ�ť
	private Listener listener=new Listener();


	public Frame() throws IOException {
		this.setTitle("\u65F6\u95F4\u7247\u8F6E\u8F6C\u8C03\u5EA6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		btn_File = new JButton("\u6253\u5F00\u6587\u4EF6");
		btn_File.setBounds(31, 20, 93, 23);
		panel.add(btn_File);
		btn_File.addActionListener(listener);

		btn_STop = new JButton("\u6682\u505C\u8C03\u5EA6");
		btn_STop.setBounds(160, 20, 93, 23);
		panel.add(btn_STop);
		btn_STop.addActionListener(listener);

		btn_Start=new JButton("\u5F00\u59CB\u8C03\u5EA6");
		btn_Start.setBounds(281,20,93,23);
		panel.add(btn_Start);
		btn_Start.addActionListener(listener);

		JLabel lblNewLabel = new JLabel("\u65F6\u95F4\u7247\u5927\u5C0F\uFF1A");
		lblNewLabel.setBounds(396, 24, 82, 15);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(488, 21, 151, 21);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u5F53\u524D\u8FD0\u884C\u8FDB\u5EA6\uFF1A");
		lblNewLabel_1.setBounds(31, 94, 138, 23);
		panel.add(lblNewLabel_1);

		RunningProcess = new JTextField();
		RunningProcess.setBounds(31, 120, 119, 23);
		panel.add(RunningProcess);
		RunningProcess.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("\u5C31\u7EEA\u961F\u5217\uFF1A");
		lblNewLabel_2.setBounds(31, 178, 72, 15);
		panel.add(lblNewLabel_2);

		ReadyQue = new JTextArea();
		ReadyQue.setBounds(31, 203, 183, 189);
		panel.add(ReadyQue);
		ReadyQue.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("\u8F93\u5165\u7B49\u5F85\u961F\u5217\uFF1A");
		lblNewLabel_3.setBounds(253, 178, 111, 15);
		panel.add(lblNewLabel_3);

		InputWaiting = new JTextArea();
		InputWaiting.setBounds(253, 207, 175, 182);
		panel.add(InputWaiting);
		InputWaiting.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("\u8F93\u51FA\u7B49\u5F85\u961F\u5217\uFF1A");
		lblNewLabel_4.setBounds(487, 178, 93, 15);
		panel.add(lblNewLabel_4);

		OutPutWaiting = new JTextArea();
		OutPutWaiting.setBounds(487, 203, 163, 189);
		panel.add(OutPutWaiting);
		OutPutWaiting.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("\u5176\u4ED6\u7B49\u5F85\u961F\u5217\uFF1A");
		lblNewLabel_5.setBounds(726, 178, 100, 15);
		panel.add(lblNewLabel_5);

		Waiting = new JTextArea();
		Waiting.setBounds(723, 207, 158, 182);
		panel.add(Waiting);
		Waiting.setColumns(10);
	}
		class Listener implements  ActionListener{
		Dispatch dispatch=Dispatch.getInstance();
		Que que =Que.getInstance();
			Thread thread;

			@SneakyThrows
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btn_File){
					btn_FileAction();


				}
				else if(e.getSource()==btn_Start){
					Runnable runnable=new Runnable() {
						@SneakyThrows
						@Override
						public void run() {
							int m=1;
							while (m>0){
								m=que.getM_InputWaiting().size()+que.getM_BackReady().size()+que.getM_nPReady().size()+que.getM_OUTPutWaiting().size();
							btn_RunAction();
							Thread.sleep(PCB.getM_nRemainTime()*10);
						}
						}
					};
					thread=new Thread(runnable);
					thread.start();
				}
				else if (e.getSource()==btn_STop){
					thread.interrupt();
				}
			}


			public void write(JTextArea text,LinkedList<PCB> list){
				text.setText("");
				if(list.size()>0){
					String temp=new String();
					for(PCB pcb:list){
						temp=temp+pcb.getM_name()+"\n";
					}
					text.setText(temp);
				}
		}
		public void showRunningProcess(PCB pcb){
				if(pcb!=null&&pcb.getM_nRunIC()!=null&&pcb.getM_nRunIC().getM_nInstructionID()==InstructionSet.CALC)
					RunningProcess.setText(pcb.getM_name());
		}
			@SneakyThrows
			public void btn_RunAction(){
				PCB pcb=null;
				while(que.getM_nPReady().size()>0)
				{
					pcb=dispatch.getPCBFromQue("R");
					dispatch.toQue(pcb);
					if(pcb.getM_nRunIC()!=null&&pcb.getM_nRunIC().getM_nInstructionID()==InstructionSet.CALC)
						break;
				}

				write(ReadyQue,(LinkedList<PCB>)que.getM_nPReady());
				write(InputWaiting,(LinkedList<PCB>)que.getM_InputWaiting());
				write(OutPutWaiting,(LinkedList<PCB>)que.getM_OUTPutWaiting());
				write(Waiting,(LinkedList<PCB>)que.getM_BackReady());



				for (PCB pcb1 : que.getM_nPReady()) {
					System.out.println("��������"+pcb1.getM_name()+pcb1.getM_nRunIC());
				}
				for (PCB pcb1 : que.getM_InputWaiting()) {
					System.out.println("����ȴ�����"+pcb1.getM_name()+pcb1.getM_nRunIC());
				}
				if(pcb!=null||que.getM_nPReady().size()>0)
				dispatch.treadQue("R",pcb);
				dispatch.treadQue("I",null);
				dispatch.treadQue("O",pcb);
				dispatch.treadQue("W",null);

				showRunningProcess(pcb);

			}


			public void btn_FileAction() throws IOException {
				JFileChooser chooser=new JFileChooser();
				String defaultDirector="D:\\";//Ĭ��Ŀ¼
				String defaultFilename="test.txt";//Ĭ���ļ���
				chooser.setCurrentDirectory(new File(defaultDirector));//����Ĭ��Ŀ¼
				chooser.setSelectedFile(new File(defaultFilename));//����Ĭ���ļ���
				int option=chooser.showOpenDialog(null);
				if(option==JFileChooser.APPROVE_OPTION){
					System.out.println(chooser.getSelectedFile().getName());
					File file=chooser.getSelectedFile();
					String text=textField.getText();
					int time=0;
					if(text.equals("")){
						JOptionPane.showMessageDialog(null,"����","δ����ʱ��Ƭ��С",JOptionPane.ERROR_MESSAGE);
						return;
					}
					else{
						try{
							time=Integer.parseInt(text);
						}catch (Exception e){
							JOptionPane.showMessageDialog(null,"����","�����쳣",JOptionPane.ERROR_MESSAGE);

						}
					}
					PCB.setM_nRemainTime(time);//����ʱ��Ƭ��С
					LinkedList<PCB>PCBList=PCBUtil.getPCBFromFile(file);
					write(ReadyQue,PCBList);
					for (PCB pcb : PCBList) {
						if(pcb!=null)
						dispatch.appendQue(pcb,"R");
					}

				}

			}

		}






	}


